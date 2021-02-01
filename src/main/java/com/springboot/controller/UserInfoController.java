package com.springboot.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.springboot.biz.MarkItemBiz;
import com.springboot.biz.MarkPipeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.biz.UserBiz;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;

@RestController
@RequestMapping(value = "/userinfo")
public class UserInfoController {

    @Resource
    private MarkItemBiz markItemBiz;
    @Resource
    private MarkPipeBiz markPipeBiz;
    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private PipeBiz pipeBiz;
    @Resource
    private UserBiz userBiz;

    private Map<String, Object> map = null;

    /**
     * 获取人员列表
     */
    @RequestMapping(value = "/showlist", method = RequestMethod.GET)
    public ModelAndView showlist(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("userinfo/showlist");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("page", page, "company", user.getCompany());
        if (!StringUtils.isEmpty(name))
            map.put("name", name);
        PageInfo<User> info = userBiz.findListUser(map);
        view.addObject("users", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    @RequestMapping(value = "/findinfo", method = RequestMethod.GET)
    public ModelAndView showInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        User temp = userBiz.findInfoUser(map);
        if (StringUtils.isEmpty(temp))
            return view;

        map = AppUtils.getMap("user", temp);
        List<Project> projects = projectBiz.findListProject(map).getList();
        List<Project> projects1 = projects.stream().filter(project -> project.getState().equals("未提交")).collect(Collectors.toList());
        List<Project> projects2 = projects.stream().filter(project -> project.getState().equals("已提交")).collect(Collectors.toList());

        // 计算管道数量
        int size1 = projects.size();
        int size2 = pipeBiz.getCount(map);

        int count = 0;
        map = AppUtils.getMap("temp", temp);
        List<MarkItem> markItems = markItemBiz.findListMarkItem(map).getList();
        List<MarkItem> markItems1 = markItems.stream().filter(markItem -> markItem.getScore1() <= 94 || markItem.getScore2() <= 84).collect(Collectors.toList());
        List<MarkItem> markItems2 = markItems.stream().filter(markItem -> markItem.getScore1() >= 95 && markItem.getScore2() >= 85).collect(Collectors.toList());

        view.setViewName("userinfo/findinfo");
        view.addObject("temp", temp);
        view.addObject("size1", size1);
        view.addObject("size2", size2);
        view.addObject("projects1", projects1);  // 未完成项目
        view.addObject("projects2", projects2);  // 已完成项目
        view.addObject("markItems1", markItems1);
        view.addObject("markItems2", markItems2);
        return view;
    }

    /**
     * 跳转编辑页面
     */
    @RequestMapping(value = "/updateview", method = RequestMethod.GET)
    public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        User temp = userBiz.findInfoUser(map);
        if (StringUtils.isEmpty(temp))
            return view;
        view.setViewName("userinfo/update");
        view.addObject("userinfo", temp);
        return view;
    }

    /**
     * 编辑人员
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(int id, String role) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        User temp = userBiz.findInfoUser(map);
        if (StringUtils.isEmpty(temp))
            return view;
        temp.setRole(role);
        userBiz.updateUser(temp);
        view.setViewName("redirect:success");
        return view;
    }

    /**
     * 修改用户昵称
     */
    @RequestMapping(value = "/updatename", method = RequestMethod.POST)
    public boolean updateName(String name) {
        User user = (User) AppUtils.getValue("user");
        if (!StringUtils.isEmpty(user)) {
            user.setName(name);
            userBiz.updateUser(user);
        }
        return true;
    }

    /**
     * 修改用户密码
     */
    @RequestMapping(value = "/updatepass", method = RequestMethod.POST)
    public boolean updatepass(String oldpass, String newpass) {
        User user = (User) AppUtils.getValue("user");
        if (!user.getPassword().equals(oldpass))
            return false;
        user.setPassword(newpass);
        userBiz.updateUser(user);
        return true;
    }

    /**
     * 修改登录邮箱
     */
    @RequestMapping(value = "/updatemail", method = RequestMethod.POST)
    public boolean updateMail(String mail, String code) {
        User user = (User) AppUtils.getValue("user");
        if (StringUtils.isEmpty(code))
            return false;
        user.setMail(mail);
        System.out.println(mail + ":" + code);
        userBiz.updateUser(user);
        return true;
    }

}
