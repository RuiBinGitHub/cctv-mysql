package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@RestController
@RequestMapping(value = "/item")
public class ItemController {
	
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ItemBiz itemBiz;

	private Map<String, Object> map = null;

	/** 删除数据 */
	@RequestMapping(value = "/delete")
	public boolean deleteItem(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "user", user);
		Item item = itemBiz.findInfoItem(map);
		if (StringUtils.isEmpty(item))
			return false;
		itemBiz.deleteItem(item);
		if (!StringUtils.isEmpty(item.getPhoto()))
			itemBiz.sortItemImg(item.getPipe().getProject());
		return true;
	}

	/** 导入图片 */
	@Transactional
	@RequestMapping(value = "/importimages", method = RequestMethod.POST)
	public ModelAndView importImages(int id, MultipartFile[] files) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return view;
		map = AppHelper.getMap("project", project, "photo", "*插入图片");
		List<Item> items = itemBiz.findListItem(map);
		if (items != null && items.size() != files.length) {
			view.setViewName("userview/message");
			view.addObject("info", "导入图片失败，所需图片数量：" + items.size());
			return view;
		}
		project.setItems(items);
		itemBiz.importImage(project, files);
		String path = "redirect:/project/editinfo?id=" + id;
		view.setViewName(path);
		return view;
	}

	/** 移除图片 */
	@RequestMapping(value = "/removeimage")
	public boolean removeImage(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return false;
		itemBiz.removeImage(project);
		return true;
	}
}
