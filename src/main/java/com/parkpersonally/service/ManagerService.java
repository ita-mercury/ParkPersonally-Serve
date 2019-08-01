package com.parkpersonally.service;

import com.parkpersonally.dto.ManagerDto;
import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.exception.ManagerFreezeFailException;
import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.*;
import com.parkpersonally.repository.ManagerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ParkingOrderService parkingOrderService;


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

    @Transactional
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
    @Transactional
    public Manager saveManager(Manager manager) {
        return  managerRepository.save(manager);
    }

    @Transactional
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

    public List<ParkingOrder> getAllParkingOrderOfManager(long managerId) {
        return parkingOrderService.getAllParkingOrderOfManager(managerId);
    }
    @Transactional
    public Manager freezeManager(long managerId, long replaceManagerId){
        Manager manager = managerRepository.findById(managerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到需要冻结的manager"));

        if (manager.getStatus() == Manager.MANAGER_STATUS_FREEZE) throw new ManagerFreezeFailException("该manager已被冻结, 无法再进行冻结操作");

        Manager replaceManager = managerRepository.findById(replaceManagerId).orElseThrow(()->new NoSuchManagerException("抱歉,没有查到接替的Manager"));

        if (replaceManager.getStatus() == Manager.MANAGER_STATUS_FREEZE) throw new ManagerFreezeFailException("接替的Manager处于冻结状态，操作失败");

        replaceManager.getParkingLots().addAll(manager.getParkingLots());
        replaceManager.getParkingBoys().addAll(manager.getParkingBoys());

        manager.getParkingBoys().clear();
        manager.getParkingLots().clear();

        manager = managerRepository.save(manager);
        managerRepository.save(replaceManager);

        return manager;
    }
}
