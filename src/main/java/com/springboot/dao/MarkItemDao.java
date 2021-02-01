package com.springboot.dao;

import com.springboot.entity.MarkItem;

import java.util.List;
import java.util.Map;

public interface MarkItemDao {

	void insertMarkItem(MarkItem markItem);

	void deleteMarkItem(MarkItem markItem);

	MarkItem findInfoMarkItem(Map<String, Object> map);

	List<MarkItem> findViewMarkItem(Map<String, Object> map);

	List<MarkItem> findListMarkItem(Map<String, Object> map);

}
