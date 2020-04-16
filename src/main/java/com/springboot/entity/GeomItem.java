package com.springboot.entity;

import java.io.Serializable;
import java.util.List;

public class GeomItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private double x;
	private double y;
	private String scope;
	private Project project;

	private List<GeomPipe> geomPipes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<GeomPipe> getGeomPipes() {
		return geomPipes;
	}

	public void setGeomPipes(List<GeomPipe> geomPipes) {
		this.geomPipes = geomPipes;
	}

}
