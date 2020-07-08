package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Message;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface MessageBiz {

	void insertMessage(Message message);

	void updateMessage(Message message);

	void deleteMessage(Message message);

	Message findInfoMessage(int id, User user);

	Message findInfoMessage(Map<String, Object> map);

	PageInfo<Message> findListMessage(Map<String, Object> map);

	void sendMessage(User user, Project project, MarkItem markItem);

	int getPage(Map<String, Object> map, int size);
}
