package com.parkpersonally.service;

import com.parkpersonally.dto.ManagerDto;
import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ManagerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ParkingBoyService parkingBoyService;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;


    public List<ParkingLotDto> getAllParkingLotOnManager(long managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到manager"));
        List<ParkingLotDto> result = new ArrayList<>();
        for (ParkingLot item : manager.getParkingLots())
            result.add(new ParkingLotDto(item));

        return result;
    }
    public List<ParkingBoyDto> getParkingBoys(long managerId) {
       Manager manager = managerRepository.findById(managerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到manager"));
       List<ParkingBoyDto> result = new ArrayList<>();
       for(ParkingBoy item : manager.getParkingBoys()){
           result.add(new ParkingBoyDto(item));
       }
        return  result;
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

    public List<ManagerDto> findAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        List<ManagerDto> result = new ArrayList<>();
        for(Manager item : managers){
            result.add(new ManagerDto(item));
        }
        return result;
    }

    public List<ParkingBoy> findByNameLike(String name,long id){
        Manager manager = managerRepository.findById(id).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到manager"));
        List<ParkingBoy> parkingBoys = manager.getParkingBoys();

        List<ParkingBoy> parkingBoys1 = parkingBoyRepository.findByNameLike("%"+name+"%");

        return parkingBoys1.stream().filter(x->parkingBoys.contains(x)).collect(Collectors.toList());

    }
}
