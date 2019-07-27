package com.parkpersonally.service;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerService {

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository repository;

}
