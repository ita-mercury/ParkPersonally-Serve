package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(managerService.getParkingboys(managerId));
    }

}
