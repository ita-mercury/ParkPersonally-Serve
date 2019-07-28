package com.parkpersonally.service;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.ParkingBoyRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CustomerServiceTest {
    private ParkingBoyRepository parkingBoyRepository;
    private  CustomerService customerService;
    @Before
    public void initTest() {
        parkingBoyRepository= mock(ParkingBoyRepository.class);
        customerService=new CustomerService(parkingBoyRepository);
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


}
