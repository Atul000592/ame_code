package nic.ame.app.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.CustomSequenceCode;

@Repository
public interface CustomSequenceCodeRepo extends JpaRepository<CustomSequenceCode , Integer>{

	@Query(nativeQuery = true,value = "select nextval('custom_sequence_code_id_seq') as num")
    int getCustomSeqCode();
}
