package com.parkpersonally.service;

import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository repository;

    public ParkingLot saveService(ParkingLot parkingLot){
        return repository.save(parkingLot);
    }
}
