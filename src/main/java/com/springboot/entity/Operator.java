package com.springboot.entity;

import java.io.Serializable;

public class Operator implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String name;
	private String firstname;
	private String lastname;
	private String chianame;
	private String nickname;
	private String membergrades;
	private String membernumber;
	private Company company;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getChianame() {
		return chianame;
	}

	public void setChianame(String chianame) {
		this.chianame = chianame;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMembergrades() {
		return membergrades;
	}

	public void setMembergrades(String membergrades) {
		this.membergrades = membergrades;
	}

	public String getMembernumber() {
		return membernumber;
	}

	public void setMembernumber(String membernumber) {
		this.membernumber = membernumber;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
