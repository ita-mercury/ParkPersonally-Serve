package com.parkpersonally.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Data
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

}
