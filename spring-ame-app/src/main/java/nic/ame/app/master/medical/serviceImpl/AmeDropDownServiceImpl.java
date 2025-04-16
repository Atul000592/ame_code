package nic.ame.app.master.medical.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nic.ame.app.board.member.repository.EyeVisonScaleMasterRepo;
import nic.ame.app.board.member.repository.NearVisionScaleMasterRepo;
import nic.ame.app.board.member.repository.RefAppendagesTypeRepo;
import nic.ame.app.board.member.repository.RefAppetiteRepo;
import nic.ame.app.board.member.repository.RefCategoryStatusTypeRepo;
import nic.ame.app.board.member.repository.RefDropDownRangeRepo;
import nic.ame.app.board.member.repository.RefGFactorRepo;

import nic.ame.app.board.member.repository.RefPsychologicalDurationRepo;
import nic.ame.app.board.member.repository.RefPsychologicalStatusRepo;
import nic.ame.app.board.member.repository.RefPsychologicalTypeRepo;
import nic.ame.app.board.member.repository.RefSleepRepo;
import nic.ame.app.master.medical.model.EyeVisonScaleMaster;
import nic.ame.app.master.medical.model.NearVisionScaleMaster;
import nic.ame.app.master.medical.service.AmeDropDownService;
import nic.ame.app.master.ref.entity.RefAppendagesType;
import nic.ame.app.master.ref.entity.RefAppetite;
import nic.ame.app.master.ref.entity.CategoryTypeMaster;
import nic.ame.app.master.ref.entity.RefDropDownRange;
import nic.ame.app.master.ref.entity.RefGFactor;
import nic.ame.app.master.ref.entity.RefPsychologicalDuration;
import nic.ame.app.master.ref.entity.RefPsychologicalStatus;
import nic.ame.app.master.ref.entity.RefPsychologicalType;
import nic.ame.app.master.ref.entity.RefSleep;

@Service
public class AmeDropDownServiceImpl implements AmeDropDownService {

	@Autowired
	private RefAppendagesTypeRepo appendagesTypeRepo;
		@Autowired
	private EyeVisonScaleMasterRepo eyeVisonScaleMasterRepo;
	@Autowired
	private NearVisionScaleMasterRepo nearVisionScaleMasterRepo;

	
	@Autowired
	private RefPsychologicalTypeRepo refPsychologicalTypeRepo;
	
	@Autowired
	private RefPsychologicalDurationRepo refPsychologicalDurationRepo;
	
	@Autowired
	private RefPsychologicalStatusRepo refPsychologicalStatusRepo;

	@Autowired
	private RefGFactorRepo refGFactorRepo;

	@Autowired
	private RefCategoryStatusTypeRepo categoryStatusTypeRepo;

	@Autowired
	private RefDropDownRangeRepo refDropDownRangeRepo;
	
	
	@Autowired
	private RefSleepRepo refSleepRepo;
	
	@Autowired
	private RefAppetiteRepo refAppetiteRepo;

	// ============================================================================================================//

	@Override
	public List<RefAppendagesType> getRefAppendagesUpperTypes() {

		List<RefAppendagesType> appendages = appendagesTypeRepo.findByTypeUpper();

		return appendages;
	}

	@Override
	public List<RefAppendagesType> getRefAppendagesLowertype() {
		List<RefAppendagesType> appendages = appendagesTypeRepo.findByTypeLower();
		return appendages;
	}

	
	@Override
	public List<NearVisionScaleMaster> getNearVisionScaleMasters() {
		List<NearVisionScaleMaster> list = nearVisionScaleMasterRepo.findAll();
		return list;
	}

	@Override
	public List<EyeVisonScaleMaster> getEyeVisonScaleMasters() {
		List<EyeVisonScaleMaster> list = eyeVisonScaleMasterRepo.findAll();
		return list;
	}

	// ============================================//


	@Override
	public List<RefGFactor> getGFactors() {
		List<RefGFactor> refGFactors = refGFactorRepo.findAll();

		return refGFactors;
	}

	@Override
	public List<CategoryTypeMaster> categoryStatusTypes() {
		List<CategoryTypeMaster> categoryStatusTypes = categoryStatusTypeRepo.findAll();
		return categoryStatusTypes;
	}

	@Override
	public RefDropDownRange getDropDownRanges(String name) {
		Optional<RefDropDownRange> downRanges = null;
		RefDropDownRange downRange = new RefDropDownRange();
		downRanges = refDropDownRangeRepo.findByName(name);
		if (downRanges.isPresent()) {
			downRange = downRanges.get();
		}
		return downRange;
	}

	
	//=========================refSleepsList===============//
	@Override
	public List<RefSleep> refSleepsList() {
		List<RefSleep> refSleeps = refSleepRepo.findAll();
		return refSleeps;
	}
	
	//=========================refAppetitesList===============//

	@Override
	public List<RefAppetite> refAppetitesList() {
		List<RefAppetite> appetites=refAppetiteRepo.findAll();
		return appetites;
	}

	@Override
	public List<RefAppendagesType> getRefAppendagesCategory(String type) {
		List<RefAppendagesType> appendagesCategory= appendagesTypeRepo.findByType(type);
		return appendagesCategory;
	}

}
