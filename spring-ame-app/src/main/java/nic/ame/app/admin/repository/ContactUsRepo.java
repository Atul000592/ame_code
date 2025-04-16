package nic.ame.app.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.admin.model.ContactUs;

public interface ContactUsRepo extends JpaRepository<ContactUs, Long>{

	
	@Query(nativeQuery = true,value = "select cu.name ,cu.designation ,cu.force_no,cu.status_code from contact_us cu")
	List<Object> ListOfContactUs(String loginForcePersonalId);
}
