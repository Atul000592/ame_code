package nic.ame.app.master.scheduler.service;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.admin.model.RefMedicalExamType;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.AlertAndNotification;
import nic.ame.app.master.model.ApplicationStateDescription;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.repository.AlertAndNotificationRepository;
import nic.ame.app.master.repository.ApplicationStateDescriptionRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.master.util.GetIpAddressClient;

@Component
@Transactional
@EnableTransactionManagement
public class ReappointmentSchedular {

	Logger logger = LoggerFactory.getLogger(ReappointmentSchedular.class);

	@Autowired
	private TTAppointmentAmeRepo appointmentAmeRepo;

	@Autowired
	private ForcePersonnelService forcePersonnelService;

	@Autowired
	private AlertAndNotificationRepository alertAndNotificationRepository;

	@Autowired
	MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;

	@Autowired
	private ApplicationStateDescriptionRepository applicationStateDescriptionRepository;

	@Autowired
	private RefMedicalExamTypeRepo medicalExamTypeRepo;

	private ThreadPoolTaskScheduler taskScheduler;
	private ScheduledFuture<?> scheduledFuture;
	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	@PostConstruct
	public void startSchedulerOnStartup() {
		cronScheduler();
		scheduleCronTask("0 0 */2 * * ?");
	}

	public void cronScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(1);
		threadPoolTaskScheduler.initialize();
		this.taskScheduler = threadPoolTaskScheduler;
	}

	private void scheduleCronTask(String cronExpressionStr) {

		stopScheduler();

		scheduledFuture = taskScheduler.schedule(this::perform, new CronTrigger(cronExpressionStr));
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	private void perform() {
		logger.info(
				"\n------------------------------------------------------------------------------------------------------------------------");
		/*
		 * ServletRequestAttributes servletRequestAttributes=
		 * (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		 * HttpServletRequest httpServletRequest= servletRequestAttributes.getRequest();
		 */
		int pageNumber = 0;
		final int BATCH_SIZE = 2;

		Page<TTAppointmentAme> page;
		do {
			PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE);
			page = appointmentAmeRepo.findAllForcePersonnel(pageRequest);

			logger.info("number of Elements fetched :" + page.getNumberOfElements());
			if (page.getNumberOfElements() != 0) {
				if (page.hasContent()) {

					for (TTAppointmentAme appointmentAmeObject : page.getContent()) {
						appointmentAmeObject.setIsAppointmentValid(5);
						appointmentAmeObject.setRescheduleFlag(true);
						// ------------------------------ Individual mapped to board member
						// ------------------------------ //

						// medicalBoardIndividualMappingRepo.updateAppointmentStatus(0,appointmentAmeObject.getForcePersonalId()
						// , appointmentAmeObject.getBoardId());

						Optional<MedicalBoardIndividualMapping> medicalBoardIndividualMapping = medicalBoardIndividualMappingRepo
								.findByBoardIdAndForcePersonalId(appointmentAmeObject.getBoardId(),
										appointmentAmeObject.getForcePersonalId());
						if (!medicalBoardIndividualMapping.isPresent()) {
							logger.info("Have no data......!");
						} else {

							logger.info("Have......... data......!");
							MedicalBoardIndividualMapping medicalBoardIndividualMappingOp = new MedicalBoardIndividualMapping();
							medicalBoardIndividualMappingOp = medicalBoardIndividualMapping.get();
							medicalBoardIndividualMappingOp.setAppointmentStatus(5);
							medicalBoardIndividualMappingRepo.save(medicalBoardIndividualMappingOp);
						}

						// ------------------------------ Alert and Notification
						// ------------------------------ //

						/*
						 * AlertAndNotification alertAndNotification= new AlertAndNotification();
						 * ForcePersonnelDto candidateForcePersonnel =
						 * forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(
						 * appointmentAmeObject.getForcePersonalId()).get();
						 * Optional<ApplicationStateDescription>
						 * stateDescription=applicationStateDescriptionRepository.findById(55);
						 * logger.info(">>>>>>>>>>>>>"+stateDescription.get());
						 * ApplicationStateDescription applicationStateDescription=new
						 * ApplicationStateDescription();
						 * applicationStateDescription=stateDescription.get();
						 * alertAndNotification.setApplicationStateDescription(
						 * applicationStateDescription); alertAndNotification.
						 * setMessage("Appointment canceled due to candidate`s absence");
						 * alertAndNotification.setReceiverForcePersonnelId(candidateForcePersonnel.
						 * getForcePersonalId());
						 * alertAndNotification.setReceiverEmail(candidateForcePersonnel.getEmailId());
						 * alertAndNotification.setReceiverMobileNumber(candidateForcePersonnel.
						 * getMobileNumber()); Optional<RefMedicalExamType>
						 * examType=medicalExamTypeRepo.findById(1L);
						 * 
						 * alertAndNotification.setExamType(examType.get());
						 * 
						 * alertAndNotification.setSendByForcePersonnelId("Auto-generated"); //
						 * alertAndNotification.setSendFrom(GetIpAddressClient.
						 * getIpAddressFromHeaderClient(httpServletRequest));
						 * alertAndNotification.setSendBy("Auto-generated");
						 * alertAndNotification.setSendOn(Calendar.getInstance().getTime());
						 * 
						 * // alertAndNotification.setLastModifiedFrom(GetIpAddressClient.
						 * getIpAddressFromHeaderClient(httpServletRequest)); //
						 * alertAndNotification.setLastModifiedBy(GetIpAddressClient.
						 * getIpAddressFromHeaderClient(httpServletRequest));
						 * alertAndNotification.setLastModifiedOn(Calendar.getInstance().getTime());
						 * 
						 * alertAndNotification.setIsSuccessSMS(1);
						 * alertAndNotification.setIsSuccessSMSOn(Calendar.getInstance().getTime());
						 * alertAndNotification.setIsSuccessEmail(null);
						 * alertAndNotification.setIsSuccessEmailOn(Calendar.getInstance().getTime());
						 * 
						 * alertAndNotificationRepository.save(alertAndNotification);
						 */

					}
					appointmentAmeRepo.saveAll(page.getContent());
				}
				pageNumber++;
			} else {
				try {
					cleanup();

					updateCron("0 */5 * * * *");
					logger.info("Schedular Stop until next schedule.");

				} catch (Exception e) {

					logger.info("Exception while stopping scheduler until next schedule.");
					e.printStackTrace();
				}
			}

		} while (page.hasNext());
		logger.info("Records checked and updated successfully at " + LocalTime.now());
		logger.info(
				"\n------------------------------------------------------------------------------------------------------------------------");
	}

	public void updateCron(String newCronExpression) {

		logger.info("Updated Cron time at " + newCronExpression);
		cronScheduler();
		scheduleCronTask(newCronExpression);
	}

	@PreDestroy
	public void cleanup() {
		stopScheduler();

		if (executorService != null) {
			executorService.shutdown();
		}
		if (taskScheduler != null) {
			taskScheduler.shutdown();
		}
	}

	private void stopScheduler() {

		if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
			scheduledFuture.cancel(false);
		}
	}

}
