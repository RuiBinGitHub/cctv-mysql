package com.springboot.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.biz.GeomItemBiz;
import com.springboot.biz.GeomPipeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.dao.GeomItemDao;
import com.springboot.entity.GeomItem;
import com.springboot.entity.GeomPipe;
import com.springboot.entity.Pipe;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
@Transactional
public class GeomItemBizImpl implements GeomItemBiz {

	@Resource
	private GeomItemDao geomItemDao;
	@Resource
	private GeomPipeBiz geomPipeBiz;
	@Resource
	private PipeBiz pipeBiz;

	private Map<String, Object> map = null;

	public void insertGeomItem(GeomItem geomItem) {
		geomItemDao.insertGeomItem(geomItem);
	}

	public void updateGeomItem(GeomItem geomItem) {
		geomItemDao.updateGeomItem(geomItem);
	}

	public void deleteGeomItem(GeomItem geomItem) {
		geomItemDao.deleteGeomItem(geomItem);
	}

	public GeomItem findInfoGeomItem(Map<String, Object> map) {
		return geomItemDao.findInfoGeomItem(map);
	}

	public GeomItem findInfoGeomItem(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return geomItemDao.findInfoGeomItem(map);
	}

	public List<GeomItem> showListGeomItem(Map<String, Object> map) {
		return geomItemDao.showListGeomItem(map);
	}
	
	public void appendGeomItem(GeomItem geomItem) {
		this.insertGeomItem(geomItem);
		List<GeomPipe> geomPipes = new ArrayList<>();
		List<Pipe> pipes = pipeBiz.findListPipe(geomItem.getProject());
		for (int i = 0; pipes != null && i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			GeomPipe geomPipe = new GeomPipe();
			geomPipe.setPipe(pipe);
			geomPipe.setGeomItem(geomItem);
			geomPipeBiz.insertGeomPipe(geomPipe);
			geomPipes.add(geomPipe);
		}
		geomItem.setGeomPipes(geomPipes);
	}

}
