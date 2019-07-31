package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Long> {
    ParkingBoy findParkingBoyByNameEqualsAndAndPasswordEquals(String name, String password);
    @Query("select pb from ParkingBoy pb where pb.tags in :inputTags")
    List<ParkingBoy> findParkingBoyContainsTags(List<Tag> inputTags);

}
