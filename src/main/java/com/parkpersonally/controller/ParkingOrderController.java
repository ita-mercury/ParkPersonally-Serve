package com.parkpersonally.controller;

import com.parkpersonally.dto.ParkingOrderDto;
import com.parkpersonally.model.Comment;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingBoyService;
import com.parkpersonally.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parking-orders")
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @Autowired
    private ParkingBoyService parkingBoyService;


    @PostMapping
    public ParkingOrderDto createOrder(@RequestBody ParkingOrder order) {
        return new ParkingOrderDto(parkingOrderService.createParkingOrder(order));
    }
    @GetMapping("/{parkingOrderId}")
    public ParkingOrderDto getOrderById(@PathVariable long parkingOrderId){

        return  new ParkingOrderDto(parkingOrderService.findOrderById(parkingOrderId));
    }

    @GetMapping
    public List<ParkingOrderDto> getOrdersOfParkingBoy(@RequestParam("type")int type, @RequestParam("parkingBoyId") long parkingBoyId){
        ParkingBoy parkingBoy = parkingBoyService.findOneById(parkingBoyId);
        List<ParkingOrder> parkingOrders = parkingOrderService.filterParkingOrders(parkingBoy,type,1);
        List<ParkingOrderDto> parkingOrderDtos = new ArrayList<>();
        for(ParkingOrder parkingOrder:parkingOrders){
            parkingOrderDtos.add(new ParkingOrderDto(parkingOrder));
        }
        return parkingOrderDtos;
    }

    @PostMapping("/{ordersId}/comments")
    public ParkingOrderDto appraiseOrder(@PathVariable("ordersId")long id, @RequestBody Comment comment){
        return new ParkingOrderDto(parkingOrderService.appraiseOrder(id,comment));
    }

    @PutMapping("/{parkingOrderId}")
    public ParkingOrderDto updateParkingOrderStatus(@PathVariable long parkingOrderId,@RequestBody ParkingOrder parkingOrder){

        return  new ParkingOrderDto(parkingOrderService.updateParkingOrderStatus(parkingOrderId,parkingOrder));
    }

    @PostMapping("/{orderId}/parking-boy")
    public ParkingOrderDto parkingBoyGetParkingOrder(@PathVariable(name = "orderId") long orderId,
                                                                  @RequestBody ParkingBoy parkingBoy){
        return new ParkingOrderDto(parkingOrderService.parkingBoyGetParkingOrder(orderId, parkingBoy));
    }



}
