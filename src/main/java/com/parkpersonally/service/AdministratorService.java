package com.parkpersonally.service;

import com.parkpersonally.dto.ManagerDto;
import com.parkpersonally.dto.ParkingBoyDto;
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


    public ParkingBoyDto updateParkingBoyOfAdministrator(long parkingBoyId, ParkingBoy parkingBoy) {
        ParkingBoy updateParkingBoy = parkingBoyRepository.save(parkingBoy);
        ParkingBoyDto parkingBoyDto = new ParkingBoyDto(updateParkingBoy);
        return parkingBoyDto;
    }

    public Manager updateManagerOfAdministrator(long managerId, Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> findAllManager() {
        return managerRepository.findAll();
    }
    public List<ParkingLot> findUnmatchedParkingLots() {
        List<ManagerDto> managers=managerService.findAllManagers();
        List<ParkingLot> parkingLots=new ArrayList<>();
        for (ManagerDto managerDto:managers){
            parkingLots.addAll( managerDto.getParkingLots());
        }
        List<ParkingLot> listParkingLots=parkingLotService.findParkingLots();
        return listParkingLots
                .stream()
                .filter(n -> !parkingLots.contains(n))
                .collect(Collectors.toList());
    }

    public List<ParkingBoyDto> findUnmatchedParkingBoys() {
        List<ManagerDto> managers=managerService.findAllManagers();
        List<ParkingBoyDto> parkingBoys=new ArrayList<>();
        for (ManagerDto managerDto:managers){
            List<ParkingBoyDto> parkingBoyDtos = new ArrayList<>();
            for(ParkingBoy parkingBoy : managerDto.getParkingBoys()){
                parkingBoyDtos.add(new ParkingBoyDto(parkingBoy));
            }
            parkingBoys.addAll(parkingBoyDtos);
        }
        List<ParkingBoyDto> listParkingBoys=parkingBoyService.findAllParkingBoys();
        return  listParkingBoys
                .stream()
                .filter(n-> !parkingBoys.contains(n))
                .collect(Collectors.toList());
    }
    public Manager saveManager(Manager manager) {
        return managerService.saveManager(manager);
    }

    public ParkingBoy saveParkingBoy(ParkingBoy parkingBoy) {
        return null;
    }
}
