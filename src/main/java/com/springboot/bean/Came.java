package com.springboot.bean;

public class Came {

	private String name;
	private String text;

	public Came() {
		this.name = "";
		this.text = "";
	}

	public Came(String name, String text) {
		this.name = name;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
