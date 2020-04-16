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
import com.springboot.biz.OperatorBiz;
import com.springboot.entity.Operator;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@RestController
@RequestMapping(value = "/operator")
public class OperatorController {

	@Resource
	private OperatorBiz operatorBiz;

	private Map<String, Object> map = null;

	/** 获取人员列表 */
	@RequestMapping(value = "/showlist")
	public ModelAndView findlistOperator(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("operator/showlist");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("page", page, "company", user.getCompany());
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Operator> info = operatorBiz.findListOperator(map);
		view.addObject("operators", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 插入数据 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(Operator operator) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		operator.setCompany(user.getCompany());
		operatorBiz.insertOperator(operator);
		view.setViewName("redirect:/success");
		return view;
	}

	/** 获取人员信息 */
	@RequestMapping(value = "/updateview")
	public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		Operator operator = operatorBiz.findInfoOperator(map);
		if (StringUtils.isEmpty(operator))
			return view;
		view.setViewName("operator/update");
		view.addObject("operator", operator);
		return view;
	}

	/** 更新数据 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(Operator operator) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", operator.getId(), "company", user.getCompany());
		if (operatorBiz.findInfoOperator(map) == null)
			return view;
		operator.setCompany(user.getCompany());
		operatorBiz.updateOperator(operator);
		view.setViewName("redirect:/success");
		return view;
	}

	/** 删除数据 */
	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		Operator operator = operatorBiz.findInfoOperator(map);
		if (!StringUtils.isEmpty(operator))
			operatorBiz.deleteOperator(operator);
		return true;
	}

	/** 判断名称是否存在 */
	@RequestMapping(value = "/isexistname")
	public boolean isExistName(int id, String name) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("name", name, "company", user.getCompany());
		Operator operator = operatorBiz.findInfoOperator(map);
		if (operator == null || operator.getId() == id)
			return false;
		return true;
	}
}
