package com.parkpersonally.repository;

import com.parkpersonally.model.*;
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
    private ParkingBoy parkingBoy = new ParkingBoy();
    private  List<List<Tag>> listTag=new ArrayList<>();
    Manager manager = new Manager();
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
        parkingBoy.setName("张三");
        parkingBoy.setNumber("SL1232432");
        parkingBoy = entityManager.persist(parkingBoy);
        long id = firstCustomer.getId();
        ParkingOrder firstParkingOrder = new ParkingOrder(0,1,11,"珠海");
        firstParkingOrder.setCustomer(firstCustomer);
        ParkingOrder secondParkingOrder = new ParkingOrder(0,1,1,"珠海");
        secondParkingOrder.setTags(fristTags);
        ParkingOrder thirdParkingOrder = new ParkingOrder(1,1,4,"珠海");
        thirdParkingOrder.setTags(secondTags);
        thirdParkingOrder.setParkingBoy(parkingBoy);
        ParkingOrder fourthParkingOrder = new ParkingOrder(0,2,10,"珠海");
        fourthParkingOrder.setTags(thirdTags);
        ParkingOrder fifthParkingOrder = new ParkingOrder(2,1,18,"珠海");
        fifthParkingOrder.setParkingBoy(parkingBoy);
        listId.add(id);
        list.add(firstParkingOrder);
        list.add(secondParkingOrder);
        listTag.add(fristTags);
        entityManager.persist(firstParkingOrder);
        entityManager.persist(secondParkingOrder);
        entityManager.persist(thirdParkingOrder);
        entityManager.persist(fourthParkingOrder);
        entityManager.persist(fifthParkingOrder);

        List<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy1 = new ParkingBoy("Dillon1","15",1);
        ParkingBoy parkingBoy2 = new ParkingBoy("Dillon1","15",1);
        ParkingBoy parkingBoy3 = new ParkingBoy("Dillon1","15",1);

        parkingBoys.add(parkingBoy1);
        parkingBoys.add(parkingBoy2);
        parkingBoys.add(parkingBoy3);


        ParkingOrder parkingOrder1 = new ParkingOrder(1, 1, 254,"珠海",125,parkingBoy1);
        ParkingOrder parkingOrder2 = new ParkingOrder(1, 1, 2019, "珠海",parkingBoy2);
        ParkingOrder parkingOrder3 = new ParkingOrder(1, 1, 2019, "珠海",parkingBoy3);
        ParkingOrder parkingOrder4 = new ParkingOrder(1, 1, 2019, "珠海");
        ParkingOrder parkingOrder5 = new ParkingOrder(1, 1, 11, "珠海");

        manager.setName("xie");
        manager.setParkingBoys(parkingBoys);
        manager.setNumber("12");
        manager.setPhone("128254");

        entityManager.persist(parkingBoy1);
        entityManager.persist(parkingBoy2);
        entityManager.persist(parkingBoy3);
        entityManager.persist(parkingOrder1);
        entityManager.persist(parkingOrder2);
        entityManager.persist(parkingOrder3);
        entityManager.persist(parkingOrder4);
        entityManager.persist(parkingOrder5);
        entityManager.persist(manager);


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
    @Test
    public  void should_return_all_order_of_parking_boy(){
        List<ParkingOrder> allCarOrders=parkingOrderRepository.findAllByParkingBoy(parkingBoy);
        assertEquals(2,allCarOrders.size());
    }

    @Test
    public void should_return_all_order_when_findAllOrderOfManager(){
        List<ParkingOrder> list = parkingOrderRepository.findAllOrderOfManager(manager.getId());
        assertEquals(3,list.size());
    }



}