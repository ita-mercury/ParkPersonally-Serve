package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("parkingOrderRepository")
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder, Long> {
}
