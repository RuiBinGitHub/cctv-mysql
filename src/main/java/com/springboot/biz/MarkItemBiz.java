package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface MarkItemBiz {

	void insertMarkItem(MarkItem markItem);

	void deleteMarkItem(MarkItem markItem);

	MarkItem findInfoMarkItem(int id, User user);

	MarkItem findInfoMarkItem(Map<String, Object> map);

	PageInfo<Project> findViewMarkItem(Map<String, Object> map);

	PageInfo<MarkItem> findListMarkItem(Map<String, Object> map);

	int appendMarkItem(MarkItem MarkItem);

}
