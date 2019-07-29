package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotService service;

    @PutMapping("/parking-lots/{parkingLotId}")
    public ParkingLot updateParkingLot(@PathVariable long parkingLotId, @RequestBody ParkingLot parkingLot){

        return service.updateParkingLot(parkingLotId, parkingLot);
    }
}
