package com.parkpersonally.controller;

import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService service;
}
