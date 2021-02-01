package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GeomItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String scope;
    private Project project;

    private double x;
    private double y;
    private List<GeomPipe> geomPipes;

}
