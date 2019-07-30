package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{parkingBoyId}/tags")
    public ResponseEntity getParkingBoyTags(@PathVariable long parkingBoyId){
        List<Tag> tags =customerService.getAllTags(parkingBoyId);
        return  ResponseEntity.ok(tags);
    }
    @GetMapping(value = "/{customerId}/allOrders")
    public  ResponseEntity getAllCarOrders(@PathVariable long customerId){
        List<ParkingOrder> parkingOrders=customerService.getAllOrders(customerId);
        return ResponseEntity.ok(parkingOrders);
    }


}
