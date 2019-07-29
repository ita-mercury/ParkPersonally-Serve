package com.parkpersonally.controller;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
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
    public ResponseEntity<List<ParkingLot>> getAllParkingLotOnParkingBoy(@PathVariable long parkingBoyId){

        return ResponseEntity.ok(service.getAllParkingLotOnParkingBoy(parkingBoyId));
    }

    @PostMapping("/parking-boys")
    public ResponseEntity<ParkingBoy> tagParkingBoy(@RequestBody ParkingBoy parkingBoy){

        return ResponseEntity.ok(service.saveParkingBoy(parkingBoy));
    }

    @GetMapping("/parking-boys/{parkingBoyId}/parking-orders")
    public ResponseEntity<List<ParkingOrder>> getAllParkingOrdersOfParkingBoy(@PathVariable long parkingBoyId){
        ParkingBoy parkingBoy = service.findOneById(parkingBoyId);
        return ResponseEntity.ok(service.getAllParkingOrdersOfParkingBoy(parkingBoy));
    }



}
