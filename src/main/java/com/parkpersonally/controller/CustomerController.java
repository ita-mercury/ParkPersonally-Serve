package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/parking-orders")
    public ParkingOrder createOrder(ParkingOrder order){

        return null;
    }
}
