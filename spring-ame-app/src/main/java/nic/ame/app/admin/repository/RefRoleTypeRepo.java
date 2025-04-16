package nic.ame.app.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.RefRoleMedical;



@Repository
public interface RefRoleTypeRepo extends JpaRepository<RefRoleMedical, Long>{

}
