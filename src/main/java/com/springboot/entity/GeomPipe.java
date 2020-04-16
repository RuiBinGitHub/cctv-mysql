package com.springboot.entity;

import java.io.Serializable;

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

	private double grade; // 管道等级
	private double score; // 管道评分
	private double x1; // 实际坐标x1
	private double y1; // 实际坐标y1
	private double x2; // 实际坐标x2
	private double y2; // 实际坐标y2

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSmhx() {
		return smhx;
	}

	public void setSmhx(double smhx) {
		this.smhx = smhx;
	}

	public double getSmhy() {
		return smhy;
	}

	public void setSmhy(double smhy) {
		this.smhy = smhy;
	}

	public double getSmhh() {
		return smhh;
	}

	public void setSmhh(double smhh) {
		this.smhh = smhh;
	}

	public double getFmhx() {
		return fmhx;
	}

	public void setFmhx(double fmhx) {
		this.fmhx = fmhx;
	}

	public double getFmhy() {
		return fmhy;
	}

	public void setFmhy(double fmhy) {
		this.fmhy = fmhy;
	}

	public double getFmhh() {
		return fmhh;
	}

	public void setFmhh(double fmhh) {
		this.fmhh = fmhh;
	}

	public String getSmhGradeA() {
		return smhGradeA;
	}

	public void setSmhGradeA(String smhGradeA) {
		this.smhGradeA = smhGradeA;
	}

	public String getSmhGradeB() {
		return smhGradeB;
	}

	public void setSmhGradeB(String smhGradeB) {
		this.smhGradeB = smhGradeB;
	}

	public String getFmhGradeA() {
		return fmhGradeA;
	}

	public void setFmhGradeA(String fmhGradeA) {
		this.fmhGradeA = fmhGradeA;
	}

	public String getFmhGradeB() {
		return fmhGradeB;
	}

	public void setFmhGradeB(String fmhGradeB) {
		this.fmhGradeB = fmhGradeB;
	}

	public Pipe getPipe() {
		return pipe;
	}

	public void setPipe(Pipe pipe) {
		this.pipe = pipe;
	}

	public GeomItem getGeomItem() {
		return geomItem;
	}

	public void setGeomItem(GeomItem geomItem) {
		this.geomItem = geomItem;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

}
