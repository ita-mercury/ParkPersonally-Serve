package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder, Long> {

    List<ParkingOrder> findAllByTypeAndStatus(int type,int status);
    List<ParkingOrder> findAllByTypeAndStatusOrderByCreatTimeAsc(int type,int status);
    List<ParkingOrder> findDistinctByTagsIsIn(List<Tag> tags);
}
