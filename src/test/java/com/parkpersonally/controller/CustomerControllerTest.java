package com.parkpersonally.controller;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.service.CustomerService;
import com.parkpersonally.service.ParkingBoyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @Test
    public  void should_return_all_tags_when_getParkingBoyTags() throws Exception {
        //given
        List<Tag> tags=new ArrayList<>();
        tags.add(new Tag("长得帅的"));
        tags.add(new Tag("服务好的"));

        //when
        given(customerService.getAllTags(100000L)).willReturn(tags);

        //then
        mockMvc.perform(get("/customers/{parkingBoyId}/tags",100000L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    public void should_return_A_Exception_when_parkingBoyId_is_error() throws Exception{
        given(customerService.getAllTags(anyLong())).willThrow(new NoSuchParkingBoyException("抱歉,没有查到停车员"));
        mockMvc.perform(get("/customers/{parkingBoyId}/tags",1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("抱歉,没有查到停车员"));
    }
    @Test
    public  void should_return_all_CarOrders_when_getAllCarOrders() throws Exception{
        //given
        List<ParkingOrder> parkingOrders=new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(10000L);
        ParkingOrder parkingOrder=new ParkingOrder(1, 20, "南方软件园");
        parkingOrder.setCustomer(customer);
        ParkingOrder parkingOrderSecond=new ParkingOrder(2, 20, "北方方软件园");
        parkingOrder.setCustomer(customer);
        parkingOrders.add(parkingOrder);
        parkingOrders.add(parkingOrderSecond);
        //when
        given(customerService.getAllOrders(10000L)).willReturn(parkingOrders);
        //then
        mockMvc.perform(get("/customers/10000/allOrders"))
                .andDo(print())
                .andExpect(status().isOk());

    }




}
