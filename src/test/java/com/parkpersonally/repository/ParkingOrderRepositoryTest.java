package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
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

    @Test
    public void should_return_specific_type_and_status_is_not_take_parking_order_order_by_create_time(){
        ParkingOrder firstParkingOrder = new ParkingOrder(0,1,11,"珠海");
        ParkingOrder secondParkingOrder = new ParkingOrder(0,1,1,"珠海");
        ParkingOrder thirdParkingOrder = new ParkingOrder(1,1,4,"珠海");
        ParkingOrder fourthParkingOrder = new ParkingOrder(0,2,10,"珠海");
        ParkingOrder fifthParkingOrder = new ParkingOrder(2,1,18,"珠海");
        entityManager.persist(firstParkingOrder);
        entityManager.persist(secondParkingOrder);
        entityManager.persist(thirdParkingOrder);
        entityManager.persist(fourthParkingOrder);
        entityManager.persist(fifthParkingOrder);

        List<ParkingOrder> fetchParkingOrders = parkingOrderRepository.findAllByTypeAndStatusOrderByCreateTimeAsc(1,0);

        assertEquals(firstParkingOrder.getCreateTime(),fetchParkingOrders.get(0).getCreateTime());
        assertEquals(secondParkingOrder.getCreateTime(),fetchParkingOrders.get(1).getCreateTime());

    }

    @Test
    public void should_return_parking_order_contatins_tag(){
        Tag firstTag = new Tag("好看的");
        Tag secondTag = new Tag("爆炸");
        Tag thirdTag = new Tag("会唱rap");
        Tag fourthTag = new Tag("服务好的");
        Tag fifthTag = new Tag("小星星");
        firstTag = entityManager.persist(firstTag);
        secondTag = entityManager.persist(secondTag);
        thirdTag = entityManager.persist(thirdTag);
        fourthTag = entityManager.persist(fourthTag);
        fifthTag = entityManager.persist(fifthTag);

        List<Tag> fristTags = new ArrayList<>();
        fristTags.add(firstTag);
        fristTags.add(secondTag);
        List<Tag> secondTags = new ArrayList<>();
        secondTags.add(thirdTag);
        secondTags.add(fourthTag);
        secondTags.add(firstTag);
        List<Tag> thirdTags = new ArrayList<>();
        thirdTags.add(fifthTag);

        ParkingOrder firstParkingOrder = new ParkingOrder(1,1,11,"珠海");
        ParkingOrder secondParkingOrder = new ParkingOrder(1,1,1,"珠海");
        secondParkingOrder.setTags(fristTags);
        ParkingOrder thirdParkingOrder = new ParkingOrder(1,1,4,"珠海");
        thirdParkingOrder.setTags(secondTags);
        ParkingOrder fourthParkingOrder = new ParkingOrder(1,1,10,"珠海");
        fourthParkingOrder.setTags(thirdTags);
        entityManager.persist(firstParkingOrder);
        entityManager.persist(secondParkingOrder);
        entityManager.persist(thirdParkingOrder);
        entityManager.persist(fourthParkingOrder);

        List<ParkingOrder> fetchParkingOrders = parkingOrderRepository.findDistinctByTagsIsIn(fristTags);


        assertEquals(2,fetchParkingOrders.size());

    }



}