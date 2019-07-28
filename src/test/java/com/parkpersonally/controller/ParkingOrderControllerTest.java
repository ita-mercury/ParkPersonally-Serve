package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkpersonally.dto.OrderComment;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.model.*;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

        this.mvc.perform(
                post("/parking-orders")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(order)));
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
    public void should_return_an_Exception_when_appraise_order_but_order_is_not_exist() throws Exception {
        //given
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setId(1);
        parkingOrder.setComments("司机真帅");
        given(service.appraiseOrder(anyLong(),any(ParkingOrder.class))).willThrow(new NoSuchParkingOrderException("抱歉,没有查到该订单"));
        //when
        mvc.perform(put("/parking-orders/2/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(parkingOrder)))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().string("抱歉,没有查到该订单"));

    }
    @Test
    public void should_return_a_order_with_comment_when_appraise_this_order() throws Exception {
        //given
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setId(1L);
        parkingOrder.setComments("司机会漂移");
        OrderComment orderComment = new OrderComment(1L,"司机会漂移");
        given(service.appraiseOrder(anyLong(),any(ParkingOrder.class))).willReturn(orderComment);
        //when
        mvc.perform(put("/parking-orders/1/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(parkingOrder)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("司机会漂移"));
    }

    @Test
    public void should_return_a_update_Order_when_updateParkingOrder() throws Exception{
        //given
        Customer customer = new Customer();
        customer.setId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        ParkingOrder order = new ParkingOrder(1,0,1,25);
        order.setCustomer(customer);
        order.setTags(tags);


        given(service.updateParkingOrder(any(ParkingOrder.class),anyLong())).willReturn(order);

        mvc.perform(put("/orders/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0));


    }

}
