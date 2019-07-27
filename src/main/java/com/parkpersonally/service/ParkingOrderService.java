package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchOrderException;
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

    public ParkingOrder findOrderById(long parkingOrderId) {
        return null;
    }
    public ParkingOrder appraiseOrder(long id, ParkingOrder parkingOrder) {
        ParkingOrder targetOrder = repository.findById(id).orElseThrow(() -> new NoSuchOrderException("抱歉,没有查到该订单"));
        targetOrder.setComments(parkingOrder.getComments());
        return repository.save(targetOrder);
    }
}
