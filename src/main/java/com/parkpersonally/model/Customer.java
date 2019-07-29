package com.parkpersonally.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
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

    public Customer() {
    }

    public Customer(@Email String email, @NotNull @Size(min = 5) String password,
                    @NotNull @Size(min = 5, max = 13) String phone, @NotNull String carNumber) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.carNumber = carNumber;
    }

}
