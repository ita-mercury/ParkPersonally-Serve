package com.parkpersonally.service;

import com.parkpersonally.exception.*;
import com.parkpersonally.model.Comment;
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

    public ParkingOrder createParkingOrder(@Valid ParkingOrder order) {
        if (order.getType() == ParkingOrder.ORDER_TYPE_PARK_CAR && order.getStatus() == ParkingOrder.ORDER_STATUS_NOT_BE_ACCEPTED)
            return repository.save(order);
        else if (order.getStatus() == ParkingOrder.ORDER_STATUS_PARK_CAR_COMPLETE)
            return createFetchOrderModel(order);

        throw new CreateParkingOrderException("创建订单失败");
    }

    private ParkingOrder createFetchOrderModel(ParkingOrder parkCarOrder) {
        parkCarOrder = findOrderById(parkCarOrder.getId());

        if (parkingLotService.findOneById(parkCarOrder.getParkingLot().getId()).getStatus() == ParkingLot.LOT_STATUS_FREEZE)
            throw new CreateParkingOrderException("由于该停车场已注销, 无法创建取车订单");

        ParkingOrder fetchCarOrder = new ParkingOrder(ParkingOrder.ORDER_STATUS_NOT_BE_ACCEPTED,
                ParkingOrder.ORDER_TYPE_FETCH_CAR,
                parkCarOrder.getPositionNumber(),
                parkCarOrder.getFetchCarAddress());

        fetchCarOrder.setParkingLot(parkCarOrder.getParkingLot());
        fetchCarOrder.setCustomer(parkCarOrder.getCustomer());

        parkCarOrder.setStatus(ParkingOrder.ORDER_STATUS_PARK_CAR_AND_START_FETCH_CAR);
        repository.save(parkCarOrder);

        return repository.save(fetchCarOrder);
    }

    public List<ParkingOrder> findAllParkingOrdersOfParkingBoy(ParkingBoy parkingBoy){
        return repository.findAllByParkingBoy(parkingBoy);
    }
    public ParkingOrderService(ParkingOrderRepository repository) {
        this.repository = repository;
    }

    public ParkingOrder findOrderById(long parkingOrderId) {
        ParkingOrder parkingOrder = repository.findById(parkingOrderId).orElseThrow(() -> new NoSuchParkingOrderException("抱歉，没有查到相应订单"));
        return parkingOrder;
    }

    public ParkingOrder appraiseOrder(long id, Comment comment) {
        ParkingOrder targetOrder = repository.findById(id).orElseThrow(() -> new NoSuchParkingOrderException("抱歉,没有查到该订单"));

        targetOrder.setComment(comment);

        return repository.save(targetOrder);
    }


    public List<ParkingOrder> filterParkingOrders(ParkingBoy parkingBoy, int type, int status) {
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
    public ParkingOrder parkingBoyGetParkingOrder(long orderId, ParkingBoy parkingBoy) {
        parkingBoy = parkingBoyService.findOneById(parkingBoy.getId());

        parkingBoy = validateParkingBoyStatus(parkingBoy);
        parkingBoy = validateParkingLotTheRest(parkingBoy);
        ParkingOrder order = validateOrderStatus(orderId);

        order.setStatus(ParkingOrder.ORDER_STATUS_BE_ACCEPTED);
        parkingBoy = parkingBoyService.changeParkingBoyStatus(parkingBoy.getId(),ParkingBoy.PARKING_BOY_STATUS_BUSY);
        // todo 可能不需要set
        order.setParkingBoy(parkingBoy);

        return repository.save(order);
    }

    public ParkingBoy validateParkingLotTheRest(ParkingBoy parkingBoy) {
        if (parkingBoy.getParkingLots().stream()
                .filter(parkingLot -> parkingLot.getStatus() != ParkingLot.LOT_STATUS_FREEZE &&
                        parkingLot.getRestCapacity() != 0)
                .collect(Collectors.toList())
                .size() == 0) throw new ParkingLotIsFullException("你所管理的停车场已满");

        return parkingBoy;
    }

    public ParkingOrder validateOrderStatus(long orderId) {
        ParkingOrder order = repository.findById(orderId).get();
        if (order.getStatus() != 1) throw new GetParkingOrderException("该订单已被其他人接取");

        return order;
    }

    private ParkingBoy validateParkingBoyStatus(ParkingBoy parkingBoy){
        if (parkingBoy.getStatus() != ParkingBoy.PARKING_BOY_STATUS_FREE){
            if (parkingBoy.getStatus() == ParkingBoy.PARKING_BOY_STATUS_BUSY)
                throw new ParkingBoyHasAOrderException("抢单失败，你已经有一个订单在进行");

            if (parkingBoy.getStatus() == ParkingBoy.PARKING_BOY_STATUS_FREEZE)
                throw new ParkingBoyIsIllegalException("抢单失败，你的账号已被冻结");
        }

        return parkingBoy;
    }

    public ParkingOrder updateParkingOrderStatus(long parkingOrderId, ParkingOrder parkingOrder) {
        switch (parkingOrder.getType()) {
            case ParkingOrder.ORDER_TYPE_PARK_CAR: {
                parkingOrder = changeParkCarOrderStatusAndLot(parkingOrder);
                break;
            }
            case ParkingOrder.ORDER_TYPE_FETCH_CAR: {
                parkingOrder = changeFetchCarOrderStatusAndLot(parkingOrder);
                break;
            }
        }
        parkingLotService.saveService(parkingOrder.getParkingLot());

        return repository.save(parkingOrder);
    }

    private ParkingOrder changeParkCarOrderStatusAndLot(ParkingOrder parkingOrder) {
        ParkingLot parkingLot = parkingOrder.getParkingLot();

        parkingOrder.setStatus(ParkingOrder.ORDER_STATUS_PARK_CAR_COMPLETE);
        parkingLot.setRestCapacity(parkingLot.getRestCapacity() - 1);

        ParkingBoy parkingBoy =  parkingBoyService.changeParkingBoyStatus(parkingOrder.getParkingBoy().getId(),
                ParkingBoy.PARKING_BOY_STATUS_FREE);

        parkingOrder.setParkingBoy(parkingBoy);

        return parkingOrder;
    }

    private ParkingOrder changeFetchCarOrderStatusAndLot(ParkingOrder parkingOrder) {
        ParkingLot parkingLot = parkingOrder.getParkingLot();

        switch (parkingOrder.getStatus()) {
            case ParkingOrder.ORDER_STATUS_BE_ACCEPTED: {
                parkingOrder.setStatus(ParkingOrder.ORDER_STATUS_PARKING_BOY_FETCH_CAR);
                parkingLot.setRestCapacity(parkingLot.getRestCapacity() + 1);
                break;
            }
            case ParkingOrder.ORDER_STATUS_PARKING_BOY_FETCH_CAR: {
                parkingOrder.setStatus(ParkingOrder.ORDER_STATUS_CUSTOMER_CHECK);
                break;
            }
        }
        ParkingBoy parkingBoy =  parkingBoyService.changeParkingBoyStatus(parkingOrder.getParkingBoy().getId(),
                ParkingBoy.PARKING_BOY_STATUS_FREE);

        parkingOrder.setParkingBoy(parkingBoy);

        return parkingOrder;
    }

    public int CountProcessingParkingOrderByParkingBoyId(long parkingBoyId){

        return repository.countProcessingParkingOrderByParkingBoyId(parkingBoyId);
    }

    public int countProcessingParkingOrderByParkingLotId(long parkingLotId){

        return repository.countProcessingParkingOrderByParkingLotId(parkingLotId);
    }

    public void setParkingBoyService (ParkingBoyService parkingBoyService){
        this.parkingBoyService = parkingBoyService;
    }

    public void setParkingLotService(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }
}
