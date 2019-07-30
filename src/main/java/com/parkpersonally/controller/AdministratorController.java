package com.parkpersonally.controller;

import com.parkpersonally.model.Administrator;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.service.AdministratorService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @PutMapping("/admin/parking-boys/{parkingBoyId}")
    public ResponseEntity<ParkingBoy> updateParkingBoyOfAdministrator(@PathVariable long parkingBoyId, @RequestBody ParkingBoy parkingBoy){
        return ResponseEntity.ok(administratorService.updateParkingBoyOfAdministrator(parkingBoyId,parkingBoy));
    }

    @PutMapping("/admin/managers/{managerId}")
    public ResponseEntity<Manager> updateManagerOfAdministrator(@PathVariable long managerId , @RequestBody Manager manager){
        return ResponseEntity.ok(administratorService.updateManagerOfAdministrator(managerId,manager));
    }
    @GetMapping("/admin/managers")
    public  ResponseEntity getAllManagers(){
        return ResponseEntity.ok(administratorService.findAllManager());
    }

}
