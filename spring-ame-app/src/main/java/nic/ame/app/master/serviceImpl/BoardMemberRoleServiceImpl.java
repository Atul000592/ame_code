package nic.ame.app.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.medical.dto.MedicalBoardMemberRoleDto;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.BoardMemberRoleService;
import nic.ame.constant.CommonConstant;

@Service
public class BoardMemberRoleServiceImpl implements BoardMemberRoleService {

	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;

	@Autowired
	private RefRoleMedicalRepo refRoleMedicalRepo;
	
	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	
	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	@Autowired
	private TTAppointmentAmeRepo ttAppointmentAmeRepo;

	public String roleName = "";
	
	
	
	@Autowired
	private AmeReviewCandidatesListRepository ameReviewCandidatesListRepository;

	@Override
	public List<MedicalBoardMemberRoleDto> getBoardMemberRoleForNGO(String forcePersonalId, HttpSession httpSession) {

		List<Object> list = medicalBoardMemberRepo
				.findAllBoardAssociatedWithForcePersonnelByForcePersonnelId(forcePersonalId.trim());
		List<MedicalBoardMemberRoleDto> boardMemberRoleDtos = new ArrayList<>();

		if (list.isEmpty()) {
			return boardMemberRoleDtos;
		}
		Iterator<Object> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			MedicalBoardMemberRoleDto boardMemberRoleDto = new MedicalBoardMemberRoleDto();

			if (obj[0] != null) {
				boardMemberRoleDto.setForcePersonalId(String.valueOf(obj[0]));
			}
			if (obj[1] != null) {
				boardMemberRoleDto.setBoardId((String.valueOf(obj[1])).trim());
				int ameCount=ameDeclarationIndividualDetailsRepo.getAmeCompleteCountByBoardId((String.valueOf(obj[1])).trim());
				int mappedMemberToBoard=medicalBoardIndividualMappingRepo.getMappedMemberToBoardByBoardId(String.valueOf(obj[1]).trim());
				int appointmentCount=ttAppointmentAmeRepo.getAppointmentCountByBoardId(String.valueOf(obj[1]).trim());
				
				boardMemberRoleDto.setAmeCompleteCount(ameCount);
				boardMemberRoleDto.setAppointmentCount(appointmentCount);
				boardMemberRoleDto.setMappedMamberCount(mappedMemberToBoard);
				int reviewCandidateCountTotal=ameReviewCandidatesListRepository.findCandidateListForReviewByBoardId((String.valueOf(obj[1])).trim(),CommonConstant.REVIEW_PENDING_RESCHEDULE);
				int appointmentCompletedForReviewCandidate=ameReviewCandidatesListRepository.findCandidateListForReviewByBoardId((String.valueOf(obj[1])).trim(),CommonConstant.REVIEW_COMPLETED_RESCHEDULE);
				boardMemberRoleDto.setReviewCandidateCount(reviewCandidateCountTotal+appointmentCompletedForReviewCandidate);
				boardMemberRoleDto.setReviewCandidateAppointmentPending(reviewCandidateCountTotal);
				
				httpSession.setAttribute("sBoardId", String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				boardMemberRoleDto.setForceName((String.valueOf(obj[2])).trim());
			}
			if (obj[3] != null) {
				boardMemberRoleDto.setUnitName((String.valueOf(obj[3])).trim());
			}
			if (obj[4] != null) {
				boardMemberRoleDto.setBoardYear((String.valueOf(obj[4])).trim());
			}
			if (obj[5] != null) {
				boardMemberRoleDto.setUsedFor((String.valueOf(obj[5])).trim());

			}

			if (obj[6] != null) {
				this.roleName = refRoleMedicalRepo
						.findById(Long.parseLong(String.valueOf(String.valueOf(obj[6])).trim())).get().getRoleName();
				boardMemberRoleDto.setRoleName(roleName);
				boardMemberRoleDto.setrCode(Integer.parseInt(String.valueOf(obj[6])));
			}
			if (obj[7] != null) {
				boardMemberRoleDto.setCreatedOn((Date) obj[7]);
			}
			if (obj[8] != null) {
				if (Integer.parseInt(String.valueOf(obj[8]).trim()) == 1) {
					boardMemberRoleDto.setUri("role-map-to-" + this.roleName.trim() + "-dashboard");
				} else {

					boardMemberRoleDto.setUri(null);
				}
				boardMemberRoleDto.setStatus(String.valueOf(obj[8]));

			}

			boardMemberRoleDtos.add(boardMemberRoleDto);

		}

		return boardMemberRoleDtos;

	}

