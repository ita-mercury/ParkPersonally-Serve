package com.parkpersonally.controller;

import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService service;

    @PutMapping("/{parkingLotId}")
    public ParkingLotDto updateParkingLot(@PathVariable long parkingLotId, @RequestBody ParkingLot parkingLot) {

        return service.updateParkingLot(parkingLotId, parkingLot);
    }

    @GetMapping
    public List<ParkingLotDto> getAllParkingLots() {
        List<ParkingLot> parkingLots = service.findParkingLots();
        List<ParkingLotDto> parkingLotDtos = new ArrayList<>();
        for(ParkingLot parkingLot:parkingLots){
            parkingLotDtos.add(new ParkingLotDto(parkingLot));
        }
        return parkingLotDtos;
    }

    @PostMapping
    public ParkingLotDto addParkingLot(@RequestBody ParkingLot parkingLot) {
        return service.saveService(parkingLot);
    }

    @PatchMapping("/{parkingLotId}")
    public ParkingLotDto changeParkingLotStatus(@PathVariable long parkingLotId, @RequestBody ParkingLot parkingLot){
        return service.changeParkingLotStatus(parkingLotId, parkingLot);
    }
}
