package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ParkingBoyService parkingBoyService;


    public List<ParkingLot> getAllParkingLotOnManager(long managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到manager"));
        return manager.getParkingLots();
    }

    public List<ParkingBoy> getParkingBoys(long managerId) {
       Manager manager = managerRepository.findById(managerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到manager"));
        return  manager.getParkingBoys();
    }

    public ParkingBoy allocateParkingLots(long managerId, long parkingBoyId, List<ParkingLot> parkingLots) {
        ParkingBoy updateParkingBoy = parkingBoyService.findOneById(parkingBoyId);
        updateParkingBoy.setParkingLots(parkingLots);
        return parkingBoyService.saveParkingBoy(updateParkingBoy);
    }

    public ManagerRepository getManagerRepository() {
        return managerRepository;
    }

    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ParkingBoyService getParkingBoyService() {
        return parkingBoyService;
    }

    public void setParkingBoyService(ParkingBoyService parkingBoyService) {
        this.parkingBoyService = parkingBoyService;
    }
    public Manager saveManager(Manager manager) {
        return  managerRepository.save(manager);
    }

    public ParkingBoy tagParkingBoy(long managerId, long parkingBoyId, List<Tag> tags) {
        ParkingBoy updateParkingBoy = parkingBoyService.findOneById(parkingBoyId);
        updateParkingBoy.setTags(tags);
        return parkingBoyService.saveParkingBoy(updateParkingBoy);
    }

    public List<Manager> findAllManagers() {
        return managerRepository.findAll();
    }
}
