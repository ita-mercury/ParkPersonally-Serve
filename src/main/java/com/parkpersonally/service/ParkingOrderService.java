package com.parkpersonally.service;


import com.parkpersonally.dto.OrderComment;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingOrderRepository;
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
        ParkingOrder parkingOrder = repository.findById(parkingOrderId).orElseThrow(()->new NoSuchParkingOrderException("没有找到ParkingOrder信息"));
        return parkingOrder;
    }
    public OrderComment appraiseOrder(long id, ParkingOrder parkingOrder) {
        ParkingOrder targetOrder = repository.findById(id).orElseThrow(() -> new NoSuchParkingOrderException("抱歉,没有查到该订单"));
        targetOrder.setComments(parkingOrder.getComments());

        repository.save(targetOrder);

        OrderComment orderComment = new OrderComment(id,parkingOrder.getComments());

        return orderComment;
    }

    public ParkingOrder updateParkingOrder(ParkingOrder parkingOrder, long id) {
        return null;
    }
}
