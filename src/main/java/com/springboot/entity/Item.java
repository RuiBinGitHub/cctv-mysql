package com.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int no;
    private String video;
    private String photo;
    private String dist;
    private String cont;
    private String code;
    private String diam;
    private String clockAt;
    private String clockTo;
    private String percent;
    private String lengths;
    private String remarks;
    private String picture;

    private transient Pipe pipe;
    private transient String type1;
    private transient String type2;
    private transient String type3;
    private transient double grade;
    private transient double score;
    private transient String define;
    private transient String depict;

    public Item(int no, String dist, String code, Pipe pipe) {
        this.no = no;
        this.dist = dist;
        this.code = code;
        this.pipe = pipe;
    }

}
