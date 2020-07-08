package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.GeomItem;

public interface GeomItemBiz {

	void insertGeomItem(GeomItem geomItem);

	void updateGeomItem(GeomItem geomItem);

	void deleteGeomItem(GeomItem geomItem);

	GeomItem findInfoGeomItem(Map<String, Object> map);
	
	List<GeomItem> showListGeomItem(Map<String, Object> map);
	
	void appendGeomItem(GeomItem geomItem);

}
