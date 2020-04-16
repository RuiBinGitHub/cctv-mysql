package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Message;

public interface MessageDao {

	public void insertMessage(Message message);

	public void updateMessage(Message message);

	public void deleteMessage(Message message);

	public Message findInfoMessage(Map<String, Object> map);

	public List<Message> findListMessage(Map<String, Object> map);

	public int getCount(Map<String, Object> map);	
	
}
