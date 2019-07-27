package com.parkpersonally.service;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.junit.Assert;
import org.junit.Test;

import static java.util.function.Predicate.isEqual;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingOrderServiceTest {

    private ParkingOrderService service;

    @Test
    public void should_return_new_order_when_add_new_order(){
        ParkingOrderRepository repository = mock(ParkingOrderRepository.class);
        service = new ParkingOrderService(repository);
        ParkingOrder order = new ParkingOrder(1, 20, "南方软件园");

        when(repository.save(any(ParkingOrder.class))).thenReturn(order);

        Assert.assertEquals(order, service.createParkingOrder(order));
    }
}
