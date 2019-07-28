package com.parkpersonally.service;

import com.parkpersonally.dto.OrderComment;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingOrderServiceTest {

    private ParkingOrderService service;
    private ParkingOrderRepository repository;


    @Before
    public void initTest() {
        repository = mock(ParkingOrderRepository.class);
        service = new ParkingOrderService(repository);
    }

    @Test
    public void should_return_new_order_when_add_new_order(){
        // given
        ParkingOrder expect = new ParkingOrder(ParkingOrder.PARK_CAR, 20, "南方软件园");
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
        ParkingOrder firstParkingOrder = new ParkingOrder(0,1,11,"珠海");
        allParkingOrders.add(firstParkingOrder);
        ParkingOrder secondParkingOrder = new ParkingOrder(0,1,1,"珠海");
        secondParkingOrder.setTags(fristTags);
        allParkingOrders.add(secondParkingOrder);
        ParkingOrder thirdParkingOrder = new ParkingOrder(0,1,4,"珠海");
        thirdParkingOrder.setTags(secondTags);
        allParkingOrders.add(thirdParkingOrder);
        ParkingOrder fourthParkingOrder = new ParkingOrder(0,1,10,"珠海");
        fourthParkingOrder.setTags(thirdTags);
        allParkingOrders.add(fourthParkingOrder);

        given(repository.findAllByTypeAndStatusOrderByCreatTimeAsc(anyInt(),anyInt())).willReturn(allParkingOrders);

        assertSame(1,service.getAllParkingOrdersOfParkingBoy(parkingBoy,1,0).size());
    }

    @Test
    public void should_return_Orders_contain_confirm_tags_and_without_tags_when_parking_boy_with_tags(){
        //given
        Tag firstTag = new Tag("好看的");
        Tag secondTag = new Tag("爆炸");
        Tag thirdTag = new Tag("会唱rap");
        Tag fourthTag = new Tag("服务好的");
        Tag fifthTag = new Tag("小星星");



        ParkingBoy parkingBoy = new ParkingBoy("zhangsan","13");

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
        ParkingOrder firstParkingOrder = new ParkingOrder(0,1,11,"珠海");
        firstParkingOrder.setId(1);
        allParkingOrders.add(firstParkingOrder);
        ParkingOrder secondParkingOrder = new ParkingOrder(0,1,1,"珠海");
        secondParkingOrder.setId(2);
        secondParkingOrder.setTags(fristTags);
        allParkingOrders.add(secondParkingOrder);
        parkingOrdersWithTags.add(secondParkingOrder);
        ParkingOrder thirdParkingOrder = new ParkingOrder(0,1,4,"珠海");
        thirdParkingOrder.setId(3);
        thirdParkingOrder.setTags(secondTags);
        allParkingOrders.add(thirdParkingOrder);
        parkingOrdersWithTags.add(thirdParkingOrder);
        ParkingOrder fourthParkingOrder = new ParkingOrder(0,1,10,"珠海");
        fourthParkingOrder.setId(4);
        fourthParkingOrder.setTags(thirdTags);
        allParkingOrders.add(fourthParkingOrder);

        given(repository.findAllByTypeAndStatusOrderByCreatTimeAsc(anyInt(),anyInt())).willReturn(allParkingOrders);
        given(repository.findDistinctByTagsIsIn(parkingBoy.getTags())).willReturn(parkingOrdersWithTags);

        assertSame(3,service.getAllParkingOrdersOfParkingBoy(parkingBoy,1,0).size());
    }
    @Test(expected = NoSuchParkingOrderException.class)
    public void should_throw_exception_when_find_a_order_is_not_exist(){
        //given
        ParkingOrder parkingOrder = new ParkingOrder();
        given(repository.findById(anyLong())).willThrow(new NoSuchParkingOrderException("抱歉,该订单不存在"));
        //when
        service.appraiseOrder(1,parkingOrder);
        //then
    }
    @Test
    public void should_return_order_comments_when_appraise_order(){
        //given
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setComments("司机会漂移");
        parkingOrder.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(parkingOrder));
        //when
        OrderComment orderComment = service.appraiseOrder(1, parkingOrder);
        //then
        assertSame("司机会漂移",orderComment.getComment());
    }
}
