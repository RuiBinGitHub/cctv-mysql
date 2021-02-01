package com.springboot.biz;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.entity.MarkItem;
import com.springboot.entity.Message;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface MessageBiz {

	void updateMessage(Message message);

	void deleteMessage(Message message);

	Message findInfoMessage(int id, User user);

	PageInfo<Message> findListMessage(Map<String, Object> map);

	void sendMessage(User user, Project project, MarkItem markItem);

	int getCount(String state, User user);
}
