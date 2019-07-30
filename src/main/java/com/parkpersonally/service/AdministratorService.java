package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.Administrator;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.repository.AdministratorRepository;
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
    private ParkingBoyRepository parkingBoyRepository;

    public ParkingBoy updateParkingBoyOfAdministrator(long parkingBoyId, Administrator administrator) {
        List<ParkingBoy> parkingBoys = administrator.getParkingBoys();
        ParkingBoy parkingBoy =  parkingBoys.stream().filter(x->x.getId()==parkingBoyId).collect(Collectors.toList()).get(0);
        parkingBoyRepository.save(parkingBoy);
        administratorRepository.save(administrator);
        return parkingBoy;
    }

    public List<Manager> findAllManager() {
        return null;
    }
}
