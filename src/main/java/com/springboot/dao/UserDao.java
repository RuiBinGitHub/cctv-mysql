package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.User;

public interface UserDao {

	void insertUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

	User findInfoUser(Map<String, Object> map);

	List<User> findListUser(Map<String, Object> map);

}
