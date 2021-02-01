package com.springboot.controller;

import com.springboot.bean.AppContext;
import com.springboot.bean.MailBean;
import com.springboot.biz.UserBiz;
import com.springboot.entity.Company;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/userview")
public class UserViewController {

    @Resource
    private UserBiz userBiz;
    @Resource
    private MailBean mailBean;
    @Resource
    private AppContext appContext;
    private Map<String, Object> map = null;

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(String username, String password, ServletRequest requst) {
        ModelAndView view = new ModelAndView("userview/loginview");
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
        } catch (IncorrectCredentialsException e) {
            view.addObject("tips", "*登录账号或密码不正确！");
            view.addObject("username", username);
            view.addObject("password", password);
            return view;
        }
        User user = (User) AppUtils.getValue("user");
        // 计算使用账号期限
        Company company = user.getCompany();
        String date = LocalDate.now().toString();
        if (date.compareTo(company.getTerm()) > 0) {
            view.addObject("tips", "*公司账号使用期限到期！");
            view.addObject("username", username);
            view.addObject("password", password);
            return view;
        }
        // 查看账号当前状态
        if ("冻结".equals(user.getState())) {
            view.addObject("tips", "*账号已冻结，登录失败！");
            view.addObject("username", username);
            view.addObject("password", password);
            return view;
        }

        // 设置session过期时间为无限
        SecurityUtils.getSubject().getSession().setTimeout(-2000);
        Session session = SecurityUtils.getSubject().getSession();
        appContext.putMap(username, session);
        // 设置登录成功后跳转路径
        SavedRequest location = WebUtils.getSavedRequest(requst);
        if (!StringUtils.isEmpty(location)) {
            String path = location.getRequestUrl();
            view.setViewName("redirect:" + path.substring(5));
        } else if ("role1".equals(user.getRole()))
            view.setViewName("redirect:/company/showlist");
        else
            view.setViewName("redirect:/project/showlist");
        return view;
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/leave")
    public ModelAndView leave() {
        ModelAndView view = new ModelAndView("redirect:loginview");
        SecurityUtils.getSubject().logout();
        AppUtils.removeValue("user");
        return view;
    }

    /**
     * 切换语言
     */
    @RequestMapping(value = "/change")
    public boolean change(@RequestParam(defaultValue = "zh_CN") String lang) {
        AppUtils.putValue("i18n", lang);
        return true;
    }

    /**
     * 判断账号和邮箱
     */
    @RequestMapping(value = "/check")
    public boolean checkNameMail(String username, String mail) {
        map = AppUtils.getMap("username", username, "mail", mail);
        return userBiz.findInfoUser(map) != null;
    }

    /**
     * 重置密码
     */
    @RequestMapping(value = "/resetpass", method = RequestMethod.POST)
    public ModelAndView resetpass(String name, String password, String mail) {
        ModelAndView view = new ModelAndView("userview/resetview");
        map = AppUtils.getMap("username", name, "mail", mail);
        User user = userBiz.findInfoUser(map);
        if (StringUtils.isEmpty(user)) {
            view.addObject("tips", "*登录账号与邮箱不匹配！");
            view.addObject("name", name);
            view.addObject("mail", mail);
        } else {
            view.setViewName("redirect:message");
            view.addObject("text", "重置密码成功，请使用新密码登录系统！");
            user.setPassword(password);
            userBiz.updateUser(user);
        }
        return view;
    }

    /**
     * 判断邮箱是否存在
     */
    @RequestMapping(value = "/isexistmail")
    public boolean isExistMail(@RequestParam("mail") String mail) {
        map = AppUtils.getMap("mail", mail);
        return userBiz.findInfoUser(map) != null;
    }

    /**
     * 发送电子邮件
     */
    @RequestMapping(value = "/sendmail")
    public String sendmail(String mail) {
        String regs = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
        Pattern pattern = Pattern.compile(regs);
        Matcher matcher = pattern.matcher(mail);
        if (!matcher.matches())
            return "";
        int random = (int) (Math.random() * 899999);
        String code = String.valueOf(100000 + random);
        mailBean.sendMail(mail, code);
        return code;
    }

}
