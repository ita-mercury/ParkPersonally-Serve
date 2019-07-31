package com.parkpersonally.dto;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotDto implements Serializable {

    private static final long serialVersionUID = 8839269655210665962L;

    public static final int LOT_STATUS_NORMAL = 1;
    public static final int LOT_STATUS_FREEZE = 0;

    private long id;

    private String name;

    private int capacity;

    private int status = LOT_STATUS_NORMAL;

    private int restCapacity;

    private List<ParkingBoy> parkingBoys = new ArrayList<>();

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
        parkingBoys.addAll(parkingLot.getParkingBoys());
    }

    public ParkingLotDto() {
    }
}
