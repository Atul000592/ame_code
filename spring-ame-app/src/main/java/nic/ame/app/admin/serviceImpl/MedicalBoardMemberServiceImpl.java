package nic.ame.app.admin.serviceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.model.RefRoleMedical;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeApprovalProcess;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeApprovalProcessRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;

import nic.ame.app.master.service.AmeAppointmentAndDeclarationService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.constant.CommonConstant;

@Service
@RequiredArgsConstructor
public class MedicalBoardMemberServiceImpl implements MedicalBoardMemberService {

	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;

	@Autowired
	private AmeAppointmentAndDeclarationService ameAppointmentAndDeclarationService;

	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;

	@Autowired
	private MedicalBoardRepo medicalBoardRepo;

	@Autowired
	private AmeParametersRepository ameParametersRepository;

	@Autowired
	private RefRoleMedicalRepo refRoleMedicalRepo;

	@Autowired
	private RefForceService RefForceService;

	@Autowired
	private RankRepo rankRepo;

	@Autowired
	private AmeApprovalProcessRepository ameApprovalProcessRepository;

	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;

	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepository;
	
	
	@Autowired
	private ForcePersonalService forcePersonalService;

	@Override
	public List<String> groupbyCommitteeId() {

		return null;
	}

	@Override
	public List<MedicalBoardMember> getBoardMembers(String forcePersonalId) {

		List<MedicalBoardMember> boardMembers = medicalBoardMemberRepo.findByForcePersonalId(forcePersonalId);

		return boardMembers;
	}

	@Override
	public Optional<MedicalBoardMember> getBoardMemberForNgoPersonal(String roleName, String forceNo, String unit) {

		Optional<MedicalBoardMember> optional = medicalBoardMemberRepo.findNameByboardAndUnit(roleName, forceNo, unit);

		if (!optional.isEmpty()) {
			return optional;
		}
		return Optional.empty();
	}

	@Override
	public List<MedicalBoardMemberDto> listOfBoardByForcePersonalId(String forcePersonnel) {
		List<Object> stringsList = medicalBoardMemberRepo.findBoardIdByForcepersonalId(forcePersonnel,
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));
		List<MedicalBoardMemberDto> boardMemberDtos = new ArrayList<>();

		if (stringsList.isEmpty()) {
			return boardMemberDtos;
		}
		Iterator<Object> iterator = stringsList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			MedicalBoardMemberDto medicalBoardMemberDto = new MedicalBoardMemberDto();

