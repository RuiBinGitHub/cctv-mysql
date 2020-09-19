package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.biz.LoginfoBiz;
import com.springboot.dao.LoginfoDao;
import com.springboot.entity.Loginfo;
import com.springboot.util.AppHelper;

@Service
public class LoginfoBizImpl implements LoginfoBiz {

	@Resource
	private LoginfoDao loginfoDao;
	private Map<String, Object> map = null;

	public void insertLoginfo(Loginfo loginfo) {
		loginfoDao.insertLoginfo(loginfo);
	}

	public List<Loginfo> findListLogInfo(int id) {
		map = AppHelper.getMap("id", id);
		List<Loginfo> loginfos = loginfoDao.findListLoginfo(map);
		return loginfos;
	}

}
