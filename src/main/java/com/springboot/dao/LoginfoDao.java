package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Loginfo;

public interface LoginfoDao {

	void insertLoginfo(Loginfo loginfo);

	List<Loginfo> findListLoginfo(Map<String, Object> map);

}
