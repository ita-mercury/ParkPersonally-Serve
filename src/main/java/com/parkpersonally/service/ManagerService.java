package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


    public List<ParkingLot> getAllParkingLotOnManager(long managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到manager"));
        return manager.getParkingLots();
    }

    public List<ParkingBoy> getParkingBoys(long managerId) {
       Manager manager = managerRepository.findById(managerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到manager"));
        return  manager.getParkingBoys();
    }

    public ParkingBoy allocateParkingLots(long managerId, long parkingBoyId, ParkingBoy parkingBoy) {
        return null;
    }
    public Manager saveManager(Manager manager) {
        return  null;
    }
}
