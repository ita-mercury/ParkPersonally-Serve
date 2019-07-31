package com.parkpersonally.controller;

import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.dto.ParkingOrderDto;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService service;

    @GetMapping("/{parkingBoyId}/parking-lots")
    public List<ParkingLotDto> getAllParkingLotOnParkingBoy(@PathVariable long parkingBoyId){
        List<ParkingLot> parkingLots = service.getAllParkingLotOnParkingBoy(parkingBoyId);
        List<ParkingLotDto> parkingLotDtos = new ArrayList<>();
        for(ParkingLot item :parkingLots){
            parkingLotDtos.add(new ParkingLotDto(item));
        }
        return parkingLotDtos;
    }

    @PostMapping
    public ParkingBoyDto tagParkingBoy(@RequestBody ParkingBoy parkingBoy){

        return new ParkingBoyDto(service.saveParkingBoy(parkingBoy));
    }

    @GetMapping("/{parkingBoyId}/parking-orders")
    public List<ParkingOrderDto> getAllParkingOrdersOfParkingBoy(@PathVariable long parkingBoyId){
        List<ParkingOrder> parkingOrders = service.getAllParkingOrdersOfParkingBoy(parkingBoyId);
        List<ParkingOrderDto> parkingOrderDtos = new ArrayList<>();
        for(ParkingOrder parkingOrder:parkingOrders){
            parkingOrderDtos.add(new ParkingOrderDto(parkingOrder));
        }
        return parkingOrderDtos;
    }

    @GetMapping
    public List<ParkingBoyDto> getAllParkingBoys(){
        return service.findAllParkingBoys();
    }

    @PutMapping("/{parkingBoyId}")
    public ParkingBoyDto changeParkingBoyStatus(@PathVariable long parkingBoyId, @RequestBody ParkingBoy parkingBoy){

        return new ParkingBoyDto(service.changeParkingBoyStatus(parkingBoyId, parkingBoy.getStatus()));
    }


}