			if (obj[0] != null) {
				medicalBoardMemberDto.setBoardId(String.valueOf(obj[0]).trim());
				medicalBoardMemberDto.setAppointmentCompletedCount(
						ameAppointmentAndDeclarationService.appointmentCompletedCount(String.valueOf(obj[0]).trim()));
				medicalBoardMemberDto.setAppointmentPendingCount(
						ameAppointmentAndDeclarationService.appointmentPendingCount(String.valueOf(obj[0]).trim()));
			}
			if (obj[1] != null) {
				medicalBoardMemberDto.setRoleName(String.valueOf(obj[1]).trim());

			}
			if (obj[2] != null) {

				medicalBoardMemberDto.setForceName(
						RefForceService.getForceNameByForceId(Integer.parseInt(String.valueOf(obj[2]).trim())));
			}
			if (obj[3] != null) {

				medicalBoardMemberDto.setUsedFor(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {

				medicalBoardMemberDto.setPlace(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				medicalBoardMemberDto.setCreatedOn((Date) (obj[5]));
			}
			if (obj[6] != null) {
				medicalBoardMemberDto.setForcePersonalId(String.valueOf(obj[6]).trim());
			}
			if (obj[7] != null) {
				medicalBoardMemberDto.setName(String.valueOf(obj[7]).trim());
			}

			boardMemberDtos.add(medicalBoardMemberDto);

		}

		return boardMemberDtos;
	}

	@Override
	public MedicalBoardDetailDto medicalBoardDetailByBoardId(String boardId) {
		List<Object> stringsList = medicalBoardMemberRepo.getBoardDetailByBoardId(boardId);
		List<MedicalBoardDetailDto> boardDetailDtos = new ArrayList<>();
		MedicalBoardDetailDto medicalBoardMemberDto = new MedicalBoardDetailDto();
		if (stringsList.isEmpty()) {
			return medicalBoardMemberDto;
		}
		Iterator<Object> iterator = stringsList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();

			if (obj[0] != null) {
				medicalBoardMemberDto.setBoardId(String.valueOf(obj[0]).trim());
			}

			if (obj[1] != null) {
				medicalBoardMemberDto.setRoleName(String.valueOf(obj[1]).trim());
			}

			if (obj[2] != null) {
				if (Integer.parseInt(String.valueOf(obj[2]).trim()) == 1) {
					medicalBoardMemberDto.setStatus("ACTIVE");
				} else {
					medicalBoardMemberDto.setStatus("INACTIVE");
				}
			}

			if (obj[3] != null) {
				medicalBoardMemberDto.setBoardYear(String.valueOf(obj[3]).trim());
			}

			if (obj[4] != null) {
				medicalBoardMemberDto.setPlace(String.valueOf(obj[4]).trim());
			}

			if (obj[5] != null) {
				medicalBoardMemberDto.setForceName(String.valueOf(obj[5]).trim());
			}
			if (obj[6] != null) {
				medicalBoardMemberDto.setCreatedBy(String.valueOf(obj[6]).trim());
			}
			if (obj[7] != null) {
				medicalBoardMemberDto.setCreatedOn((Date) obj[7]);
			}
			if (obj[8] != null) {
				medicalBoardMemberDto.setUseFor(String.valueOf(obj[8]).trim());
			}
			if (obj[9] != null) {
				if (Integer.parseInt(String.valueOf(obj[9]).trim()) == 1) {
					medicalBoardMemberDto.setgFlag("Gazetted");
				} else {
					medicalBoardMemberDto.setgFlag("Non-Gazetted");
				}
			}

			boardDetailDtos.add(medicalBoardMemberDto);

		}
		return medicalBoardMemberDto;

	}

	@Override
	public MedicalBoardMemberDto findCreatedByDetailsByBoardId(String boardId) {
		List<Object> objects = forcePersonalRepository.getCreatedByDetailsByBoardId(boardId.trim());
		Iterator<Object> it = objects.iterator();
		MedicalBoardMemberDto boardMemberDto = new MedicalBoardMemberDto();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			if (obj[0] != null) {
				boardMemberDto.setName(String.valueOf(obj[0]));
			}
			if (obj[1] != null) {
				boardMemberDto.setRank(String.valueOf(obj[1]));
			}
			if (obj[2] != null) {
				boardMemberDto.setForceName(String.valueOf(obj[2]));
			}
			if (obj[3] != null) {
				boardMemberDto.setUnitName(String.valueOf(obj[3]));
			}
			if (obj[4] != null) {
				boardMemberDto.setIrlaNumber(String.valueOf(obj[4]));
			}

		}

