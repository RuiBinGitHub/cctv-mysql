package com.springboot.biz.impl;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.CompanyBiz;
import com.springboot.biz.UserBiz;
import com.springboot.dao.CompanyDao;
import com.springboot.entity.Company;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
@Transactional
public class CompanyBizImpl implements CompanyBiz {

	@Resource
	private CompanyDao companyDao;
	@Resource
	private UserBiz userBiz;

	private Map<String, Object> map = null;

	public void insertCompany(Company company) {
		companyDao.insertCompany(company);
	}

	public void updateCompany(Company company) {
		companyDao.updateCompany(company);
	}

	public void deleteCompany(Company company) {
		companyDao.deleteCompany(company);
	}

	public Company findInfoCompany(int id) {
		map = AppHelper.getMap("id", id);
		return companyDao.findInfoCompany(map);
	}

	public Company findInfoCompany(Map<String, Object> map) {
		return companyDao.findInfoCompany(map);
	}

	public PageInfo<Company> findListCompany(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<Company> companies = companyDao.findListCompany(map);
		PageInfo<Company> info = new PageInfo<Company>(companies);
		return info;
	}

	public int appendCompany(Company company) {
		company.setDate(AppHelper.getDate(null));
		companyDao.insertCompany(company);
		Format foramt1 = new DecimalFormat("#0000");
		for (int i = 0; i < company.getCont(); i++) {
			String name = foramt1.format(i + 1);
			String role = i == 0 ? "role2" : "role4";
			User user = new User();
			user.setName("No：" + name);
			user.setUsername(company.getCode() + name);
			user.setPassword(AppHelper.findPass());
			user.setEmail("--");
			user.setPhone("--");
			user.setState("正常");
			user.setRole(role);
			user.setCompany(company);
			userBiz.insertUser(user);
		}
		return company.getId();
	}

	public int repeatCompany(Company company) {
		this.updateCompany(company);
		Format foramt1 = new DecimalFormat("#0000");
		List<User> users = userBiz.findListUser(company);
		for (int i = users.size(); i > company.getCont(); i--) {
			users.get(i - 1).setState("冻结");
			userBiz.updateUser(users.get(i - 1));
		}
		for (int i = 0; i < company.getCont() && i < users.size(); i++) {
			if (users.get(i).getState().equals("冻结")) {
				users.get(i).setState("正常");
				userBiz.updateUser(users.get(i));
			}
		}
		for (int i = users.size(); i < company.getCont(); i++) {
			User user = new User();
			String name = foramt1.format(i + 1);
			user.setName("No：" + name);
			user.setUsername(company.getCode() + name);
			user.setPassword(AppHelper.findPass());
			user.setEmail("--");
			user.setPhone("--");
			user.setState("正常");
			user.setRole("role4");
			user.setCompany(company);
			userBiz.insertUser(user);
		}
		return company.getId();
	}

}
