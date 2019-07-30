package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.Administrator;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.repository.AdministratorRepository;
import com.parkpersonally.repository.ManagerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class AdministratorService {

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
}
