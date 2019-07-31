package com.parkpersonally.controller;

import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.service.AdministratorService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @PutMapping("/parking-boys/{parkingBoyId}")
    public ResponseEntity<ParkingBoy> updateParkingBoyOfAdministrator(@PathVariable long parkingBoyId, @RequestBody ParkingBoy parkingBoy){
        return ResponseEntity.ok(administratorService.updateParkingBoyOfAdministrator(parkingBoyId,parkingBoy));
    }

    @PutMapping("/managers/{managerId}")
    public ResponseEntity<Manager> updateManagerOfAdministrator(@PathVariable long managerId , @RequestBody Manager manager){
        return ResponseEntity.ok(administratorService.updateManagerOfAdministrator(managerId,manager));
    }
    @GetMapping("/managers/unmatchedParkingLots")
    public  ResponseEntity getAllUnmatchedParkingLots(){
        return ResponseEntity.ok(administratorService.findUnmatchedParkingLots());
    }
    @GetMapping("/managers/unmatchedParkingBoys")
     public  ResponseEntity getAllUnmatchedParkingBoys(){
        return  ResponseEntity.ok(administratorService.findUnmatchedParkingBoys());
    }



}

