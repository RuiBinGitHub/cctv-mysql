package com.springboot.biz.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.biz.ItemBiz;
import com.springboot.dao.ItemDao;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
@Transactional
public class ItemBizImpl implements ItemBiz {

	@Value("${myfile}")
	private String myfile;
	@Resource
	private ItemDao itemDao;

	private Map<String, Object> map = null;

	public void insertItem(Item item) {
		itemDao.insertItem(item);
	}

	public void updateItem(Item item) {
		itemDao.updateItem(item);
	}

	public void deleteItem(Item item) {
		itemDao.deleteItem(item);
		String name = item.getPicture();
		String path = myfile + "/ItemImage/";
		if (!StringUtils.isEmpty(name)) {
			File file = FileUtils.getFile(path + name + ".png");
			FileUtils.deleteQuietly(file);
		}
	}

	public Item findInfoItem(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return itemDao.findInfoItem(map);
	}

	public Item findInfoItem(Map<String, Object> map) {
		return itemDao.findInfoItem(map);
	}

	public List<Item> findListItem(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			map.put("size", ((int) map.get("page") - 1) * 30);
		return itemDao.findListItem(map);
	}

	public List<Item> findListItem(Project project) {
		map = AppHelper.getMap("project", project);
		return itemDao.findListItem(map);
	}

	public List<Item> findListItem(Pipe pipe) {
		map = AppHelper.getMap("pipe", pipe);
		return itemDao.findListItem(map);
	}

	public boolean importImage(Project project, MultipartFile[] files) {
		List<Item> items = project.getItems();
		String path = myfile + "/ItemImage/";
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			String name = AppHelper.UUIDCode();
			File dest = new File(path + name + ".png");
			AppHelper.move(files[i], dest);
			item.setPicture(name);
			this.updateItem(item);
		}
		sortItemImg(project);
		return true;
	}

	public boolean removeImage(Project project) {
		String path = myfile + "/ItemImage/";
		map = AppHelper.getMap("project", project, "picture", "");
		List<Item> items = this.findListItem(map);
		for (int i = 0; items != null && i < items.size(); i++) {
			String name = items.get(i).getPicture();
			items.get(i).setPhoto("*插入图片");
			items.get(i).setPicture("");
			this.updateItem(items.get(i));
			File file = new File(path + name + ".png");
			file.delete();
		}
		return true;
	}

	public void sortItemImg(Project project) {
		DecimalFormat format = new DecimalFormat("#000");
		map = AppHelper.getMap("project", project, "picture", "");
		List<Item> items = this.findListItem(map);
		for (int i = 0; items != null && i < items.size(); i++) {
			items.get(i).setPhoto(format.format(i + 1));
			this.updateItem(items.get(i));
		}
	}
}
