package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.GeomPipe;

public interface GeomPipeDao {

	public void insertGeomPipe(GeomPipe geomPipe);

	public void updateGeomPipe(GeomPipe geomPipe);

	public void deleteGeomPipe(GeomPipe geomPipe);

	public GeomPipe findInfoGeomPipe(Map<String, Object> map);

	public List<GeomPipe> findListGeomPipe(Map<String, Object> map);

	public Map<String, Double> findSMHGradeA(Map<String, Object> map);

	public Map<String, Double> findFMHGradeA(Map<String, Object> map);
}
