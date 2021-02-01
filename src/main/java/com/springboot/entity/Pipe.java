package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Pipe")
@Data
public class Pipe implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int no;
	private String operator;
	private String workorder;
	private String reference;
	private String purposes;
	private String slope;
	private String sloperef;
	private String yearlaid;
	private String date;
	private String time;

	private String district1;
	private String district2;
	private String district3;
	private String roadname;
	private String housenum;
	private String building;
	private String division;
	private String areacode;

	private String smanholeno;
	private String fmanholeno;
	private String uses;
	private String dire;
	private String hsize;
	private String wsize;
	private String shape;
	private String mater;
	private String lining;
	private double pipelength;
	private double testlength;

	private String sdepth;
	private String scoverlevel;
	private String sinvertlevel;
	private String fdepth;
	private String fcoverlevel;
	private String finvertlevel;
	private String category;
	private String cleaned;
	private String weather;
	private String videono;
	private String comment;
	private transient Project project;

	private List<Item> items;
	private transient int[] surve = new int[20];
	private transient double[] score = new double[9];
	private transient double[] grade = new double[9];

}
