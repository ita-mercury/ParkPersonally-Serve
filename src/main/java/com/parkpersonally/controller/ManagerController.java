package com.parkpersonally.controller;

import com.parkpersonally.dto.ManagerDto;
import com.parkpersonally.dto.ParkingBoyDto;
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
    public List<ParkingLotDto> getAllParkingLotOnManager(@PathVariable long managerId){
        return managerService.getAllParkingLotOnManager(managerId);
    }

    @GetMapping("/{managerId}/parking-boys")
    public List<ParkingBoyDto> getAllParkingBoys(@PathVariable long managerId){
        return managerService.getParkingBoys(managerId);
    }

    @PutMapping("/{managerId}/parking-boys/{parkingBoyId}/parking-lots")
    public ParkingBoyDto allocateParkingLots(@PathVariable long managerId, @PathVariable long parkingBoyId, @RequestBody List<ParkingLot> parkingLots) {
        return new ParkingBoyDto(managerService.allocateParkingLots(managerId, parkingBoyId, parkingLots));
    }
    @PostMapping
    public ManagerDto createManager(@RequestBody Manager manager){
        return new ManagerDto(managerService.saveManager(manager));
    }
    @PutMapping("/{managerId}/parking-boys/{parkingBoyId}/tags")
    public ParkingBoyDto tagParkingBoy(@PathVariable long managerId, @PathVariable long parkingBoyId, @RequestBody List<Tag> tags){
        return new ParkingBoyDto(managerService.tagParkingBoy(managerId,parkingBoyId,tags));
    }

    @GetMapping
    public List<ManagerDto> getAllManagers(){
        return managerService.findAllManagers();
    }

}


