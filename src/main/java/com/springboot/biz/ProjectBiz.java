package com.springboot.biz;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ProjectBiz {

    void insertProject(Project project);

    void updateProject(Project project);

    void deleteProject(Project project);

    Project findInfoProject(int id, User user);

    Project findInfoProject(Map<String, Object> map);

    PageInfo<Project> findListProject(Map<String, Object> map);

    PageInfo<Project> findViewProject(Map<String, Object> map);

    List<Project> findRealProject(Map<String, Object> map);

    int appendProject(Project project, User user);

    void importDepth(Project project, MultipartFile file);

    void importImage(Project project, MultipartFile[] files);

    void removeImage(Project project);

    void sortImage(Project project);

}
