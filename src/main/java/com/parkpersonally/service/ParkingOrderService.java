package com.parkpersonally.service;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service("parkingOrderService")
public class ParkingOrderService {


    private final ParkingOrderRepository repository;

    public ParkingOrder createParkingOrder(@Valid ParkingOrder order){

        return repository.save(order);
    }


    public ParkingOrderService(ParkingOrderRepository repository) {
        this.repository = repository;
    }
}
