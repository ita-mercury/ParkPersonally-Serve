package com.parkpersonally.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Manager {
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

    @OneToMany
    private List<ParkingBoy> parkingBoys;

    @OneToMany
    private List<ParkingLot> parkingLots;

    public Manager(long id, @NotNull String name, String password, @NotNull String number, List<ParkingLot> parkingLots) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.number = number;
        this.parkingLots = parkingLots;
    }

}
