package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.MarkPipe;

public interface MarkPipeDao {

	void insertMarkPipe(MarkPipe markPipe);

	void updateMarkPipe(MarkPipe markPipe);

	void deleteMarkPipe(MarkPipe markPipe);

	MarkPipe findInfoMarkPipe(Map<String, Object> map);

	List<MarkPipe> findListMarkPipe(Map<String, Object> map);

}
