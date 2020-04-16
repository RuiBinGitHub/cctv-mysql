package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.GeomItem;

public interface GeomItemDao {

	public void insertGeomItem(GeomItem geomItem);

	public void updateGeomItem(GeomItem geomItem);

	public void deleteGeomItem(GeomItem geomItem);

	public GeomItem findInfoGeomItem(Map<String, Object> map);

	public List<GeomItem> showListGeomItem(Map<String, Object> map);

}
