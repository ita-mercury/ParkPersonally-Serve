package com.parkpersonally.controller;

import com.parkpersonally.dto.ParkingLotDto;
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
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/{managerId}/parking-lots")
    public ResponseEntity<List<ParkingLot>> getAllParkingLotOnManager(@PathVariable long managerId){
        return ResponseEntity.ok(managerService.getAllParkingLotOnManager(managerId));
    }

    @GetMapping("/{managerId}/parking-boys")
    public ResponseEntity<List<ParkingBoy>> getAllParkingBoys(@PathVariable long managerId){
        return ResponseEntity.ok(managerService.getParkingBoys(managerId));
    }

    @PutMapping("/{managerId}/parking-boys/{parkingBoyId}/parking-lots")
    public ParkingBoy allocateParkingLots(@PathVariable long managerId, @PathVariable long parkingBoyId, @RequestBody List<ParkingLot> parkingLots) {
        return managerService.allocateParkingLots(managerId, parkingBoyId, parkingLots);
    }
    @PostMapping
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager){
        return ResponseEntity.ok(managerService.saveManager(manager));
    }
    @PutMapping("/{managerId}/parking-boys/{parkingBoyId}/tags")
    public ParkingBoy tagParkingBoy(@PathVariable long managerId, @PathVariable long parkingBoyId, @RequestBody List<Tag> tags){
        return managerService.tagParkingBoy(managerId,parkingBoyId,tags);
    }

    @GetMapping("/managers/{managerId}/parking-boys/like")
    public ResponseEntity<List<ParkingBoy>> findByNameLike(@RequestParam String name,@PathVariable long id){
        return ResponseEntity.ok(managerService.findByNameLike(name,id));
    }

    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers(){
        return ResponseEntity.ok(managerService.findAllManagers());
    }

}


