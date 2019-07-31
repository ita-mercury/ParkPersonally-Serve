package com.parkpersonally.model;

import lombok.Data;

@Data
public class AccountCredentials {

    public static final String ROLE_MANAGER = "manager";
    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_PARKING_BOY = "parkingBoy";
    public static final String ROLE_ADMIN = "admin";

    private String name;
    private String password;
    private String role;
    private String email;
}
