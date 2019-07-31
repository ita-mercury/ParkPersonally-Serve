package com.parkpersonally.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkpersonally.configuration.AuthProvider;
import com.parkpersonally.dto.ManagerDto;
import com.parkpersonally.dto.ParkingBoyDto;
import com.parkpersonally.model.*;
import com.parkpersonally.service.AdministratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdministratorController.class)
public class AdministratorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthProvider authProvider;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private AdministratorService administratorService;

    @Before
    public void init() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthorityImpl(AccountCredentials.ROLE_ADMIN));
        given(authProvider.authenticate(any(Authentication.class))).willReturn(new UsernamePasswordAuthenticationToken("a123456", "123456", authorities));
    }

    @Test
    public void should_return_A_new_ParkingBoy_when_updateParkingBoyOfAdministrator() throws Exception{
        //given
        ParkingBoy parkingBoy = new ParkingBoy("张一","134554");
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001L,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002L,"停车场2",100,20));
        parkingBoy.setParkingLots(parkingLots);
        ParkingBoyDto parkingBoyDto = new ParkingBoyDto(parkingBoy);

        given(administratorService.updateParkingBoyOfAdministrator(anyLong(),any(ParkingBoy.class))).willReturn(parkingBoyDto);

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
        ManagerDto managerDto = new ManagerDto(manager);
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
        List<ParkingBoyDto> unmatchedParkingBoys=new ArrayList<>();
        unmatchedParkingBoys.add(new ParkingBoyDto(firstParkingBoy));
        unmatchedParkingBoys.add(new ParkingBoyDto(secondParkingBoy));
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
    @Test
    public  void should_return_a_parking_boy_when_createParkingBoy() throws Exception {
        //given
        ParkingBoy parkingBoy=new ParkingBoy("小鬼","12345");
        //when
        given(administratorService.saveParkingBoy(any(ParkingBoy.class))).willReturn(parkingBoy);
        //then
        mockMvc.perform(post("/admin/parking-Boys")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(parkingBoy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("小鬼"));
    }
}
