package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MarkItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Project project;
	private User user;
	private String date;
	private double score1;
	private double score2;
	private List<MarkPipe> markPipes;

}
