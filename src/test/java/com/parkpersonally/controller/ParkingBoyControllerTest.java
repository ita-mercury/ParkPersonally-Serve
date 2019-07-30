package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.service.ParkingBoyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingBoyController.class)
public class ParkingBoyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ParkingBoyService service;

    @Test
    public  void should_return_all_parkingLot_when_getAllParkingLotOnParkingBoy() throws Exception {

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001L,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002L,"停车场2",100,20));

        given(service.getAllParkingLotOnParkingBoy(100000L)).willReturn(parkingLots);

        mockMvc.perform(get("/parking-boys/{parkingBoyId}/parking-lots",100000L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }
    @Test
    public void should_return_A_Exception_when_parkingBoyId_is_error() throws Exception{
        given(service.getAllParkingLotOnParkingBoy(anyLong())).willThrow(new NoSuchParkingBoyException("抱歉,没有查到停车员"));
        mockMvc.perform(get("/parking-boys/{parkingBoyId}/parking-lots",1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("抱歉,没有查到停车员"));
    }

    @Test
    public void should_return_parking_boy_when_manager_tag_a_parking_boy() throws Exception{
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));
        ParkingBoy parkingBoy = new ParkingBoy(100000L,"zhangsan","15",tags);
        given(service.saveParkingBoy(any(ParkingBoy.class))).willReturn(parkingBoy);
        mockMvc.perform(post("/parking-boys")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(parkingBoy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags[0].feature").value("smart"));
    }

    @Test
    public  void should_return_all_parkingOrders_when_getAllParkingOrdersOfParkingBoy() throws Exception {

        ParkingBoy parkingBoy = new ParkingBoy("zhangsan","15");
        ParkingOrder firstParkingOrder = new ParkingOrder(2,1,11,"珠海");
        firstParkingOrder.setParkingBoy(parkingBoy);
        ParkingOrder secondParkingOrder = new ParkingOrder(3,1,1,"珠海");
        secondParkingOrder.setParkingBoy(parkingBoy);
        List<ParkingOrder> parkingOrders=new ArrayList<>();
        parkingOrders.add(firstParkingOrder);
        parkingOrders.add(secondParkingOrder);

        given(service.findOneById(anyLong())).willReturn(parkingBoy);
        given(service.getAllParkingOrdersOfParkingBoy(any(ParkingBoy.class))).willReturn(parkingOrders);

        mockMvc.perform(get("/parking-boys/{parkingBoyId}/parking-orders",1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    public  void should_return_all_parkingBoys_when_getAllParkingBoys() throws Exception {
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy firstParkingBoy = new ParkingBoy("zhangsan","15");
        ParkingBoy secondParkingBoy = new ParkingBoy("zhansan","15");
        ParkingBoy thirdParkingBoy = new ParkingBoy("zan","15");
        parkingBoys.add(firstParkingBoy);
        parkingBoys.add(secondParkingBoy);
        parkingBoys.add(thirdParkingBoy);
        given(service.findAllParkingBoys()).willReturn(parkingBoys);

        mockMvc.perform(get("/parking-boys"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

    }



}