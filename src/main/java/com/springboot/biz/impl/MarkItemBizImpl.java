package com.springboot.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.MessageBiz;
import com.springboot.dao.MarkItemDao;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MarkItemBizImpl implements MarkItemBiz {

	@Resource
	private MarkItemDao markItemDao;
	@Resource
	private MessageBiz messageBiz;
	private Map<String, Object> map = null;

	public void insertMarkItem(MarkItem MarkItem) {
		markItemDao.insertMarkItem(MarkItem);
	}

	public void deleteMarkItem(MarkItem MarkItem) {
		markItemDao.deleteMarkItem(MarkItem);
	}

	public MarkItem findInfoMarkItem(int id, User user) {
		map = AppUtils.getMap("id", id, "user", user);
		return markItemDao.findInfoMarkItem(map);
	}

	public MarkItem findInfoMarkItem(Map<String, Object> map) {
		return markItemDao.findInfoMarkItem(map);
	}

	public PageInfo<MarkItem> findViewMarkItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<MarkItem> markItems = markItemDao.findViewMarkItem(map);
		return new PageInfo<>(markItems);
	}

	public PageInfo<MarkItem> findListMarkItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<MarkItem> markItems = markItemDao.findListMarkItem(map);
		return new PageInfo<>(markItems);
	}

	public MarkItem appendMarkItem(Project project, User user) {
		MarkItem markItem = new MarkItem();
		markItem.setDate(LocalDate.now().toString());
		markItem.setProject(project);
		markItem.setUser(user);
		markItemDao.insertMarkItem(markItem);
		messageBiz.sendMessage(project.getUser(), project, markItem);
		return markItem;
	}

}
