package com.parkpersonally.service;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ParkingBoyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ParkingBoyServiceTest {

    @MockBean
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingBoyService parkingBoyService;

    @Test
    public void should_return_all_parkingLot_when_getAllParkingLotOnParkingBoy(){

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001l,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002l,"停车场2",100,20));

        ParkingBoy parkingBoy = new ParkingBoy(100000l,"Dillon1","15",parkingLots,tags);

        given(parkingBoyRepository.findById(anyLong())).willReturn(Optional.of(parkingBoy));

        assertSame(2,parkingBoyService.getAllParkingLotOnParkingBoy(000001l).size());


    }
}