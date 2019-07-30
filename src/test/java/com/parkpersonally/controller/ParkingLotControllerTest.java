package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkpersonally.exception.NoSuchParkingLotException;
import com.parkpersonally.exception.UpdateParkingLotCapacitySmallerException;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ParkingLotService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingLotController.class)
public class ParkingLotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
      private ParkingLotService service;

    @Test
    public void should_throw_NoSuchParkingLotException_when_updateParkingLot_can_not_find_target() throws Exception {
        given(service.updateParkingLot(anyLong(), any(ParkingLot.class))).willThrow(new NoSuchParkingLotException("没有找到对应的停车场"));

        mockMvc.perform(put("/parking-lots/1")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(new ParkingLot())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("没有找到对应的停车场"));
    }

    @Test
    public void should_throw_UpdateParkingLotCapacitySmallerException_when_update_parking_lot_Capacity_is_smaller() throws Exception{
        given(service.updateParkingLot(anyLong(), any(ParkingLot.class))).willThrow(new UpdateParkingLotCapacitySmallerException("无法把停车场容量缩小"));

        mockMvc.perform(put("/parking-lots/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(new ParkingLot())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("无法把停车场容量缩小"));
    }

    @Test
    public void should_return_Parking_lot_when_update_parking_lot_capacity_success() throws Exception{
        ParkingLot updateParkingLot = new ParkingLot(1, "南方软件园停车场", 10, 5);
        ParkingLot parkingLot = new ParkingLot(1, "南方软件园停车场", 9, 5);

        given(service.updateParkingLot(anyLong() ,any(ParkingLot.class))).willReturn(updateParkingLot);

        mockMvc.perform(put("/parking-lots/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(parkingLot)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(updateParkingLot)));
    }
    @Test
    public  void should_retrun_all_parkinglots_when_find_ParkingLots() throws Exception {
        //given
        ParkingLot firstParkingLot=new ParkingLot();
        ParkingLot secondParkingLot=new ParkingLot();
        List<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        //when
        given(service.findParkingLots()).willReturn(parkingLots);
        //then
        mockMvc.perform(get("/parking-lots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

}
