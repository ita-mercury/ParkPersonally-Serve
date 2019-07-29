package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingBoyRepository;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("ParkingBoyService")
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingOrderService parkingOrderService;

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
        ParkingBoy parkingBoy = findOneById(id);
        parkingBoy.setStatus(status);

        return parkingBoyRepository.save(parkingBoy);
    }

    public ParkingBoy saveParkingBoy(ParkingBoy parkingBoy){
        return parkingBoyRepository.save(parkingBoy);
    }

    public List<ParkingOrder> getAllParkingOrdersOfParkingBoy(ParkingBoy parkingBoy){
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

}
