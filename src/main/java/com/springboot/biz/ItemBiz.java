package com.springboot.biz;

import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.User;

import java.util.List;
import java.util.Map;

public interface ItemBiz {

    void insertItem(Item item);

    void updateItem(Item item);

    void deleteItem(Item item);

    Item findInfoItem(int id, User user);

    Item findInfoItem(Map<String, Object> map);

    List<Item> findListItem(Pipe pipe);

    List<Item> findListItem(Map<String, Object> map);

    void removeItem(Item item, User user);

}
