package com.springboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.springboot.bean.AppBean;
import com.springboot.entity.*;
import com.springboot.util.*;
import lombok.Cleanup;
import lombok.SneakyThrows;
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

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    @Resource
    private TranHelper tranHelper;
    @Resource
    private ModeHelper modeHelper;
    @Resource
    private ModePreview modePreview;
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

    /**
     * 获取个人项目列表
     */
    @RequestMapping(value = "/showlist", method = RequestMethod.GET)
    public ModelAndView showList(String name, String sort, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("project/showlist");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("state", "未提交", "sort", sort, "page", page, "user", user);
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        PageInfo<Project> info = projectBiz.findViewProject(map);
        view.addObject("projects", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 获取公司项目列表
     */
    @RequestMapping(value = "/findlist", method = RequestMethod.GET)
    public ModelAndView findList(String name, String sort, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("project/findlist");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("state", "已提交", "sort", sort, "page", page, "company", user.getCompany());
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        PageInfo<Project> info = projectBiz.findViewProject(map);
        view.addObject("projects", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    @RequestMapping(value = "/viewlist1", method = RequestMethod.POST)
    public List<Project> viewList1(String name, @RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("state", "未提交", "name", name, "user", user);
        return projectBiz.findRealProject(map).stream().filter(project -> project.getId() != id).peek(project -> {
            if (StringUtils.isEmpty(project.getWorkorder()))
                project.setWorkorder("");
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/viewlist2", method = RequestMethod.POST)
    public List<Project> viewList2(String name, @RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("state", "已提交", "name", name, "company", user.getCompany());
        return projectBiz.findRealProject(map).stream().filter(project -> project.getId() != id).peek(project -> {
            if (StringUtils.isEmpty(project.getWorkorder()))
                project.setWorkorder("");
        }).collect(Collectors.toList());
    }

    /**
     * 新建项目信息
     */
    @RequestMapping(value = "/insertview", method = RequestMethod.GET)
    public ModelAndView insertView() {
        ModelAndView view = new ModelAndView("project/insert");
        User user = (User) AppUtils.getValue("user");
        List<Operator> operators = operatorBiz.findListOperator(user.getCompany());
        view.addObject("operators", operators);
        return view;
    }

    /**
     * 新建项目
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Project project) {
        ModelAndView view = new ModelAndView();
        User user = (User) AppUtils.getValue("user");
        int id = projectBiz.appendProject(project, user);
        view.setViewName("redirect:editinfo?id=" + id);
        return view;
    }

    /**
     * 更新项目信息
     */
    @RequestMapping(value = "/updateview", method = RequestMethod.GET)
    public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return view;
        List<Operator> operators = operatorBiz.findListOperator(user.getCompany());
        view.setViewName("project/update");
        view.addObject("project", project);
        view.addObject("operators", operators);
        return view;
    }

    /**
     * 更新项目
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Project project) {
        ModelAndView view = new ModelAndView("redirect:failure");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", project.getId(), "user", user);
        if (projectBiz.findInfoProject(map) == null)
            return view;
        projectBiz.updateProject(project);
        view.setViewName("redirect:success");
        return view;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public boolean submit(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (!StringUtils.isEmpty(project)) {
            project.setState("已提交");
            projectBiz.updateProject(project);
        }
        return true;
    }

    /**
     * 撤回项目
     */
    @RequestMapping(value = "/revoke", method = RequestMethod.POST)
    public boolean revoke(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (!StringUtils.isEmpty(project)) {
            project.setState("未提交");
            projectBiz.updateProject(project);
        }
        return true;
    }

    /**
     * 删除项目
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (!StringUtils.isEmpty(project))
            projectBiz.deleteProject(project);
        return true;
    }

    /**
     * 移除项目
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public boolean remove(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (!StringUtils.isEmpty(project))
            projectBiz.deleteProject(project);
        return true;
    }

    /**
     * 获取项目列表
     */
    @RequestMapping(value = "/combinelist", method = RequestMethod.POST)
    public List<Project> combineList(String name) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("state", "已提交", "company", user.getCompany());
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        PageInfo<Project> info = projectBiz.findListProject(map);
        return info.getList();
    }

    /**
     * 合并项目
     */
    @RequestMapping(value = "/combine", method = RequestMethod.POST)
    public ModelAndView combin(String list) {
        ModelAndView view = new ModelAndView("redirect:failure");
        User user = (User) AppUtils.getValue("user");
        String[] split = list.split(",");
        List<Project> projects = new ArrayList<>(split.length);
        for (int i = 0; split.length > 1 && i < split.length; i++) {
            map = AppUtils.getMap("id", split[i], "company", user.getCompany());
            Project project = projectBiz.findInfoProject(map);
            if (StringUtils.isEmpty(project))
                return view;
            projects.add(project);
        }

        Project project = projects.get(0);
        for (int i = 1; i < projects.size(); i++) {
            Project temp = projects.get(i);
            List<Pipe> pipes = pipeBiz.findListPipe(temp);
            projectBiz.deleteProject(temp);
            for (Pipe pipe : pipes) {
                pipe.setProject(project);
                pipeBiz.updatePipe(pipe);
            }
        }
        projectBiz.sortImage(project);
        view.setViewName("redirect:success");
        return view;
    }

    /**
     * 编辑项目
     */
    @RequestMapping(value = "/editinfo", method = RequestMethod.GET)
    public ModelAndView editInfo(int id, @RequestParam(defaultValue = "0") int no) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return view;
        view.setViewName("project/editinfo");
        Pipe pipe = new Pipe();
        List<Pipe> pipes = pipeBiz.findListPipe(project);
        if (pipes != null && pipes.size() != 0) {
            int index = Math.min(no, pipes.size() - 1);
            pipe = pipes.get(index);
            pipe.setItems(itemBiz.findListItem(pipe));
        }
        List<Operator> operators = operatorBiz.findListOperator(user.getCompany());
        List<Code> codes = codeBiz.findListCode(null);
        view.addObject("project", project);
        view.addObject("pipes", pipes);
        view.addObject("operators", operators);
        view.addObject("codes", codes);
        view.addObject("pipe", pipe);
        return view;
    }

    /**
     * 项目浏览
     */
    @RequestMapping(value = "/findinfo", method = RequestMethod.GET)
    public ModelAndView findInfo(int id, @RequestParam(defaultValue = "0") int no) {
        ModelAndView view = new ModelAndView("common/failure");
        Project project = projectBiz.findInfoProject(id, null);
        if (StringUtils.isEmpty(project))
            return view;
        view.setViewName("project/findinfo");
        Pipe pipe = new Pipe();
        List<Pipe> pipes = pipeBiz.findListPipe(project);
        if (pipes != null && pipes.size() != 0) {
            int index = Math.min(no, pipes.size() - 1);
            pipe = pipes.get(index);
            pipe.setItems(itemBiz.findListItem(pipe));
        }
        view.addObject("project", project);
        view.addObject("pipes", pipes);
        view.addObject("pipe", pipe);
        return view;
    }

    @RequestMapping(value = "/checkview")
    public ModelAndView check(int id, @RequestParam(defaultValue = "0") int no) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (StringUtils.isEmpty(project))
            return view;
        view.setViewName("project/checkview");
        Pipe pipe = new Pipe();
        List<Pipe> pipes = pipeBiz.findListPipe(project);
        if (pipes != null && pipes.size() != 0) {
            int index = Math.min(no, pipes.size() - 1);
            pipe = pipes.get(index);
            pipe.setItems(itemBiz.findListItem(pipe));
        }
        List<Operator> operators = operatorBiz.findListOperator(user.getCompany());
        List<Code> codes = codeBiz.findListCode(null);
        view.addObject("project", project);
        view.addObject("pipes", pipes);
        view.addObject("operators", operators);
        view.addObject("codes", codes);
        view.addObject("pipe", pipe);
        return view;
    }

    /**
     * 导入项目
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ModelAndView inportItems(MultipartFile file) {
        ModelAndView view = new ModelAndView("redirect:showlist");
        User user = (User) AppUtils.getValue("user");
        if (!StringUtils.isEmpty(file))
            modeHelper.ItemMode(file, user);
        return view;
    }

    /**
     * 预览文件
     */
    @RequestMapping(value = "/previe")
    public ModelAndView previewItem(MultipartFile file) {
        ModelAndView view = new ModelAndView("common/failure");
        String path = modePreview.ItemPrev(file);
        if (StringUtils.isEmpty(path))
            return view;
        view.setViewName("project/preview");
        view.addObject("path", path);
        return view;
    }

    @RequestMapping(value = "/tranview")
    public ModelAndView transView(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("project/tranview");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("standard", "HKCCEC 2009", "page", page, "user", user);
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        PageInfo<Project> info = projectBiz.findListProject(map);
        view.addObject("projects", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;

    }

    /**
     * 转换项目规范
     */
    @RequestMapping(value = "/tranitem", method = RequestMethod.POST)
    public boolean trans(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (!StringUtils.isEmpty(project))
            tranHelper.ItemTran(project);
        return true;
    }

    @SneakyThrows
    @RequestMapping(value = "/export")
    public void export(int id, HttpServletResponse response) {
        Project project = projectBiz.findInfoProject(id, null);
        if (StringUtils.isEmpty(project))
            return;
        @Cleanup ExcelWriter workbook = ExcelUtil.getWriter(true);
        workbook.setColumnWidth(0, 8);
        workbook.setColumnWidth(1, 12);
        workbook.setColumnWidth(2, 15);
        workbook.setColumnWidth(3, 12);
        workbook.setColumnWidth(4, 10);
        workbook.setColumnWidth(5, 10);
        workbook.setColumnWidth(6, 10);
        workbook.setColumnWidth(7, 18);
        workbook.setDefaultRowHeight(18);

        workbook.addHeaderAlias("item", "Item");
        workbook.addHeaderAlias("date", "Survey Date");
        workbook.addHeaderAlias("location", "Location");
        workbook.addHeaderAlias("section", "Section");
        workbook.addHeaderAlias("material", "Material");
        workbook.addHeaderAlias("type", "Type");
        workbook.addHeaderAlias("size", "Dia (mm)");
        workbook.addHeaderAlias("length", "Surveyed Length (m)");

        List<Pipe> pipes = pipeBiz.findListPipe(project);
        pipes = pipes.stream().peek(pipe -> {
            if (pipe.getSmanholeno() == null)
                pipe.setSmanholeno("");
            if (pipe.getFmanholeno() == null)
                pipe.setFmanholeno("");
            if (!StringUtils.isEmpty(pipe.getDire()))
                pipe.setDire(pipe.getDire() + "S");
            else
                pipe.setDire("");
        }).collect(Collectors.toList());

        List<Map<String, String>> list = new ArrayList<>(pipes.size());
        for (Pipe pipe : pipes) {
            Map<String, String> map = new HashMap<>();
            map.put("item", pipe.getNo() + "");
            map.put("date", pipe.getDate());
            map.put("location", pipe.getRoadname());
            map.put("section", pipe.getSmanholeno() + " " + pipe.getDire() + " " + pipe.getFmanholeno());
            map.put("material", CameHelper.getCameText(pipe.getMater(), "mat"));
            map.put("type", CameHelper.getCameText(pipe.getUses(), "use"));
            map.put("size", pipe.getHsize());
            map.put("length", pipe.getTestlength() + "");
            list.add(map);
        }

        workbook.write(list);
        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment;filename=pipe.xlsx");
        workbook.flush(response.getOutputStream());
        response.flushBuffer();
    }

    /**
     * 导入深度
     */
    @RequestMapping(value = "/importdepth", method = RequestMethod.POST)
    public ModelAndView importDepth(int id, MultipartFile file) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (project == null || file == null)
            return view;
        view.setViewName("redirect:editinfo?id=" + id);
        projectBiz.importDepth(project, file);
        return view;
    }

    /**
     * 导入图片
     */
    @RequestMapping(value = "/importimages", method = RequestMethod.POST)
    public boolean importImage(int id, MultipartFile[] files) {
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return true;
        map = AppUtils.getMap("project", project, "photo", "*插入图片");
        List<Item> items = itemBiz.findListItem(map);
        if (items != null && items.size() != files.length)
            return true;
        project.setItems(items);
        projectBiz.importImage(project, files);
        return true;
    }

    /**
     * 移除图片
     */
    @RequestMapping(value = "/removeimage")
    public boolean removeImage(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (!StringUtils.isEmpty(project))
            projectBiz.removeImage(project);
        return true;
    }
}
