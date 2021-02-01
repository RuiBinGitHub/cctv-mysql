package com.springboot.dao;

import com.springboot.entity.Code;

import java.util.List;
import java.util.Map;

public interface CodeDao {

	Code findInfoCode(Map<String, Object> map);

	List<Code> findListCode(Map<String, Object> map);
}
