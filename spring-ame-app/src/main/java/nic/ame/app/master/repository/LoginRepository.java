package nic.ame.app.master.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import nic.ame.app.master.model.Login;


@Repository
public interface LoginRepository extends JpaRepository<Login, String>{

	
	@Query(nativeQuery = true,value = "select * from login_tbl where force_personal_id=?1" )
	Optional<Login> getByForcePersonalId(String forcePersonalId);

	@Query(nativeQuery = true,value = "select * from login_tbl where user_name=?1 and \"password\"=?2")
	Optional<Login> findByUserNameAndPassword(String userName, String password);
	
	@Query(nativeQuery = true,value = "select * from login_tbl where user_name=?1")
	Optional<Login> findByUserName(String userName);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value = "update login_tbl set password=?1,password1=?2,password2=?3,first_login_flag='N' where force_personal_id=?4")
	int updateByUserName(String newPassword,String changedPassword1,String changedPassword2,String loginForcePersonalId);


}
