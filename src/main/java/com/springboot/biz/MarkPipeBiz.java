package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.MarkItem;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.User;

public interface MarkPipeBiz {

	public void insertMarkPipe(MarkPipe markPipe);

	public void updateMarkPipe(MarkPipe markPipe);

	public void deleteMarkPipe(MarkPipe markPipe);

	public MarkPipe findInfoMarkPipe(int id, User user);

	public MarkPipe findInfoMarkPipe(Map<String, Object> map);

	public List<MarkPipe> findListMarkPipe(MarkItem markItem);

}
