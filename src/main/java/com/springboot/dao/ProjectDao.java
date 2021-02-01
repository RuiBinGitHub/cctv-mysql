package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Project;

public interface ProjectDao {

    void insertProject(Project project);

    void updateProject(Project project);

    void deleteProject(Project project);

    Project findInfoProject(Map<String, Object> map);

    List<Project> findListProject(Map<String, Object> map);

    List<Project> findViewProject(Map<String, Object> map);

    List<Project> findRealProject(Map<String, Object> map);

}
