package nic.ame.app.test.controller;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyObjectRepository extends JpaRepository<MyObjectEntity, Long> {
}

