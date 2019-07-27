package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingBoyService;
import com.parkpersonally.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @PostMapping("/parking-orders")
    public ParkingOrder createOrder(@Valid @RequestBody ParkingOrder order) {

        return parkingOrderService.createParkingOrder(order);
    }
}
