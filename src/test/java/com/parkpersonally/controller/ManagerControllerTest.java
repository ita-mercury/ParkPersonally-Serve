package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.parkpersonally.dto.ManagerDto;
import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.dto.ParkingLotDto;
import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.*;

import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;

import com.parkpersonally.service.ManagerService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        List<ParkingLotDto> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLotDto(new ParkingLot(1,"停车场1",50,20)));
        parkingLots.add(new ParkingLotDto(new ParkingLot(2,"停车场2",50,10)));
        parkingLots.add(new ParkingLotDto(new ParkingLot(3,"停车场3",55,25)));

        given(managerService.getAllParkingLotOnManager(anyLong())).willReturn(parkingLots);

        mockMvc.perform(get("/managers/1/parking-lots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    public void should_return_All_ParkingBoys_when_getAllParkingBoys() throws Exception{
        //given
        List<ParkingBoyDto> parkingBoys = new ArrayList<>();
        parkingBoys.add(new ParkingBoyDto(new ParkingBoy("张一","134554")));
        parkingBoys.add(new ParkingBoyDto(new ParkingBoy("张一","134554")));
        parkingBoys.add(new ParkingBoyDto(new ParkingBoy("张一","134554")));
        given(managerService.getParkingBoys(anyLong())).willReturn(parkingBoys);

        mockMvc.perform(get("/managers/1/parking-boys"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));


    }


    @Test
    public void should_return_A_Exception_when_managerId_id_error() throws Exception{

        given(managerService.getAllParkingLotOnManager(anyLong())).willThrow(new NoSuchManagerException("抱歉,没有查到manager"));

        mockMvc.perform(get("/managers/1/parking-lots"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("抱歉,没有查到manager"));
    }

    @Test
    public void should_return_ParkingBoy_when_allocate_parking_lots() throws Exception{
        //given
        ParkingBoy parkingBoy = new ParkingBoy("张一","134554");
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001L,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002L,"停车场2",100,20));
        parkingBoy.setParkingLots(parkingLots);
        given(managerService.allocateParkingLots(anyLong(),anyLong(),anyList())).willReturn(parkingBoy);

        mockMvc.perform(put("/managers/{managerId}/parking-boys/{parkingBoyId}/parking-lots",1,1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(parkingLots)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parkingLots.size()").value(2));


    }

    @Test
    public void should_return_ParkingBoy_when_manager_tag_a_parking_boy() throws Exception{
        //given
        ParkingBoy parkingBoy = new ParkingBoy("张一","134554");
        List<Tag> tags = new ArrayList<>();
        Tag firstTag = new Tag("有腹肌的");
        Tag secondTag = new Tag("会唱rap的");
        tags.add(firstTag);
        tags.add(secondTag);
        parkingBoy.setTags(tags);
        given(managerService.tagParkingBoy(anyLong(),anyLong(),anyList())).willReturn(parkingBoy);

        mockMvc.perform(put("/managers/{managerId}/parking-boys/{parkingBoyId}/tags",1,1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(tags)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags.size()").value(2));


    }
    @Test
    public void should_return_manager_when_admin_add_a_manager() throws Exception{
        //given
       List<ParkingLot> parkingLots=new ArrayList<>();
       parkingLots.add(new ParkingLot(1,"南方软件园",100,30));
       parkingLots.add(new ParkingLot(2,"唐家市场",200,20));
       Manager manager=new Manager(1,"李四","10001",parkingLots);
       //when
       given(managerService.saveManager(any(Manager.class))).willReturn(manager);
       //then
       mockMvc.perform(post("/managers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(manager)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parkingLots[0].name").value("南方软件园"));
    }

    @Test
    public void should_return_all_managers_when_admin_query_all_managers() throws Exception{
        //given
        List<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"南方软件园",100,30));
        parkingLots.add(new ParkingLot(2,"唐家市场",200,20));
        List<ManagerDto> managers = new ArrayList<>();
        Manager firstManager=new Manager(1,"李四","10001",parkingLots);
        Manager secondManager=new Manager(2,"老张","10001",parkingLots);
        Manager thirdManager=new Manager(3,"老李","10001",parkingLots);
        managers.add(new ManagerDto(firstManager));
        managers.add(new ManagerDto(secondManager));
        managers.add(new ManagerDto(thirdManager));
        //when
        given(managerService.findAllManagers()).willReturn(managers);
        //then
        mockMvc.perform(get("/managers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }


}
