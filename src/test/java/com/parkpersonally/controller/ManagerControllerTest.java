package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.ManagerService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ManagerService managerService;

    @Test
    public void should_return_All_ParkingLot_when_getAllParkingLotOnManager() throws Exception{
        //given
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"停车场1",50,20));
        parkingLots.add(new ParkingLot(2,"停车场2",50,10));
        parkingLots.add(new ParkingLot(3,"停车场3",55,25));

        given(managerService.getAllParkingLotOnManager(anyLong())).willReturn(parkingLots);

        mockMvc.perform(get("/manager/1/parking-lots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));


    }

}
