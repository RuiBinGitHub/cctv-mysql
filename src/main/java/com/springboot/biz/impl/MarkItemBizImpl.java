package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springboot.biz.MarkPipeBiz;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.MessageBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.dao.MarkItemDao;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
@Transactional
public class MarkItemBizImpl implements MarkItemBiz {

	@Resource
	private MessageBiz messageBiz;
	@Resource
	private MarkItemDao markItemDao;
	@Resource
	private MarkPipeBiz markPipeBiz;
	@Resource
	private PipeBiz pipeBiz;

	private Map<String, Object> map = null;

	public void insertMarkItem(MarkItem MarkItem) {
		markItemDao.insertMarkItem(MarkItem);
	}

	public void deleteMarkItem(MarkItem MarkItem) {
		markItemDao.deleteMarkItem(MarkItem);
	}

	public MarkItem findInfoMarkItem(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return markItemDao.findInfoMarkItem(map);
	}

	public MarkItem findInfoMarkItem(Map<String, Object> map) {
		return markItemDao.findInfoMarkItem(map);
	}

	public PageInfo<Project> findViewMarkItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<Project> projects = markItemDao.findViewMarkItem(map);
		PageInfo<Project> info = new PageInfo<Project>(projects);
		return info;
	}

	public PageInfo<MarkItem> findListMarkItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<MarkItem> markItems = markItemDao.findListMarkItem(map);
		PageInfo<MarkItem> info = new PageInfo<MarkItem>(markItems);
		return info;
	}

	public int getPage(Map<String, Object> map, int size) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		int count = markItemDao.getCount(map);
		return (int) Math.ceil((double) count / size);
	}

	public int appendMarkItem(MarkItem MarkItem) {
		this.insertMarkItem(MarkItem);
		Project project = MarkItem.getProject();
		messageBiz.sendMessage(project.getUser(), project, MarkItem);
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (int i = 0; pipes != null && i < pipes.size(); i++) {
			MarkPipe markPipe = new MarkPipe();
			markPipe.setScore1(0);
			markPipe.setScore2(0);
			markPipe.setLevel1(5);
			markPipe.setLevel2(5);
			markPipe.setPipe(pipes.get(i));
			markPipe.setMarkItem(MarkItem);
			markPipeBiz.insertMarkPipe(markPipe);
		}
		return MarkItem.getId();
	}

}
