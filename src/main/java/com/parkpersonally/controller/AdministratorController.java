package com.parkpersonally.controller;

import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.service.AdministratorService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @PutMapping("/administratorController/parking-boys/{parkingBoyId}")
    public ResponseEntity<ParkingBoy> updateParkingBoyOfAdministrator(@PathVariable long parkingBoyId, @RequestBody ParkingBoy parkingBoy){
        return ResponseEntity.ok(administratorService.updateParkingBoyOfAdministrator(parkingBoyId,parkingBoy));
    }
}
