package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.GeomItem;

public interface GeomItemDao {

	void insertGeomItem(GeomItem geomItem);

	void updateGeomItem(GeomItem geomItem);

	void deleteGeomItem(GeomItem geomItem);

	GeomItem findInfoGeomItem(Map<String, Object> map);

	List<GeomItem> findListGeomItem(Map<String, Object> map);

}
