package com.springboot.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;

@Component
@Transactional
public class ModeHelper {

	@Value(value = "${myfile}")
	private String myfile;

	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;

	private File temp = null;
	private Connection conn = null;
	private Statement stat = null;
	private ResultSet result1 = null;
	private ResultSet result2 = null;
	private ResultSet result3 = null;

	public boolean ItemMode(MultipartFile file, User user) {
		try {
			String path = myfile + "/ItemImage/";
			String name = AppHelper.UUIDCode();
			temp = new File(myfile + "/TempFile/" + name + ".mdb");
			file.transferTo(temp);
			conn = getConnection(temp.getPath());
			
			Project project = new Project();
			Map<Integer, Pipe> pipes = new TreeMap<Integer, Pipe>();
			Map<Integer, Item> items = new TreeMap<Integer, Item>();
			Pipe pipe = null;
			Item item = null;

			/** 查询项目表 */
			stat = conn.createStatement();
			result1 = stat.executeQuery("select * from project");
			if (result1.next()) {
				project.setName(result1.getString(3));
				project.setClient(result1.getString(4));
				if (result1.getString(5) != null && result1.getString(5).indexOf("H") != -1)
					project.setStandard("HKCCEC 2009");
				if (result1.getString(5) != null && result1.getString(5).indexOf("M") != -1)
					project.setStandard("MSCC 2004");
				project.setSlope(result1.getBoolean(6) ? "Y" : "N");
				project.setState("未提交");
				project.setUser(user);
			}

			/** 查询管道表 */
			stat = conn.createStatement();
			result2 = stat.executeQuery("select * from table1 order by IID");
			while (result2.next()) {
				pipe = new Pipe();
				pipe.setNo(AppHelper.getInt(result2.getString(30)));
				pipe.setOperator(result2.getString(6));
				pipe.setWorkorder(result2.getString(7));
				pipe.setReference(result2.getString(8));
				pipe.setPurposes(result2.getString(5));
				pipe.setSlope(result2.getString(43).substring(0, 1));
				pipe.setSloperef(result2.getString(44));
				pipe.setYearlaid(getYear(result2.getString(42)));
				pipe.setDate(replaceDate(result2.getString(37)));
				pipe.setTime(result2.getString(25));

				pipe.setDistrict1(result2.getString(18));
				pipe.setDistrict2(result2.getString(45));
				pipe.setDistrict3(result2.getString(46));
				pipe.setRoadname(result2.getString(9));
				pipe.setHousenum(result2.getString(47));
				pipe.setBuilding(result2.getString(10));
				pipe.setDivision(result2.getString(19));
				pipe.setAreacode(result2.getString(36));

				pipe.setSmanholeno(result2.getString(11));
				pipe.setFmanholeno(result2.getString(12));
				pipe.setUses(result2.getString(28).substring(0, 1));
				pipe.setDire(result2.getString(33).substring(0, 1));
				pipe.setHsize(result2.getString(39));
				pipe.setWsize(result2.getString(13));

				pipe.setShape(CameHelper.getCameName(result2.getString(14), "sha"));
				pipe.setMater(CameHelper.getCameName(result2.getString(15), "mat"));
				pipe.setLining(CameHelper.getCameName(result2.getString(21), "lin"));
				pipe.setPipelength(AppHelper.getDoule(result2.getString(29)));
				pipe.setTestlength(AppHelper.getDoule(result2.getString(03)));

				pipe.setSdepth(result2.getString(16));
				pipe.setScoverlevel(result2.getString(26));
				pipe.setSinvertlevel(result2.getString(27));
				pipe.setFdepth(result2.getString(33));
				pipe.setFcoverlevel(result2.getString(34));
				pipe.setFinvertlevel(result2.getString(35));
				pipe.setCategory(result2.getString(20));
				pipe.setCleaned(result2.getString(41));
				pipe.setWeather(CameHelper.getCameName(result2.getString(31), "wea"));
				pipe.setVideono(result2.getString(24));
				pipe.setComment(result2.getString(22));
				pipe.setProject(project);
				// 添加管道至map中
				pipes.put(result2.getInt(2), pipe);
			}

			/** 查询记录表 */
			stat = conn.createStatement();
			result3 = stat.executeQuery("select * from table2 order by IID");
			while (result3.next()) {
				item = new Item();
				item.setNo(result3.getInt(1));
				item.setVideo(result3.getString(8));
				item.setPhoto(result3.getString(5));
				item.setDist(result3.getString(9));
				item.setCont(result3.getString(10));
				item.setCode(result3.getString(3));
				item.setDiam(result3.getString(11));
				item.setClockAt(result3.getString(12));
				item.setClockTo(result3.getString(13));
				item.setPercent(result3.getString(14));
				item.setLengths(result3.getString(15));
				item.setRemarks(result3.getString(16));
				byte[] data = result3.getBytes(19);
				if (data != null && data.length > 0) {
					name = AppHelper.UUIDCode();
					saveImage(data, path, name + ".png");
					item.setPicture(name);
				}
				item.setPipe(pipes.get(result3.getInt(7)));
				// 添加记录至map中
				items.put(result3.getInt(20), item);
			}
			// 设置项目的操作人员和日期
			if (!StringUtils.isEmpty(pipe)) {
				project.setOperator(pipe.getOperator());
				project.setDate(pipe.getDate());
			}
			// 添加数据至数据库
			projectBiz.insertProject(project);
			for (Pipe mypipe : pipes.values())
				pipeBiz.insertPipe(mypipe);
			for (Item myitem : items.values())
				itemBiz.insertItem(myitem);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			colse();
		}

	}

	/** 获取数据库链接对象 */
	private Connection getConnection(String path) {
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			String url = "jdbc:Access:///" + path;
			Connection conn = DriverManager.getConnection(url, "", "");
			conn.setAutoCommit(true);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void colse() {
		try {
			if (conn != null)
				conn.close();
			if (stat != null)
				stat.close();
			if (result1 != null)
				result1.close();
			if (result2 != null)
				result2.close();
			if (result3 != null)
				result3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** 将文件流转换为图片 */
	private void saveImage(byte[] data, String path, String name) {
		try {
			OutputStream fstream = new FileOutputStream(path + name);
			OutputStream bstream = new BufferedOutputStream(fstream);
			bstream.write(data);
			bstream.flush();
			bstream.close();
			fstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getYear(String text) {
		if ("<30".equals(text))
			return "10";
		else if ("30-50".equals(text))
			return "40";
		else if (">50".equals(text))
			return "60";
		else
			return "0";
	}

	private String replaceDate(String text) {
		if (!StringUtils.isEmpty(text))
			return text.replace("/", "-");
		else
			return "";
	}

}
