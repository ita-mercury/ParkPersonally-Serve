package com.parkpersonally.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    private long id;
    private double rate;
    private String content;

    public Comment() {
    }

    public Comment(double rate, String content) {
        this.rate = rate;
        this.content = content;
    }
}