	@Override
	public List<MedicalBoardMemberRoleDto> getBoardMemberRoleForGO(String forcePersonalId, HttpSession httpSession) {
		List<Object> list = medicalBoardMemberRepo
				.findAllBoardAssociatedBoardForGOWithForcePersonnelByForcePersonnelId(forcePersonalId.trim());
		List<MedicalBoardMemberRoleDto> boardMemberRoleDtos = new ArrayList<>();

		if (list.isEmpty()) {
			return boardMemberRoleDtos;
		}
		Iterator<Object> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			MedicalBoardMemberRoleDto boardMemberRoleDto = new MedicalBoardMemberRoleDto();

			if (obj[0] != null) {
				boardMemberRoleDto.setForcePersonalId(String.valueOf(obj[0]));
			}
			if (obj[1] != null) {
				boardMemberRoleDto.setBoardId((String.valueOf(obj[1])).trim());
				int ameCount = ameDeclarationIndividualDetailsRepo
						.getAmeCompleteCountByBoardId((String.valueOf(obj[1])).trim());
				int mappedMemberToBoard = medicalBoardIndividualMappingRepo
						.getMappedMemberToBoardByBoardId(String.valueOf(obj[1]).trim());
				int appointmentCount = ttAppointmentAmeRepo.getAppointmentCountByBoardId(String.valueOf(obj[1]).trim());

				boardMemberRoleDto.setAmeCompleteCount(ameCount);
				boardMemberRoleDto.setAppointmentCount(appointmentCount);
				boardMemberRoleDto.setMappedMamberCount(mappedMemberToBoard);

				httpSession.setAttribute("sBoardId", String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				boardMemberRoleDto.setForceName((String.valueOf(obj[2])).trim());
			}
			if (obj[3] != null) {
				boardMemberRoleDto.setUnitName((String.valueOf(obj[3])).trim());
			}
			if (obj[4] != null) {
				boardMemberRoleDto.setBoardYear((String.valueOf(obj[4])).trim());
			}
			if (obj[5] != null) {
				boardMemberRoleDto.setUsedFor((String.valueOf(obj[5])).trim());

			}

			if (obj[6] != null) {
				this.roleName = refRoleMedicalRepo
						.findById(Long.parseLong(String.valueOf(String.valueOf(obj[6])).trim())).get().getRoleName();
				if(roleName.equalsIgnoreCase("mb1")||roleName.equalsIgnoreCase("mb2")) {
					roleName="board-member";
				}
				boardMemberRoleDto.setRoleName(roleName);
				boardMemberRoleDto.setrCode(Integer.parseInt(String.valueOf(obj[6])));
			}
			if (obj[7] != null) {
				boardMemberRoleDto.setCreatedOn((Date) obj[7]);
			}
			if (obj[8] != null) {
				if (Integer.parseInt(String.valueOf(obj[8]).trim()) == 1) {
					if(roleName.equalsIgnoreCase("mb1")||roleName.equalsIgnoreCase("mb2")) {
						roleName="board-member";
					}
					boardMemberRoleDto.setUri("role-map-to-" + this.roleName.trim() + "-dashboard");
				} else {

					boardMemberRoleDto.setUri(null);
				}
				boardMemberRoleDto.setStatus(String.valueOf(obj[8]));

			}

			boardMemberRoleDtos.add(boardMemberRoleDto);

		}
		return boardMemberRoleDtos;
	}
	
	
	@Override
	public Optional<MedicalBoardMemberRoleDto> getSingleBoardMemberRoleForGO(String forcePersonalId,String boardId, HttpSession httpSession) {
		 Object object = medicalBoardMemberRepo
				.findBoardAssociatedBoardForGOWithForcePersonnelByForcePersonnelId(forcePersonalId.trim(),boardId).get();
		MedicalBoardMemberRoleDto boardMemberRoleDto = new MedicalBoardMemberRoleDto();

		if (object==null) {
			return Optional.ofNullable(null);
		}
		//Iterator<Object> iterator = list.iterator();

		//while (iterator.hasNext()) {
			Object[] obj = (Object[]) object;

			if (obj[0] != null) {
				boardMemberRoleDto.setForcePersonalId(String.valueOf(obj[0]));
			}
			if (obj[1] != null) {
				boardMemberRoleDto.setBoardId((String.valueOf(obj[1])).trim());
				int ameCount = ameDeclarationIndividualDetailsRepo
						.getAmeCompleteCountByBoardId((String.valueOf(obj[1])).trim());
				int mappedMemberToBoard = medicalBoardIndividualMappingRepo
						.getMappedMemberToBoardByBoardId(String.valueOf(obj[1]).trim());
				int appointmentCount = ttAppointmentAmeRepo.getAppointmentCountByBoardId(String.valueOf(obj[1]).trim());

				boardMemberRoleDto.setAmeCompleteCount(ameCount);
				boardMemberRoleDto.setAppointmentCount(appointmentCount);
				boardMemberRoleDto.setMappedMamberCount(mappedMemberToBoard);

				httpSession.setAttribute("sBoardId", String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				boardMemberRoleDto.setForceName((String.valueOf(obj[2])).trim());
			}
			if (obj[3] != null) {
				boardMemberRoleDto.setUnitName((String.valueOf(obj[3])).trim());
			}
			if (obj[4] != null) {
				boardMemberRoleDto.setBoardYear((String.valueOf(obj[4])).trim());
			}
			if (obj[5] != null) {
				boardMemberRoleDto.setUsedFor((String.valueOf(obj[5])).trim());

			}

			if (obj[6] != null) {
				this.roleName = refRoleMedicalRepo
						.findById(Long.parseLong(String.valueOf(String.valueOf(obj[6])).trim())).get().getRoleName();
				boardMemberRoleDto.setRoleName(roleName);
				boardMemberRoleDto.setrCode(Integer.parseInt(String.valueOf(obj[6])));
			}
			if (obj[7] != null) {
				boardMemberRoleDto.setCreatedOn((Date) obj[7]);
			}
			if (obj[8] != null) {
				if (Integer.parseInt(String.valueOf(obj[8]).trim()) == 1) {
					boardMemberRoleDto.setUri("role-map-to-" + this.roleName.trim() + "-dashboard");
				} else {

					boardMemberRoleDto.setUri(null);
				}
				boardMemberRoleDto.setStatus(String.valueOf(obj[8]));

			}

		
		return Optional.ofNullable(boardMemberRoleDto);
	}

	
	

}
