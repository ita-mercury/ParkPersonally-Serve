package com.parkpersonally.service;

import com.parkpersonally.model.Administrator;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.repository.AdministratorRepository;
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

    private AdministratorRepository administratorRepository;
    private ParkingBoyRepository parkingBoyRepository;
    private AdministratorService administratorService;

    @Before
    public void init() {
        administratorRepository = mock(AdministratorRepository.class);
        parkingBoyRepository = mock(ParkingBoyRepository.class);
        administratorService = new AdministratorService();
        administratorService.setAdministratorRepository(administratorRepository);
        administratorService.setParkingBoyRepository(parkingBoyRepository);
    }

    @Test
    public void should_return_ParkingLot_when_updateParkingBoyOfAdministrator(){
        //given
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy(100000L,"Dillon1","15","18711345569");
        parkingBoys.add(parkingBoy);
        parkingBoys.add(new ParkingBoy(100001L,"Dillon2","15","18711345569"));
        Administrator administrator = new Administrator(000000L,"Xie","1","18711345569",parkingBoys);

        given(parkingBoyRepository.save(any(ParkingBoy.class))).willReturn(parkingBoy);
        given(administratorRepository.save(any(Administrator.class))).willReturn(administrator);

        assertSame(parkingBoy,administratorService.updateParkingBoyOfAdministrator(100000L,administrator));

    }

}