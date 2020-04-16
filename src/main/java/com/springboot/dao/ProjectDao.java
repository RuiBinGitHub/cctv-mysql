package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Project;

public interface ProjectDao {

	public void insertProject(Project project);

	public void updateProject(Project project);

	public void deleteProject(Project project);
	
	public int removeProject(Map<String, Object> map);

	public Project findInfoProject(Map<String, Object> map);

	public List<Project> findListProject(Map<String, Object> map);

}
