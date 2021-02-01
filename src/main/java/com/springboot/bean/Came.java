package com.springboot.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Came implements Serializable {

    private static final long serialVersionUID = 1L;

    private String model;
    private String name;
    private String text;

    public static Came getInstance(String model, String name, String text) {
        return new Came(model, name, text);
    }

    private Came(String model, String name, String text) {
        this.model = model;
        this.name = name;
        this.text = text;
    }

}
