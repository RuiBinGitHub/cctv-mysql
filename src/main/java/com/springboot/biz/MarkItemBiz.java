package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface MarkItemBiz {

	public void insertMarkItem(MarkItem markItem);

	public void deleteMarkItem(MarkItem markItem);

	public MarkItem findInfoMarkItem(int id, User user);

	public MarkItem findInfoMarkItem(Map<String, Object> map);

	public PageInfo<Project> findViewMarkItem(Map<String, Object> map);

	public PageInfo<MarkItem> findListMarkItem(Map<String, Object> map);

	public int appendMarkItem(MarkItem MarkItem);

}
