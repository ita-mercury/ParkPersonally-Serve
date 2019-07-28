package com.parkpersonally.service;

import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ParkingBoyRepository;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
public class CustomerServiceTest {
    private ParkingBoyRepository parkingBoyRepository;
    private ParkingOrderRepository parkingOrderRepository;
    private  CustomerService customerService;
    @Before
    public void initTest() {
        parkingBoyRepository= mock(ParkingBoyRepository.class);
        parkingOrderRepository=mock(ParkingOrderRepository.class);
        customerService=new CustomerService(parkingBoyRepository,parkingOrderRepository);
    }
    @Test
    public void should_return_all_tags_when_getParkingBoyTags(){
        //Given
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("smart"));
        tags.add(new Tag("handsome"));
        ParkingBoy parkingBoy = new ParkingBoy(100000L,"Dillon1","15",tags);
        //when
        given(parkingBoyRepository.findById(anyLong())).willReturn(Optional.of(parkingBoy));

        //then
        assertSame(2,customerService.getAllTags(100000L).size());

    }
    @Test
    public  void should_return_all_CarOrders_when_getAllCarOrders(){
        //given
        List<ParkingOrder> parkingOrders=new ArrayList<>();
        Customer customer=new Customer();
        customer.setId(10000L);
        ParkingOrder parkingOrder=new ParkingOrder(1, 20, "南方软件园");
        parkingOrder.setCustomer(customer);
        ParkingOrder parkingOrderSecond=new ParkingOrder(2, 20, "北方方软件园");
        parkingOrder.setCustomer(customer);
        parkingOrders.add(parkingOrder);
        parkingOrders.add(parkingOrderSecond);
        //when
        given(customerService.getAllOrders(10000L)).willReturn(parkingOrders);

        //then
        assertSame(2,customerService.getAllOrders(10000L).size());
    }


}
