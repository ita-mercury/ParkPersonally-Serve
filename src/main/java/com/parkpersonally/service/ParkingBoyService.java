package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingBoyRepository;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {

    private final ParkingBoyRepository parkingBoyRepository;

    private final ParkingOrderService parkingOrderService;


    public ParkingBoyService(ParkingBoyRepository parkingBoyRepository, ParkingOrderService parkingOrderService) {
        this.parkingBoyRepository = parkingBoyRepository;
        this.parkingOrderService = parkingOrderService;
    }

    public List<ParkingLot> getAllParkingLotOnParkingBoy(long parkingBoyId) {
        ParkingBoy parkingBoy = parkingBoyRepository.findById(parkingBoyId).orElseThrow(()->new NoSuchParkingBoyException("抱歉,没有查到停车员"));
        return parkingBoy.getParkingLots();
    }

    public ParkingBoy findOneById(long id){
        ParkingBoy parkingBoy = parkingBoyRepository.findById(id).orElseThrow(
                () -> new NoSuchParkingBoyException("没有找到ParkingBoy信息")
        );
        return parkingBoy;
    }

    public ParkingBoy changeParkingBoyStatus(long id, int status){
        ParkingBoy parkingBoy = parkingBoyRepository.findById(id).get();
        parkingBoy.setStatus(status);

        return parkingBoyRepository.save(parkingBoy);
    }

    public ParkingBoy saveParkingBoy(ParkingBoy parkingBoy){
        return parkingBoyRepository.save(parkingBoy);
    }

    public List<ParkingOrder> getAllParkingOrdersOfParkingBoy(ParkingBoy parkingBoy){
        return parkingOrderService.findAllParkingOrdersOfParkingBoy(parkingBoy);
    }
}
