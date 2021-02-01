package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.*;
import org.springframework.web.multipart.MultipartFile;

public interface GeomPipeBiz {


    void updateGeomPipe(GeomPipe geomPipe);

    GeomPipe findInfoGeomPipe(int id, User user);

    GeomPipe findInfoGeomPipe(Map<String, Object> map);

    List<GeomPipe> findListGeomPipe(Project project, User user);

    List<GeomPipe> findListGeomPipe(Map<String, Object> map);

    Map<String, Double> findSMHGradeA(String smhGradeA, Company company);

    Map<String, Double> findFMHGradeA(String fmhGradeA, Company company);

    void importValue(GeomItem geomItem, MultipartFile file);

    void replaceGeomPipe(GeomPipe geomPipe);

}
