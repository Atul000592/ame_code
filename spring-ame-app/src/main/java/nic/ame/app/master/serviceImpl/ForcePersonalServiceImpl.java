package nic.ame.app.master.serviceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.service.RefForceService;

import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleHistoryDto;

import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.user.dto.UserStatusDto;

@Service
public class ForcePersonalServiceImpl implements ForcePersonalService {

	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;

	@Autowired
	private ForcePersonnelService forcePersonnelService;

	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	private RankRepo rankRepo;

	@Override
	public Optional<ForcePersonnelDto> findByForcePersonalId(String forcePersonalId) {

		Optional<ForcePersonnelDto> getOptional = forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);
		if (!getOptional.isEmpty()) {
			ForcePersonnelDto forcePersonal = new ForcePersonnelDto();
			forcePersonal = getOptional.get();
			return Optional.of(forcePersonal);
		}

		return Optional.empty();
	}

	@Override
	public Optional<UserStatusDto> findForcePersonalByBoardMemberId(String candidateForcePersonalId) {

		return Optional.empty();
	}

	@Override
	public List<ForcePersonnel> findNgoAmaListWithTransactionalId(String transactionalId) {

		List<ForcePersonnel> forcePersonalsList = forcePersonnelRepository
				.getNgoAmaListWithTransactionalId(transactionalId);

		return forcePersonalsList;
	}

	@Override
	public ForcePersonnelDto getForcePersonalDetails(String forcePersonalId) {
		List<Object> getdto = forcePersonnelRepository.getForcePersonalDetailsObject(forcePersonalId.trim());

		ForcePersonnelDto usePersonalDto = new ForcePersonnelDto();

		if (getdto.isEmpty()) {
			return usePersonalDto;
		}
		Iterator<Object> iterator = getdto.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();

			if (obj[0] != null) {
				usePersonalDto.setForceId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				usePersonalDto.setName(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				usePersonalDto.setRank(rankRepo.findById(Integer.parseInt(String.valueOf(obj[2]).trim())).get().getRankFullName());
			}
			if (obj[3] != null) {
				// usePersonalDto.setForceNo(String.valueOf(obj[3]).trim());
				usePersonalDto.setForceName(String.valueOf(obj[3]).trim());
				// ============================get forceName================================//
			}
			if (obj[4] != null) {
				// usePersonalDto.setUnitNo(String.valueOf(obj[4]).trim());
				usePersonalDto.setUnitName(String.valueOf(obj[4]).trim());
			}

			if (obj[5] != null) {
				usePersonalDto.setDob((java.util.Date) obj[5]);

			}
			if (obj[6] != null) {
				if(String.valueOf(obj[6]).trim().equalsIgnoreCase("M"))
					usePersonalDto.setGender("Male");
				else
					usePersonalDto.setGender("Female");
			}
			if (obj[7] != null) {
				usePersonalDto.setDojPresentRank((java.util.Date) obj[7]);

			}

		}
		return usePersonalDto;
	}

	
	@Override
	public ForcePersonnel findByForceId(String forceId) throws Exception {
		Optional<ForcePersonnel> forcePersonnelOptional = this.forcePersonnelRepository.findByForceId(forceId);
		if (!forcePersonnelOptional.isPresent()) {
			throw new Exception("Data Error : ForcePersonal Is Not Present" + forcePersonnelOptional);
		}
		ForcePersonnel forcePersonnel = forcePersonnelOptional.get();
		return forcePersonnel;
	}
	 

	@Override
	public ForcePersonnelDto getForcePersonalDetailsObjectByForceId(String forcePersonalId) {
		// TODO Auto-generated method stub
		return null;
	}


}
