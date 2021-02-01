package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private int itemId;
    private int markId;
    private String state;
    private String date;
    private User user;

    private String itemname;
    private String username;

}
