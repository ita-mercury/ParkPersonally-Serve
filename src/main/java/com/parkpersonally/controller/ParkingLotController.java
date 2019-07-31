package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService service;

    @PutMapping("/{parkingLotId}")
    public ParkingLot updateParkingLot(@PathVariable long parkingLotId, @RequestBody ParkingLot parkingLot) {

        return service.updateParkingLot(parkingLotId, parkingLot);
    }

    @GetMapping
    public ResponseEntity getAllParkingLots() {
        return ResponseEntity.ok(service.findParkingLots());
    }

    @PostMapping
    public ResponseEntity addParkingLot(@RequestBody ParkingLot parkingLot) {
        return ResponseEntity.ok(service.saveService(parkingLot));
    }

    @PatchMapping("/parking-lots/{parkingLotId}")
    public ResponseEntity changeParkingLotStatus(@PathVariable long parkingLotId, @RequestBody ParkingLot parkingLot){
        return ResponseEntity.ok(service.changeParkingLotStatus(parkingLotId, parkingLot));
    }
}
