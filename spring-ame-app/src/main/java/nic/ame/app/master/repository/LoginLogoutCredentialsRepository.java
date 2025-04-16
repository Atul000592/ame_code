package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.LoginLogoutCredentials;
@Repository
public interface LoginLogoutCredentialsRepository extends CrudRepository<LoginLogoutCredentials, Integer> {

Optional<LoginLogoutCredentials> findByHttpSessionValue(String currentSession);

}
