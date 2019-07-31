package com.parkpersonally.repository;

import com.parkpersonally.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Long> {

}
