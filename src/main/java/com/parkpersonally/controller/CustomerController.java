package com.parkpersonally.controller;

import com.parkpersonally.dto.ParkingOrderDto;
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
    public List<Tag> getParkingBoyTags(@PathVariable long parkingBoyId){
        return customerService.getAllTags(parkingBoyId);
    }
    @GetMapping(value = "/{customerId}/allOrders")
    public  List<ParkingOrderDto> getAllCarOrders(@PathVariable long customerId){
        return customerService.getAllOrders(customerId);
    }


}
