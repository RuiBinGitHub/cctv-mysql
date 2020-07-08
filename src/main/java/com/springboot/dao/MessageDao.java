package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Message;

public interface MessageDao {

	void insertMessage(Message message);

	void updateMessage(Message message);

	void deleteMessage(Message message);

	Message findInfoMessage(Map<String, Object> map);

	List<Message> findListMessage(Map<String, Object> map);

	int getCount(Map<String, Object> map);	
	
}
