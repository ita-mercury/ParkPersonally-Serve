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


    public Administrator() {
    }

}
