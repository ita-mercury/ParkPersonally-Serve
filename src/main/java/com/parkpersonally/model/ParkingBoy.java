package com.parkpersonally.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ParkingBoy {

    public static final int PARKING_BOY_STATUS_FREEZE = 0;
    public static final int PARKING_BOY_STATUS_FREE = 1;
    public static final int PARKING_BOY_STATUS_BUSY = 2;

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    // todo password validate
    private String password;

    @NotNull
    @Column(nullable = false)
    private String number;

    @NotNull
    @Column(nullable = false)
    private int status = PARKING_BOY_STATUS_FREE;

    @ManyToMany
    private List<ParkingLot> parkingLots;

    @ManyToMany
    private List<Tag> tags;

    public ParkingBoy(long id, @NotNull String name, @NotNull String number, List<ParkingLot> parkingLots, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.parkingLots = parkingLots;
        this.tags = tags;
    }


    public ParkingBoy() {}

    public ParkingBoy(@NotNull String name, @NotNull String number) {
        this.name = name;
        this.number = number;

    }

    public ParkingBoy(long id, @NotNull String name, @NotNull String number, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
