package com.parkpersonally.service;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ParkingBoyRepository;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ParkingBoyServiceTest {
    private ParkingBoyRepository parkingBoyRepository;
    private ParkingBoyService parkingBoyService;
    @Before
    public void initTest() {
        parkingBoyRepository= mock(ParkingBoyRepository.class);
        parkingBoyService= new ParkingBoyService(parkingBoyRepository);
    }

    @Test
    public void should_return_all_parkingLot_when_getAllParkingLotOnParkingBoy(){
        //Given
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001L,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002L,"停车场2",100,20));

        ParkingBoy parkingBoy = new ParkingBoy(100000L,"Dillon1","15",parkingLots,tags);
        //when
        given(parkingBoyRepository.findById(anyLong())).willReturn(Optional.of(parkingBoy));
        //then
        assertSame(2,parkingBoyService.getAllParkingLotOnParkingBoy(000001L).size());
    }
}