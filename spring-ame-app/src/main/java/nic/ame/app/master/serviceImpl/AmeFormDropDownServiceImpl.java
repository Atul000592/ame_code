package nic.ame.app.master.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.model.ColumnIdentifier;
import nic.ame.app.master.ref.entity.RefAbdomenSystemDropDown;
import nic.ame.app.master.ref.entity.RefCentralNervousSystemDropDown;
import nic.ame.app.master.ref.entity.RefEyeSightVisionDropDown;
import nic.ame.app.master.ref.entity.RefGeneralExamination;
import nic.ame.app.master.ref.entity.RefHearingDropDown;
import nic.ame.app.master.ref.entity.RefPsychologicalDropDown;
import nic.ame.app.master.ref.entity.RefRespiratoryDropDown;
import nic.ame.app.master.ref.entity.repo.AbdomenSystemDropDownRepo;
import nic.ame.app.master.ref.entity.repo.CentralNervousSystemDropDownRepo;
import nic.ame.app.master.ref.entity.repo.RefEyeSightVisionDropDownRepo;
import nic.ame.app.master.ref.entity.repo.RefGeneralExaminationRepo;
import nic.ame.app.master.ref.entity.repo.RefHearingDropDownRepository;
import nic.ame.app.master.ref.entity.repo.RefPsychologicalDropDownRepo;
import nic.ame.app.master.ref.entity.repo.RefRespiratoryDropDownRepository;
import nic.ame.app.master.service.AmeFormDropDownService;

@Service
public class AmeFormDropDownServiceImpl implements AmeFormDropDownService {

	@Autowired
	private RefHearingDropDownRepository refHearingDropDownRepository;

	@Autowired
	private RefGeneralExaminationRepo refGeneralExaminationRepo;

	@Autowired
	private CentralNervousSystemDropDownRepo centralNervousSystemDropDownRepo;

	@Autowired
	private RefRespiratoryDropDownRepository refRespiratoryDropDownRepository;

	@Autowired
	private AbdomenSystemDropDownRepo abdomenSystemDropDownRepo;

	@Autowired
	private RefPsychologicalDropDownRepo refPsychologicalDropDownRepo;

	@Autowired
	private RefEyeSightVisionDropDownRepo refEyeSightVisionDropDownRepo;

	/*
	 * =============================================================================
	 * =============================================================================
	 * =======
	 */

	/*
	 * ============================================Hearing
	 * DropDown=================================================================
	 */

	@Override
	public ColumnIdentifier getHearingDropDownOption() {
		List<RefHearingDropDown> hearingDropDowns = refHearingDropDownRepository.findAll();
		return AmeFormDropDownServiceImpl.setsetDropDown(hearingDropDowns);
	}

	public static ColumnIdentifier setsetDropDown(List<RefHearingDropDown> name) {
		Map<String, List<RefHearingDropDown>> refHearingDropDownMap = name.stream()
				.collect(Collectors.groupingBy(RefHearingDropDown::getColumnIdentifier));
		ColumnIdentifier columnIdentifier = new ColumnIdentifier();

		refHearingDropDownMap.forEach((columnName, getData) -> {
			// System.out.println("Column: " + columnName);
			List<DropDownDto> list = new ArrayList<>();

			for (int i = 0; i < getData.size(); i++) {
				if (columnName.equals("c1")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC1(list);
				}
				if (columnName.equals("c2")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC2(list);
				}
				if (columnName.equals("c3")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC3(list);
				}
				if (columnName.equals("c4")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC4(list);
				}
				if (columnName.equals("c5")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC5(list);
				}
				if (columnName.equals("c6")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC6(list);
				}
				if (columnName.equals("c7")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC7(list);
				}

			}
		});

		return columnIdentifier;
	}

	/*
	 * ============================================GeneralExamination
	 * DropDown=================================================================
	 */

