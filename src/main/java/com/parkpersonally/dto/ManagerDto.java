package com.parkpersonally.dto;

import com.parkpersonally.model.Manager;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingLot;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ManagerDto {

    private long id;

    private String name;


    private String password;


    private String number;


    private String phone;


    private List<ParkingBoy> parkingBoys = new ArrayList<>();


    private List<ParkingLot> parkingLots = new ArrayList<>();

    public ManagerDto(long id, String name, String password, String number, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.number = number;
        this.phone = phone;
    }

    public ManagerDto(Manager manager) {
        this.id = manager.getId();
        this.name = manager.getName();
        this.password = manager.getPassword();
        this.number = manager.getNumber();
        this.phone = manager.getPhone();
        if(manager.getParkingBoys()!= null) {
            this.parkingBoys = manager.getParkingBoys()
                    .stream()
                    .peek(n -> n.setParkingLots(null))
                    .collect(Collectors.toList());
        }
        if(manager.getParkingLots() != null) {
            this.parkingLots = manager.getParkingLots()
                    .stream()
                    .peek(n -> n.setParkingBoys(null))
                    .collect(Collectors.toList());
        }
    }

    public ManagerDto() {
    }
}
