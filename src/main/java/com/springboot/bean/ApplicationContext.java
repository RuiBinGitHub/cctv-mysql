package com.springboot.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class ApplicationContext {

	private Session temp = null;
	private Map<String, Session> map = new HashMap<>();

	public void pushMap(String name, Session session) {
		try {
			if ((temp = map.get(name)) != null)
				temp.stop();
		} catch (Exception e) {
			// 结束异常
		}
		map.put(name, session);
	}

}