	@Override
	public ColumnIdentifier getGeneralExamination() {
		List<RefGeneralExamination> refGeneralExaminations = refGeneralExaminationRepo.findAll();
		return AmeFormDropDownServiceImpl.setsetDropDownGeneralExamination(refGeneralExaminations);

	}

	private static ColumnIdentifier setsetDropDownGeneralExamination(
			List<RefGeneralExamination> refGeneralExaminations) {
		Map<String, List<RefGeneralExamination>> refGeneralExaminationMap = refGeneralExaminations.stream()
				.collect(Collectors.groupingBy(RefGeneralExamination::getColumnIdentifier));
		ColumnIdentifier columnIdentifier = new ColumnIdentifier();
		refGeneralExaminationMap.forEach((columnName, getData) -> {
			// System.out.println("Column: " + columnName);
			List<DropDownDto> list = new ArrayList<>();

			for (int i = 0; i < getData.size(); i++) {

				if (columnName.equals("c1")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC1(list);
				}
				if (columnName.equals("c2")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC2(list);
				}
				if (columnName.equals("c3")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC3(list);
				}
				if (columnName.equals("c4")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC4(list);
				}
				if (columnName.equals("c5")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC5(list);
				}
				if (columnName.equals("c6")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC6(list);
				}
				if (columnName.equals("c7")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC7(list);
				}
				if (columnName.equals("c8")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC8(list);
				}
				if (columnName.equals("c9")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC9(list);
				}
				if (columnName.equals("c10")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC10(list);
				}
				if (columnName.equals("c11")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC11(list);
				}
				if (columnName.equals("c12")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC12(list);
				}
				if (columnName.equals("c13")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC13(list);
				}
				if (columnName.equals("c14")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC14(list);
				}
				if (columnName.equals("c15")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC15(list);
				}
				if (columnName.equals("c16")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC16(list);
				}
				if (columnName.equals("c17")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC17(list);
				}
				if (columnName.equals("c18")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC18(list);
				}
			}
		});

		return columnIdentifier;
	}

	/*
	 * ============================================Respiratory
	 * DropDown=================================================================
	 */

	@Override
	public ColumnIdentifier getRespiratoryDropDown() {
		List<RefRespiratoryDropDown> refRespiratoryDropDowns = refRespiratoryDropDownRepository.findAll();
		return AmeFormDropDownServiceImpl.setsetDropDownRespiratory(refRespiratoryDropDowns);
	}

	private static ColumnIdentifier setsetDropDownRespiratory(List<RefRespiratoryDropDown> refRespiratoryDropDowns) {

		Map<String, List<RefRespiratoryDropDown>> RefRespiratoryDropDownMap = refRespiratoryDropDowns.stream()
				.collect(Collectors.groupingBy(RefRespiratoryDropDown::getColumnIdentifier));
		ColumnIdentifier columnIdentifier = new ColumnIdentifier();

		RefRespiratoryDropDownMap.forEach((columnName, getData) -> {
			// System.out.println("Column: " + columnName);
			List<DropDownDto> list = new ArrayList<>();

			for (int i = 0; i < getData.size(); i++) {
				if (columnName.equals("c1")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC1(list);
				}
				if (columnName.equals("c2")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC2(list);
				}
				if (columnName.equals("c3")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC3(list);
				}
				if (columnName.equals("c4")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC4(list);
				}

			}
		});

		return columnIdentifier;
	}

	/*
	 * ============================================CNS,Motor,Sensory
	 * DropDown=================================================================
	 */

	@Override
	public ColumnIdentifier getCNSDropDown() {
		List<RefCentralNervousSystemDropDown> dropDowns = centralNervousSystemDropDownRepo.findAll();
		return AmeFormDropDownServiceImpl.setsetDropDownCNS(dropDowns);

	}

