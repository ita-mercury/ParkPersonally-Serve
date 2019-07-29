package com.parkpersonally.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    @Size(min = 5)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @NotNull
    @Size(min = 5, max = 13)
    private String phone;

    @Column(nullable = false)
    @NotNull
    private String carNumber;

    public Customer(@Email String email, @NotNull @Size(min = 5) String password,
                    @NotNull @Size(min = 5, max = 13) String phone, @NotNull String carNumber) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.carNumber = carNumber;
    }

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
