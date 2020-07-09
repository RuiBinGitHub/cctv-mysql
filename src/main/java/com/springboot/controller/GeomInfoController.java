package com.springboot.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.GeomItemBiz;
import com.springboot.biz.GeomPipeBiz;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.GeomItem;
import com.springboot.entity.GeomPipe;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;
import com.springboot.util.CameHelper;
import com.springboot.util.Computes;

@RestController
@RequestMapping(value = "/geominfo")
public class GeomInfoController {

	@Value(value = "${mypath}")
	private String path;
	
	@Resource
	private GeomItemBiz geomItemBiz;
	@Resource
	private GeomPipeBiz geomPipeBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ItemBiz itemBiz;
	@Resource
	private Computes computes;

	private Map<String, Object> map = null;
	private List<GeomItem> geomItems = null;
	private List<GeomPipe> geomPipes = null;

	@RequestMapping(value = "/showlist")
	public ModelAndView showList() {
		ModelAndView view = new ModelAndView("geominfo/showlist");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("company", user.getCompany());
		geomItems = geomItemBiz.showListGeomItem(map);
		geomPipes = geomPipeBiz.findListGeomPipe(map);
		for (int i = 0; geomPipes != null && i < geomPipes.size(); i++) {
			Pipe pipe = geomPipes.get(i).getPipe();
			pipe.setItems(itemBiz.findListItem(pipe));
			computes.computePipe(pipe, "HKCCEC 2009");
			double value1 = pipe.getGrade()[0], value2 = pipe.getGrade()[3];
			geomPipes.get(i).setGrade(value1 > value2 ? value1 : value2);
		}
		view.addObject("geomItems", geomItems);
		view.addObject("geomPipes", geomPipes);
		return view;
	}

	/** 查看坐标录入 */
	@RequestMapping(value = "/showinfo")
	public ModelAndView showInfo(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userviwe/failure");
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return view;
		map = AppHelper.getMap("project", project, "user", user);
		GeomItem geomItem = geomItemBiz.findInfoGeomItem(map);
		if (StringUtils.isEmpty(geomItem)) {
			geomItem = new GeomItem();
			geomItem.setProject(project);
			geomItemBiz.appendGeomItem(geomItem);
		} else {
			geomPipes = geomPipeBiz.findListGeomPipe(geomItem);
			geomItem.setGeomPipes(geomPipes);
		}
		List<String> scopes = null;
		if (!StringUtils.isEmpty(geomItem.getScope()))
			scopes = Arrays.asList(geomItem.getScope().split(","));
		view.setViewName("geominfo/showinfo");
		view.addObject("geomItem", geomItem);
		view.addObject("scopes", scopes);
		return view;
	}

	@RequestMapping(value = "/findinfo")
	public GeomPipe findInfo(@RequestParam(defaultValue = "0") int id) {
		GeomPipe geomPipe = geomPipeBiz.findInfoGeomPipe(id, null);
		if (StringUtils.isEmpty(geomPipe))
			return null;
		Pipe pipe = computes.computePipe(geomPipe.getPipe(), "HKCCEC 2009");
		pipe.setMater(CameHelper.getCameText(pipe.getMater(), "mat"));
		pipe.setShape(CameHelper.getCameText(pipe.getShape(), "sha"));
		return geomPipe;
	}

	/** 管道对比 */
	@RequestMapping(value = "/compare")
	public ModelAndView compare(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		GeomPipe geomPipe = geomPipeBiz.findInfoGeomPipe(id, null);
		if (StringUtils.isEmpty(geomPipe))
			return view;
		view.setViewName("userview/message");
		view.addObject("info", "请输入该管道的坐标信息！");
		if (StringUtils.isEmpty(geomPipe.getSmhGradeA()))
			return view;
		if (StringUtils.isEmpty(geomPipe.getFmhGradeA()))
			return view;
		map = AppHelper.getMap("company", user.getCompany());
		map.put("smhGradeA", geomPipe.getSmhGradeA());
		map.put("fmhGradeA", geomPipe.getFmhGradeA());
		List<GeomPipe> geomPipes = geomPipeBiz.findListGeomPipe(map);
		for (GeomPipe geomTemp : geomPipes) {
			Pipe pipe = geomTemp.getPipe();
			pipe.setItems(itemBiz.findListItem(pipe));
		}
		view.setViewName("geominfo/compare");
		view.addObject("geomPipes", geomPipes);
		view.addObject("path", path);
		return view;
	}

	/** 输入项目范围 */
	@RequestMapping(value = "/inputscope", method = RequestMethod.POST)
	public boolean inputRange(GeomItem geomItem) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", geomItem.getId(), "user", user);
		GeomItem geomTemp = geomItemBiz.findInfoGeomItem(map);
		if (StringUtils.isEmpty(geomTemp))
			return false;
		geomTemp.setScope(geomItem.getScope());
		geomItemBiz.updateGeomItem(geomTemp);
		return true;
	}

	/** 输入管道坐标 */
	@RequestMapping(value = "/inputvalue", method = RequestMethod.POST)
	public boolean inputValue(GeomPipe geomPipe) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", geomPipe.getId(), "user", user);
		GeomPipe geomTemp = geomPipeBiz.findInfoGeomPipe(map);
		if (StringUtils.isEmpty(geomTemp))
			return false;
		geomTemp.setSmhx(geomPipe.getSmhx());
		geomTemp.setSmhy(geomPipe.getSmhy());
		geomTemp.setSmhh(geomPipe.getSmhh());
		geomTemp.setFmhx(geomPipe.getFmhx());
		geomTemp.setFmhy(geomPipe.getFmhy());
		geomTemp.setFmhh(geomPipe.getFmhh());
		geomPipeBiz.replacePipegeom(geomTemp);
		return true;
	}

	/** 导入管道坐标 */
	@RequestMapping(value = "/importvalue", method = RequestMethod.POST)
	public ModelAndView importValue(int id, MultipartFile file) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "user", user);
		GeomItem geomTemp = geomItemBiz.findInfoGeomItem(map);
		if (StringUtils.isEmpty(geomTemp))
			return view;
		view.setViewName("userview/success");
		geomPipeBiz.importValue(geomTemp, file);
		return view;
	}

	/** 修改管道编码 */
	@RequestMapping(value = "/updategrade", method = RequestMethod.POST)
	public boolean updateGrade(GeomPipe geomPipe) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", geomPipe.getId(), "user", user);
		GeomPipe geomTemp = geomPipeBiz.findInfoGeomPipe(map);
		if (StringUtils.isEmpty(geomTemp))
			return false;
		geomTemp.setSmhGradeA(geomPipe.getSmhGradeA());
		geomTemp.setSmhGradeB(geomPipe.getSmhGradeB());
		geomTemp.setFmhGradeA(geomPipe.getFmhGradeA());
		geomTemp.setFmhGradeB(geomPipe.getFmhGradeB());
		geomPipeBiz.updateGeomPipe(geomTemp);
		return true;
	}

}
