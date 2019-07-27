package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("parkingBoyRepository")
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Long> {
}
