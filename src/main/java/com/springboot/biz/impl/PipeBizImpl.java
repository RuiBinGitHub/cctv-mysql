package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.dao.PipeDao;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
public class PipeBizImpl implements PipeBiz {

	@Resource
	private PipeDao pipeDao;
	@Resource
	private ItemBiz itemBiz;

	private Map<String, Object> map = null;

	public void insertPipe(Pipe pipe) {
		pipeDao.insertPipe(pipe);
	}

	public void updatePipe(Pipe pipe) {
		pipeDao.updatePipe(pipe);
	}

	public void deletePipe(Pipe pipe) {
		pipeDao.deletePipe(pipe);
	}

	public void combinPipe(int id, List<?> list) {
		map  = AppHelper.getMap("id", id, "list", list);
		pipeDao.combinPipe(map);
	}
	
	public Pipe findInfoPipe(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return pipeDao.findInfoPipe(map);
	}

	public Pipe findInfoPipe(Map<String, Object> map) {
		return pipeDao.findInfoPipe(map);
	}

	public List<Pipe> findListPipe(Project project) {
		map = AppHelper.getMap("project", project);
		return pipeDao.findListPipe(map);
	}

	public List<Pipe> findListPipe(Map<String, Object> map) {
		return pipeDao.findListPipe(map);
	}

	public int getCount(Map<String, Object> map) {
		return pipeDao.getCount(map);
	}
	
	public void appendPipe(Pipe pipe) {
		insertPipe(pipe);
		String standard = pipe.getProject().getStandard();
		if ("HKCCEC 2009".equals(standard)) {
			Item item1 = new Item();
			Item item2 = new Item();
			Item item3 = new Item();
			item1.setNo(0);
			item1.setDist("0.0");
			item1.setCode("ST");
			item1.setPipe(pipe);

			item2.setNo(1);
			item2.setDist("0.0");
			item2.setCode("MH");
			item2.setPipe(pipe);

			item3.setNo(2);
			item3.setDist("0.0");
			item3.setCode("WL");
			item3.setPipe(pipe);

			itemBiz.insertItem(item1);
			itemBiz.insertItem(item2);
			itemBiz.insertItem(item3);
		} else {
			Item item = new Item();
			item.setNo(0);
			item.setDist("0.0");
			item.setPipe(pipe);
			itemBiz.insertItem(item);
		}
	}

}
