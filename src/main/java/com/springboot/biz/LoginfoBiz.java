package com.springboot.biz;

import java.util.List;

import com.springboot.entity.Loginfo;

public interface LoginfoBiz {

	void insertLoginfo(Loginfo loginfo);

	List<Loginfo> findListLogInfo(int id);

}
