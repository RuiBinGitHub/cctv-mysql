package com.springboot.entity;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String state;
	private String date;
	private User user; // 收件人
	private Project project;
	private MarkItem markItem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public MarkItem getMarkItem() {
		return markItem;
	}

	public void setMarkItem(MarkItem markItem) {
		this.markItem = markItem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
