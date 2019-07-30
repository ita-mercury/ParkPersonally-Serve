package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotService service;

    @PutMapping("/parking-lots/{parkingLotId}")
    public ParkingLot updateParkingLot(@PathVariable long parkingLotId, @RequestBody ParkingLot parkingLot){

        return service.updateParkingLot(parkingLotId, parkingLot);
    }
    @GetMapping("/parking-lots")
    public ResponseEntity getAllParkingLots(){
        return ResponseEntity.ok(service.findParkingLots());
    }
}
