package com.springboot.biz.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.bean.Coordinate;
import com.springboot.biz.GeomPipeBiz;
import com.springboot.dao.GeomPipeDao;
import com.springboot.entity.Company;
import com.springboot.entity.GeomItem;
import com.springboot.entity.GeomPipe;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
@Transactional
public class GeomPipeBizImpl implements GeomPipeBiz {

	@Resource
	private GeomPipeDao geomPipeDao;

	private Map<String, Object> map = null;

	public void insertGeomPipe(GeomPipe geomPipe) {
		geomPipeDao.insertGeomPipe(geomPipe);
	}

	public void updateGeomPipe(GeomPipe geomPipe) {
		geomPipeDao.updateGeomPipe(geomPipe);
	}

	public void deleteGeomPipe(GeomPipe geomPipe) {
		geomPipeDao.deleteGeomPipe(geomPipe);
	}

	public GeomPipe findInfoGeomPipe(Map<String, Object> map) {
		return geomPipeDao.findInfoGeomPipe(map);
	}

	public GeomPipe findInfoGeomPipe(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return geomPipeDao.findInfoGeomPipe(map);
	}

	public List<GeomPipe> findListGeomPipe(Map<String, Object> map) {
		return geomPipeDao.findListGeomPipe(map);
	}

	public List<GeomPipe> findListGeomPipe(GeomItem geomItem) {
		map = AppHelper.getMap("geomItem", geomItem);
		return this.findListGeomPipe(map);
	}

	public Map<String, Double> findSMHGradeA(String smhGradeA, Company company) {
		map = AppHelper.getMap("smhGradeA", smhGradeA, "company", company);
		return geomPipeDao.findSMHGradeA(map);
	}

	public Map<String, Double> findFMHGradeA(String fmhGradeA, Company company) {
		map = AppHelper.getMap("fmhGradeA", fmhGradeA, "company", company);
		return geomPipeDao.findFMHGradeA(map);
	}

	public boolean importValue(GeomItem geomItem, MultipartFile file) {
		XSSFWorkbook workbook = AppHelper.getWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Map<String, Coordinate> map = new HashMap<>();
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			XSSFRow row = sheet.getRow(i);
			Coordinate coordinate = new Coordinate();
			String smh = AppHelper.getString(row.getCell(0));
			coordinate.setX(AppHelper.getNumber(row.getCell(4)));
			coordinate.setY(AppHelper.getNumber(row.getCell(5)));
			coordinate.setH(AppHelper.getNumber(row.getCell(3)));
			map.put(smh, coordinate);
		}
		List<GeomPipe> geomPipes = this.findListGeomPipe(geomItem);
		for (int i = 0; geomPipes != null && i < geomPipes.size(); i++) {
			GeomPipe geomPipe = geomPipes.get(i);
			String smhNo = geomPipe.getPipe().getSmanholeno();
			String fmhNo = geomPipe.getPipe().getFmanholeno();
			if (map.containsKey(smhNo)) {
				Coordinate coordinate = map.get(smhNo);
				geomPipe.setSmhx(coordinate.getX());
				geomPipe.setSmhy(coordinate.getY());
				geomPipe.setSmhh(coordinate.getH());
				replacePipegeom(geomPipe);
			}
			if (map.containsKey(fmhNo)) {
				Coordinate coordinate = map.get(fmhNo);
				geomPipe.setFmhx(coordinate.getX());
				geomPipe.setFmhy(coordinate.getY());
				geomPipe.setFmhh(coordinate.getH());
				replacePipegeom(geomPipe);
			}
		}
		return true;
	}

	public boolean replacePipegeom(GeomPipe geomPipe) {
		String uses = geomPipe.getPipe().getUses();
		geomPipe.setSmhGradeA(findGrideA(geomPipe.getSmhx(), geomPipe.getSmhy(), uses));
		geomPipe.setSmhGradeB(findGrideB(geomPipe.getSmhx(), geomPipe.getSmhy(), uses));
		geomPipe.setFmhGradeA(findGrideA(geomPipe.getFmhx(), geomPipe.getFmhy(), uses));
		geomPipe.setFmhGradeB(findGrideB(geomPipe.getFmhx(), geomPipe.getFmhy(), uses));
		this.updateGeomPipe(geomPipe);
		return true;
	}

	/** 计算GradeA */
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

	/** 计算GradeB */
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
