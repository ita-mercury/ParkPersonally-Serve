package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingBoy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingBoyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Before
    public void initData(){
        entityManager.persist(new ParkingBoy("逍遥子1","15"));
        entityManager.persist(new ParkingBoy("段誉","15"));
        entityManager.persist(new ParkingBoy("萧峰","15"));
        entityManager.persist(new ParkingBoy("杨过","15"));
        entityManager.persist(new ParkingBoy("逍遥子2","15"));
    }

    @Test
    public void should_return_filter_ParkingBoy_when_findByNameLike(){
        List<ParkingBoy> parkingBoys = parkingBoyRepository.findByNameLike("%萧%");
        assertEquals(1,parkingBoys.size());
    }

}