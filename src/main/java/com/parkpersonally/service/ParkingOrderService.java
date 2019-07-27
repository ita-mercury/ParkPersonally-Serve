package com.parkpersonally.service;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("parkingOrderService")
public class ParkingOrderService {

    @Autowired
    @Qualifier("parkingOrderRepository")
    private ParkingOrderRepository repository;

    public ParkingOrder createParkingOrder(ParkingOrder order){

        return null;
    }
}
