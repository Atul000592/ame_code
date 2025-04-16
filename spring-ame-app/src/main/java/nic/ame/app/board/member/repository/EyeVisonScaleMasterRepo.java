package nic.ame.app.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.EyeVisonScaleMaster;

@Repository
public interface EyeVisonScaleMasterRepo  extends JpaRepository<EyeVisonScaleMaster, Integer>{

}
