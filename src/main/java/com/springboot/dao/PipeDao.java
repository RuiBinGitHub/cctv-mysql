package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Pipe;

public interface PipeDao {

	public void insertPipe(Pipe pipe);

	public void updatePipe(Pipe pipe);

	public void deletePipe(Pipe pipe);

	public void combinPipe(Map<String, Object> map);

	public Pipe findInfoPipe(Map<String, Object> map);

	public List<Pipe> findListPipe(Map<String, Object> map);

	public int getCount(Map<String, Object> map);

}
