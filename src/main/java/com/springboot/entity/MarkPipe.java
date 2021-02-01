package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MarkPipe implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private double score1;
	private double score2;
	private double level1;
	private double level2;
	private String remark;
	private Pipe pipe;
	private MarkItem markItem;

}
