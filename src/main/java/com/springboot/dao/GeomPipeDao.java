package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.GeomPipe;

public interface GeomPipeDao {

	void insertGeomPipe(GeomPipe geomPipe);

	void updateGeomPipe(GeomPipe geomPipe);

	void deleteGeomPipe(GeomPipe geomPipe);

	GeomPipe findInfoGeomPipe(Map<String, Object> map);

	List<GeomPipe> findListGeomPipe(Map<String, Object> map);

	Map<String, Double> findSMHGradeA(Map<String, Object> map);

	Map<String, Double> findFMHGradeA(Map<String, Object> map);
}
