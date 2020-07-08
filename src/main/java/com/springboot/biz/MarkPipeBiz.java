package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.MarkItem;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.User;

public interface MarkPipeBiz {

	void insertMarkPipe(MarkPipe markPipe);

	void updateMarkPipe(MarkPipe markPipe);

	void deleteMarkPipe(MarkPipe markPipe);

	MarkPipe findInfoMarkPipe(int id, User user);

	MarkPipe findInfoMarkPipe(Map<String, Object> map);

	List<MarkPipe> findListMarkPipe(MarkItem markItem);

}
