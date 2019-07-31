package com.parkpersonally.repository;

import com.parkpersonally.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findParkingBoyByNameEqualsAndAndPasswordEquals(String name, String password);
}
