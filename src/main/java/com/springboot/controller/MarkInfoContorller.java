package com.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.*;
import com.springboot.entity.*;
import com.springboot.util.AppUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/markinfo")
public class MarkInfoContorller {

    @Resource
    private MarkItemBiz markItemBiz;
    @Resource
    private MarkPipeBiz markPipeBiz;
    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private PipeBiz pipeBiz;
    @Resource
    private ItemBiz itemBiz;

    private Map<String, Object> map = null;

    /**
     * 获取项目列表
     */
    @RequestMapping(value = "/markview", method = RequestMethod.GET)
    public ModelAndView markView(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("markinfo/markview");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("page", page, "user", user, "company", user.getCompany());
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        PageInfo<MarkItem> info = markItemBiz.findViewMarkItem(map);
        view.addObject("markItems", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 获取个人评分列表
     */
    @RequestMapping(value = "/marklist")
    public ModelAndView marklist(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("markinfo/marklist");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("page", page, "user", user);
        if (!StringUtils.isEmpty("name"))
            map.put("name", name);
        PageInfo<MarkItem> info = markItemBiz.findListMarkItem(map);
        view.addObject("markItems", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 获取项目评分列表
     */
    @RequestMapping(value = "/findlist")
    public ModelAndView findList(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("markinfo/findlist");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("page", page, "company", user.getCompany());
        if (!StringUtils.isEmpty("name"))
            map.put("name", name);
        PageInfo<MarkItem> info = markItemBiz.findListMarkItem(map);
        view.addObject("markItems", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    /**
     * 创建项目评分
     */
    @RequestMapping(value = "/markitem")
    public ModelAndView markItem(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (StringUtils.isEmpty(project))
            return view;
        map = AppUtils.getMap("project", project, "user", user);
        MarkItem markItem = markItemBiz.findInfoMarkItem(map);
        if (StringUtils.isEmpty(markItem))
            markItem = markItemBiz.appendMarkItem(project, user);
        view.setViewName("redirect:editinfo?id=" + markItem.getId());
        return view;
    }

    /**
     * 删除项目评分
     */
    @RequestMapping(value = "/delete")
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        MarkItem markItem = markItemBiz.findInfoMarkItem(id, user);
        if (!StringUtils.isEmpty(markItem))
            markItemBiz.deleteMarkItem(markItem);
        return true;
    }

    /**
     * 移除项目评分
     */
    @RequestMapping(value = "/remove")
    public boolean remove(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        MarkItem markItem = markItemBiz.findInfoMarkItem(map);
        if (!StringUtils.isEmpty(markItem))
            markItemBiz.deleteMarkItem(markItem);
        return true;
    }

    /**
     * 编辑项目评分
     */
    @RequestMapping(value = "/editinfo")
    public ModelAndView editinfo(int id, @RequestParam(defaultValue = "0") int no) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        MarkItem markItem = markItemBiz.findInfoMarkItem(id, user);
        if (StringUtils.isEmpty(markItem))
            return view;
        List<MarkPipe> markPipes = markPipeBiz.findListMarkPipe(markItem);
        int index = Math.min(no, markPipes.size() - 1);
        MarkPipe markPipe = markPipes.get(index);
        if (!StringUtils.isEmpty(markPipe)) {
            Pipe pipe = markPipe.getPipe();
            List<Item> items = itemBiz.findListItem(pipe);
            pipe.setItems(items);
        }
        view.setViewName("markinfo/editinfo");
        view.addObject("markItem", markItem);
        view.addObject("markPipes", markPipes);
        view.addObject("markPipe", markPipe);
        return view;
    }

    /**
     * 查看项目评分
     */
    @RequestMapping(value = "/findinfo")
    public ModelAndView findinfo(int id, @RequestParam(defaultValue = "0") int no) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        MarkItem markItem = markItemBiz.findInfoMarkItem(map);
        if (StringUtils.isEmpty(markItem))
            return view;
        List<MarkPipe> markPipes = markPipeBiz.findListMarkPipe(markItem);
        if (markPipes != null && markPipes.size() > no) {
            MarkPipe markPipe = markPipes.get(no);
            List<Item> items = itemBiz.findListItem(markPipe.getPipe());
            markPipe.getPipe().setItems(items);
            view.addObject("markPipe", markPipe);
        }
        view.setViewName("markinfo/findinfo");
        view.addObject("markItem", markItem);
        view.addObject("markPipes", markPipes);
        return view;
    }

    /**
     * 修改项目评分
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public boolean commit(MarkPipe markPipe) {
        User user = (User) AppUtils.getValue("user");
        if (markPipeBiz.findInfoMarkPipe(markPipe.getId(), user) != null)
            markPipeBiz.updateMarkPipe(markPipe);
        return true;
    }
}
