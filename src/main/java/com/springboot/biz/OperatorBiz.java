package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;
import com.springboot.entity.Operator;

public interface OperatorBiz {

	void insertOperator(Operator operator);

	void updateOperator(Operator operator);

	void deleteOperator(Operator operator);

	Operator findInfoOperator(Map<String, Object> map);

	PageInfo<Operator> findListOperator(Map<String, Object> map);

	List<String> findListName(Company company);

}
