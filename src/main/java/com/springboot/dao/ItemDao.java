package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Item;

public interface ItemDao {

	void insertItem(Item item);

	void updateItem(Item item);

	void deleteItem(Item item);

	Item findInfoItem(Map<String, Object> map);

	List<Item> findListItem(Map<String, Object> map);
	
}
