package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String name;
    private String firstname;
    private String lastname;
    private String chianame;
    private String nickname;
    private String membergrades;
    private String membernumber;
    private Company company;

}
