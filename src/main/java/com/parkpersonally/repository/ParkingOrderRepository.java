package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.lang.annotation.Inherited;
import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder, Long> {

    List<ParkingOrder> findAllByTypeAndStatus(int type,int status);
    List<ParkingOrder> findAllByTypeAndStatusOrderByCreateTimeAsc(int type,int status);
    List<ParkingOrder> findDistinctByTagsIsIn(List<Tag> tags);
    @Query(value = "SELECT p FROM ParkingOrder p where p.customer.id = :customerId order by createTime desc")
    List<ParkingOrder> findAllByCustomerId( @Param("customerId") long customerId);
    List<ParkingOrder> findAllByParkingBoy(ParkingBoy parkingBoy);

    @Query(value = "SELECT COUNT(p.id) FROM ParkingOrder p where p.status = 2 or p.status = 4")
    int countProcessingParkingOrderByParkingBoyId(long parkingBoyId);

    @Query(value = "SELECT COUNT(p.id) FROM ParkingOrder p where p.parkingLot.id = :parkingLotId and p.status = 2 or p.status = 4")
    int countProcessingParkingOrderByParkingLotId(@Param("parkingLotId") long parkingLotId);
}