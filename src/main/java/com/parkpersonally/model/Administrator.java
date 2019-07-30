package com.parkpersonally.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Entity
@Data
public class Administrator {
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

    @Column(nullable = false)
    @NotNull
    @Size(min = 5, max = 13)
    private String phone;

    @OneToMany
    private List<ParkingBoy> parkingBoys;

    @OneToMany
    private List<ParkingLot> parkingLots;

    @OneToMany
    private List<Manager> managers;

    public Administrator() {
    }

    public Administrator(long id, @NotNull String name, @NotNull String number, @NotNull @Size(min = 5, max = 13) String phone, List<ParkingBoy> parkingBoys) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.parkingBoys = parkingBoys;
    }
}
