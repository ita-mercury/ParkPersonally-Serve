package com.parkpersonally.controller;

import com.parkpersonally.dto.ManagerDto;
import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.AdministratorService;
import com.parkpersonally.service.ManagerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Data
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @PutMapping("/parking-boys/{parkingBoyId}")
    public ParkingBoyDto updateParkingBoyOfAdministrator(@PathVariable long parkingBoyId, @RequestBody ParkingBoy parkingBoy){
        return administratorService.updateParkingBoyOfAdministrator(parkingBoyId,parkingBoy);
    }

    @PutMapping("/managers/{managerId}")
    public ManagerDto updateManagerOfAdministrator(@PathVariable long managerId , @RequestBody Manager manager){
        return new ManagerDto(administratorService.updateManagerOfAdministrator(managerId,manager));
    }
    @GetMapping("/managers/unmatchedParkingLots")
    public List<ParkingLotDto> getAllUnmatchedParkingLots(){
        List<ParkingLot> parkingLots = administratorService.findUnmatchedParkingLots();
        List<ParkingLotDto> parkingLotDtos = new ArrayList<>();
        for(ParkingLot parkingLot:parkingLots){
            parkingLotDtos.add(new ParkingLotDto(parkingLot));
        }
        return parkingLotDtos;
    }
    @GetMapping("/managers/unmatchedParkingBoys")
     public  List<ParkingBoyDto> getAllUnmatchedParkingBoys(){
        return  administratorService.findUnmatchedParkingBoys();
    }
    @PostMapping("/managers")
    public  ManagerDto createManagerOfAdministrator(@RequestBody Manager manager){
        return new ManagerDto(administratorService.saveManager(manager));
    }
    @PostMapping("/parking-Boys")
    public  ParkingBoyDto createParkingBoy(@RequestBody ParkingBoy parkingBoy){
        return  new ParkingBoyDto(administratorService.saveParkingBoy(parkingBoy));
    }



}

