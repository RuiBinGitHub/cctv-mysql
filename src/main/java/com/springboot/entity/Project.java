package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Project")
@Data
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String client;
	private String slope;
	private String standard;
	private String operator;
	private String state;
	private String date;
	private User user;

	private int count;
	private String workorder;
	private List<Pipe> pipes;
	private List<Item> items;

}
