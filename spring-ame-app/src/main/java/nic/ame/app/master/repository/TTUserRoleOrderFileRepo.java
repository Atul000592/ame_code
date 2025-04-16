package nic.ame.app.master.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.TTOrderFileRoleCreation;

@Repository
public interface TTUserRoleOrderFileRepo extends JpaRepository<TTOrderFileRoleCreation, BigInteger>{

}
