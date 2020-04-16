package com.springboot.biz;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Company;
import com.springboot.entity.GeomItem;
import com.springboot.entity.GeomPipe;
import com.springboot.entity.User;

public interface GeomPipeBiz {

	public void insertGeomPipe(GeomPipe geomPipe);

	public void updateGeomPipe(GeomPipe geomPipe);

	public void deleteGeomPipe(GeomPipe geomPipe);

	public GeomPipe findInfoGeomPipe(Map<String, Object> map);

	public List<GeomPipe> findListGeomPipe(Map<String, Object> map);

	public GeomPipe findInfoGeomPipe(int id, User user);

	public List<GeomPipe> findListGeomPipe(GeomItem geomItem);

	public Map<String, Double> findSMHGradeA(String smhGradeA, Company company);

	public Map<String, Double> findFMHGradeA(String fmhGradeA, Company company);

	public boolean importValue(GeomItem geomItem, MultipartFile file);
	
	public boolean replacePipegeom(GeomPipe geomPipe);

}
