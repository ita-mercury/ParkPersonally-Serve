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

    @Before
    public void init() {
        managerRepository=mock(ManagerRepository.class);
        administratorService = new AdministratorService();
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

}