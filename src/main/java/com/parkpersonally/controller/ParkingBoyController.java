package com.parkpersonally.controller;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService service;

    @GetMapping("/parking-boys/{parkingBoyId}/parking-lots")
    public List<ParkingLot> getAllParkingLotOnParkingBoy(@PathVariable long parkingBoyId){

        return service.getAllParkingLotOnParkingBoy(parkingBoyId);
    }

    @ExceptionHandler(NoSuchParkingBoyException.class)
    public ResponseEntity handlNoSuchParkingBoy(NoSuchParkingBoyException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
