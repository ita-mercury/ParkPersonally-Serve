package com.parkpersonally.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private int capacity;

    @Column(nullable = false)
    @NotNull
    private int restCapacity;

    @ManyToMany
    private List<ParkingBoy> parkingBoys;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return id == that.id &&
                restCapacity == that.restCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restCapacity);
    }

    public ParkingLot(long id, @NotNull String name, @NotNull int capacity, @NotNull int restCapacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.restCapacity = restCapacity;
    }

    public ParkingLot() {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRestCapacity() {
        return restCapacity;
    }

    public void setRestCapacity(int restCapacity) {
        this.restCapacity = restCapacity;
    }

    public List<ParkingBoy> getParkingBoys() {
        return parkingBoys;
    }

    public void setParkingBoys(List<ParkingBoy> parkingBoys) {
        this.parkingBoys = parkingBoys;
    }
}
