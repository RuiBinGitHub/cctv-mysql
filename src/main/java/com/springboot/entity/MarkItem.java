package com.springboot.entity;

import java.io.Serializable;
import java.util.List;

public class MarkItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Project project;
	private User user;
	private String date;

	private double score1;
	private double score2;
	private List<MarkPipe> markPipes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public List<MarkPipe> getMarkPipes() {
		return markPipes;
	}

	public void setMarkPipes(List<MarkPipe> markPipes) {
		this.markPipes = markPipes;
	}

}
