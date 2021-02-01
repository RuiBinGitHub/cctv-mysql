package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String context;
	private String date;
	private User user;
	private Pipe pipe;

}
