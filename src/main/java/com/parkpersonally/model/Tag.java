package com.parkpersonally.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Tag {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotNull
    private String feature;

    public long getId() {
        return id;
    }

    public Tag(@NotNull String feature) {
        this.feature = feature;
    }

    public Tag() {
    }
}
