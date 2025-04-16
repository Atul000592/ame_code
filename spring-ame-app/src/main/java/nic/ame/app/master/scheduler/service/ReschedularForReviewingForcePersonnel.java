package nic.ame.app.master.scheduler.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.master.medical.model.AmeReviewCandidatesList;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.AmeStatusCode;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.repository.AlertAndNotificationRepository;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.ApplicationStateDescriptionRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.AmeStatusCodeRepository;

@Component
@Transactional
@EnableTransactionManagement
public class ReschedularForReviewingForcePersonnel {

	@Autowired
	private TTAppointmentAmeRepo appointmentAmeRepo;

	@Autowired
	private ForcePersonnelService forcePersonnelService;

	@Autowired
	private AlertAndNotificationRepository alertAndNotificationRepository;

	@Autowired
	private ApplicationStateDescriptionRepository applicationStateDescriptionRepository;

	@Autowired
	private RefMedicalExamTypeRepo medicalExamTypeRepo;

	@Autowired
	private AmeReviewCandidatesListRepository ameReviewCandidatesListRepository;

	@Autowired
	private AmeStatusCodeRepository ameStatusCodeRepository;

	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
    
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	private ThreadPoolTaskScheduler taskScheduler;

	private ScheduledFuture<?> scheduledFuture;

	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	Logger logger = LoggerFactory.getLogger(ReappointmentSchedular.class);

	@PostConstruct
	public void startSchedulerOnStartup() {
		cronScheduler();
		scheduleCronTask("0/10 * * * * ?");
	}

	public void cronScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(1);
		threadPoolTaskScheduler.initialize();
		this.taskScheduler = threadPoolTaskScheduler;
	}

	private void scheduleCronTask(String cronExpressionStr) {

		stopScheduler();

		scheduledFuture = taskScheduler.schedule(this::reviewReschedularHandler, new CronTrigger(cronExpressionStr));
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

	@SuppressWarnings("deprecation")
	@Transactional
	private void reviewReschedularHandler() {
		logger.info(
				"\n------------------------------------------------------------------------------------------------------------------------");

		int pageNumber = 0;
		final int BATCH_SIZE = 2;

		Page<AmeReviewCandidatesList> page = null;
		do {
			PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE);
			page = ameReviewCandidatesListRepository.getForcePersonnelListForReview(pageRequest);
			System.out.println(page);
			logger.info("number of Elements fetched: " + page.getNumberOfElements());
			if (page.getNumberOfElements() != 0) {
				if (page.hasContent()) {
					for (AmeReviewCandidatesList ameReviewCandidatesListItr : page.getContent()) {
						 Optional<AmeStatusCode> ameStatusCode = ameStatusCodeRepository.getByCode(0);
							ameReviewCandidatesListItr.setAmeStatusCode(ameStatusCode.get());
							ameReviewCandidatesListItr.setRescheduleFlag(false);

							List<TTAppointmentAme> appointmentAmeObject = appointmentAmeRepo
									.getByBoardIdAndForcePersonalIdAndDeclarationYearAndIsAppointmentValid(
											ameReviewCandidatesListItr.getBoardId(),
											ameReviewCandidatesListItr.getCandidateForcePersonalId(),
											ameReviewCandidatesListItr.getYear(),1);
							for (TTAppointmentAme appointmentAmeObjectItr : appointmentAmeObject) {
								appointmentAmeObjectItr.setIsAppointmentValid(6);
								appointmentAmeObjectItr.setRescheduleFlag(true);
								
				Optional<AmeDeclarationIndividualModel> ameDeclarationIndividualModelOptional = ameDeclarationRepository.findByAmeId(ameReviewCandidatesListItr.getAmeId());
				//Optional<AmeDeclarationIndividualDetails> ameDeclarationIndividualDetailsOptional = ameDeclarationIndividualDetailsRepo.findByAmeId(ameReviewCandidatesListItr.getAmeId());
       if(ameDeclarationIndividualModelOptional.isPresent()) {
	     AmeDeclarationIndividualModel ameDeclarationIndividualModel=ameDeclarationIndividualModelOptional.get();
	     ameDeclarationIndividualModel.setDeclarationStatusValid(false);
	     ameDeclarationRepository.save(ameDeclarationIndividualModel);
        }
				
				Optional<MedicalBoardIndividualMapping> medicalBoardIndividualMapping = medicalBoardIndividualMappingRepo
										.findByBoardIdAndForcePersonalId(appointmentAmeObjectItr.getBoardId(),
												appointmentAmeObjectItr.getForcePersonalId());
								if (!medicalBoardIndividualMapping.isPresent()) {
									logger.info("Have no data......!");
								} else {

									logger.info("Have......... data......!");
									MedicalBoardIndividualMapping medicalBoardIndividualMappingOp = new MedicalBoardIndividualMapping();
									medicalBoardIndividualMappingOp = medicalBoardIndividualMapping.get();
									medicalBoardIndividualMappingOp.setAppointmentStatus(1);
									medicalBoardIndividualMappingRepo.save(medicalBoardIndividualMappingOp);
								}
								appointmentAmeRepo.save(appointmentAmeObjectItr);
							}
							ameReviewCandidatesListRepository.save(ameReviewCandidatesListItr);

						}

					}
					pageNumber++;
				}
			

			else {
				try {
					cleanup();

					updateCron("0 */2 * * * *");
					logger.info("Schedular Stop until next schedule.");

				} catch (Exception e) {

					logger.info("Exception while stopping scheduler until next schedule.");
					e.printStackTrace();
				}
			}
		} while (page.hasNext());

	}

}
