package com.springboot.biz.impl;

import com.springboot.biz.GeomItemBiz;
import com.springboot.dao.GeomItemDao;
import com.springboot.entity.GeomItem;
import com.springboot.entity.Project;
import com.springboot.util.AppUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GeomItemBizImpl implements GeomItemBiz {

    @Resource
    private GeomItemDao geomItemDao;

    public GeomItem findInfoGeomItem(Project project) {
        Map<String, Object> map = AppUtils.getMap("project", project);
        return geomItemDao.findInfoGeomItem(map);
    }

    public GeomItem findInfoGeomItem(Map<String, Object> map) {
        return geomItemDao.findInfoGeomItem(map);
    }

    public List<GeomItem> findListGeomItem(Map<String, Object> map) {
        return geomItemDao.findListGeomItem(map);
    }

    public void appendGeomItem(Project project) {
        GeomItem geomItem = new GeomItem();
        geomItem.setProject(project);
        geomItemDao.insertGeomItem(geomItem);
    }

}
