package com.parkpersonally.dto;

import com.parkpersonally.model.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParkingOrderDto {
    public static final int ORDER_TYPE_PARK_CAR = 1;
    public static final int ORDER_TYPE_FETCH_CAR = 2;

    public static final int ORDER_STATUS_NOT_BE_ACCEPTED = 1;
    public static final int ORDER_STATUS_BE_ACCEPTED = 2;
    public static final int ORDER_STATUS_PARK_CAR_COMPLETE = 3;
    public static final int ORDER_STATUS_CUSTOMER_CHECK = 5;
    public static final int ORDER_STATUS_PARKING_BOY_FETCH_CAR = 4;
    public static final int ORDER_STATUS_PARK_CAR_AND_START_FETCH_CAR = 6;


    private long id;

    private int status = ORDER_STATUS_NOT_BE_ACCEPTED;

    private int type;

    private int positionNumber;


    private Comment comment;


    private String fetchCarAddress;


    private long createTime = System.currentTimeMillis();


    private ParkingBoyDto parkingBoyDto;


    private ParkingLotDto parkingLotDto;


    private List<Tag> tags = new ArrayList<>();

    private Customer customer;

    public ParkingOrderDto(long id, int status, int type, int positionNumber, String fetchCarAddress, long createTime) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.positionNumber = positionNumber;
        this.fetchCarAddress = fetchCarAddress;
        this.createTime = createTime;
    }

    public ParkingOrderDto(ParkingOrder parkingOrder) {
        this.id = parkingOrder.getId();
        this.status = parkingOrder.getStatus();
        this.type = parkingOrder.getType();
        this.positionNumber = parkingOrder.getPositionNumber();
        this.comment = parkingOrder.getComment();
        this.fetchCarAddress = parkingOrder.getFetchCarAddress();
        this.createTime = parkingOrder.getCreateTime();
        if(parkingOrder.getParkingBoy() != null) {
            this.parkingBoyDto = new ParkingBoyDto(parkingOrder.getParkingBoy(), parkingOrder.getParkingBoy().getId());
        }
        if(parkingOrder.getParkingLot()!= null) {
            this.parkingLotDto = new ParkingLotDto(parkingOrder.getParkingLot(), parkingOrder.getParkingLot().getId());
        }
    }



    public ParkingOrderDto() {
    }
}
