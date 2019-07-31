package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkpersonally.model.Administrator;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.service.AdministratorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(AdministratorController.class)
public class AdministratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AdministratorService administratorService;

    @Test
    public void should_return_A_new_ParkingBoy_when_updateParkingBoyOfAdministrator() throws Exception{
        //given
        ParkingBoy parkingBoy = new ParkingBoy("张一","134554");
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001L,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002L,"停车场2",100,20));
        parkingBoy.setParkingLots(parkingLots);

        given(administratorService.updateParkingBoyOfAdministrator(anyLong(),any(ParkingBoy.class))).willReturn(parkingBoy);

        mockMvc.perform(put("/admin/parking-boys/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(parkingBoy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("张一"));

    }

    @Test
    public void should_return_A_new_Manager_when_updateManagerOfAdministrator() throws Exception{
        //given
        Manager manager = new Manager(1,"经理1","123456","452654");

        given(administratorService.updateManagerOfAdministrator(anyLong(),any(Manager.class))).willReturn(manager);

        mockMvc.perform(put("/admin/managers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(manager)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("经理1"));


    }
    @Test
    public  void should_return_unmatchedParkingLots_when_getAllUnmatchedParkingLots() throws Exception {
        //given
        ParkingLot thirdParkingLot=new ParkingLot();
        ParkingLot fourthParkingLot=new ParkingLot();
        List<ParkingLot> unmatchedParkingLots=new ArrayList<>();
        unmatchedParkingLots.add(thirdParkingLot);
        unmatchedParkingLots.add(fourthParkingLot);

        given(administratorService.findUnmatchedParkingLots()).willReturn(unmatchedParkingLots);

        mockMvc.perform(get("/admin/managers/unmatchedParkingLots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    public  void should_return_unmatchedParkingBoys_when_getAllUnmatchedParkingBoys() throws Exception {
        //given
        ParkingBoy firstParkingBoy=new ParkingBoy();
        ParkingBoy secondParkingBoy=new ParkingBoy();
        List<ParkingBoy> unmatchedParkingBoys=new ArrayList<>();
        unmatchedParkingBoys.add(firstParkingBoy);
        unmatchedParkingBoys.add(secondParkingBoy);
        //when
        given(administratorService.findUnmatchedParkingBoys()).willReturn(unmatchedParkingBoys);
        //then
        mockMvc.perform(get("/admin/managers/unmatchedParkingBoys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    public  void should_return_a_manager_when_createManager() throws Exception {
        //given
        Manager manager = new Manager(1,"经理1","123456","452654");
        //when
        given(administratorService.saveManager(any(Manager.class))).willReturn(manager);
        //then
        mockMvc.perform(post("/admin/managers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(manager)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("经理1"));

    }
}