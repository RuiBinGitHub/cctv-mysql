package com.springboot;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.bean.MailBean;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MainApplicationTests {


	@Resource
	private MailBean mailBean;

	public Map<String, Object> map = null;

	@Test
	public void contextLoads() {
		mailBean.sendMail("919732942@qq.com", "88888");
		System.out.println("--");
	}

}
