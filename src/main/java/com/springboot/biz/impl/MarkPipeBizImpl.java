package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.MarkPipeBiz;
import com.springboot.dao.MarkPipeDao;
import com.springboot.entity.MarkItem;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
public class MarkPipeBizImpl implements MarkPipeBiz {

	@Resource
	private MarkPipeDao markPipeDao;
	@Resource
	private ItemBiz itemBiz;

	private Map<String, Object> map = null;

	public void insertMarkPipe(MarkPipe markPipe) {
		markPipeDao.insertMarkPipe(markPipe);
	}

	public void updateMarkPipe(MarkPipe markPipe) {
		markPipeDao.updateMarkPipe(markPipe);
	}

	public void deleteMarkPipe(MarkPipe markPipe) {
		markPipeDao.deleteMarkPipe(markPipe);
	}

	public MarkPipe findInfoMarkPipe(Map<String, Object> map) {
		return markPipeDao.findInfoMarkPipe(map);
	}

	public MarkPipe findInfoMarkPipe(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return markPipeDao.findInfoMarkPipe(map);
	}

	public List<MarkPipe> findListMarkPipe(MarkItem markItem) {
		map = AppHelper.getMap("markItem", markItem);
		return markPipeDao.findListMarkPipe(map);
	}
}
