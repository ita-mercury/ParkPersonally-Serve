package com.parkpersonally.repository;

import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import org.assertj.core.api.Assertions;
import org.junit.Before;
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
    private List<ParkingOrder> list=new ArrayList();
    private List<Long> listId=new ArrayList<>();
    private  List<List<Tag>> listTag=new ArrayList<>();
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;
    @Before
    public void initData(){
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
        Customer firstCustomer=new Customer();
        firstCustomer.setPassword("1213123");
        firstCustomer.setPhone("12345678912");
        firstCustomer.setCarNumber("888888");
        firstCustomer.setEmail("2458461623@qq.com");
        firstCustomer = entityManager.persist(firstCustomer);
        long id = firstCustomer.getId();
        ParkingOrder firstParkingOrder = new ParkingOrder(0,1,11,"珠海");
        firstParkingOrder.setCustomer(firstCustomer);
        ParkingOrder secondParkingOrder = new ParkingOrder(0,1,1,"珠海");
        secondParkingOrder.setTags(fristTags);
        ParkingOrder thirdParkingOrder = new ParkingOrder(1,1,4,"珠海");
        thirdParkingOrder.setTags(secondTags);
        ParkingOrder fourthParkingOrder = new ParkingOrder(0,2,10,"珠海");
        fourthParkingOrder.setTags(thirdTags);
        ParkingOrder fifthParkingOrder = new ParkingOrder(2,1,18,"珠海");
        listId.add(id);
        list.add(firstParkingOrder);
        list.add(secondParkingOrder);
        listTag.add(fristTags);
        entityManager.persist(firstParkingOrder);
        entityManager.persist(secondParkingOrder);
        entityManager.persist(thirdParkingOrder);
        entityManager.persist(fourthParkingOrder);
        entityManager.persist(fifthParkingOrder);
    }


    @Test
    public void should_return_specific_type_and_status_is_not_take_parking_order(){

        List<ParkingOrder> fetchParkingOrders = parkingOrderRepository.findAllByTypeAndStatus(1,0);

        assertEquals(2,fetchParkingOrders.size());

    }

    @Test
    public void should_return_specific_type_and_status_is_not_take_parking_order_order_by_create_time(){
        List<ParkingOrder> fetchParkingOrders = parkingOrderRepository.findAllByTypeAndStatusOrderByCreateTimeAsc(1,0);

        assertEquals(list.get(0).getCreateTime(),fetchParkingOrders.get(0).getCreateTime());
        assertEquals(list.get(1).getCreateTime(),fetchParkingOrders.get(1).getCreateTime());

    }

    @Test
    public void should_return_parking_order_contatins_tag(){
        List<ParkingOrder> fetchParkingOrders = parkingOrderRepository.findDistinctByTagsIsIn(listTag.get(0));
        assertEquals(2,fetchParkingOrders.size());
    }
    @Test
    public  void should_return_all_order_contatins_fetch_car_and_parking_car(){
        List<ParkingOrder> allCarOrders=parkingOrderRepository.findAllByCustomerId(listId.get(0));
        assertEquals(list.get(1).getCreateTime(),allCarOrders.get(0).getCreateTime());


    }



}