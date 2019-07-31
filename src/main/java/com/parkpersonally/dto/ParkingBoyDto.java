package com.parkpersonally.dto;

import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.model.Tag;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ParkingBoyDto{


    public static final int PARKING_BOY_STATUS_FREEZE = 0;
    public static final int PARKING_BOY_STATUS_FREE = 1;
    public static final int PARKING_BOY_STATUS_BUSY = 2;

    private long id;

    private String name;

    private String password;

    private String number;

    private int status = PARKING_BOY_STATUS_FREE;


    private String phone;


    private List<ParkingLot> parkingLots = new ArrayList<>();

    private List<Tag> tags = new ArrayList<>();

    public ParkingBoyDto(long id, String name, String password, String number, int status, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.number = number;
        this.status = status;
        this.phone = phone;
    }

    public ParkingBoyDto(ParkingBoy parkingBoy){
        this.id = parkingBoy.getId();
        this.name = parkingBoy.getName();
        this.password = parkingBoy.getPassword();
        this.number = parkingBoy.getNumber();
        this.status = parkingBoy.getStatus();
        this.phone = parkingBoy.getPhone();
        if(parkingBoy.getParkingLots() != null) {
            this.parkingLots = parkingBoy.getParkingLots()
                    .stream()
                    .peek(n -> n.setParkingBoys(null))
                    .collect(Collectors.toList());
        }
        if(parkingBoy.getTags()!= null) {
            this.tags.addAll(parkingBoy.getTags());
        }
    }

    public ParkingBoyDto(ParkingBoy parkingBoy,long id){
        this.id = id;
        this.name = parkingBoy.getName();
        this.password = parkingBoy.getPassword();
        this.number = parkingBoy.getNumber();
        this.status = parkingBoy.getStatus();
        this.phone = parkingBoy.getPhone();
        if (parkingBoy.getTags() != null) {
            this.tags.addAll(parkingBoy.getTags());
        }
    }

    public ParkingBoyDto() {
    }
}
