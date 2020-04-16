package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Message;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface MessageBiz {

	public void insertMessage(Message message);

	public void updateMessage(Message message);

	public void deleteMessage(Message message);

	public Message findInfoMessage(int id, User user);

	public Message findInfoMessage(Map<String, Object> map);

	public PageInfo<Message> findListMessage(Map<String, Object> map);

	public void sendMessage(User user, Project project, MarkItem markItem);

	public int getPage(Map<String, Object> map, int size);
}
