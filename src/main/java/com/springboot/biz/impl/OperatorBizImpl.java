package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.springboot.util.AppUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.OperatorBiz;
import com.springboot.dao.OperatorDao;
import com.springboot.entity.Company;
import com.springboot.entity.Operator;

@Service
public class OperatorBizImpl implements OperatorBiz {

    @Resource
    private OperatorDao operatorDao;
    private Map<String, Object> map = null;

    public void insertOperator(Operator operator) {
        operatorDao.insertOperator(operator);
    }

    public void updateOperator(Operator operator) {
        operatorDao.updateOperator(operator);
    }

    public void deleteOperator(Operator operator) {
        operatorDao.deleteOperator(operator);
    }

    public Operator findInfoOperator(int id, Company company) {
        map = AppUtils.getMap("id", id, "company", company);
        return operatorDao.findInfoOperator(map);
    }

    public Operator findInfoOperator(Map<String, Object> map) {
        return operatorDao.findInfoOperator(map);
    }

    public PageInfo<Operator> findListOperator(Map<String, Object> map) {
        if (!StringUtils.isEmpty(map.get("name")))
            map.put("name", "%" + map.get("name") + "%");
        if (!StringUtils.isEmpty(map.get("page")))
            PageHelper.startPage((int) map.get("page"), 15);
        List<Operator> operators = operatorDao.findListOperator(map);
        return new PageInfo<>(operators);
    }

    public List<Operator> findListOperator(Company company) {
        map = AppUtils.getMap("company", company);
        return operatorDao.findListOperator(map);
    }

}
