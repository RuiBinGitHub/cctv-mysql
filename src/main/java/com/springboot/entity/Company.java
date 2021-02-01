package com.springboot.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Company")
@Data
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String code;
	private int count;
	private String level;
	private String date;
	private String term;

}
