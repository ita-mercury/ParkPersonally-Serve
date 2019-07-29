package com.parkpersonally.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class ParkingOrder {

    public static final int ORDER_TYPE_PARK_CAR = 1;
    public static final int ORDER_TYPE_FETCH_CAR = 2;

    public static final int ORDER_STATUS_NOT_BE_ACCEPTED = 1;
    public static final int ORDER_STATUS_BE_ACCEPTED = 2;
    public static final int ORDER_STATUS_PARK_CAR_COMPLETE = 3;
    public static final int ORDER_STATUS_CUSTOMER_CHECK = 5;
    public static final int ORDER_STATUS_PARKING_BOY_FETCH_CAR = 4;

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getFetchCarAddress() {
        return fetchCarAddress;
    }

    public void setFetchCarAddress(String fetchCarAddress) {
        this.fetchCarAddress = fetchCarAddress;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public ParkingBoy getParkingBoy() {
        return parkingBoy;
    }

    public void setParkingBoy(ParkingBoy parkingBoy) {
        this.parkingBoy = parkingBoy;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

}
