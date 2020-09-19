package com.springboot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.LoginfoBiz;
import com.springboot.entity.Loginfo;

@RestController
@RequestMapping(value = "/loginfo")
public class LoginfoController {

	@Resource
	private LoginfoBiz loginfoBiz;

	@RequestMapping(value = "/findlist")
	public List<Loginfo> findList(int id) {
		return loginfoBiz.findListLogInfo(id);
	}

}
