package com.parkpersonally.service;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ParkingBoyRepository;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ParkingBoyServiceTest {
    private ParkingBoyRepository parkingBoyRepository;
    private ParkingOrderService parkingOrderService;
    private ParkingBoyService parkingBoyService;

    @Before
    public void initTest() {
        parkingBoyRepository= mock(ParkingBoyRepository.class);
        parkingOrderService = mock(ParkingOrderService.class);
        parkingBoyService= new ParkingBoyService();
        parkingBoyService.setParkingBoyRepository(parkingBoyRepository);
        parkingBoyService.setParkingOrderService(parkingOrderService);
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

    @Test
    public void should_return_parking_boy_when_save_a_parking_boy(){
        //Given
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001L,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002L,"停车场2",100,20));
        ParkingBoy parkingBoy = new ParkingBoy(100L,"zhangsan","15",parkingLots,tags);
        //when
        given(parkingBoyRepository.save(any(ParkingBoy.class))).willReturn(parkingBoy);
        //then
        assertSame(tags,parkingBoyService.saveParkingBoy(parkingBoy).getTags());
    }

    @Test
    public void should_return_parking_orders_of_parking_boy(){
        //Given
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(000001L,"停车场1",100,18));
        parkingLots.add(new ParkingLot(000002L,"停车场2",100,20));
        ParkingBoy parkingBoy = new ParkingBoy(100000L,"Dillon1","15",parkingLots,tags);
        ParkingOrder firstParkingOrder = new ParkingOrder(2,1,11,"珠海");
        firstParkingOrder.setParkingBoy(parkingBoy);
        ParkingOrder secondParkingOrder = new ParkingOrder(3,1,1,"珠海");
        secondParkingOrder.setTags(tags);
        secondParkingOrder.setParkingBoy(parkingBoy);
        List<ParkingOrder> parkingOrders=new ArrayList<>();
        parkingOrders.add(firstParkingOrder);
        parkingOrders.add(secondParkingOrder);
        //when
        given(parkingOrderService.findAllParkingOrdersOfParkingBoy(any(ParkingBoy.class))).willReturn(parkingOrders);
        //then
        assertSame(2,parkingBoyService.getAllParkingOrdersOfParkingBoy(parkingBoy).size());
    }


}