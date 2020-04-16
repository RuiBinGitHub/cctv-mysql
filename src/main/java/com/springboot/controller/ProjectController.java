package com.springboot.controller;

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

import com.github.pagehelper.PageInfo;
import com.springboot.biz.CodeBiz;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.OperatorBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Code;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;
import com.springboot.util.ModeHelper;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

	@Value(value = "${mypath}")
	private String path;

	@Resource
	private ModeHelper helperMode;
	@Resource
	private OperatorBiz operatorBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;
	@Resource
	private CodeBiz codeBiz;

	private Map<String, Object> map = null;
	private List<String> names = null;

	/** 获取个人项目列表 */
	@RequestMapping(value = "/showlist")
	public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("project/showlist");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("state", "未提交", "page", page, "user", user);
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Project> info = projectBiz.findListProject(map);
		view.addObject("projects", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 获取公司项目列表 */
	@RequestMapping(value = "/findlist")
	public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
		ModelAndView view = new ModelAndView("project/findlist");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("state", "已提交", "page", page, "company", user.getCompany());
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Project> info = projectBiz.findListProject(map);
		view.addObject("projects", info.getList());
		view.addObject("count", info.getPages());
		view.addObject("page", page);
		return view;
	}

	/** 新建项目信息 */
	@RequestMapping(value = "/insertview")
	public ModelAndView insertView() {
		ModelAndView view = new ModelAndView("project/insert");
		User user = (User) AppHelper.findMap("user");
		names = operatorBiz.findListName(user.getCompany());
		view.addObject("names", names);
		return view;
	}

	/** 新建项目 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(Project project) {
		ModelAndView view = new ModelAndView();
		User user = (User) AppHelper.findMap("user");
		project.setState("未提交");
		project.setUser(user);
		int id = projectBiz.appendProject(project);
		view.setViewName("redirect:editinfo?id=" + id);
		return view;
	}

	/** 更新项目信息 */
	@RequestMapping(value = "/updateview")
	public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
		ModelAndView view = new ModelAndView("userivew/failure");
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return view;
		names = operatorBiz.findListName(user.getCompany());
		view.setViewName("project/update");
		view.addObject("project", project);
		view.addObject("names", names);
		return view;
	}

	/** 更新项目 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(Project project) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", project.getId(), "user", user);
		if (projectBiz.findInfoProject(map) == null)
			return view;
		project.setUser(user);
		projectBiz.updateProject(project);
		view.setViewName("redirect:/success");
		return view;
	}

	/** 提交数据 */
	@RequestMapping(value = "/submit")
	public boolean submit(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (!StringUtils.isEmpty(project)) {
			project.setState("已提交");
			projectBiz.updateProject(project);
		}
		return true;
	}

	/** 撤回项目 */
	@RequestMapping(value = "/revoke")
	public boolean revoke(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		Project project = projectBiz.findInfoProject(map);
		if (!StringUtils.isEmpty(project)) {
			project.setState("未提交");
			projectBiz.updateProject(project);
		}
		return true;
	}

	/** 删除项目 */
	@RequestMapping(value = "/delete")
	public boolean delete(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (!StringUtils.isEmpty(project))
			projectBiz.deleteProject(project);
		return true;
	}

	/** 移除项目 */
	@RequestMapping(value = "/remove")
	public boolean remove(@RequestParam(defaultValue = "0") int id) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		Project project = projectBiz.findInfoProject(map);
		if (!StringUtils.isEmpty(project))
			projectBiz.deleteProject(project);
		return true;
	}

	/** 获取项目列表 */
	@RequestMapping(value = "/combinelist")
	public List<Project> combineList(String name) {
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("state", "已提交", "company", user.getCompany());
		if (!StringUtils.isEmpty(name))
			map.put("name", name);
		PageInfo<Project> info = projectBiz.findListProject(map);
		return info.getList();
	}

	/** 合并项目 */
	@RequestMapping(value = "/combine", method = RequestMethod.POST)
	public ModelAndView combinItem(String list) {
		ModelAndView view = new ModelAndView("redirect:/success");
		User user = (User) AppHelper.findMap("user");
		projectBiz.combinProject(list, user.getCompany());
		return view;
	}

	/** 编辑项目 */
	@RequestMapping(value = "/editinfo")
	public ModelAndView editInfo(int id, @RequestParam(defaultValue = "0") int no) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (StringUtils.isEmpty(project))
			return view;
		view.setViewName("project/editinfo");
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		if (pipes != null && pipes.size() != 0) {
			no = no >= pipes.size() ? pipes.size() - 1 : no;
			Pipe pipe = pipes.get(no);
			pipe.setItems(itemBiz.findListItem(pipe));
			view.addObject("pipe", pipe);
		}
		names = operatorBiz.findListName(user.getCompany());
		List<Code> codes = codeBiz.findListCode(null);
		view.addObject("project", project);
		view.addObject("pipes", pipes);
		view.addObject("names", names);
		view.addObject("codes", codes);
		view.addObject("path", path);
		return view;
	}

	/** 项目浏览 */
	@RequestMapping(value = "/findinfo")
	public ModelAndView findInfo(int id, @RequestParam(defaultValue = "0") int no) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		map = AppHelper.getMap("id", id, "company", user.getCompany());
		Project project = projectBiz.findInfoProject(map);
		if (StringUtils.isEmpty(project))
			return view;
		view.setViewName("project/findinfo");
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		if (pipes != null && pipes.size() != 0) {
			no = no >= pipes.size() ? pipes.size() - 1 : no;
			Pipe pipe = pipes.get(no);
			pipe.setItems(itemBiz.findListItem(pipe));
			view.addObject("pipe", pipe);
		}
		view.addObject("project", project);
		view.addObject("pipes", pipes);
		view.addObject("path", path);
		return view;
	}

	/** 导入项目 */
	@RequestMapping(value = "/importitems", method = RequestMethod.POST)
	public ModelAndView inportItems(MultipartFile file) {
		ModelAndView view = new ModelAndView();
		User user = (User) AppHelper.findMap("user");
		view.setViewName("redirect:showlist");
		if (!StringUtils.isEmpty(file))
			helperMode.ItemMode(file, user);
		return view;
	}

	/** 导入深度 */
	@RequestMapping(value = "/importdepth", method = RequestMethod.POST)
	public ModelAndView importDepth(int id, MultipartFile xfile) {
		ModelAndView view = new ModelAndView("userview/failure");
		User user = (User) AppHelper.findMap("user");
		Project project = projectBiz.findInfoProject(id, user);
		if (project == null || xfile == null)
			return view;
		view.setViewName("redirect:editinfo?id=" + id);
		projectBiz.importProject(project, xfile);
		return view;
	}
}
