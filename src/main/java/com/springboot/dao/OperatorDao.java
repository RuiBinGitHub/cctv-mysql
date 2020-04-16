package com.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.entity.Company;
import com.springboot.entity.Operator;

@Mapper
public interface OperatorDao {

	public void insertOperator(Operator operator);

	public void updateOperator(Operator operator);

	public void deleteOperator(Operator operator);

	public Operator findInfoOperator(Map<String, Object> map);

	public List<Operator> findListOperator(Map<String, Object> map);

	public List<String> findListName(Company company);

}
