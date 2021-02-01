package com.springboot.biz.impl;

import com.springboot.biz.MarkPipeBiz;
import com.springboot.dao.MarkPipeDao;
import com.springboot.entity.MarkItem;
import com.springboot.entity.MarkPipe;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MarkPipeBizImpl implements MarkPipeBiz {

    @Resource
    private MarkPipeDao markPipeDao;

    private Map<String, Object> map = null;

    public void insertMarkPipe(MarkPipe markPipe) {
        markPipeDao.insertMarkPipe(markPipe);
    }

    public void updateMarkPipe(MarkPipe markPipe) {
        markPipeDao.updateMarkPipe(markPipe);
    }

    public MarkPipe findInfoMarkPipe(int id, User user) {
        map = AppUtils.getMap("id", id, "user", user);
        return markPipeDao.findInfoMarkPipe(map);
    }

    public MarkPipe findInfoMarkPipe(Map<String, Object> map) {
        return markPipeDao.findInfoMarkPipe(map);
    }

    public List<MarkPipe> findListMarkPipe(MarkItem markItem) {
        map = AppUtils.getMap("markItem", markItem);
        return markPipeDao.findListMarkPipe(map);
    }
}
