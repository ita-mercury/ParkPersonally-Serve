package com.parkpersonally.service;


import com.parkpersonally.exception.GetParkingOrderException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.exception.ParkingLotIsFullException;
import com.parkpersonally.model.*;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingOrderServiceTest {

    private ParkingOrderService service;
    private ParkingOrderRepository repository;
    private ParkingBoyService parkingBoyService;
    private ParkingLotService parkingLotService;

    @Before
    public void initTest() {
        repository = mock(ParkingOrderRepository.class);
        service = new ParkingOrderService(repository);
        parkingBoyService = mock(ParkingBoyService.class);
        service.setParkingBoyService(parkingBoyService);
        parkingLotService = mock(ParkingLotService.class);
        service.setParkingLotService(parkingLotService);
    }

    @Test
    public void should_return_new_order_when_add_new_order(){
        // given
        ParkingOrder expect = new ParkingOrder(ParkingOrder.ORDER_TYPE_PARK_CAR, 20, "南方软件园");
        when(repository.save(any(ParkingOrder.class))).thenReturn(expect);
        // when
        ParkingOrder actual = service.createParkingOrder(expect);
        // then
        Assert.assertEquals(expect, actual);
    }

//    @Test
//    public void should_throw_exception_when_new_order_have_null_property() {
//        // given
//        ParkingOrder expect = new ParkingOrder();
//        expect.setPositionNumber(16);
//        // when
//        service.createParkingOrder(expect);
//        // then
//    }

    @Test
    public void should_return_Order_when_findOrderById(){
        //given
        Customer customer = new Customer();
        customer.setId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        ParkingOrder order = new ParkingOrder(1, 20, "南方软件园");
        order.setCustomer(customer);
        order.setTags(tags);

        given(repository.findById(anyLong())).willReturn(Optional.of(order));

        assertSame(order,service.findOrderById(1));
    }

    @Test
    public void should_return_Order_without_tags_when_parking_boy_without_tags(){
        //given
        Tag firstTag = new Tag("好看的");
        Tag secondTag = new Tag("爆炸");
        Tag thirdTag = new Tag("会唱rap");
        Tag fourthTag = new Tag("服务好的");
        Tag fifthTag = new Tag("小星星");



        ParkingBoy parkingBoy = new ParkingBoy("zhangsan","13");
        parkingBoy.setTags(new ArrayList<>());
        List<Tag> fristTags = new ArrayList<>();
        fristTags.add(firstTag);
        fristTags.add(secondTag);
        List<Tag> secondTags = new ArrayList<>();
        secondTags.add(thirdTag);
        secondTags.add(fourthTag);
        secondTags.add(firstTag);
        List<Tag> thirdTags = new ArrayList<>();
        thirdTags.add(fifthTag);

        List<ParkingOrder> allParkingOrders = new ArrayList<>();
        ParkingOrder firstParkingOrder = new ParkingOrder(1,1,11,"珠海");
        firstParkingOrder.setTags(new ArrayList<>());
        allParkingOrders.add(firstParkingOrder);
        ParkingOrder secondParkingOrder = new ParkingOrder(1,1,1,"珠海");
        secondParkingOrder.setTags(fristTags);
        allParkingOrders.add(secondParkingOrder);
        ParkingOrder thirdParkingOrder = new ParkingOrder(1,1,4,"珠海");
        thirdParkingOrder.setTags(secondTags);
        allParkingOrders.add(thirdParkingOrder);
        ParkingOrder fourthParkingOrder = new ParkingOrder(1,1,10,"珠海");
        fourthParkingOrder.setTags(thirdTags);
        allParkingOrders.add(fourthParkingOrder);

        given(repository.findAllByTypeAndStatusOrderByCreateTimeAsc(anyInt(),anyInt())).willReturn(allParkingOrders);

        assertSame(1,service.getAllParkingOrdersOfParkingBoy(parkingBoy,1,0).size());
    }

    @Test
    public void should_return_Orders_contain_confirm_tags_and_without_tags_when_parking_boy_with_tags() {
        //given
        Tag firstTag = new Tag("好看的");
        Tag secondTag = new Tag("爆炸");
        Tag thirdTag = new Tag("会唱rap");
        Tag fourthTag = new Tag("服务好的");
        Tag fifthTag = new Tag("小星星");


        ParkingBoy parkingBoy = new ParkingBoy("zhangsan", "13");

        List<Tag> fristTags = new ArrayList<>();
        fristTags.add(firstTag);
        fristTags.add(secondTag);
        parkingBoy.setTags(fristTags);
        List<Tag> secondTags = new ArrayList<>();
        secondTags.add(thirdTag);
        secondTags.add(fourthTag);
        secondTags.add(firstTag);
        List<Tag> thirdTags = new ArrayList<>();
        thirdTags.add(fifthTag);

        List<ParkingOrder> allParkingOrders = new ArrayList<>();
        List<ParkingOrder> parkingOrdersWithTags = new ArrayList<>();
        ParkingOrder firstParkingOrder = new ParkingOrder(1, 1, 11, "珠海");
        firstParkingOrder.setId(1);
        firstParkingOrder.setTags(new ArrayList<>());
        allParkingOrders.add(firstParkingOrder);
        ParkingOrder secondParkingOrder = new ParkingOrder(1, 1, 1, "珠海");
        secondParkingOrder.setId(2);
        secondParkingOrder.setTags(fristTags);
        allParkingOrders.add(secondParkingOrder);
        parkingOrdersWithTags.add(secondParkingOrder);
        ParkingOrder thirdParkingOrder = new ParkingOrder(1, 1, 4, "珠海");
        thirdParkingOrder.setId(3);
        thirdParkingOrder.setTags(secondTags);
        allParkingOrders.add(thirdParkingOrder);
        parkingOrdersWithTags.add(thirdParkingOrder);
        ParkingOrder fourthParkingOrder = new ParkingOrder(1, 1, 10, "珠海");
        fourthParkingOrder.setId(4);
        fourthParkingOrder.setTags(thirdTags);
        allParkingOrders.add(fourthParkingOrder);

        given(repository.findAllByTypeAndStatusOrderByCreateTimeAsc(anyInt(), anyInt())).willReturn(allParkingOrders);
        given(repository.findDistinctByTagsIsIn(parkingBoy.getTags())).willReturn(parkingOrdersWithTags);

        assertSame(3, service.getAllParkingOrdersOfParkingBoy(parkingBoy, 1, 0).size());
    }

    @Test(expected = NoSuchParkingOrderException.class)
    public void should_throw_exception_when_find_a_order_is_not_exist(){
        //given
        ParkingOrder parkingOrder = new ParkingOrder();
        given(repository.findById(anyLong())).willThrow(new NoSuchParkingOrderException("抱歉,该订单不存在"));
        //when
        service.appraiseOrder(1,parkingOrder.getComment());
        //then
    }
     // todo commit add comment to order test
    @Test
    public void should_return_order_comments_when_appraise_order(){

        ParkingOrder inputSaveOrder = new ParkingOrder();
        ParkingOrder expectOrder = new ParkingOrder();

        expectOrder.setId(1L);
        inputSaveOrder.setId(1L);
        Comment comment = new Comment(7.5, "司机会漂移");
        inputSaveOrder.setComment(comment);
        expectOrder.setComment(comment);

        given(repository.findById(anyLong())).willReturn(Optional.of(inputSaveOrder));
        given(repository.save(inputSaveOrder)).willReturn(expectOrder);

        assertEquals("司机会漂移", service.appraiseOrder(1, comment).getComment().getContent());
    }


    // todo commit complete park car
    @Test
    public void should_return_the_right_park_car_order_when_complete_park_car(){
        //given
        ParkingLot inputLot = new ParkingLot(1,"停车场1",50,20);
        ParkingLot expectLot = new ParkingLot(1, "停车场1", 50, 19);
        ParkingOrder input = new ParkingOrder(1,ParkingOrder.ORDER_STATUS_BE_ACCEPTED,1,24,inputLot);
        ParkingOrder expect = new ParkingOrder(1, ParkingOrder.ORDER_STATUS_PARK_CAR_COMPLETE, 1, 24, expectLot);

        given(parkingLotService.saveService(expectLot)).willReturn(expectLot);

        given(repository.save(expect)).willReturn(expect);

        assertSame(19,service.updateParkingOrderStatus(1,input).getParkingLot().getRestCapacity());

    }

    @Test
    public void should_return_parking_boy_when_parking_lots_of_parking_boy_is_not_full() {
        ParkingLot firstLot = new ParkingLot();
        ParkingLot secondLot = new ParkingLot();

        firstLot.setRestCapacity(1);
        secondLot.setRestCapacity(0);

        ParkingBoy parkingBoy = new ParkingBoy();

        parkingBoy.setParkingLots(new ArrayList<>());
        parkingBoy.getParkingLots().add(firstLot);
        parkingBoy.getParkingLots().add(secondLot);

        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);

        assertEquals(service.validateParkingLotTheRest(parkingBoy), parkingBoy);
    }

    @Test(expected = ParkingLotIsFullException.class)
    public void should_throw_ParkingLotIsFullException_when_parking_lots_of_parking_boy_is_full() {
        ParkingLot firstLot = new ParkingLot();
        ParkingLot secondLot = new ParkingLot();

        firstLot.setRestCapacity(0);
        secondLot.setRestCapacity(0);

        ParkingBoy parkingBoy = new ParkingBoy();

        parkingBoy.setParkingLots(new ArrayList<>());
        parkingBoy.getParkingLots().add(firstLot);
        parkingBoy.getParkingLots().add(secondLot);

        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);

        service.parkingBoyGetParkingOrder(anyLong(), parkingBoy);
    }

    @Test
    public void should_return_order_when_order_status_is_not_be_accepted() {
        ParkingOrder order = new ParkingOrder();
        order.setStatus(ParkingOrder.ORDER_STATUS_NOT_BE_ACCEPTED);

        given(repository.findById(anyLong())).willReturn(Optional.of(order));

        assertEquals(service.validateOrderStatus(anyLong()), order);

    }

    @Test(expected = GetParkingOrderException.class)
    public void should_throw_GetParkingOrderFailedException_when_order_status_is_be_accepted() {
        ParkingOrder order = new ParkingOrder();
        order.setStatus(ParkingOrder.ORDER_STATUS_BE_ACCEPTED);

        given(repository.findById(anyLong())).willReturn(Optional.of(order));

        service.validateOrderStatus(anyLong());

    }

    @Test
    public void should_return_order_when_parking_boy_get_order_success(){
        ParkingLot firstLot = new ParkingLot();
        ParkingLot secondLot = new ParkingLot();

        firstLot.setRestCapacity(1);
        secondLot.setRestCapacity(0);

        ParkingBoy parkingBoy = new ParkingBoy();

        parkingBoy.setParkingLots(new ArrayList<>());
        parkingBoy.getParkingLots().add(firstLot);
        parkingBoy.getParkingLots().add(secondLot);

        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);

        ParkingOrder order = new ParkingOrder();
        order.setStatus(ParkingOrder.ORDER_STATUS_NOT_BE_ACCEPTED);

        given(repository.findById(anyLong())).willReturn(Optional.of(order));

        order.setParkingBoy(parkingBoy);

        given(repository.save(any(ParkingOrder.class))).willReturn(order);

        assertEquals(service.parkingBoyGetParkingOrder(anyLong(), parkingBoy), order);
    }

    @Test(expected = ParkingLotIsFullException.class)
    public void should_throw_ParkingLotIsFullException_when_parking_lots_of_parking_boy_are_full(){
        ParkingLot firstLot = new ParkingLot();
        ParkingLot secondLot = new ParkingLot();

        firstLot.setRestCapacity(0);
        secondLot.setRestCapacity(0);

        ParkingBoy parkingBoy = new ParkingBoy();

        parkingBoy.setParkingLots(new ArrayList<>());
        parkingBoy.getParkingLots().add(firstLot);
        parkingBoy.getParkingLots().add(secondLot);

        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);

        ParkingOrder order = new ParkingOrder();
        order.setStatus(ParkingOrder.ORDER_STATUS_NOT_BE_ACCEPTED);

        given(repository.findById(anyLong())).willReturn(Optional.of(order));

        order.setParkingBoy(parkingBoy);

        given(repository.save(any(ParkingOrder.class))).willReturn(order);

        service.parkingBoyGetParkingOrder(anyLong(), parkingBoy);
    }

    @Test(expected = GetParkingOrderException.class)
    public void should_throw_GetParkingOrderException_when_order_status_is_accepted(){
        ParkingLot firstLot = new ParkingLot();
        ParkingLot secondLot = new ParkingLot();

        firstLot.setRestCapacity(1);
        secondLot.setRestCapacity(0);

        ParkingBoy parkingBoy = new ParkingBoy();

        parkingBoy.setParkingLots(new ArrayList<>());
        parkingBoy.getParkingLots().add(firstLot);
        parkingBoy.getParkingLots().add(secondLot);

        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);

        ParkingOrder order = new ParkingOrder();
        order.setStatus(ParkingOrder.ORDER_STATUS_BE_ACCEPTED);

        given(repository.findById(anyLong())).willReturn(Optional.of(order));

        order.setParkingBoy(parkingBoy);

        given(repository.save(any(ParkingOrder.class))).willReturn(order);

        service.parkingBoyGetParkingOrder(anyLong(), parkingBoy);
    }
}
