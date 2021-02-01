package com.springboot.biz.impl;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.NoteBiz;
import com.springboot.dao.ItemDao;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ItemBizImpl implements ItemBiz {

    @Resource
    private ItemDao itemDao;
    @Resource
    private NoteBiz noteBiz;
    private Map<String, Object> map = null;

    public void insertItem(Item item) {
        itemDao.insertItem(item);
    }

    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }

    public void deleteItem(Item item) {
        itemDao.deleteItem(item);
    }

    public Item findInfoItem(int id, User user) {
        map = AppUtils.getMap("id", id, "user", user);
        return itemDao.findInfoItem(map);
    }

    public Item findInfoItem(Map<String, Object> map) {
        return itemDao.findInfoItem(map);
    }

    public List<Item> findListItem(Pipe pipe) {
        map = AppUtils.getMap("pipe", pipe);
        return itemDao.findListItem(map);
    }

    public List<Item> findListItem(Map<String, Object> map) {
        if (!StringUtils.isEmpty(map.get("page")))
            map.put("size", ((int) map.get("page") - 1) * 30);
        return itemDao.findListItem(map);
    }

    public void removeItem(Item item, User user) {
        itemDao.deleteItem(item);
        String context = "*.删除管道记录>> Distance：" + item.getDist() + "  \tCode：" + item.getCode();
        noteBiz.appendNote(context, user, item.getPipe());
    }

}
