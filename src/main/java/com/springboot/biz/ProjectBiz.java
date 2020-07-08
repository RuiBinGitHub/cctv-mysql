package com.springboot.biz;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ProjectBiz {

	void insertProject(Project project);

	void updateProject(Project project);

	void deleteProject(Project project);

	Project findInfoProject(int id, User user);

	Project findInfoProject(Map<String, Object> map);

	PageInfo<Project> findListProject(Map<String, Object> map);

	int appendProject(Project project);
	
	int removeProject(Company company, List<?> list);

	void combinProject(String list, Company company);

	void importProject(Project project, MultipartFile file);

}
