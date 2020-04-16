package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.Company;

public interface CompanyBiz {

	public void insertCompany(Company company);

	public void updateCompany(Company company);

	public void deleteCompany(Company company);

	public Company findInfoCompany(int id);
	
	public Company findInfoCompany(Map<String, Object> map);

	public PageInfo<Company> findListCompany(Map<String, Object> map);
	
	public int appendCompany(Company company);

	public int repeatCompany(Company company);
	

}
