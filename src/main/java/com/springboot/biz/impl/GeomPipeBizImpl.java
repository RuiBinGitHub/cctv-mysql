package com.springboot.biz.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.hutool.poi.excel.ExcelReader;
import com.springboot.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.bean.AppBean;
import com.springboot.biz.GeomPipeBiz;
import com.springboot.dao.GeomPipeDao;
import com.springboot.util.AppUtils;

@Service
@Transactional
public class GeomPipeBizImpl implements GeomPipeBiz {

    @Resource
    private GeomPipeDao geomPipeDao;
    private Map<String, Object> map = null;

    public void updateGeomPipe(GeomPipe geomPipe) {
        geomPipeDao.updateGeomPipe(geomPipe);
    }

    public GeomPipe findInfoGeomPipe(int id, User user) {
        map = AppUtils.getMap("id", id, "user", user);
        return geomPipeDao.findInfoGeomPipe(map);
    }

    public GeomPipe findInfoGeomPipe(Map<String, Object> map) {
        return geomPipeDao.findInfoGeomPipe(map);
    }

    public List<GeomPipe> findListGeomPipe(Project project, User user) {
        map = AppUtils.getMap("project", project, "user", user);
        return geomPipeDao.findListGeomPipe(map);
    }

    public List<GeomPipe> findListGeomPipe(Map<String, Object> map) {
        return geomPipeDao.findListGeomPipe(map);
    }

    public Map<String, Double> findSMHGradeA(String smhGradeA, Company company) {
        map = AppUtils.getMap("smhGradeA", smhGradeA, "company", company);
        return geomPipeDao.findSMHGradeA(map);
    }

    public Map<String, Double> findFMHGradeA(String fmhGradeA, Company company) {
        map = AppUtils.getMap("fmhGradeA", fmhGradeA, "company", company);
        return geomPipeDao.findFMHGradeA(map);
    }

    public void importValue(GeomItem geomItem, MultipartFile file) {
        ExcelReader reader = AppUtils.getReader(file);
        if (StringUtils.isEmpty(reader))
            return; // 上传文件错误
        List<List<Object>> lists = reader.read(1, reader.getRowCount());
        Map<String, AppBean> map = new HashMap<>(32);
        for (int i = 0; lists != null && i < lists.size(); i++) {
            List<Object> list = lists.get(i);
            AppBean appBean = new AppBean();
            String manhole = list.get(0) == null ? "" : list.get(0).toString();
            appBean.setX(list.get(4) == null ? 0.0 : (double) list.get(4));
            appBean.setY(list.get(5) == null ? 0.0 : (double) list.get(5));
            appBean.setH(list.get(3) == null ? 0.0 : (double) list.get(3));
            map.put(manhole, appBean);
        }

        for (GeomPipe geomPipe : findListGeomPipe(geomItem.getProject(), null)) {
            // 起始井号
            String smhNo = geomPipe.getPipe().getSmanholeno();
            if (map.containsKey(smhNo)) {
                AppBean appBean = map.get(smhNo);
                geomPipe.setSmhx(appBean.getX());
                geomPipe.setSmhy(appBean.getY());
                geomPipe.setSmhh(appBean.getH());
                replaceGeomPipe(geomPipe);
            }
            // 终止井号
            String fmhNo = geomPipe.getPipe().getFmanholeno();
            if (map.containsKey(fmhNo)) {
                AppBean appBean = map.get(fmhNo);
                geomPipe.setFmhx(appBean.getX());
                geomPipe.setFmhy(appBean.getY());
                geomPipe.setFmhh(appBean.getH());
                replaceGeomPipe(geomPipe);
            }
        }
    }

    public void replaceGeomPipe(GeomPipe geomPipe) {
        String uses = geomPipe.getPipe().getUses();
        geomPipe.setSmhGradeA(findGrideA(geomPipe.getSmhx(), geomPipe.getSmhy(), uses));
        geomPipe.setSmhGradeB(findGrideB(geomPipe.getSmhx(), geomPipe.getSmhy(), uses));
        geomPipe.setFmhGradeA(findGrideA(geomPipe.getFmhx(), geomPipe.getFmhy(), uses));
        geomPipe.setFmhGradeB(findGrideB(geomPipe.getFmhx(), geomPipe.getFmhy(), uses));
        geomPipeDao.updateGeomPipe(geomPipe);
    }

    /**
     * 计算GradeA
     */
    private String findGrideA(double x, double y, String uses) {
        if (x == 0.0 || y == 0.0)
            return null;
        DecimalFormat foramt1 = new DecimalFormat("#00");
        DecimalFormat foramt2 = new DecimalFormat("#000");
        String x1 = foramt1.format((int) x / 1000 % 100);
        String y1 = foramt1.format((int) y / 1000 % 100);
        String x2 = foramt2.format((int) x % 1000);
        String y2 = foramt2.format((int) y % 1000);
        return x1 + y1 + x2 + y2 + uses;
    }

    /**
     * 计算GradeB
     */
    private String findGrideB(double x, double y, String uses) {
        if (x == 0.0 || y == 0.0)
            return null;
        DecimalFormat foramt1 = new DecimalFormat("#00");
        DecimalFormat foramt2 = new DecimalFormat("#000");
        String x1 = foramt1.format((int) x / 1000 % 100);
        String y1 = foramt1.format((int) y / 1000 % 100);
        String x2 = foramt2.format((int) x % 1000);
        String y2 = foramt2.format((int) y % 1000);
        String x3 = foramt1.format(x * 100 % 100);
        String y3 = foramt1.format(y * 100 % 100);
        return x1 + y1 + x2 + y2 + x3 + y3 + uses;
    }

}
