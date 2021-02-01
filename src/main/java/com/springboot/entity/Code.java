package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Code implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String code1;
	private String code2;
	private String explain1;
	private String explain2;
	private String score;
	private String grade;
	private String type1;
	private String type2;
	private String type3;
	private String value;

}
