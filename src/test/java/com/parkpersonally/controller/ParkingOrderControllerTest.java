package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.parkpersonally.configuration.AuthProvider;
import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.dto.ParkingOrderDto;
import com.parkpersonally.exception.GetParkingOrderException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.exception.ParkingLotIsFullException;
import com.parkpersonally.model.*;
import com.parkpersonally.service.ParkingBoyService;
import com.parkpersonally.service.ParkingOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingOrderController.class)
public class ParkingOrderControllerTest {

    @MockBean
    private ParkingOrderService service;
    @MockBean
    private ParkingBoyService parkingBoyService;
    @MockBean
    private AuthProvider authProvider;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_new_parking_order_when_add_new_parking() throws Exception{
        Customer customer = new Customer();
        customer.setId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        ParkingOrder order = new ParkingOrder(1, 20, "南方软件园");
        order.setCustomer(customer);
        order.setTags(tags);

        given(service.createParkingOrder(any(ParkingOrder.class))).willReturn(order);
        ParkingOrderDto parkingOrderDto = new ParkingOrderDto(order);

        this.mvc.perform(
                post("/parking-orders")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(parkingOrderDto)));
    }

    @Test
    public void should_hand_exception_when_parkingOrder_property_is_error() throws Exception{
        // given
        ParkingOrder expect = new ParkingOrder();
        given(service.createParkingOrder(any(ParkingOrder.class))).willThrow(ConstraintViolationException.class);

        // then
        this.mvc.perform(
                post("/parking-orders")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(expect)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_Parking_Order_when_getOrderById() throws Exception{
        //given
        Customer customer = new Customer();
        customer.setId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        ParkingOrder order = new ParkingOrder(1, 20, "南方软件园");
        order.setCustomer(customer);
        order.setTags(tags);

        given(service.findOrderById(anyLong())).willReturn(order);

        //Then
        mvc.perform(get("/parking-orders/{parkingOrderId}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(1));


    }
    @Test
    public void should_return_Exception_when_parkingOrderId_is_error() throws Exception {

        //given
        Customer customer = new Customer();
        customer.setId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        ParkingOrder order = new ParkingOrder(1, 20, "南方软件园");
        order.setCustomer(customer);
        order.setTags(tags);

        given(service.findOrderById(anyLong())).willThrow(new NoSuchParkingOrderException("抱歉,没有查到该订单"));

        mvc.perform(get("/parking-orders/{parkingOrderId}",1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("抱歉,没有查到该订单"));

    }


    @Test
    public void should_return_an_Exception_when_appraise_order_but_order_is_not_exist() throws Exception {
        //given
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setId(1);
        Comment comment = new Comment(7.5, "司机真帅");
        parkingOrder.setComment(comment);
        given(service.appraiseOrder(anyLong(),any(Comment.class))).willThrow(new NoSuchParkingOrderException("抱歉,没有查到该订单"));
        //when
        mvc.perform(post("/parking-orders/2/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(comment)))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string("抱歉,没有查到该订单"));

    }
    @Test
    public void should_return_a_order_with_comment_when_appraise_this_order() throws Exception {
        //given
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setId(1L);
        Comment comment = new Comment(7.5, "司机真帅");
        parkingOrder.setComment(comment);
        given(service.appraiseOrder(anyLong(),any(Comment.class))).willReturn(parkingOrder);

        ParkingOrderDto parkingOrderDto = new ParkingOrderDto(parkingOrder);
        //when
        mvc.perform(post("/parking-orders/1/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(comment)))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(parkingOrderDto)));
    }

    @Test
    public void should_return_parking_lot_is_full_when_all_parking_lot_of_parking_boy_is_full() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy();

        given(service.parkingBoyGetParkingOrder(anyLong(), any(ParkingBoy.class)))
                .willThrow(new ParkingLotIsFullException("你管理的所有停车场已满"));

        mvc.perform(post("/parking-orders/1/parking-boy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(parkingBoy)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("你管理的所有停车场已满"));
    }
    @Test
    public void should_return_a_update_Order_when_updateParkingOrder() throws Exception{
        //given
        ParkingBoy parkingBoy = new ParkingBoy("zhangsan","12324");
        parkingBoy.setId(1);
        Tag firstTag = new Tag("好看的");
        Tag secondTag = new Tag("爆炸");
        Tag thirdTag = new Tag("会唱rap");
        Tag fourthTag = new Tag("服务好的");
        List<Tag> fristTags = new ArrayList<>();
        fristTags.add(firstTag);
        fristTags.add(secondTag);
        parkingBoy.setTags(fristTags);

        List<Tag> secondTags = new ArrayList<>();
        secondTags.add(thirdTag);
        secondTags.add(fourthTag);
        secondTags.add(firstTag);

        List<ParkingOrder> allParkingOrders = new ArrayList<>();
        ParkingOrder firstParkingOrder = new ParkingOrder(1,1,11,"珠海");
        allParkingOrders.add(firstParkingOrder);
        ParkingOrder secondParkingOrder = new ParkingOrder(1,1,1,"珠海");
        secondParkingOrder.setTags(fristTags);
        allParkingOrders.add(secondParkingOrder);
        ParkingOrder thirdParkingOrder = new ParkingOrder(1,1,4,"珠海");
        thirdParkingOrder.setTags(secondTags);
        allParkingOrders.add(thirdParkingOrder);
        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);
        given(service.filterParkingOrders(any(ParkingBoy.class),anyInt(),anyInt())).willReturn(allParkingOrders);

        mvc.perform(get("/parking-orders?type=1&parkingBoyId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(allParkingOrders.size()));



    }


    @Test
    public void should_return_orders_of_parkingboy_when_parkingboy_query_own_parking_orders() throws Exception {

        //given
        Customer customer = new Customer();
        customer.setId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        ParkingOrder order = new ParkingOrder(1, 20, "南方软件园");
        order.setCustomer(customer);
        order.setTags(tags);

        given(service.findOrderById(anyLong())).willThrow(new NoSuchParkingOrderException("抱歉,没有查到该订单"));

        mvc.perform(get("/parking-orders/{parkingOrderId}",1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("抱歉,没有查到该订单"));

    }


    @Test
    public void should_return_a_new_parkingOrder_when_updateParkingOrderStatus() throws Exception{
        //given
        Customer customer = new Customer();
        customer.setId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        ParkingLot parkingLot = new ParkingLot(1,"停车场1",50,20);
        ParkingOrder order = new ParkingOrder(1, 20, "南方软件园");
        order.setCustomer(customer);
        order.setTags(tags);
        order.setParkingLot(parkingLot);


        given(service.updateParkingOrderStatus(anyLong(),any(ParkingOrder.class))).willReturn(order);

        mvc.perform(put("/parking-orders/{parkingOrderId}",1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(order)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.positionNumber").value(20));
    }

    @Test
    public void should_return_your_order_is_accepted_by_others_when_validate_throw_exception() throws Exception{
        ParkingBoy parkingBoy = new ParkingBoy();

        given(service.parkingBoyGetParkingOrder(anyLong(), any(ParkingBoy.class)))
                .willThrow(new GetParkingOrderException("该订单已被其他人接取"));

        mvc.perform(post("/parking-orders/1/parking-boy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(parkingBoy)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("该订单已被其他人接取"));
    }

}
