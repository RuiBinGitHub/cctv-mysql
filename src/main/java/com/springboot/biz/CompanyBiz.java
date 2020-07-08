package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;

public interface CompanyBiz {

	void insertCompany(Company company);

	void updateCompany(Company company);

	void deleteCompany(Company company);

	Company findInfoCompany(int id);
	
	Company findInfoCompany(Map<String, Object> map);

	PageInfo<Company> findListCompany(Map<String, Object> map);
	
	int appendCompany(Company company);

	int repeatCompany(Company company);
	

}
