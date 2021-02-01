package com.springboot.biz.impl;

import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDate;
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
import com.springboot.util.AppUtils;

@Service
@Transactional
public class CompanyBizImpl implements CompanyBiz {

    @Resource
    private CompanyDao companyDao;
    @Resource
    private UserBiz userBiz;

    public void deleteCompany(Company company) {
        companyDao.deleteCompany(company);
    }

    public Company findInfoCompany(int id) {
        Map<String, Object> map = AppUtils.getMap("id", id);
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
        return new PageInfo<>(companies);
    }

    public int appendCompany(Company company) {
        company.setDate(LocalDate.now().toString());
        companyDao.insertCompany(company);
        Format format = new DecimalFormat("#0000");
        for (int i = 1; i <= company.getCount(); i++) {
            String name = format.format(i);
            User user = new User();
            user.setName("No：" + name);
            user.setUsername(company.getCode() + name);
            user.setPassword(company.getCode() + name);
            user.setMail("--");
            user.setMobi("--");
            user.setState("正常");
            user.setRole(i == 1 ? "role2" : "role4");
            user.setCompany(company);
            userBiz.insertUser(user);
        }
        return company.getId();
    }

    public int repeatCompany(Company company) {
        companyDao.updateCompany(company);
        Format format = new DecimalFormat("#0000");
        List<User> users = userBiz.findListUser(company);
        for (int i = company.getCount(); i < users.size(); i++) {
            if (users.get(i).getState().equals("正常")) {
                users.get(i).setState("冻结");
                userBiz.updateUser(users.get(i));
            }
        }
        for (int i = 0; i < Math.min(company.getCount(), users.size()); i++) {
            if (users.get(i).getState().equals("冻结")) {
                users.get(i).setState("正常");
                userBiz.updateUser(users.get(i));
            }
        }
        for (int i = users.size(); i < company.getCount(); i++) {
            User user = new User();
            String name = format.format(i + 1);
            user.setName("No：" + name);
            user.setUsername(company.getCode() + name);
            user.setPassword(company.getCode() + name);
            user.setMail("--");
            user.setMobi("--");
            user.setState("正常");
            user.setRole(i == 1 ? "role2" : "role4");
            user.setCompany(company);
            userBiz.insertUser(user);
        }
        return company.getId();
    }

}
