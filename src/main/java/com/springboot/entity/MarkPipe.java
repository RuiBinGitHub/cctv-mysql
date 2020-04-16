package com.springboot.entity;

import java.io.Serializable;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getScore1() {
		return score1;
	}

	public void setScore1(double score1) {
		this.score1 = score1;
	}

	public double getScore2() {
		return score2;
	}

	public void setScore2(double score2) {
		this.score2 = score2;
	}

	public double getLevel1() {
		return level1;
	}

	public void setLevel1(double level1) {
		this.level1 = level1;
	}

	public double getLevel2() {
		return level2;
	}

	public void setLevel2(double level2) {
		this.level2 = level2;
	}

	public String getRemark() {
		return remark;
	}

	public Pipe getPipe() {
		return pipe;
	}

	public void setPipe(Pipe pipe) {
		this.pipe = pipe;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MarkItem getMarkItem() {
		return markItem;
	}

	public void setMarkItem(MarkItem markItem) {
		this.markItem = markItem;
	}

}
