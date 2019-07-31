package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.Administrator;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.AdministratorRepository;
import com.parkpersonally.repository.ManagerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class AdministratorService {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private ParkingBoyService parkingBoyService;

    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;


    public ParkingBoy updateParkingBoyOfAdministrator(long parkingBoyId, ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }

    public Manager updateManagerOfAdministrator(long managerId, Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> findAllManager() {
        return managerRepository.findAll();
    }
    public List<ParkingLot> findUnmatchedParkingLots() {
        List<Manager> managers=managerService.findAllManagers();
        List<ParkingLot> parkingLots=new ArrayList<>();
        for (Manager manager:managers){
            parkingLots.addAll( manager.getParkingLots());
        }
        List<ParkingLot> listParkingLots=parkingLotService.findParkingLots();
        return listParkingLots
                .stream()
                .filter(n -> !parkingLots.contains(n))
                .collect(Collectors.toList());
    }

    public List<ParkingBoy> findUnmatchedParkingBoys() {
        List<Manager> managers=managerService.findAllManagers();
        List<ParkingBoy> parkingBoys=new ArrayList<>();
        for (Manager manager:managers){
            parkingBoys.addAll(manager.getParkingBoys());
        }
        List<ParkingBoy> listParkingBoys=parkingBoyService.findAllParkingBoys();
        return  listParkingBoys
                .stream()
                .filter(n-> !parkingBoys.contains(n))
                .collect(Collectors.toList());
    }
}
