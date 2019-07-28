package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository  extends JpaRepository<ParkingLot, Long> {
}
