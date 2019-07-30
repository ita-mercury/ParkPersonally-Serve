package com.parkpersonally.service;

import com.parkpersonally.model.Administrator;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.AdministratorRepository;
import com.parkpersonally.repository.ManagerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AdministratorServiceTest {

    private AdministratorService administratorService;
    private ManagerRepository managerRepository;
    private  ManagerService managerService;
    private  ParkingLotService parkingLotService;

    @Before
    public void init() {
        administratorService = new AdministratorService();
        managerRepository = mock(ManagerRepository.class);
        managerService = mock(ManagerService.class);
        parkingLotService = mock(ParkingLotService.class);

        administratorService.setManagerService(managerService);
        administratorService.setParkingLotService(parkingLotService);
        administratorService.setManagerRepository(managerRepository);
    }

    @Test
    public  void should_return_all_managers_when_findAll() {
        //given
        Manager firstManager=new Manager();
        Manager secondManager=new Manager();
        List<Manager> managers=new ArrayList<>();
        managers.add(firstManager);
        managers.add(secondManager);
        //when
        given(managerRepository.findAll()).willReturn(managers);
        //then
        assertSame(2,administratorService.findAllManager().size());
    }
    @Test
    public void should_return_unmatch_parkingLots_when_getAllUnmatchParkingLots(){
        //given
        List<Manager> managers = new ArrayList<>();
        ParkingLot firstParkingLot=new ParkingLot(1,"南方软件园",100,30);
        ParkingLot secondParkingLot=new ParkingLot(2,"北方软件园",100,30);
        ParkingLot thirdParkingLot=new ParkingLot(3,"南方软件园",100,30);
        ParkingLot fourthParkingLot=new ParkingLot(4,"南方软件园",100,30);
        List<ParkingLot> allParkingLots=new ArrayList<>();
        allParkingLots.add(firstParkingLot);
        allParkingLots.add(secondParkingLot);
        allParkingLots.add(thirdParkingLot);
        allParkingLots.add(fourthParkingLot);
        Manager manager=new Manager();
        List<ParkingLot> manageParkingLots = new ArrayList<>();
        manageParkingLots.add(firstParkingLot);
        manageParkingLots.add(secondParkingLot);
        manager.setParkingLots(manageParkingLots);
        managers.add(manager);
        given(managerService.findAllManagers()).willReturn(managers);
        given(parkingLotService.findParkingLots()).willReturn(allParkingLots);
        assertSame(2,administratorService.findUnmatchedParkingLots().size());
    }

}