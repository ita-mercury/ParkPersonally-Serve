package com.parkpersonally.service;

import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.exception.ChangeParkingLotStatusException;
import com.parkpersonally.exception.NoSuchParkingLotException;
import com.parkpersonally.exception.UpdateParkingLotCapacitySmallerException;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.ParkingLotRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository repository;

    @Autowired
    private ParkingOrderService parkingOrderService;

    @Transactional
    public ParkingLotDto saveService(ParkingLot parkingLot){
        return new ParkingLotDto(repository.save(parkingLot));
    }

    @Transactional
    public ParkingLotDto updateParkingLot(long id, ParkingLot updateParkingLot){
        ParkingLot parkingLot = repository.findById(id)
                .orElseThrow(() -> new NoSuchParkingLotException("没有找到对应的停车场"));

        // todo 没有解决并发问题
        if (updateParkingLot.getCapacity() >= parkingLot.getCapacity()) {
            parkingLot.setRestCapacity(updateParkingLot.getCapacity()-(parkingLot.getCapacity()-parkingLot.getRestCapacity()));
            parkingLot.setCapacity(updateParkingLot.getCapacity());
        }else throw new UpdateParkingLotCapacitySmallerException("无法把停车场容量缩小");

        parkingLot.setName(updateParkingLot.getName());

        return new ParkingLotDto(repository.save(parkingLot));
    }

    public List<ParkingLot> findParkingLots() {
        return repository.findAll();
    }

    @Transactional
    public ParkingLotDto changeParkingLotStatus(long id, ParkingLot updateParkingLot){
        ParkingLot parkingLot = repository.findById(id)
                .orElseThrow(() -> new NoSuchParkingLotException("没有找到对应的停车场"));

        switch (updateParkingLot.getStatus()){
            case ParkingLot.LOT_STATUS_FREEZE: {
                if (parkingOrderService.countProcessingParkingOrderByParkingLotId(parkingLot.getId()) > 0)
                    throw new ChangeParkingLotStatusException("该停车场存在还在进行的订单，无法冻结");
                parkingLot.setStatus(switchParkingLotStatusFreeze(parkingLot));
                break;
            }
            case ParkingLot.LOT_STATUS_NORMAL: {
                parkingLot.setStatus(updateParkingLot.getStatus());
                break;
            }
            default: throw new ChangeParkingLotStatusException("传入的目标状态不合法");
        }

        return new ParkingLotDto(repository.save(parkingLot));
    }

    private int switchParkingLotStatusFreeze(ParkingLot parkingLot){
        if (parkingLot.getStatus() == ParkingLot.LOT_STATUS_FREEZE) return ParkingLot.LOT_STATUS_NORMAL;
        else return ParkingLot.LOT_STATUS_FREEZE;
    }

    public ParkingLot findOneById(long id){
        return repository.findById(id).orElseThrow(() -> new NoSuchParkingLotException("没有找到对应的停车场"));
    }


    public ParkingLotService() {
    }

    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.repository = parkingLotRepository;
    }
}
