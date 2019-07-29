package com.parkpersonally.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class ParkingOrder {

    public static final int ORDER_TYPE_PARK_CAR = 1;
    public static final int ORDER_TYPE_FETCH_CAR = 2;

    public static final int ORDER_STATUS_NOT_BE_ACCEPTED = 1;
    public static final int ORDER_STATUS_BE_ACCEPTED = 2;
    public static final int ORDER_STATUS_PARK_CAR_COMPLETE = 3;
    public static final int ORDER_STATUS_CUSTOMER_CHECK = 5;
    public static final int ORDER_STATUS_PARKING_BOY_FETCH_CAR = 4;
    public static final int ORDER_STATUS_PARK_CAR_AND_START_FETCH_CAR = 6;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotNull
    private int status = ORDER_STATUS_NOT_BE_ACCEPTED;

    @Column(nullable = false)
    @NotNull
    private int type;

    private int positionNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Comment comment;

    @Column(nullable = false)
    @NotNull
    private String fetchCarAddress;

    @Column(nullable = false)
    @NotNull
    private long createTime = System.currentTimeMillis();

    @ManyToOne(fetch = FetchType.EAGER)
    private ParkingBoy parkingBoy;

    @OneToOne(fetch = FetchType.EAGER)
    private ParkingLot parkingLot;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    public ParkingOrder() {
    }

    public ParkingOrder(long id, @NotNull int status, @NotNull int type, int positionNumber) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.positionNumber = positionNumber;
    }

    public ParkingOrder(@NotNull int type, int positionNumber, @NotNull String fetchCarAddress) {
        this.type = type;
        this.positionNumber = positionNumber;
        this.fetchCarAddress = fetchCarAddress;
    }
    public ParkingOrder(@NotNull int status, @NotNull int type, int positionNumber, @NotNull String fetchCarAddress) {
        this.status = status;
        this.type = type;
        this.positionNumber = positionNumber;
        this.fetchCarAddress = fetchCarAddress;
    }

    public ParkingOrder(@NotNull int status, @NotNull int type, int positionNumber, @NotNull String fetchCarAddress, List<Tag> tags) {
        this.status = status;
        this.type = type;
        this.positionNumber = positionNumber;
        this.fetchCarAddress = fetchCarAddress;
        this.tags = tags;
    }

    public ParkingOrder(long id, @NotNull int status, @NotNull int type, int positionNumber, ParkingLot parkingLot) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.positionNumber = positionNumber;
        this.parkingLot = parkingLot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingOrder order = (ParkingOrder) o;
        return id == order.id &&
                parkingLot.equals(order.parkingLot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parkingLot);
    }

}
