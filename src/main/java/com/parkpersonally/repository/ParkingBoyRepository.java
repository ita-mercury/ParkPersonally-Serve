package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Long> {
    ParkingBoy findParkingBoyByNameEqualsAndAndPasswordEquals(String name, String password);
//    List<ParkingBoy> findParkingBoyContainsTags(@Param("inputTags") List<Tag> inputTags);

}
