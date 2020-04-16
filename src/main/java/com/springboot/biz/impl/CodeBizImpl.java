package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.biz.CodeBiz;
import com.springboot.dao.CodeDao;
import com.springboot.entity.Code;
import com.springboot.util.AppHelper;

@Service
public class CodeBizImpl implements CodeBiz {

	@Resource
	private CodeDao codeDao;
	private Map<String, Object> map = null;

	public Code findInfoCode(Map<String, Object> map) {
		return codeDao.findInfoCode(map);
	}

	public Code findInfoCode(String standard, String code) {
		if (standard.indexOf("H") != -1)
			map = AppHelper.getMap("hkccec", code);
		else
			map = AppHelper.getMap("mscc", code);
		return codeDao.findInfoCode(map);
	}

	public List<Code> findListCode(Map<String, Object> map) {
		return codeDao.findListCode(map);
	}

}
