package com.parkpersonally.service;


import com.parkpersonally.dto.OrderComment;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
        ParkingOrder parkingOrder = repository.findById(parkingOrderId).orElseThrow(()->new NoSuchParkingOrderException("抱歉，没有查到相应订单"));
        return parkingOrder;
    }

    public OrderComment appraiseOrder(long id, ParkingOrder parkingOrder) {
        ParkingOrder targetOrder = repository.findById(id).orElseThrow(() -> new NoSuchParkingOrderException("抱歉,没有查到该订单"));

        targetOrder.setComments(parkingOrder.getComments());

        repository.save(targetOrder);

        OrderComment orderComment = new OrderComment(id,parkingOrder.getComments());

        return orderComment;
    }


    public List<ParkingOrder> getAllParkingOrdersOfParkingBoy(ParkingBoy parkingBoy, int type, int status) {
        List<ParkingOrder> allParkingOrders = repository.findAllByTypeAndStatusOrderByCreatTimeAsc(type, status);

        if (allParkingOrders != null) {

            List<ParkingOrder> allParkingOrdersWithoutTags = allParkingOrders.stream()
                    .filter(parkingOrder -> parkingOrder.getTags() == null)
                    .collect(Collectors.toList());

            if (parkingBoy.getTags() != null) {

                List<ParkingOrder> parkingBoyMeetsParkingOrdersTags = repository.findDistinctByTagsIsIn(parkingBoy.getTags());

                List<Long> parkingBoyMeetsParkingOrdersTagsId = parkingBoyMeetsParkingOrdersTags.stream()
                        .mapToLong(ParkingOrder::getId)
                        .boxed().collect(Collectors.toList());

                if (parkingBoyMeetsParkingOrdersTags != null) {

                    return allParkingOrders.stream()
                            .filter(parkingOrder -> parkingOrder.getTags() == null
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

}
