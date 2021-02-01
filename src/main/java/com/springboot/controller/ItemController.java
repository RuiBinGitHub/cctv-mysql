package com.springboot.controller;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private ItemBiz itemBiz;
    private Map<String, Object> map = null;

    /**
     * 删除数据
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        Item item = itemBiz.findInfoItem(id, user);
        if (!StringUtils.isEmpty(item))
            itemBiz.deleteItem(item);
        if (!StringUtils.isEmpty(item.getPicture()))
            projectBiz.sortImage(item.getPipe().getProject());
        return true;
    }

    /**
     * 移除数据
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public boolean remove(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Item item = itemBiz.findInfoItem(map);
        if (!StringUtils.isEmpty(item))
            itemBiz.removeItem(item, user);
        if (!StringUtils.isEmpty(item.getPicture()))
            projectBiz.sortImage(item.getPipe().getProject());
        return true;
    }

}