	private static ColumnIdentifier setsetDropDownCNS(List<RefCentralNervousSystemDropDown> refCentralNervours) {

		Map<String, List<RefCentralNervousSystemDropDown>> refCentralNervoursMap = refCentralNervours.stream()
				.collect(Collectors.groupingBy(RefCentralNervousSystemDropDown::getColumnIdentifier));
		ColumnIdentifier columnIdentifier = new ColumnIdentifier();
		refCentralNervoursMap.forEach((columnName, getData) -> {
			// System.out.println("Column: " + columnName);
			List<DropDownDto> list = new ArrayList<>();

			for (int i = 0; i < getData.size(); i++) {

				if (columnName.equals("c1")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC1(list);
				}
				if (columnName.equals("c2")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC2(list);
				}
				if (columnName.equals("c3")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC3(list);
				}
				if (columnName.equals("c4")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC4(list);
				}
				if (columnName.equals("c5")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC5(list);
				}
				if (columnName.equals("c6")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC6(list);
				}
				if (columnName.equals("c7")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC7(list);
				}
				if (columnName.equals("c8")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC8(list);
				}
				if (columnName.equals("c9")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC9(list);
				}
				if (columnName.equals("c10")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC10(list);
				}
				if (columnName.equals("c11")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC11(list);
				}
				if (columnName.equals("c12")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC12(list);
				}
				if (columnName.equals("c13")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC13(list);
				}
				if (columnName.equals("c14")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC14(list);
				}
				if (columnName.equals("c15")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC15(list);
				}
				if (columnName.equals("c16")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC16(list);
				}

			}
		});

		return columnIdentifier;
	}

	/*
	 * ============================================Abdomen
	 * DropDown=================================================================
	 */

	@Override
	public ColumnIdentifier getAbdomenDropDown() {
		List<RefAbdomenSystemDropDown> abdomenSystemDropDownsList = abdomenSystemDropDownRepo.findAll();
		return AmeFormDropDownServiceImpl.setabdomenSystemDropDown(abdomenSystemDropDownsList);
	}

	private static ColumnIdentifier setabdomenSystemDropDown(
			List<RefAbdomenSystemDropDown> abdomenSystemDropDownsList) {

		Map<String, List<RefAbdomenSystemDropDown>> RefAbdomenSystemMap = abdomenSystemDropDownsList.stream()
				.collect(Collectors.groupingBy(RefAbdomenSystemDropDown::getColumnIdentifier));
		ColumnIdentifier columnIdentifier = new ColumnIdentifier();
		RefAbdomenSystemMap.forEach((columnName, getData) -> {
			// System.out.println("Column: " + columnName);
			List<DropDownDto> list = new ArrayList<>();

			for (int i = 0; i < getData.size(); i++) {

				if (columnName.equals("c1")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC1(list);
				}
				if (columnName.equals("c2")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC2(list);
				}
				if (columnName.equals("c3")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC3(list);
				}
				if (columnName.equals("c4")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC4(list);
				}
				if (columnName.equals("c5")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC5(list);
				}
			}
		});

		return columnIdentifier;
	}

	/*
	 * ============================================Psychology
	 * DropDown=================================================================
	 */

	@Override
	public ColumnIdentifier getPsychologyDropDown() {
		List<RefPsychologicalDropDown> psychologicalDropDowns = refPsychologicalDropDownRepo.findAll();
		return AmeFormDropDownServiceImpl.setPsychologicalDropDown(psychologicalDropDowns);
	}

	private static ColumnIdentifier setPsychologicalDropDown(List<RefPsychologicalDropDown> psychologicalDropDowns) {
		// TODO Auto-generated method stub
		Map<String, List<RefPsychologicalDropDown>> RefPsychologicalMap = psychologicalDropDowns.stream()
				.collect(Collectors.groupingBy(RefPsychologicalDropDown::getColumnIdentifier));
		ColumnIdentifier columnIdentifier = new ColumnIdentifier();
		RefPsychologicalMap.forEach((columnName, getData) -> {
			// System.out.println("Column: " + columnName);
			List<DropDownDto> list = new ArrayList<>();

			for (int i = 0; i < getData.size(); i++) {

				if (columnName.equals("c1")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC1(list);
				}
				if (columnName.equals("c2")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC2(list);
				}
				if (columnName.equals("c3")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC3(list);
				}

			}
		});

		return columnIdentifier;
	}

