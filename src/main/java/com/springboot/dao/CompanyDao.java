package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Company;

public interface CompanyDao {

	void insertCompany(Company company);

	void updateCompany(Company company);

	void deleteCompany(Company company);

	Company findInfoCompany(Map<String, Object> map);

	List<Company> findListCompany(Map<String, Object> map);
	
}
