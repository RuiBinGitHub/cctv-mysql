package com.springboot.biz;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Company;
import com.springboot.entity.GeomItem;
import com.springboot.entity.GeomPipe;
import com.springboot.entity.User;

public interface GeomPipeBiz {

	void insertGeomPipe(GeomPipe geomPipe);

	void updateGeomPipe(GeomPipe geomPipe);

	void deleteGeomPipe(GeomPipe geomPipe);

	GeomPipe findInfoGeomPipe(Map<String, Object> map);

	GeomPipe findInfoGeomPipe(int id, User user);

	List<GeomPipe> findListGeomPipe(Map<String, Object> map);

	List<GeomPipe> findListGeomPipe(GeomItem geomItem);

	Map<String, Double> findSMHGradeA(String smhGradeA, Company company);

	Map<String, Double> findFMHGradeA(String fmhGradeA, Company company);

	boolean importValue(GeomItem geomItem, MultipartFile file);

	boolean replacePipegeom(GeomPipe geomPipe);

}
