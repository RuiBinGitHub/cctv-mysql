package com.springboot.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.CompanyBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.biz.UserBiz;
import com.springboot.entity.Company;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import com.springboot.vo.CityVo;
import com.springboot.vo.UserVo;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Resource
    private CompanyBiz companyBiz;
    @Resource
    private UserBiz userBiz;
    private Map<String, Object> map = null;

    @RequestMapping(value = "/showlist", method = RequestMethod.GET)
    public ModelAndView showList(String name, @RequestParam(defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView("company/showlist");
        map = AppUtils.getMap("name", name, "page", page);
        PageInfo<Company> info = companyBiz.findListCompany(map);
        view.addObject("companies", info.getList());
        view.addObject("count", info.getTotal());
        view.addObject("page", page);
        return view;
    }

    @RequestMapping(value = "/findinfo", method = RequestMethod.GET)
    public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        Company company = companyBiz.findInfoCompany(id);
        if (StringUtils.isEmpty(company))
            return view;
        List<User> users = userBiz.findListUser(company);
        view.setViewName("company/findinfo");
        view.addObject("company", company);
        view.addObject("users", users);
        return view;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Company company) {
        ModelAndView view = new ModelAndView("company/insert");
        map = AppUtils.getMap("code", company.getCode());
        if (companyBiz.findInfoCompany(map) != null) {
            view.addObject("tips", "账号前缀已存在！");
            view.addObject("company", company);
        } else {
            int id = companyBiz.appendCompany(company);
            view.setViewName("redirect:findinfo?id=" + id);
        }
        return view;
    }

    @RequestMapping(value = "/updateview", method = RequestMethod.GET)
    public ModelAndView updateView(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        Company company = companyBiz.findInfoCompany(id);
        if (StringUtils.isEmpty(company))
            return view;
        view.setViewName("company/update");
        view.addObject("company", company);
        return view;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Company company) {
        ModelAndView view = new ModelAndView("redirect:failure");
        if (companyBiz.findInfoCompany(company.getId()) != null) {
            int id = companyBiz.repeatCompany(company);
            view.setViewName("redirect:findinfo?id=" + id);
        }
        return view;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        Company company = companyBiz.findInfoCompany(id);
        if (!StringUtils.isEmpty(company))
            companyBiz.deleteCompany(company);
        return true;
    }

    @SneakyThrows
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void findUserList(int id, HttpServletResponse response) {
        ExcelWriter workbook = ExcelUtil.getWriter(true);
        workbook.addHeaderAlias("id", "编号");
        workbook.addHeaderAlias("name", "姓名");
        workbook.addHeaderAlias("username", "登录账号");
        workbook.addHeaderAlias("password", "登录密码");
        workbook.addHeaderAlias("mail", "邮箱");
        workbook.addHeaderAlias("mobi", "手机");
        workbook.addHeaderAlias("state", "状态");
        workbook.addHeaderAlias("role", "职务");

        Company company = companyBiz.findInfoCompany(id);
        List<User> users = userBiz.findListUser(company);
        List<Map<String, String>> list = new ArrayList<>(users.size());
        for (User user : users) {
            Map<String, String> map = new HashMap<>();
            map.put("id", users.indexOf(user) + 1 + "");
            map.put("name", user.getName());
            map.put("username", user.getUsername());
            map.put("password", user.getPassword());
            map.put("mail", user.getMail());
            map.put("mobi", user.getMobi());
            map.put("state", user.getState());
            if ("role2".equals(user.getRole()))
                map.put("role", "管理人员");
            if ("role3".equals(user.getRole()))
                map.put("role", "评分人员");
            if ("role4".equals(user.getRole()))
                map.put("role", "操作人员");
            list.add(map);
        }

        workbook.write(list);
        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment;filename=user.xlsx");
        workbook.flush(response.getOutputStream());
        response.flushBuffer();
        workbook.close();
    }

}
