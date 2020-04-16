package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

	public void insertOperator(Operator operator) {
		operatorDao.insertOperator(operator);
	}

	public void updateOperator(Operator operator) {
		operatorDao.updateOperator(operator);
	}

	public void deleteOperator(Operator operator) {
		operatorDao.deleteOperator(operator);
	}

	public Operator findInfoOperator(Map<String, Object> map) {
		return operatorDao.findInfoOperator(map);
	}

	public PageInfo<Operator> findListOperator(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		List<Operator> operators = operatorDao.findListOperator(map);
		PageInfo<Operator> info = new PageInfo<Operator>(operators);
		return info;
	}

	public List<String> findListName(Company company) {
		return operatorDao.findListName(company);
	}

}
