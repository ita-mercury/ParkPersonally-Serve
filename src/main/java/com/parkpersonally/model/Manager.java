package com.parkpersonally.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
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


    public Manager() {
    }

    public Manager(long id, @NotNull String name, String password, @NotNull String number, List<ParkingLot> parkingLots) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.number = number;
        this.parkingLots = parkingLots;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ParkingBoy> getParkingBoys() {
        return parkingBoys;
    }

    public void setParkingBoys(List<ParkingBoy> parkingBoys) {
        this.parkingBoys = parkingBoys;
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }
}
