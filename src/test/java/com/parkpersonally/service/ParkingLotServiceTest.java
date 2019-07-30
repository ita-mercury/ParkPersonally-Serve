package com.parkpersonally.service;

import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.ParkingBoyRepository;
import com.parkpersonally.repository.ParkingLotRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


public class ParkingLotServiceTest {
    private ParkingLotRepository parkingLotRepository;
    private ParkingLotService parkingLotService;
    @Before
    public  void initData(){
        parkingLotRepository= mock(ParkingLotRepository.class);
        parkingLotService=new ParkingLotService();
        parkingLotService.setRepository(parkingLotRepository);
    }
    @Test
    public  void should_return_all_parking_lots_when_findAll() {
        //given
        ParkingLot firstParkingLot=new ParkingLot();
        ParkingLot secondParkingLot=new ParkingLot();
        List<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        //when
        given(parkingLotRepository.findAll()).willReturn(parkingLots);
        //then
        assertSame(2,parkingLotService.findParkingLots().size());
    }
}

