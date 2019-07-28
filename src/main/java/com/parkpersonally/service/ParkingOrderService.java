package com.parkpersonally.service;


import com.parkpersonally.dto.OrderComment;
import com.parkpersonally.exception.GetParkingOrderException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.exception.ParkingLotIsFullException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service("parkingOrderService")
public class ParkingOrderService {

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ParkingBoyService parkingBoyService;

    private final ParkingOrderRepository repository;

    public ParkingOrder createParkingOrder(@Valid ParkingOrder order){

        return repository.save(order);
    }


    public ParkingOrderService(ParkingOrderRepository repository) {
        this.repository = repository;
    }

    public ParkingOrder findOrderById(long parkingOrderId) {
        ParkingOrder parkingOrder = repository.findById(parkingOrderId).orElseThrow(()->new NoSuchParkingOrderException("抱歉，没有查到相应订单"));
        return parkingOrder;
    }

    public OrderComment appraiseOrder(long id, ParkingOrder parkingOrder) {
        ParkingOrder targetOrder = repository.findById(id).orElseThrow(() -> new NoSuchParkingOrderException("抱歉,没有查到该订单"));

        targetOrder.setComments(parkingOrder.getComments());

        repository.save(targetOrder);

        OrderComment orderComment = new OrderComment(id,targetOrder.getComments());

        return orderComment;
    }


    public List<ParkingOrder> getAllParkingOrdersOfParkingBoy(ParkingBoy parkingBoy, int type, int status) {
        List<ParkingOrder> allParkingOrders = repository.findAllByTypeAndStatusOrderByCreateTimeAsc(type, status);

        if (allParkingOrders != null || allParkingOrders.size() != 0) {

            List<ParkingOrder> allParkingOrdersWithoutTags = allParkingOrders.stream()
                    .filter(parkingOrder -> parkingOrder.getTags().size() == 0)
                    .collect(Collectors.toList());

            if (parkingBoy.getTags().size() != 0) {

                List<ParkingOrder> parkingBoyMeetsParkingOrdersTags = repository.findDistinctByTagsIsIn(parkingBoy.getTags());

                List<Long> parkingBoyMeetsParkingOrdersTagsId = parkingBoyMeetsParkingOrdersTags.stream()
                        .mapToLong(ParkingOrder::getId)
                        .boxed().collect(Collectors.toList());

                if (parkingBoyMeetsParkingOrdersTags.size() != 0) {

                    return allParkingOrders.stream()
                            .filter(parkingOrder -> parkingOrder.getTags().size() == 0
                                    || parkingBoyMeetsParkingOrdersTagsId.contains(parkingOrder.getId()))
                            .collect(Collectors.toList());

                } else {

                    return allParkingOrdersWithoutTags;

                }
            } else {

                return allParkingOrdersWithoutTags;

            }
        } else {

            return null;

        }
    }
    @Transactional
    public ParkingOrder parkingBoyGetParkingOrder(long orderId, ParkingBoy parkingBoy){
        parkingBoy = validateParkingLotTheRest(parkingBoy);

        ParkingOrder order = validateOrderStatus(orderId);
        order.setStatus(ParkingOrder.ORDER_STATUS_BE_ACCEPTED);
        order.setParkingBoy(parkingBoy);

        return repository.save(order);
    }

    public ParkingBoy validateParkingLotTheRest(ParkingBoy parkingBoy){
        parkingBoy = parkingBoyService.findOneById(parkingBoy.getId());
        if (parkingBoy.getParkingLots().stream()
                .filter(parkingLot -> parkingLot.getRestCapacity() != 0)
                .collect(Collectors.toList())
                .size() == 0) throw new ParkingLotIsFullException("你所管理的停车场已满");

        return parkingBoy;
    }

    public ParkingOrder validateOrderStatus(long orderId){
        ParkingOrder order = repository.findById(orderId).get();
        if (order.getStatus() != 1) throw new GetParkingOrderException("该订单已被其他人接取");

        return order;
    }

    public ParkingOrder updateParkingOrderStatus(long parkingOrderId, ParkingOrder parkingOrder) {

        parkingOrder.setStatus(ParkingOrder.ORDER_STATUS_COMPLETE);
        ParkingLot parkingLot = parkingOrder.getParkingLot();
        int type = parkingOrder.getType();
        if (type==ParkingOrder.ORDER_TYPE_PARK_CAR) {
            parkingLot.setRestCapacity(parkingLot.getRestCapacity()-1);
        } else {
            parkingLot.setRestCapacity(parkingLot.getRestCapacity()+1);
        }

        parkingLot = parkingLotService.saveService(parkingLot);

        parkingOrder.setParkingLot(parkingLot);
        parkingOrder = repository.save(parkingOrder);

        return parkingOrder;
    }

    public void setParkingBoyService(ParkingBoyService parkingBoyService) {
        this.parkingBoyService = parkingBoyService;
    }
}
