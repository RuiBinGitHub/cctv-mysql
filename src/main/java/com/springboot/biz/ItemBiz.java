package com.springboot.biz;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface ItemBiz {

	public void insertItem(Item item);

	public void updateItem(Item item);

	public void deleteItem(Item item);

	public Item findInfoItem(int id, User user);

	public Item findInfoItem(Map<String, Object> map);

	public List<Item> findListItem(Map<String, Object> map);

	public List<Item> findListItem(Project project);

	public List<Item> findListItem(Pipe pipe);

	public boolean importImage(Project project, MultipartFile[] files);

	public boolean removeImage(Project project);

	public void sortItemImg(Project project);
}
