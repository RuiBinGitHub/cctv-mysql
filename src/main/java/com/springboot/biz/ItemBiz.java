package com.springboot.biz;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ItemBiz {

	void insertItem(Item item);

	void updateItem(Item item);

	void deleteItem(Item item);

	Item findInfoItem(int id, User user);

	Item findInfoItem(Map<String, Object> map);

	List<Item> findListItem(Map<String, Object> map);

	List<Item> findListItem(Project project);

	List<Item> findListItem(Pipe pipe);

	boolean importImage(Project project, MultipartFile[] files);

	boolean removeImage(Project project);

	void sortItemImg(Project project);
}
