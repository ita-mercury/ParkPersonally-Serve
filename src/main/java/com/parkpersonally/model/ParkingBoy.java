package com.parkpersonally.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
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

    @Column
    @Size(min = 5, max = 13)
    private String phone;

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

    public ParkingBoy(long id, @NotNull String name, @NotNull String number, @Size(min = 5, max = 13) String phone) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.phone = phone;
    }
}
