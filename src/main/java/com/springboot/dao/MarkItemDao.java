package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;

public interface MarkItemDao {

	public void insertMarkItem(MarkItem markItem);

	public void deleteMarkItem(MarkItem markItem);

	public MarkItem findInfoMarkItem(Map<String, Object> map);

	public List<Project> findViewMarkItem(Map<String, Object> map);

	public List<MarkItem> findListMarkItem(Map<String, Object> map);

	public int getCount(Map<String, Object> map);
}
