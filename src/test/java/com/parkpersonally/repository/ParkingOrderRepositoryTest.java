package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingOrder;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingOrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Test
    public void should_return_specific_type_and_status_is_not_take_parking_order(){
        entityManager.persist(new ParkingOrder(0,1,11,"珠海"));
        entityManager.persist(new ParkingOrder(0,1,1,"珠海"));
        entityManager.persist(new ParkingOrder(1,1,4,"珠海"));
        entityManager.persist(new ParkingOrder(0,2,10,"珠海"));
        entityManager.persist(new ParkingOrder(2,1,18,"珠海"));

        List<ParkingOrder> fetchParkingOrders = parkingOrderRepository.findAllByTypeAndStatus(1,0);

        assertEquals(2,fetchParkingOrders.size());

    }



}