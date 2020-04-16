package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.MarkPipeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.MarkItem;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@RestController
@RequestMapping(value = "/markinfo")
public class MarkInfoContorller {

	@Value(value = "${mypath}")
	private String path;

	@Resource
	private MarkItemBiz markItemBiz;
	@Resource
	private MarkPipeBiz markPipeBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;

	private Map<String, Object> map = null;

	/** 获取项目列表 */
	@RequestMapping(value = "/markview")
	public ModelAndView markView(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("markinfo/markview");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("state", "已提交", "page", page, "user", user, "company", user.getCompany());
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Project> info = markItemBiz.findViewMarkItem(map);
		view.addObject("projects", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 获取个人评分列表 */
	@RequestMapping(value = "/marklist")
	public ModelAndView marklist(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("markinfo/marklist");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("page", page, "user", user);
		if (!StringUtils.isEmpty("name"))
			map.put("name", name);
		PageInfo<MarkItem> info = markItemBiz.findListMarkItem(map);
		view.addObject("markItems", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 获取项目评分列表 */
	@RequestMapping(value = "/findlist")
	public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("markinfo/findlist");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("page", page, "company", user.getCompany());
		if (!StringUtils.isEmpty("name"))
			map.put("name", name);
		PageInfo<MarkItem> info = markItemBiz.findListMarkItem(map);
		view.addObject("markItems", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 获取指定项目评分列表 */
	@RequestMapping(value = "/showlist")
	public List<MarkItem> showList(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		Project project = projectBiz.findInfoProject(map);
		if (StringUtils.isEmpty(project))
			return null;
		map = AppHelper.getMap("project", project, "user", user);
		PageInfo<MarkItem> info = markItemBiz.findListMarkItem(map);
		return info.getList();
	}

	/** 创建项目评分 */
	@RequestMapping(value = "/insert")
	public ModelAndView insert(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		Project project = projectBiz.findInfoProject(map);
		if (StringUtils.isEmpty(project))
			return view;
		MarkItem markItem = new MarkItem();
		markItem.setUser(user);
		markItem.setProject(project);
		markItem.setDate(AppHelper.getDate(null));
		id = markItemBiz.appendMarkItem(markItem);
		view.setViewName("redirect:editinfo?id=" + id);
		return view;
	}

	/** 修改项目评分 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public boolean update(MarkPipe markPipe) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", markPipe.getId(), "user", user);
		if (markPipeBiz.findInfoMarkPipe(map) != null) 
		  markPipeBiz.updateMarkPipe(markPipe);
		return true;
	}

	/** 删除项目评分 */
	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "user", user);
		MarkItem markItem = markItemBiz.findInfoMarkItem(map);
		if (!StringUtils.isEmpty(markItem))
			markItemBiz.deleteMarkItem(markItem);
		return true;
	}

	/** 移除项目评分 */
	@RequestMapping(value = "/remove")
	public boolean remove(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		MarkItem markItem = markItemBiz.findInfoMarkItem(map);
		if (!StringUtils.isEmpty(markItem))
			markItemBiz.deleteMarkItem(markItem);
		return true;
	}

	/** 编辑项目评分 */
	@RequestMapping(value = "/editinfo")
	public ModelAndView editinfo(int id, @RequestParam(defaultValue = "0") int no) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "user", user);
		MarkItem markItem = markItemBiz.findInfoMarkItem(map);
		if (StringUtils.isEmpty(markItem))
			return view;
		List<MarkPipe> markPipes = markPipeBiz.findListMarkPipe(markItem);
		if (markPipes != null && markPipes.size() > no) {
			MarkPipe markPipe = markPipes.get(no);
			List<Item> items = itemBiz.findListItem(markPipe.getPipe());
			markPipe.getPipe().setItems(items);
			view.addObject("markPipe", markPipe);
		}
		view.setViewName("markinfo/editinfo");
		view.addObject("markItem", markItem);
		view.addObject("markPipes", markPipes);
		view.addObject("path", path);
		return view;
	}

	/** 查看项目评分 */
	@RequestMapping(value = "/findinfo")
	public ModelAndView findinfo(int id, @RequestParam(defaultValue = "0") int no) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		MarkItem markItem = markItemBiz.findInfoMarkItem(map);
		if (StringUtils.isEmpty(markItem))
			return view;
		List<MarkPipe> markPipes = markPipeBiz.findListMarkPipe(markItem);
		if (markPipes != null && markPipes.size() > no) {
			MarkPipe markPipe = markPipes.get(no);
			List<Item> items = itemBiz.findListItem(markPipe.getPipe());
			markPipe.getPipe().setItems(items);
			view.addObject("markPipe", markPipe);
		}
		view.setViewName("markinfo/findinfo");
		view.addObject("markItem", markItem);
		view.addObject("markPipes", markPipes);
		view.addObject("path", path);
		return view;
	}
}
