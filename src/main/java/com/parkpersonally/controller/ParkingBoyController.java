package com.parkpersonally.controller;

import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService service;

    @GetMapping("/{parkingBoyId}/parking-lots")
    public ResponseEntity<List<ParkingLot>> getAllParkingLotOnParkingBoy(@PathVariable long parkingBoyId){

        return ResponseEntity.ok(service.getAllParkingLotOnParkingBoy(parkingBoyId));
    }

    @PostMapping
    public ResponseEntity<ParkingBoy> tagParkingBoy(@RequestBody ParkingBoy parkingBoy){

        return ResponseEntity.ok(service.saveParkingBoy(parkingBoy));
    }

    @GetMapping("/{parkingBoyId}/parking-orders")
    public ResponseEntity<List<ParkingOrder>> getAllParkingOrdersOfParkingBoy(@PathVariable long parkingBoyId){
        ParkingBoy parkingBoy = service.findOneById(parkingBoyId);
        return ResponseEntity.ok(service.getAllParkingOrdersOfParkingBoy(parkingBoy));
    }

    @GetMapping
    public ResponseEntity<List<ParkingBoyDto>> getAllParkingBoys(){
        return ResponseEntity.ok(service.findAllParkingBoys());
    }

    @PutMapping("/{parkingBoyId}")
    public ParkingBoy changeParkingBoyStatus(@PathVariable long parkingBoyId, @RequestBody ParkingBoy parkingBoy){

        return service.changeParkingBoyStatus(parkingBoyId, parkingBoy.getStatus());
    }


}