	/*
	 * ============================================Eye Vision
	 * DropDown=================================================================
	 */

	@Override
	public ColumnIdentifier getEyeVisionDropDown() {
		List<RefEyeSightVisionDropDown> eyeSightVisionDropDowns = refEyeSightVisionDropDownRepo.findAll();
		return AmeFormDropDownServiceImpl.setEyeVisionDropDown(eyeSightVisionDropDowns);

	}

	private static ColumnIdentifier setEyeVisionDropDown(List<RefEyeSightVisionDropDown> eyeSightVisionDropDowns) {
		// TODO Auto-generated method stub
		Map<String, List<RefEyeSightVisionDropDown>> RefEyeSightVisionMap = eyeSightVisionDropDowns.stream()
				.collect(Collectors.groupingBy(RefEyeSightVisionDropDown::getColumnIdentifier));
		ColumnIdentifier columnIdentifier = new ColumnIdentifier();
		RefEyeSightVisionMap.forEach((columnName, getData) -> {
			// System.out.println("Column: " + columnName);
			List<DropDownDto> list = new ArrayList<>();

			for (int i = 0; i < getData.size(); i++) {

				if (columnName.equals("c1")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC1(list);
				}
				if (columnName.equals("c2")) {

					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC2(list);
				}
				if (columnName.equals("c3")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC3(list);
				}
				if (columnName.equals("c4")) {
					DropDownDto downDto = new DropDownDto();
					downDto.setValue(getData.get(i).getValue());
					list.add(downDto);
					columnIdentifier.setC4(list);
				}

			}
		});

		return columnIdentifier;
	}

	@Override
	public List<DropDownDto> getListOfDownCategory(AmeMasterStatus ameMasterStatus,String gender) {

		List<DropDownDto> getListOfDownCategoryType = new ArrayList<>();

		if (ameMasterStatus.getPsycological_shape() != null) {
			if (!ameMasterStatus.getPsycological_shape().contains("S-1")) {
				getListOfDownCategoryType.add(new DropDownDto("Psycological", "S"));
			}
		}

		if (ameMasterStatus.getUpperLimbShape() != null) {
			if (!ameMasterStatus.getUpperLimbShape().contains("A-1(U)")) {
				getListOfDownCategoryType.add(new DropDownDto("UpperLimb", "UA"));
			}
		}
		if (ameMasterStatus.getLowerLimbShape() != null) {
			if (!ameMasterStatus.getLowerLimbShape().contains("A-1(L)")) {
				getListOfDownCategoryType.add(new DropDownDto("LowerLimb", "LA"));
			}
		}
		if (ameMasterStatus.getSpineShape() != null) {
			if (!ameMasterStatus.getSpineShape().contains("A-1(S)")) {
				getListOfDownCategoryType.add(new DropDownDto("Spine", "SA"));
			}
		}

		if (ameMasterStatus.getPhysicalShape() != null) {
			if (!ameMasterStatus.getPhysicalShape().contains("P-1")) {
				getListOfDownCategoryType.add(new DropDownDto("Physical", "P"));
			}
		}
		if (ameMasterStatus.getEyeShape() != null) {
			if (!ameMasterStatus.getEyeShape().contains("E-1")) {
				getListOfDownCategoryType.add(new DropDownDto("Eye", "E"));
			}
		}
		if(gender=="Female") {
		if (ameMasterStatus.getGynaecologyShape()!= null) {
			if (!ameMasterStatus.getGynaecologyShape().contains("G-1")) {
				getListOfDownCategoryType.add(new DropDownDto("Gynecology", "G"));
			}
		}
		}
		if (ameMasterStatus.getHearingShape()!= null) {
			if (!ameMasterStatus.getHearingShape().contains("H-1")) {
				getListOfDownCategoryType.add(new DropDownDto("Hearing", "H"));
			}
		}


		return getListOfDownCategoryType;
	}

}