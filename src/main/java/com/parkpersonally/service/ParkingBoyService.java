package com.parkpersonally.service;

import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.exception.ChangeParkingBoyStatusException;
import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingBoyRepository;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.comparator.Comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ParkingBoyService")
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingOrderService parkingOrderService;

    public List<ParkingLot> getAllParkingLotOnParkingBoy(long parkingBoyId) {
        ParkingBoy parkingBoy = parkingBoyRepository.findById(parkingBoyId).orElseThrow(()->new NoSuchParkingBoyException("抱歉,没有查到停车员"));
        return parkingBoy.getParkingLots()
                .stream()
                .filter((parkingLot) -> parkingLot.getStatus() != ParkingLot.LOT_STATUS_FREEZE)
                .collect(Collectors.toList());
    }

    public ParkingBoy findOneById(long id){
        ParkingBoy parkingBoy = parkingBoyRepository.findById(id).orElseThrow(
                () -> new NoSuchParkingBoyException("没有找到ParkingBoy信息")
        );
        return parkingBoy;
    }

    @Transactional
    public ParkingBoy   changeParkingBoyStatus(long id, int status){
        ParkingBoy parkingBoy = findOneById(id);
        switch (status){
            case ParkingBoy.PARKING_BOY_STATUS_FREEZE: {
                parkingBoy.setStatus(switchParkingBoyFreezeStatus(parkingBoy.getStatus(), id));
                break;
            }
            case ParkingBoy.PARKING_BOY_STATUS_FREE: {
                parkingBoy.setStatus(validateFreeze(parkingBoy, ParkingBoy.PARKING_BOY_STATUS_FREE));
                break;
            }
            case ParkingBoy.PARKING_BOY_STATUS_BUSY: {
                parkingBoy.setStatus(validateFreeze(parkingBoy, ParkingBoy.PARKING_BOY_STATUS_BUSY));
                break;
            }
            default: throw new ChangeParkingBoyStatusException("传入的目标状态不合法");
        }

        return parkingBoyRepository.save(parkingBoy);
    }

    @Transactional
    public ParkingBoy saveParkingBoy(ParkingBoy parkingBoy){
        return parkingBoyRepository.save(parkingBoy);
    }

    private int switchParkingBoyFreezeStatus(int status, long parkingBoyId){
        if (status == ParkingBoy.PARKING_BOY_STATUS_FREEZE) {
            if (parkingOrderService.CountProcessingParkingOrderByParkingBoyId(parkingBoyId) > 0)
                status = ParkingBoy.PARKING_BOY_STATUS_BUSY;
            else status = ParkingBoy.PARKING_BOY_STATUS_FREE;
        }else status = ParkingBoy.PARKING_BOY_STATUS_FREEZE;

        return status;
    }

    private int validateFreeze(ParkingBoy parkingBoy, int status){
        if (parkingBoy.getStatus() == ParkingBoy.PARKING_BOY_STATUS_FREEZE)
            return ParkingBoy.PARKING_BOY_STATUS_FREEZE;
        else return status;
    }

    public List<ParkingOrder> getAllParkingOrdersOfParkingBoy(long parkingBoyId){
         ParkingBoy parkingBoy = findOneById(parkingBoyId);
         return parkingOrderService
                .findAllParkingOrdersOfParkingBoy(parkingBoy)
                .stream()
                .sorted(Comparator.comparingLong(ParkingOrder::getCreateTime).reversed())
                .collect(Collectors.toList());
    }

    public ParkingBoyRepository getParkingBoyRepository() {
        return parkingBoyRepository;
    }

    public void setParkingBoyRepository(ParkingBoyRepository parkingBoyRepository) {
        this.parkingBoyRepository = parkingBoyRepository;
    }

    public ParkingOrderService getParkingOrderService() {
        return parkingOrderService;
    }

    public void setParkingOrderService(ParkingOrderService parkingOrderService) {
        this.parkingOrderService = parkingOrderService;
    }

    public List<ParkingBoyDto> findAllParkingBoys() {
        List<ParkingBoy> parkingBoys = parkingBoyRepository.findAll();
        List<ParkingBoyDto> result = new ArrayList<>();
        for(ParkingBoy parkingBoy : parkingBoys){
            result.add(new ParkingBoyDto(parkingBoy));
        }
        return result;
    }
}
