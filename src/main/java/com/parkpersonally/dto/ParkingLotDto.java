package com.parkpersonally.dto;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class ParkingLotDto {


    public static final int LOT_STATUS_NORMAL = 1;
    public static final int LOT_STATUS_FREEZE = 0;

    private long id;

    private String name;

    private int capacity;

    private int status;

    private int restCapacity;

    private List<ParkingBoy> parkingBoys;

    public ParkingLotDto(long id, String name, int capacity, int restCapacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.restCapacity = restCapacity;
    }

    public ParkingLotDto (ParkingLot parkingLot){
        this.id = parkingLot.getId();
        this.name= parkingLot.getName();
        this.capacity = parkingLot.getCapacity();
        this.status = parkingLot.getStatus();
        this.restCapacity = parkingLot.getRestCapacity();
        if(parkingLot.getParkingBoys()!= null) {
            this.parkingBoys = parkingLot
                    .getParkingBoys()
                    .stream()
                    .peek(n -> n.setParkingLots(null))
                    .collect(Collectors.toList());
        }
    }

    public ParkingLotDto (ParkingLot parkingLot,long id){
        this.id = id;
        this.name= parkingLot.getName();
        this.capacity = parkingLot.getCapacity();
        this.status = parkingLot.getStatus();
        this.restCapacity = parkingLot.getRestCapacity();
    }

    public ParkingLotDto() {
    }


}
