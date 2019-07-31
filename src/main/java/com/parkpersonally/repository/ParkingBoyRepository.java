package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Long> {

    List<ParkingBoy> findByNameLike(String name);
}
