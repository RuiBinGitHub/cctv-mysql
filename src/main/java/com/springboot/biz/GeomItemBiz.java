package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.GeomItem;
import com.springboot.entity.Project;

public interface GeomItemBiz {

    GeomItem findInfoGeomItem(Project project);

    GeomItem findInfoGeomItem(Map<String, Object> map);

    List<GeomItem> findListGeomItem(Map<String, Object> map);

    void appendGeomItem(Project project);

}
