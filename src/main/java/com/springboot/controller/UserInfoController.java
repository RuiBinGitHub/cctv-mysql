package com.springboot.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.MarkPipeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.biz.UserBiz;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@RestController
@RequestMapping(value = "/userinfo")
public class UserInfoController {
	
	@Resource
	private MarkItemBiz markItemBiz;
	@Resource
	private MarkPipeBiz markPipeBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private UserBiz userBiz;
	
	private Map<String, Object> map = null;

	/** 获取人员列表 */
	@RequestMapping(value = "/showlist")
	public ModelAndView showlist(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("userinfo/showlist");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("page", page, "company", user.getCompany());
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<User> info = userBiz.findListUser(map);
		view.addObject("users", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	@RequestMapping(value = "/showinfo")
	public ModelAndView showInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		User temp = userBiz.findInfoUser(map);
		if (StringUtils.isEmpty(temp))
			return view;
		// 计算未提交项目
		map = AppHelper.getMap("user", temp, "state", "未提交");
		PageInfo<Project> info1 = projectBiz.findListProject(map);
		// 计算已提交项目
		map = AppHelper.getMap("user", temp, "state", "已提交");
		PageInfo<Project> info2 = projectBiz.findListProject(map);
		 // 计算管道数量
		int pipesize = pipeBiz.getCount(map);
		
		int count = 0;  
		map = AppHelper.getMap("temp", temp);
		PageInfo<MarkItem> info = markItemBiz.findListMarkItem(map);
		for (int i = 0; info.getList() != null && i < info.getList().size(); i++) {
			MarkItem markItem = info.getList().get(i);
			if (markItem.getScore1() >= 95 && markItem.getScore2() >= 85)
				count++;
		}
		view.setViewName("userinfo/showinfo");
		view.addObject("temp", temp);
		view.addObject("pipesize", pipesize);
		view.addObject("projects1", info1.getList());  // 未完成项目
		view.addObject("projects2", info2.getList());  // 已完成项目
		view.addObject("markItems", info.getList());
		view.addObject("count", count);  // 及格项目
		return view;
	}
	
	
	/** 跳转编辑页面 */
	@RequestMapping(value = "/updateview")
	public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		User temp = userBiz.findInfoUser(map);
		if (StringUtils.isEmpty(temp))
			return view;
		view.setViewName("userinfo/update");
		view.addObject("userinfo", temp);
		return view;
	}

	/** 编辑人员 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(int id, String role) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		User temp = userBiz.findInfoUser(map);
		if (StringUtils.isEmpty(temp))
			return view;
		temp.setRole(role);
		userBiz.updateUser(temp);
		view.setViewName("redirect:/success");
		return view;
	}

	/** 修改用户昵称 */
	@RequestMapping(value = "/updatename", method = RequestMethod.POST)
	public boolean updatename(String name) {
		User user = (User) AppHelper.findMap("user");
		user.setName(name);
		userBiz.updateUser(user);
		return true;
	}

	/** 修改用户密码 */
	@RequestMapping(value = "/updatepass", method = RequestMethod.POST)
	public boolean updatepass(String name, String pass) {
		User user = (User) AppHelper.findMap("user");
		if (!user.getPassword().equals(name))
			return false;
		user.setPassword(pass);
		userBiz.updateUser(user);
		return true;
	}

	/** 修改登录邮箱 */
	@RequestMapping(value = "/updatemail", method = RequestMethod.POST)
	public boolean updatemail(String mail, String code) {
		User user = (User) AppHelper.findMap("user");
		if (StringUtils.isEmpty(code))
			return false;
		user.setEmail(mail);
		userBiz.updateUser(user);
		return true;
	}
}
