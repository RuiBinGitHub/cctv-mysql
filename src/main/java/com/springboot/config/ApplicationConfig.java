package com.springboot.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot.bean.MyRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Resource
	private MyRealm myRealm;

	private SecurityManager manager = null;
	private ShiroFilterFactoryBean factoryBean = null;

	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		manager = new DefaultWebSecurityManager(myRealm);
		factoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		factoryBean.setSecurityManager(manager);
		Map<String, String> filterMap = new HashMap<>();
		filterMap.put("/userview/**", "anon");
		filterMap.put("/company/**", "roles[role1]"); // 公司操作
		filterMap.put("/operator/**", "roles[role2]"); // 人员操作
		
		filterMap.put("/project/showlist", "roles[role4]"); // 个人列表
		filterMap.put("/project/findlist", "roles[role2]"); // 项目管理
		filterMap.put("/project/insertview", "roles[role4]"); // 新建项目链接
		filterMap.put("/project/updateview", "roles[role4]"); // 更新项目链接
		filterMap.put("/project/insert", "roles[role4]"); // 新建项目
		filterMap.put("/project/update", "roles[role4]"); // 更新项目
		filterMap.put("/project/delete", "roles[role4]"); // 删除项目
		filterMap.put("/project/remove", "roles[role2]"); // 移除项目
		filterMap.put("/project/submit", "roles[role4]"); // 提交项目
		filterMap.put("/project/revoke", "roles[role2]"); // 撤回项目
		filterMap.put("/project/editinfo", "roles[role4]"); // 编辑项目
		filterMap.put("/project/findinfo", "roles[role4]"); // 浏览项目
		filterMap.put("/project/importitems", "roles[role4]"); // 导入项目
		filterMap.put("/project/importdepth", "roles[role4]"); // 坐标深度
		filterMap.put("/project/combineview", "roles[role2]"); // 合并项目
		filterMap.put("/project/combinelist", "roles[role2]"); // 合并列表
		filterMap.put("/project/combine", "roles[role2]"); // 项目合并
		filterMap.put("/pipe/**", "roles[role4]"); // 管道操作
		filterMap.put("/item/**", "roles[role4]"); // 记录操作
		
		filterMap.put("/markinfo/markview", "roles[vrole,role3]"); // 评分项目
		filterMap.put("/markinfo/marklist", "roles[vrole,role3]"); // 评分列表
		filterMap.put("/markinfo/showlist", "roles[vrole,role3]"); // 个人列表
		filterMap.put("/markinfo/findlist", "roles[vrole,role2]"); // 评分管理
		filterMap.put("/markinfo/editinfo", "roles[vrole,role3]"); // 评分编辑
		filterMap.put("/markinfo/findinfo", "roles[vrole,role4]"); // 评分浏览
		filterMap.put("/markinfo/insert", "roles[vrole,role3]"); // 新建评分
		filterMap.put("/markinfo/update", "roles[vrole,role3]"); // 更新评分
		filterMap.put("/markinfo/delete", "roles[vrole,role3]"); // 删除评分
		filterMap.put("/markinfo/remove", "roles[vrole,role2]"); // 移除评分
		
		filterMap.put("/geominfo/findinfo", "roles[vrole,role2]"); // 坐标输入
		filterMap.put("/geominfo/showlist", "roles[vrole,role4]"); // 地图展示
		
		filterMap.put("/userinfo/showlist", "roles[role2]"); // 人员管理
		filterMap.put("/userinfo/showinfo", "roles[role2]"); // 信息统计
		filterMap.put("/userinfo/updatepass", "roles[role4]"); // 更新密码
		filterMap.put("/userinfo/updatemail", "roles[role4]"); // 更新邮箱
		filterMap.put("/userinfo/updateview", "roles[role2]"); // 更新人员
		filterMap.put("/userinfo/update", "roles[role2]"); // 更新人员
		filterMap.put("/userinfo/center", "roles[role4]"); // 个人中心
		
		factoryBean.setFilterChainDefinitionMap(filterMap);
		// 配置跳转的登录页面
		factoryBean.setLoginUrl("/userview/loginview");
		// 设置未授权提示页面
		factoryBean.setUnauthorizedUrl("/failure");
		return factoryBean;
	}

	/** 定义识图控制器 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/success").setViewName("userview/success");
		registry.addViewController("/failure").setViewName("userview/failure");
		registry.addViewController("**/message").setViewName("userview/message");
		registry.addViewController("**/loginview").setViewName("userview/loginview");
		registry.addViewController("**/resetview").setViewName("userview/resetview");
		registry.addViewController("**/completes").setViewName("userview/completes");

		registry.addViewController("/company/insertview").setViewName("company/insert");
		registry.addViewController("/project/combineview").setViewName("project/combine");
		registry.addViewController("/operator/insertview").setViewName("operator/insert");
		registry.addViewController("/userinfo/center").setViewName("userinfo/center");
	}

	@Bean
	public ShiroDialect getShiroDialect() {
		ShiroDialect dialect = new ShiroDialect();
		return dialect;
	}

}
