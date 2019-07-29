package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.ManagerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ManagerServiceTest {

    private ManagerRepository managerRepository;
    private ManagerService managerService;

    @Before
    public void init() {
        managerRepository = mock(ManagerRepository.class);
        managerService = new ManagerService(managerRepository);
    }

    @Test
    public void should_All_ParkingLot_when_getAllParkingLotOnManager(){
        //given
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"停车场1",50,20));
        parkingLots.add(new ParkingLot(2,"停车场2",50,10));
        parkingLots.add(new ParkingLot(3,"停车场3",55,25));

        Manager manager = new Manager(1,"经理1","123456","45",parkingLots);

        given(managerRepository.findById(anyLong())).willReturn(Optional.ofNullable(manager));

        assertSame(parkingLots.size(),managerService.getAllParkingLotOnManager(anyLong()).size());


    }

    @Test(expected = NoSuchManagerException.class)
    public void should_throw_NoSuchManagerException_when_managerId_is_error(){
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"停车场1",50,20));
        parkingLots.add(new ParkingLot(2,"停车场2",50,10));
        parkingLots.add(new ParkingLot(3,"停车场3",55,25));

        Manager manager = new Manager(1,"经理1","123456","45",parkingLots);

        given(managerRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        managerService.getAllParkingLotOnManager(anyLong());
    }
}
