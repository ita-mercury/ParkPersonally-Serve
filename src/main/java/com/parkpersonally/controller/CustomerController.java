package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/{parkingBoyId}/tags")
    public ResponseEntity getParkingBoyTags(@PathVariable long parkingBoyId){
        List<Tag> tags =customerService.getAllTags(parkingBoyId);
        return  ResponseEntity.ok(tags);
    }
//    @GetMapping("/customers")
//    public  ResponseEntity getAllCarOrders(@RequestParam("type")int type,@RequestParam("customerId")int customerId){
//        List<ParkingOrder> parkingOrders=customerService.getAllOrders(type,customerId);
//        return ResponseEntity.ok(parkingOrders);
//    }
    @GetMapping(value = "/customers/{customerId}/allOrders")
    public  ResponseEntity getAllCarOrders(@PathVariable long customerId){
        List<ParkingOrder> parkingOrders=customerService.getAllOrders(customerId);
        return ResponseEntity.ok(parkingOrders);
    }


}
