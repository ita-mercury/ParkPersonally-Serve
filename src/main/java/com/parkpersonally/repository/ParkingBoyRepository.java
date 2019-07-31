package com.parkpersonally.repository;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Long> {

    List<ParkingBoy> findByNameLike(String name);

    ParkingBoy findParkingBoyByNameEqualsAndAndPasswordEquals(String name, String password);
<<<<<<< HEAD
=======
    @Query("select pb from ParkingBoy pb where pb.tags in :inputTags")
    List<ParkingBoy> findParkingBoyContainsTags(List<Tag> inputTags);
>>>>>>> 98f2ecbd16dfaedfcc402849f6fbc6925cfd6f0b

}
