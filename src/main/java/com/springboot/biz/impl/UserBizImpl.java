package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.UserBiz;
import com.springboot.dao.UserDao;
import com.springboot.entity.Company;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;

@Service
public class UserBizImpl implements UserBiz {

    @Resource
    private UserDao userDao;

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public User findInfoUser(Map<String, Object> map) {
        return userDao.findInfoUser(map);
    }

    public PageInfo<User> findListUser(Map<String, Object> map) {
        if (!StringUtils.isEmpty(map.get("name")))
            map.put("name", "%" + map.get("name") + "%");
        if (!StringUtils.isEmpty(map.get("page")))
            PageHelper.startPage((int) map.get("page"), 10);
        List<User> users = userDao.findListUser(map);
        return new PageInfo<>(users);
    }

    public List<User> findListUser(Company company) {
        Map<String, Object> map = AppUtils.getMap("company", company);
        return userDao.findListUser(map);
    }

}
