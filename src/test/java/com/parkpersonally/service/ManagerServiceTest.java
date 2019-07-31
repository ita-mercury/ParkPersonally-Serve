package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchManagerException;
import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ManagerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
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
    private ParkingBoyRepository parkingBoyRepository;

    @Before
    public void init() {
        managerRepository = mock(ManagerRepository.class);
        parkingBoyService = mock(ParkingBoyService.class);
        parkingBoyRepository = mock(ParkingBoyRepository.class);
        managerService = new ManagerService();
        managerService.setManagerRepository(managerRepository);
        managerService.setParkingBoyService(parkingBoyService);
        managerService.setParkingBoyRepository(parkingBoyRepository);
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
    public void should_return_ParkingBoy_when_manager_allocate_parkingLots_to_parkingBoy() {
        //given
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1, "停车场1", 50, 20));
        parkingLots.add(new ParkingLot(2, "停车场2", 50, 10));
        parkingLots.add(new ParkingLot(3, "停车场3", 55, 25));
        ParkingBoy parkingBoy = new ParkingBoy("张一", "134554");
        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);
        parkingBoy.setParkingLots(parkingLots);
        given(parkingBoyService.saveParkingBoy(any(ParkingBoy.class))).willReturn(parkingBoy);

        assertSame(parkingBoy.getParkingLots().size(), managerService.allocateParkingLots(1, 1, parkingLots).getParkingLots().size());
    }

    @Test
    public void should_return_ParkingBoy_when_manager_tag_parkingBoy() {
        //given
        List<Tag> tags = new ArrayList<>();
        Tag firstTag = new Tag("有腹肌的");
        Tag secondTag = new Tag("会唱rap的");
        tags.add(firstTag);
        tags.add(secondTag);
        ParkingBoy parkingBoy = new ParkingBoy("张一", "134554");
        given(parkingBoyService.findOneById(anyLong())).willReturn(parkingBoy);
        parkingBoy.setTags(tags);
        given(parkingBoyService.saveParkingBoy(any(ParkingBoy.class))).willReturn(parkingBoy);

        assertSame(parkingBoy.getTags().size(), managerService.tagParkingBoy(1, 1, tags).getTags().size());
    }

    @Test
    public void should_return_manager_when_save_a_manager(){
        //Given
        List<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"南方软件园",100,30));
        parkingLots.add(new ParkingLot(2,"唐家市场",200,20));
        Manager manager=new Manager(1,"李四","10001",parkingLots);
        //when
        given(managerService.saveManager(any(Manager.class))).willReturn(manager);
        //then
        assertSame(parkingLots,managerService.saveManager(manager).getParkingLots());
    }
    @Test
    public void should_return_all_managers_when_get_all_managers(){
        //Given
        List<ParkingLot> parkingLots=new ArrayList<>();
        parkingLots.add(new ParkingLot(1,"南方软件园",100,30));
        parkingLots.add(new ParkingLot(2,"唐家市场",200,20));
        List<Manager> managers = new ArrayList<>();
        Manager firstManager=new Manager(1,"李四","10001",parkingLots);
        Manager secondManager=new Manager(2,"小四","10001",parkingLots);
        Manager thirdManager=new Manager(3,"老张","10001",parkingLots);
        managers.add(firstManager);
        managers.add(secondManager);
        managers.add(thirdManager);
        //when
        given(managerService.findAllManagers()).willReturn(managers);
        //then
        assertSame(3,managerService.findAllManagers().size());
    }

    @Test
    public void should_return_filtered_ParkingBoys_when_findByNameLike(){
       //given
        ParkingBoy parkingBoy1 = new ParkingBoy("逍遥子1","134554");
        ParkingBoy parkingBoy2 = new ParkingBoy("段誉","134554");
        ParkingBoy parkingBoy3 = new ParkingBoy("萧峰","134554");
        ParkingBoy parkingBoy4 = new ParkingBoy("逍遥子2","134554");
        ParkingBoy parkingBoy5 = new ParkingBoy("逍遥子3","134554");

        List<ParkingBoy> parkingBoys1 = new ArrayList<>();
        parkingBoys1.add(parkingBoy1);
        parkingBoys1.add(parkingBoy2);
        parkingBoys1.add(parkingBoy3);
        parkingBoys1.add(parkingBoy4);
        parkingBoys1.add(parkingBoy5);

        List<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(parkingBoy1);
        parkingBoys.add(parkingBoy2);
        parkingBoys.add(parkingBoy3);

        Manager manager = new Manager(1,"逍遥子2","123456","187113455698","45",parkingBoys);

        given(managerRepository.findById(anyLong())).willReturn(Optional.of(manager));
        given(parkingBoyRepository.findByNameLike(anyString())).willReturn(parkingBoys1);

        assertSame(3,managerService.findByNameLike("萧",1).size());


    }
}
