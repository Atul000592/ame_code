package nic.ame.app.esign;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EsignRequestDataRepository extends JpaRepository<EsignRequestData,String>{

Optional<EsignRequestData> findByTxn(String tnxId);

}
