package com.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.util.Computes;
import com.springboot.util.HelperPDF;

@RestController
@RequestMapping(value = "/test")
public class TestController {

	@Resource
	private Computes computes;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;
	@Resource
	private HelperPDF helperPDF;

	@RequestMapping(value = "/preview")
	public void preview(HttpServletRequest request, HttpServletResponse response) {

	}

	@RequestMapping(value = "/getpath")
	public ModelAndView contextLoads() {
		ModelAndView view = new ModelAndView();
		int port = 8080;
		String addr = "192.168.0.103";
		String path = "redirect:http://" + addr + ":" + port;
		view.setViewName(path);
		return view;
	}

}
