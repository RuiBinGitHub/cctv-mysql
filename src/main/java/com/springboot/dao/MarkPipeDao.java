package com.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.entity.MarkPipe;

@Mapper
public interface MarkPipeDao {

	public void insertMarkPipe(MarkPipe markPipe);

	public void updateMarkPipe(MarkPipe markPipe);

	public void deleteMarkPipe(MarkPipe markPipe);

	public MarkPipe findInfoMarkPipe(Map<String, Object> map);

	public List<MarkPipe> findListMarkPipe(Map<String, Object> map);

}
