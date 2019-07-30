package com.parkpersonally.controller;

import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.Tag;
import com.parkpersonally.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/managers/{managerId}/parking-lots")
    public ResponseEntity<List<ParkingLot>> getAllParkingLotOnManeger(@PathVariable long managerId){
        return ResponseEntity.ok(managerService.getAllParkingLotOnManager(managerId));
    }

    @GetMapping("/managers/{managerId}/parking-boys")
    public ResponseEntity<List<ParkingBoy>> getAllParkingBoys(@PathVariable long managerId){
        return ResponseEntity.ok(managerService.getParkingBoys(managerId));
    }

    @PutMapping("/managers/{managerId}/parking-boys/{parkingBoyId}/parking-lots")
    public ParkingBoy allocateParkingLots(@PathVariable long managerId, @PathVariable long parkingBoyId, @RequestBody List<ParkingLot> parkingLots) {
        return managerService.allocateParkingLots(managerId, parkingBoyId, parkingLots);
    }
    @PostMapping("/managers")
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager){
        return ResponseEntity.ok(managerService.saveManager(manager));
    }
    @PutMapping("/managers/{managerId}/parking-boys/{parkingBoyId}/tags")
    public ParkingBoy tagParkingBoy(@PathVariable long managerId, @PathVariable long parkingBoyId, @RequestBody List<Tag> tags){
        return managerService.tagParkingBoy(managerId,parkingBoyId,tags);
    }
}


