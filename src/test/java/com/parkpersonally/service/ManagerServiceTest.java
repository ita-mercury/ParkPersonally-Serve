package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
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
    private ParkingBoyService parkingBoyService;
    private ManagerService managerService;

    @Before
    public void init() {
        managerRepository = mock(ManagerRepository.class);
        parkingBoyService = mock(ParkingBoyService.class);
        managerService = new ManagerService();
        managerService.setManagerRepository(managerRepository);
        managerService.setParkingBoyService(parkingBoyService);
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

    @Test
    public void should_All_ParkingBoys_when_getAllParkingBoysOfManager(){
        //given
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"停车场1",50,20));
        parkingLots.add(new ParkingLot(2,"停车场2",50,10));
        parkingLots.add(new ParkingLot(3,"停车场3",55,25));
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(new ParkingBoy("张一","134554"));
        parkingBoys.add(new ParkingBoy("张一","134554"));
        parkingBoys.add(new ParkingBoy("张一","134554"));

        Manager manager = new Manager(1,"经理1","123456","45",parkingLots);
        manager.setParkingBoys(parkingBoys);

        given(managerRepository.findById(anyLong())).willReturn(Optional.ofNullable(manager));

        assertSame(parkingBoys.size(),managerService.getParkingBoys(1).size());
    }

    @Test(expected = NoSuchManagerException.class)
    public void should_throw_NoSuchManagerException_when_managerId_is_error_and_get_all_parking_boys(){
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"停车场1",50,20));
        parkingLots.add(new ParkingLot(2,"停车场2",50,10));
        parkingLots.add(new ParkingLot(3,"停车场3",55,25));
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(new ParkingBoy("张一","134554"));
        parkingBoys.add(new ParkingBoy("张一","134554"));
        parkingBoys.add(new ParkingBoy("张一","134554"));

        Manager manager = new Manager(1,"经理1","123456","45",parkingLots);
        manager.setParkingBoys(parkingBoys);
        given(managerRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        managerService.getParkingBoys(anyLong());
    }

    @Test
    public void should_return_ParkingBoy_when_manager_allocate_parkingLots_to_parkingBoy(){
        //given
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"停车场1",50,20));
        parkingLots.add(new ParkingLot(2,"停车场2",50,10));
        parkingLots.add(new ParkingLot(3,"停车场3",55,25));
        ParkingBoy parkingBoy = new ParkingBoy("张一","134554");
        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);
        parkingBoy.setParkingLots(parkingLots);
        given(parkingBoyService.saveParkingBoy(any(ParkingBoy.class))).willReturn(parkingBoy);

        assertSame(parkingBoy.getParkingLots().size(),managerService.allocateParkingLots(1,1,parkingBoy).getParkingLots().size());
    }
}
