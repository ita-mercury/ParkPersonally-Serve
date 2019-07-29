package com.parkpersonally.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Manager {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    // todo
    private String password;

    @NotNull
    @Column(nullable = false)
    private String number;


    public Manager() {
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
}
