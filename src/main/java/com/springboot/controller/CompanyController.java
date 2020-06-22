package com.springboot.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.CompanyBiz;
import com.springboot.biz.UserBiz;
import com.springboot.entity.Company;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

	@Resource
	private CompanyBiz companyBiz;
	@Resource
	private UserBiz userBiz;
	private Map<String, Object> map = null;

	@RequestMapping(value = "/showlist")
	public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("company/showlist");
		map = AppHelper.getMap("name", name, "page", page);
		PageInfo<Company> info = companyBiz.findListCompany(map);
		view.addObject("companies", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	@RequestMapping(value = "/findinfo")
	public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		Company company = companyBiz.findInfoCompany(id);
		if (StringUtils.isEmpty(company))
			return view;
		List<User> users = userBiz.findListUser(company);
		view.setViewName("company/findinfo");
		view.addObject("company", company);
		view.addObject("users", users);
		return view;
	}

	@RequestMapping(value = "/updateview")
	public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		Company company = companyBiz.findInfoCompany(id);
		if (StringUtils.isEmpty(company))
			return view;
		view.setViewName("company/update");
		view.addObject("company", company);
		return view;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(Company company) {
		ModelAndView view = new ModelAndView("company/insert");
		map = AppHelper.getMap("code", company.getCode());
		if (companyBiz.findInfoCompany(map) != null) {
			view.addObject("tips", "*账号前缀已被使用！");
			view.addObject("company", company);
			return view;
		}
		int id = companyBiz.appendCompany(company);
		view.setViewName("redirect:findinfo?id=" + id);
		return view;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(Company company) {
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/success");
		companyBiz.repeatCompany(company);
		return view;
	}

	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		Company company = companyBiz.findInfoCompany(id);
		if (!StringUtils.isEmpty(company))
			companyBiz.deleteCompany(company);
		return true;
	}

	@RequestMapping(value = "/download")
	public void findUserList(@RequestParam(defaultValue = "0") int id) {
		String[] header = {"编号", "用户昵称", "登录账号", "登录密码", "用户职务", "当前状态"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("用户表");
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeight((short) 360);
		HSSFRow headrow = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			HSSFCell cell = headrow.createCell(i);
			cell.setCellValue(header[i]);
		}
		Company company = companyBiz.findInfoCompany(id);
		List<User> users = userBiz.findListUser(company);
		for (int i = 0; users != null && i < users.size(); i++) {
			User user = users.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(i + "");
			row.createCell(1).setCellValue(user.getName());
			row.createCell(2).setCellValue(user.getUsername());
			row.createCell(3).setCellValue(user.getPassword());
			if ("role2".equals(user.getRole()))
				row.createCell(4).setCellValue("管理人员");
			else if ("role3".equals(user.getRole()))
				row.createCell(4).setCellValue("评分人员");
			else if ("role4".equals(user.getRole()))
				row.createCell(4).setCellValue("操作人员");
			row.createCell(5).setCellValue(user.getState());
		}

		HttpServletResponse response = AppHelper.getResponse();
		response.setHeader("Content-disposition", "attachment;filename=user.xls");
		response.setContentType("application/octet-stream");
		try {
			workbook.write(response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
