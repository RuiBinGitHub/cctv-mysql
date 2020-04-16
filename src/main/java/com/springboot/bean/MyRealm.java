package com.springboot.bean;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
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
import com.springboot.util.AppHelper;

@Component(value = "myRealm")
public class MyRealm extends AuthorizingRealm {

	@Resource
	private UserBiz userBiz;
	private Map<String, Object> map = null;

	private SimpleAuthorizationInfo info1 = null;
	private SimpleAuthenticationInfo info2 = null;
	private AuthenticationException exception = null;

	/** 执行授权逻辑 */
	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
		info1 = new SimpleAuthorizationInfo();
		User user = (User) collection.getPrimaryPrincipal();
		Company company = user.getCompany();
		if ("role1".equals(user.getRole())) {
			info1.addRole("role1");
		} else if ("role2".equals(user.getRole())) {
			info1.addRole("role2");
			info1.addRole("role4");
		} else if ("role3".equals(user.getRole())) {
			info1.addRole("role3");
			info1.addRole("role4");
		} else if ("role4".equals(user.getRole()))
			info1.addRole("role4");
		if ("高级".equals(company.getLevel()))
			info1.addRole("vrole");
		return info1;
	}

	/** 执行认证逻辑 */
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		UsernamePasswordToken tempToken = (UsernamePasswordToken) token;
		String username = tempToken.getUsername();
		String password = new String((char[]) tempToken.getCredentials());
		map = AppHelper.getMap("username", username, "password", password);
		User user = userBiz.findInfoUser(map);
		if (user == null) { // 账号密码错误
			exception = new IncorrectCredentialsException();
			throw exception;
		}
		AppHelper.pushMap("user", user);
		info2 = new SimpleAuthenticationInfo(user, password, "");
		return info2;
	}
}
