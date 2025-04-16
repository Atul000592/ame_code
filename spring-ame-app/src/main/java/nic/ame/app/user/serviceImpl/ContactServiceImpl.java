package nic.ame.app.user.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.ContactUs;
import nic.ame.app.admin.model.Rank;
import nic.ame.app.admin.repository.ContactUsRepo;
import nic.ame.app.admin.repository.ForcePersonalAdminRepository;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PersonnelOthersDto;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.ref.entity.ForcePersonalOthersRepo;
import nic.ame.app.user.service.ContactService;
import nic.ame.constant.CommonConstant;


@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactUsRepo contactUsRepo;
    @Autowired
    private ForcePersonalOthersRepo forcePersonalOthersRepo;

    @Autowired
    private ForcePersonalAdminRepository forcePersonalAdminRepository;

    @Autowired
    private RefForceService refForceService;
    
    
    @Autowired
    private ForceRepo forceRepo;
    
    
    @Autowired
    private RankRepo rankRepo;

    @Override
    public boolean saveContact(ContactUs contactUs) {
        ContactUs contact=new ContactUs();
  
        contact.setDepartment(contactUs.getDepartment());
        contact.setEmail(contactUs.getEmail());
        contact.setIrlaNo(contactUs.getIrlaNo());
        contact.setDesignation(contactUs.getDesignation());
        contact.setName(contactUs.getIrlaNo());
        contact.setiSD(contactUs.getiSD());
        contact.setStD(contactUs.getStD());
        contact.setMobileNo(contactUs.getMobileNo());
        contact.setPhNO(contactUs.getPhNO());
        contact.setModifiedOn(new Date());
        contact.setCreatedBy(new Date());
        contact.setCreatedOn(new Date());
        contact.setModifiedBy(new Date());
        contact.setInactiveDate(new Date());
        contact.setStatusCode(CommonConstant.ACTIVE_FLAG_YES);
        contact.setContactType(contactUs.getContactType());
        this.contactUsRepo.save(contact);
        return true;
    }

    
    @Override
    public boolean editContact(ContactUs contactUs) {   

        ContactUs contact=new ContactUs();
        contact.setId(contactUs.getId());
        contact.setDepartment(contactUs.getDepartment());
        contact.setEmail(contactUs.getEmail());
        contact.setDesignation(contactUs.getDesignation());
        contact.setIrlaNo(contactUs.getIrlaNo());
        contact.setInactiveDate(new Date());
        contact.setiSD(contactUs.getiSD());
        contact.setiSD(contactUs.getiSD());
        contact.setiSD(contactUs.getiSD());
        contact.setiSD(contactUs.getiSD());
        contact.setStD(contact.getStD());
        contact.setPhNO(contactUs.getPhNO());
        contact.setMobileNo(contactUs.getMobileNo());
        contact.setModifiedOn(new Date());
        contact.setCreatedBy(new Date());
        contact.setCreatedOn(new Date());
        contact.setModifiedBy(new Date());
        contact.setStatusCode(CommonConstant.ACTIVE_FLAG_YES);
        contact.setName(contactUs.getName());
        contact.setContactType(contactUs.getContactType());
        this.contactUsRepo.save(contact);
        return true;
     


    }


    @Override
    public ContactUs showEditForm(long id) {
        ContactUs contact=  this.contactUsRepo.findById(id).orElseThrow(()->new RuntimeException("Data Error Contact us is Not Present"+id));
      Force force= forceRepo.findByForceNo(Integer.parseInt(contact.getDepartment()));
      Rank rank =rankRepo.findById(Integer.parseInt(contact.getDesignation())).get();
      contact.setDepartment(force.getForceName());
      contact.setDesignation(rank.getRankFullName());
        return contact;
    }


   @Override
	public List<ForcePersonnelDto> getForcePersonnelListByForceNoAndDesignation(Integer forceNo,Integer designation) {
		
		List<Object> forcePersonnelObjectList = forcePersonalAdminRepository.getForcePersonnelListByForceNoAndDesignation(forceNo,designation);
		List<ForcePersonnelDto> forcePersonalDtoList= new ArrayList<>();
		
		Iterator<Object> iterator = forcePersonnelObjectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ForcePersonnelDto forcePersonnel= new ForcePersonnelDto();
		   if (obj[0] != null) {
			   forcePersonnel.setForceId(String.valueOf(obj[1]).trim());
			}
		   if (obj[1] != null) {
			   forcePersonnel.setName(String.valueOf(obj[1]).trim());
			}
		   if (obj[2] != null) {
			   forcePersonnel.setDesignation(String.valueOf(obj[2]).trim());
			}
		   if (obj[3] != null) {
			   forcePersonnel.setUnit(String.valueOf(obj[3]).trim());
			   forcePersonnel.setUnitName (refForceService.getUnitNameByUnitId(forceNo,forcePersonnel.getUnit()));
			}
		   forcePersonalDtoList.add(forcePersonnel);
		
		}
		
		return forcePersonalDtoList;
	}

	@Override
	public List<PersonnelOthersDto> getPersonalOthersByForceNoAndDesignation(Integer forceNo, Integer designation) {
		List<Object> personnelOthersObjectList = forcePersonalOthersRepo
				.getPersonalOthersByForceNoAndDesignation(forceNo, designation);
		List<PersonnelOthersDto> personnelOthersDtoList = new ArrayList<>();

		Iterator<Object> iterator = personnelOthersObjectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			PersonnelOthersDto personnelOthers = new PersonnelOthersDto();
			if (obj[0] != null) {
				personnelOthers.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				personnelOthers.setDesignation(String.valueOf(obj[1]).trim());
			}
			personnelOthersDtoList.add(personnelOthers);
		}
		return personnelOthersDtoList;
		
	}
	
    
}
