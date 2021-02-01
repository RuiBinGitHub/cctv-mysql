package com.springboot.bean;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.springboot.biz.UserBiz;
import com.springboot.entity.Company;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.springframework.util.StringUtils;

@Component(value = "myRealm")
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserBiz userBiz;
    private Map<String, Object> map = null;

    /**
     * 执行授权逻辑
     */
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) collection.getPrimaryPrincipal();
        if ("role1".equals(user.getRole())) {
            info.addRole("role1");
        } else if ("role2".equals(user.getRole())) {
            info.addRole("role2");
            info.addRole("role4");
        } else if ("role3".equals(user.getRole())) {
            info.addRole("role3");
            info.addRole("role4");
        } else if ("role4".equals(user.getRole()))
            info.addRole("role4");
        Company company = user.getCompany();
        if ("高级".equals(company.getLevel()))
            info.addRole("vrole");
        return info;
    }

    /**
     * 执行认证逻辑
     */
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken tempToken = (UsernamePasswordToken) token;
        String username = tempToken.getUsername();
        String password = new String((char[]) tempToken.getCredentials());
        map = AppUtils.getMap("username", username, "password", password);
        User user = userBiz.findInfoUser(map);
        if (StringUtils.isEmpty(user))  // 账号密码错误
            throw new IncorrectCredentialsException();
        AppUtils.putValue("user", user);
        return new SimpleAuthenticationInfo(user, password, "");
    }
}
