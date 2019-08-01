package com.parkpersonally.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class ParkingLot {

    public static final int LOT_STATUS_NORMAL = 1;
    public static final int LOT_STATUS_FREEZE = 0;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private int capacity;

    @NotNull
    @Column(nullable = false)
    private int status = LOT_STATUS_NORMAL;

    @Column(nullable = false)
    @NotNull
    private int restCapacity;

//    @JsonIgnore
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

    @Override
    public String toString() {
        return "ParkingLot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", status=" + status +
                ", restCapacity=" + restCapacity +
                '}';
    }
}
