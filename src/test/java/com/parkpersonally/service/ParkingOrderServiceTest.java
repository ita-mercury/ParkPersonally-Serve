package com.parkpersonally.service;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingOrderServiceTest {

    private ParkingOrderService service;
    private ParkingOrderRepository repository;

    @Before
    public void initTest() {
        repository = mock(ParkingOrderRepository.class);
        service = new ParkingOrderService(repository);
    }

    @Test
    public void should_return_new_order_when_add_new_order(){
        // given
        ParkingOrder expect = new ParkingOrder(ParkingOrder.PARK_CAR, 20, "南方软件园");
        when(repository.save(any(ParkingOrder.class))).thenReturn(expect);
        // when
        ParkingOrder actual = service.createParkingOrder(expect);
        // then
        Assert.assertEquals(expect, actual);
    }

//    @Test
//    public void should_throw_exception_when_new_order_have_null_property() {
//        // given
//        ParkingOrder expect = new ParkingOrder();
//        expect.setPositionNumber(16);
//        // when
//        service.createParkingOrder(expect);
//        // then
//    }
}
