package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ParkingOrderController {

    @PostMapping("/parking-orders")
    public ParkingOrder createOrder(@Valid @RequestBody ParkingOrder order){

        return null;
    }
}
