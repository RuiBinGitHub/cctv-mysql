package com.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeomPipe implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private double smhx; // 管道起始X坐标
	private double smhy; // 管道起始Y坐标
	private double smhh; // 管道起始Z坐标
	private double fmhx; // 管道终止X坐标
	private double fmhy; // 管道终止Y坐标
	private double fmhh; // 管道终止Z坐标
	private String smhGradeA;
	private String smhGradeB;
	private String fmhGradeA;
	private String fmhGradeB;
	private Pipe pipe;
	private GeomItem geomItem;

	private double x1; // 实际坐标x1
	private double y1; // 实际坐标y1
	private double x2; // 实际坐标x2
	private double y2; // 实际坐标y2
	private double grade; // 管道等级
	private double score; // 管道评分

}
