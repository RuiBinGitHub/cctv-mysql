package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "User")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String username;
    private String password;
    private String mail;
    private String mobi;
    private String state;
    private String role;
    private Company company;

}
