package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;

public interface MarkItemDao {

	void insertMarkItem(MarkItem markItem);

	void deleteMarkItem(MarkItem markItem);

	MarkItem findInfoMarkItem(Map<String, Object> map);

	List<Project> findViewMarkItem(Map<String, Object> map);

	List<MarkItem> findListMarkItem(Map<String, Object> map);

	int getCount(Map<String, Object> map);
}