		return boardMemberDto;
	}

	@Override
	public MedicalBoardDetailDto findBoardDetailsByBoardId(String boardId) {
		List<Object> objects = medicalBoardRepo.findByBoardIdDto(boardId.trim());
		Iterator<Object> it = objects.iterator();
		MedicalBoardDetailDto boardMemberDto = new MedicalBoardDetailDto();

		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			if (obj[0] != null) {
				boardMemberDto.setBoardId(String.valueOf(obj[0]));
			}
			if (obj[1] != null) {
				boardMemberDto.setForceName(String.valueOf(obj[1]));
			}
			if (obj[2] != null) {
				boardMemberDto.setPlace(String.valueOf(obj[2]));
			}
			if (obj[3] != null) {
				boardMemberDto.setUseFor(String.valueOf(obj[3]));
			}
			if (obj[4] != null) {
				boardMemberDto.setCreatedOn((Date) obj[4]);
			}

		}
		return boardMemberDto;
	}

	@Override
	public List<MedicalBoardMemberDto> findBoardDetailsListByLoginForcePersonal(String loginInForcePersonalId) {

		List<Object> ListOfObject = medicalBoardRepo.getMedicalBoardByCreatedBy(loginInForcePersonalId);

		List<MedicalBoardMemberDto> medicalBoardDetailsListByForcePersonal = new ArrayList<>();

		medicalBoardDetailsListByForcePersonal = ListOfObject.stream().map(MedicalBoardMemberServiceImpl::mapDetails)
				.collect(Collectors.toList());

		return medicalBoardDetailsListByForcePersonal;
	}

	public static MedicalBoardMemberDto mapDetails(Object object) {
		MedicalBoardMemberDto boardMemberDto = new MedicalBoardMemberDto();
		Object[] obj = (Object[]) object;
		if (obj[0] != null) {
			boardMemberDto.setForceName(String.valueOf(obj[0]));
		}
		if (obj[1] != null) {
			boardMemberDto.setPlace(String.valueOf(obj[1]));
		}

		if (obj[2] != null) {
			boardMemberDto.setBoardId(String.valueOf(obj[2]));
		}

		if (obj[3] != null) {
			boardMemberDto.setBoardYear(String.valueOf(obj[3]));
		}
		if (obj[4] != null) {
			boardMemberDto.setCreatedOn((Date) obj[4]);
		}
		if (obj[5] != null) {
			boardMemberDto.setToDate((Date) obj[5]);
		}
		if (obj[6] != null) {
			boardMemberDto.setFromDate((Date) obj[6]);
		}
		if (obj[7] != null) {

			boardMemberDto.setgFlag(Integer.parseInt(String.valueOf(obj[7])));
			/*
			 * if(Integer.parseInt(String.valueOf(obj[7]))==0) {
			 * boardMemberDto.setGazettedFlagValue("Non-gazetted"); }else {
			 * boardMemberDto.setGazettedFlagValue("Gazetted"); }
			 */
		}
		if (obj[8] != null) {
			boardMemberDto.setUsedFor(String.valueOf(obj[8]));
		}

		return boardMemberDto;
	}

	@Override
	public Map<String, Long> findReserveByBoardIdAndRoleName(String boardId, String roleNameId) {

		List<MedicalBoardMember> boardMembers = medicalBoardMemberRepo.getByBoardId(boardId);

		Map<String, Long> map = new HashMap<>();

		Optional<Long> id = refRoleMedicalRepo.findAll().stream().filter(s -> s.getRoleName().equals(roleNameId))
				.map(s -> s.getId()).findAny();

		Optional<Long> reserveRoleCode = refRoleMedicalRepo.findAll().stream()
				.filter(s -> s.getReserverForId() == id.get()).map(s -> s.getId()).findAny();

		boardMembers = boardMembers.stream().filter(s -> s.getAlternateMedicalRoleId() == (reserveRoleCode.get()))
				.filter(s -> s.getResereveFlag() != 0).collect(Collectors.toList());
		if (!boardMembers.isEmpty()) {
			map.put("reserveRoleCode", reserveRoleCode.get());
			map.put("roleCode", id.get());
		}
		return map;
	}

	@Override
	public List<MedicalBoardMemberDto> getlistOfReservePersonalByBoardIdAndAlternateMedicalRoleId(String boardId,
			String roleId) {
		List<Object> objects = medicalBoardMemberRepo
				.listOfReservePersonalByBoardIdAndAlternateMedicalRoleId(boardId.trim(), roleId.trim());
		List<MedicalBoardMemberDto> medicalBoardMemberDtos = objects.stream()
				.map(MedicalBoardMemberServiceImpl::reserverListDetails).collect(Collectors.toList());

		return medicalBoardMemberDtos;
	}

	public static MedicalBoardMemberDto reserverListDetails(Object object) {
		MedicalBoardMemberDto boardMemberDto = new MedicalBoardMemberDto();
		Object[] obj = (Object[]) object;
		if (obj[0] != null) {
			boardMemberDto.setForcePersonalId(String.valueOf(obj[0]));
		}
		if (obj[1] != null) {
			boardMemberDto.setIrlaNumber(String.valueOf(obj[1]));
		}

		if (obj[2] != null) {
			boardMemberDto.setName(String.valueOf(obj[2]));
		}

		if (obj[3] != null) {
			boardMemberDto.setDesignation(String.valueOf(obj[3]));
		}
		if (obj[4] != null) {
			if (Integer.parseInt(String.valueOf(obj[4])) == CommonConstant.USER_ROLE_STATUS_ACTIVE) {
				boardMemberDto.setRoleStatus("ACTIVE");
				boardMemberDto.setStatus(Integer.parseInt(String.valueOf(obj[4])));
			} else {
				boardMemberDto.setRoleStatus("INACTIVE");
				boardMemberDto.setStatus(Integer.parseInt(String.valueOf(obj[4])));
			}
		}
		if (obj[5] != null) {
			boardMemberDto.setBoardId(String.valueOf(obj[5]));
		}
		if (obj[6] != null) {
			boardMemberDto.setForceName(String.valueOf(obj[6]));
		}
		if (obj[7] != null) {

			boardMemberDto.setUnitName(String.valueOf(obj[7]));

		}
		if (obj[8] != null) {
			boardMemberDto.setReserveFlag(Integer.parseInt(String.valueOf(obj[8])));
		}

		return boardMemberDto;
	}

	@Override
	@Transactional
	public void updateTransferOFMedicalRoleByBoardMember(MedicalBoardMemberDto medicalBoardMemberDto) {

		// Active the role first
		String roleTransferBy = medicalBoardMemberDto.getCreatedBy().trim();
		String roleTransferTo = medicalBoardMemberDto.getForcePersonalId().trim();
		String boardId = medicalBoardMemberDto.getBoardId().trim();
		String roleId = medicalBoardMemberDto.getRoleName();
		String changeReason, remark = null;

		Optional<MedicalBoardMember> optional = medicalBoardMemberRepo
				.findByForcePersonalAndBoardIdToTransferRoleTo(roleTransferTo, boardId, roleId);

		if (optional.isPresent()) {

			Class<?> medicalBoardMember = MedicalBoardMember.class;
			try {
				MedicalBoardMember medicalBoardMemberObject = (MedicalBoardMember) medicalBoardMember.newInstance();
				medicalBoardMemberObject = optional.get();
				medicalBoardMemberObject.setChangeReason(medicalBoardMemberDto.getRemark());
				medicalBoardMemberObject.setChangeStatusFromDate(new Date());
				medicalBoardMemberObject.setStatusCode(1);
				medicalBoardMemberObject.setModifiedBy("Board Detailing Authority");
				medicalBoardMemberObject.setLinkMember(roleTransferBy);
				MedicalBoardMember updatedMedicalBoardMember = medicalBoardMemberRepo.save(medicalBoardMemberObject);

				if (updatedMedicalBoardMember.getBoardId() != null) {
					Optional<MedicalBoardMember> transferByOptional = medicalBoardMemberRepo
							.findByForcePersonalAndBoardIdToTransferRoleTo(roleTransferBy, boardId, roleId);
					if (transferByOptional.isPresent()) {

						try {
							Constructor<?> constructor = medicalBoardMember.getDeclaredConstructor();
							constructor.setAccessible(true);
							try {
								MedicalBoardMember medicalBoardMemberTransferObject = (MedicalBoardMember) constructor
										.newInstance();

								medicalBoardMemberTransferObject = transferByOptional.get();
								medicalBoardMemberTransferObject.setStatusCode(0);
								medicalBoardMemberTransferObject.setChangeReason("transfer of individual");
								medicalBoardMemberRepo.save(medicalBoardMemberTransferObject);

							} catch (IllegalArgumentException | InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} catch (NoSuchMethodException | SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}

			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<MedicalBoardMemberDto> getListOfAllBoardMembersByBoardId(String boardId) {
		List<Object> MedicalBoardMemberDto = medicalBoardMemberRepo.getListOfAllBoardMembersByBoardId(boardId);
		List<MedicalBoardMemberDto> medicalBoardMemberDtos = new ArrayList<>();

		Iterator<Object> itr = MedicalBoardMemberDto.iterator();
		while (itr.hasNext()) {
			MedicalBoardMemberDto boardMemberDto = new MedicalBoardMemberDto();
			Object[] obj = (Object[]) itr.next();
			if (obj[0] != null) {
				boardMemberDto.setForceCodeName(String.valueOf(obj[0]).trim());

			}
			if (obj[1] != null) {
				boardMemberDto.setForceId(String.valueOf(obj[1]).trim());

			}
			if (obj[2] != null) {
				boardMemberDto.setName(String.valueOf(obj[2]).trim());

			}
			if (obj[3] != null) {
				boardMemberDto.setDesignation(String.valueOf(obj[3]).trim());

			}
			if (obj[4] != null) {
				boardMemberDto.setRoleName(String.valueOf(obj[4]).trim());

			}
			if (obj[5] != null) {
				boardMemberDto.setForcePersonalId(String.valueOf(obj[5]).trim());

			}
			if (obj[6] != null) {
				boardMemberDto.setBoardId(String.valueOf(obj[6]).trim());

			}

			medicalBoardMemberDtos.add(boardMemberDto);

		}
		return medicalBoardMemberDtos;
	}

	@Override
	public MedicalBoardDetailDto findMedicalBoardDetailsByCreatedByAndBoardId(String boardId) {

		List<Object> list = medicalBoardMemberRepo.getBoardDetailsByCreatedByAndBoardId(boardId);
		MedicalBoardDetailDto boardMemberDto = new MedicalBoardDetailDto();
		Iterator<Object> itr = list.iterator();
		while (itr.hasNext()) {

			Object[] obj = (Object[]) itr.next();
			if (obj[0] != null) {
				boardMemberDto.setBoardId(String.valueOf(obj[0]).trim());

			}
			if (obj[1] != null) {
				boardMemberDto.setBoardYear(String.valueOf(obj[1]).trim());

			}
			if (obj[2] != null) {
				boardMemberDto.setCreatedBy(String.valueOf(obj[2]).trim());

			}
			if (obj[3] != null) {
				boardMemberDto.setName(String.valueOf(obj[3]).trim());

			}
			if (obj[4] != null) {
				boardMemberDto.setCreatedByRank(
						rankRepo.findById(Integer.parseInt(String.valueOf(obj[4]).trim())).get().getRankFullName());

			}
			if (obj[5] != null) {
				boardMemberDto.setCreatedOn((Date) (obj[5]));

			}
			if (obj[6] != null) {
				boardMemberDto.setUseFor(String.valueOf(obj[6]).trim());

			}
			if (obj[7] != null) {
				boardMemberDto.setCreatedForForce(String.valueOf(obj[7]).trim());

			}
			if (obj[8] != null) {
				boardMemberDto.setPlace(String.valueOf(obj[8]).trim());

			}
			if (obj[9] != null) {
				if (Integer.parseInt(String.valueOf(obj[9]).trim()) == 1) {
					boardMemberDto.setgFlag("Gazetted");
				} else {
					boardMemberDto.setgFlag("Non-Gazetted");
				}
			}

		}

		return boardMemberDto;
	}

	@Override
	public List<MedicalBoardMemberDto> getListOfAllBoardMembersByBoardIdOnlyActiveMember(String boardId) {
		List<Object> MedicalBoardMemberDto = medicalBoardMemberRepo.getBoardMemberDetailsForFinalSubmission(boardId);
		List<MedicalBoardMemberDto> medicalBoardMemberDtos = new ArrayList<>();

		Iterator<Object> itr = MedicalBoardMemberDto.iterator();
		while (itr.hasNext()) {
			MedicalBoardMemberDto boardMemberDto = new MedicalBoardMemberDto();
			Object[] obj = (Object[]) itr.next();
			if (obj[0] != null) {
				boardMemberDto.setForceCodeName(String.valueOf(obj[0]).trim());

			}
			if (obj[1] != null) {
				boardMemberDto.setForceId(String.valueOf(obj[1]).trim());

			}
			if (obj[2] != null) {
				boardMemberDto.setName(String.valueOf(obj[2]).trim());

			}
			if (obj[3] != null) {
				boardMemberDto.setRank(String.valueOf(obj[3]).trim());
				boardMemberDto.setDesignation(String.valueOf(obj[3]).trim());

			}
			if (obj[4] != null) {
				boardMemberDto.setRoleName(String.valueOf(obj[4]).trim());

			}
			if (obj[5] != null) {
				boardMemberDto.setForcePersonalId(String.valueOf(obj[5]).trim());

			}
			if (obj[6] != null) {
				boardMemberDto.setBoardId(String.valueOf(obj[6]).trim());

			}

			medicalBoardMemberDtos.add(boardMemberDto);

		}
		return medicalBoardMemberDtos;
	}

	@Override
	public List<MedicalBoardMemberDto> listOfBoardByLoginForcePersonnelBoardId(String boardId, String forcePersonnel) {
		List<Object> stringsList = medicalBoardMemberRepo.findBoardIdByLoginForcePersonnelBoardId(boardId,
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)),
				forcePersonnel);
		List<MedicalBoardMemberDto> boardMemberDtos = new ArrayList<>();

		if (stringsList.isEmpty()) {
			return boardMemberDtos;
		}
		Iterator<Object> iterator = stringsList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			MedicalBoardMemberDto medicalBoardMemberDto = new MedicalBoardMemberDto();

			if (obj[0] != null) {
				medicalBoardMemberDto.setBoardId(String.valueOf(obj[0]).trim());
				medicalBoardMemberDto.setAppointmentCompletedCount(
						ameAppointmentAndDeclarationService.appointmentCompletedCount(String.valueOf(obj[0]).trim()));
				medicalBoardMemberDto.setAppointmentPendingCount(
						ameAppointmentAndDeclarationService.appointmentPendingCount(String.valueOf(obj[0]).trim()));
			}
			if (obj[1] != null) {
				medicalBoardMemberDto.setRoleName(String.valueOf(obj[1]).trim());

			}
			if (obj[2] != null) {

				medicalBoardMemberDto.setForceName(
						RefForceService.getForceNameByForceId(Integer.parseInt(String.valueOf(obj[2]).trim())));
			}
			if (obj[3] != null) {

				medicalBoardMemberDto.setUsedFor(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {

				medicalBoardMemberDto.setPlace(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				medicalBoardMemberDto.setCreatedOn((Date) (obj[5]));
			}
			if (obj[6] != null) {
				medicalBoardMemberDto.setForcePersonalId(String.valueOf(obj[6]).trim());
			}
			if (obj[7] != null) {
				medicalBoardMemberDto.setName(String.valueOf(obj[7]).trim());
			}

			boardMemberDtos.add(medicalBoardMemberDto);

		}

		return boardMemberDtos;
	}

//	@Override
//	public List<ForcePersonnel> getStatusesByForcePersonnelIdAndRole(String forcePersonnelId, String roleCode) {
//		List<ForcePersonnel> forcePersonallist = new ArrayList<>();
//		List<String> boardIds = medicalBoardMemberRepository.findBoardIdsByForcePersonalIdAndRole(forcePersonnelId,
//				roleCode);
//		if (boardIds.isEmpty()) {
//			return Collections.emptyList();
//		}
//		
//		//Long roleCodeLong = Long.parseLong(roleCode); 
//		List<String> ameIds = ameApprovalProcessRepository.findAmeIdByRoleCodeAndBoardId(boardIds, roleCode);
//
//		if (ameIds.isEmpty()) {
//			return Collections.emptyList();
//		}
//		
//	
//	  List<AmeApplicationFlowStatus> forcePersonnelDetails = ameApplicationFlowStatusRepo.findByAmeIds(ameIds);
//	  if (forcePersonnelDetails.isEmpty()) {
//			return Collections.emptyList();
//		}
//	  for(AmeApplicationFlowStatus ameApplicationFlowStatus:forcePersonnelDetails) {
//		  Optional<ForcePersonnelDto> optionalforcePersonalDto=	this.forcePersonalService.findByForcePersonalId(ameApplicationFlowStatus.getForcepersonalId());
//		  ForcePersonnel forcePersonnel = new ForcePersonnel();
//		  forcePersonnel.setName(optionalforcePersonalDto.get().getName());
//		  forcePersonnel.setforceNo(optionalforcePersonalDto.get().getForceNo());
//		  forcePersonnel.setForceId(optionalforcePersonalDto.get().getForceId());
//		 forcePersonnel.setUnit(optionalforcePersonalDto.get().getUnit());
//		 
//		  forcePersonallist.add(forcePersonnel);
//	  }
//
//	  return forcePersonallist;
//		
//	}

}
