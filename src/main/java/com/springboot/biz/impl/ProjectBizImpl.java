package com.springboot.biz.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.bean.Coordinate;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.dao.ProjectDao;
import com.springboot.entity.Company;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;

@Service
@Transactional
public class ProjectBizImpl implements ProjectBiz {

	@Resource
	private ProjectDao projectDao;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;

	private Map<String, Object> map = null;

	public void insertProject(Project project) {
		projectDao.insertProject(project);
	}

	public void updateProject(Project project) {
		projectDao.updateProject(project);
	}

	public void deleteProject(Project project) {
		projectDao.deleteProject(project);
	}

	public Project findInfoProject(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return projectDao.findInfoProject(map);
	}

	public Project findInfoProject(Map<String, Object> map) {
		return projectDao.findInfoProject(map);
	}

	public PageInfo<Project> findListProject(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		List<Project> projects = projectDao.findListProject(map);
		PageInfo<Project> info = new PageInfo<Project>(projects);
		return info;
	}


	public int appendProject(Project project) {
		insertProject(project);
		Pipe pipe = new Pipe();
		pipe.setNo(1);
		pipe.setOperator(project.getOperator());
		pipe.setPurposes("Structural Defects");
		pipe.setSlope(project.getSlope());
		if ("N".equals(project.getSlope()))
			pipe.setSloperef("N/A");
		pipe.setDate(project.getDate());
		pipe.setTime("00:00");
		pipe.setDivision("--");
		pipe.setAreacode("--");
		pipe.setCategory("Z");
		pipe.setCleaned("N");
		pipe.setWeather("1");
		pipe.setProject(project);
		pipeBiz.appendPipe(pipe);
		return project.getId();
	}

	public int removeProject(Company company, List<?> list) {
		map = AppHelper.getMap("company", company, "list", list);
		return projectDao.removeProject(map);
	}
	
	public void combinProject(String list, Company company) {
		List<String> name = Arrays.asList(list.split(","));
		int id = AppHelper.getInt(name.get(0));
		Project project = findInfoProject(id, null);
		List<Integer> options = new ArrayList<Integer>();
		for (int i = 1; name != null && i < name.size(); i++)
			options.add(AppHelper.getInt(name.get(i)));
		this.pipeBiz.combinPipe(id, options);
		if (removeProject(company, options) != options.size())
			throw new RuntimeException();
		itemBiz.sortItemImg(project);
	}

	public void importProject(Project project, MultipartFile file) {
		XSSFWorkbook workbook = AppHelper.getWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Map<String, Coordinate> map = new HashMap<>();
		DecimalFormat foramt1 = new DecimalFormat("#0.00");
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			XSSFRow row = sheet.getRow(i);
			Coordinate coordinate = new Coordinate();
			String mh = AppHelper.getString(row.getCell(0));
			double value1 = AppHelper.getNumber(row.getCell(1));
			double value2 = AppHelper.getNumber(row.getCell(2));
			double value3 = AppHelper.getNumber(row.getCell(3));
			coordinate.setDepth(value1 == 0.0 ? "--" : foramt1.format(value1));
			coordinate.setClevel(value2 == 0.0 ? "--" : foramt1.format(value2));
			coordinate.setIlevel(value3 == 0.0 ? "--" : foramt1.format(value3));
			map.put(mh, coordinate);
		}
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (Pipe pipe : pipes) {
			String smhNo = pipe.getSmanholeno();
			String fmhNo = pipe.getFmanholeno();
			if (map.containsKey(smhNo)) {
				Coordinate coordinate = map.get(smhNo);
				pipe.setSdepth(coordinate.getDepth());
				pipe.setScoverlevel(coordinate.getClevel());
				pipe.setSinvertlevel(coordinate.getIlevel());
				pipeBiz.updatePipe(pipe);
			}
			if (map.containsKey(fmhNo)) {
				Coordinate coordinate = map.get(fmhNo);
				pipe.setFdepth(coordinate.getDepth());
				pipe.setFcoverlevel(coordinate.getClevel());
				pipe.setFinvertlevel(coordinate.getIlevel());
				pipeBiz.updatePipe(pipe);
			}
		}
	}

}
