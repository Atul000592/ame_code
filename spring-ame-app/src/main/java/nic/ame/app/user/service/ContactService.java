package nic.ame.app.user.service;

import java.util.List;

import nic.ame.app.admin.model.ContactUs;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PersonnelOthersDto;

public interface ContactService {
    

    public boolean saveContact(ContactUs contactUs);

    public boolean editContact(ContactUs contactUs );

    public ContactUs showEditForm(long id);

    public List<ForcePersonnelDto> getForcePersonnelListByForceNoAndDesignation(Integer forceNo,Integer designation);
	
List<PersonnelOthersDto> getPersonalOthersByForceNoAndDesignation(Integer forceNo,Integer designation);
	
}
