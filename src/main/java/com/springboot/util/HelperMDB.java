package com.springboot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;

@Component
public class HelperMDB {

	@Value(value = "${myfile}")
	private String myfile;

	private Connection conn = null;
	private PreparedStatement pstat = null;

	public void initMDB(Project project, String path) {
		try {
			String FileName = path + "/" + project.getDate() + "_" + project.getName();
			File file1 = new File(myfile + "temp.mdb"); // 模版位置
			File file2 = new File(FileName + "_CCTV.mdb");
			FileUtils.copyFile(file1, file2);
			conn = getConnection(file2.getPath());

			String insertSql1 = "insert into project values(?, ?, ?, ?, ?, ?)";
			pstat = conn.prepareStatement(insertSql1);
			pstat.setInt(1, 1);
			pstat.setString(2, "1");
			pstat.setString(3, project.getName());
			pstat.setString(4, project.getClient());
			pstat.setString(5, project.getStandard());
			pstat.setBoolean(6, "Y".equals(project.getSlope()) ? true : false);
			pstat.executeUpdate(); // 插入项目数据

			int pipeNo = 1;
			int itemNo = 1;
			int ImageName = 1;
			InputStream stream = null;
			StringBuffer str1 = new StringBuffer();
			StringBuffer str2 = new StringBuffer();
			for (int i = 0; i < 46; i++)
				str1.append("?, ");
			for (int i = 0; i < 19; i++)
				str2.append("?, ");

			for (Pipe pipe : project.getPipes()) {
				pstat = conn.prepareStatement("insert into table1 values(" + str1.toString() + "?)");
				pstat.setInt(1, pipeNo++);
				pstat.setString(2, pipe.getNo() + "");
				pstat.setDouble(3, pipe.getTestlength());
				pstat.setString(4, "");
				pstat.setString(5, getString(pipe.getPurposes()));
				pstat.setString(6, getString(pipe.getOperator()));
				pstat.setString(7, getString(pipe.getWorkorder()));
				pstat.setString(8, getString(pipe.getReference()));
				pstat.setString(9, getString(pipe.getRoadname()));
				pstat.setString(10, getString(pipe.getBuilding()));
				pstat.setString(11, getString(pipe.getSmanholeno()));
				pstat.setString(12, getString(pipe.getFmanholeno()));
				pstat.setString(13, getString(pipe.getWsize()));

				pstat.setString(14, CameHelper.getCameText(pipe.getShape(), "sha"));
				pstat.setString(15, CameHelper.getCameText(pipe.getMater(), "mat"));
				pstat.setString(16, getString(pipe.getSdepth()));
				pstat.setString(17, "");
				pstat.setString(18, getString(pipe.getDistrict1()));
				pstat.setString(19, getString(pipe.getDivision()));
				pstat.setString(20, getString(pipe.getCategory()));
				pstat.setString(21, CameHelper.getCameText(pipe.getLining(), "lin"));
				pstat.setString(22, getString(pipe.getComment()));
				pstat.setString(23, "");
				pstat.setString(24, getString(pipe.getVideono()));
				pstat.setString(25, getString(pipe.getTime()));
				pstat.setString(26, getString(pipe.getScoverlevel()));
				pstat.setString(27, getString(pipe.getSinvertlevel()));
				pstat.setString(28, CameHelper.getCameText(pipe.getUses(), "use"));
				pstat.setString(29, pipe.getPipelength() + "");
				pstat.setString(30, pipe.getNo() + "");
				pstat.setString(31, CameHelper.getCameText(pipe.getWeather(), "wea"));
				pstat.setString(32, CameHelper.getCameText(pipe.getDire(), "dir"));
				pstat.setString(33, getString(pipe.getFdepth()));
				pstat.setString(34, getString(pipe.getFcoverlevel()));
				pstat.setString(35, getString(pipe.getFinvertlevel()));
				pstat.setString(36, getString(pipe.getAreacode()));
				pstat.setString(37, getString(pipe.getDate()));
				pstat.setString(38, "");
				pstat.setString(39, getString(pipe.getHsize()));
				pstat.setString(40, "Z");
				pstat.setString(41, getString(pipe.getCleaned()));
				if (AppHelper.getInt(pipe.getYearlaid()) < 30)
					pstat.setString(42, "<30");
				else if (AppHelper.getInt(pipe.getYearlaid()) >= 30 && AppHelper.getInt(pipe.getYearlaid()) <= 50)
					pstat.setString(42, "30-50");
				else if (AppHelper.getInt(pipe.getYearlaid()) > 50)
					pstat.setString(42, ">50");
				if ("Y".equals(pipe.getSlope()))
					pstat.setString(43, "YES");
				else
					pstat.setString(43, "NO");
				pstat.setString(44, getString(pipe.getSloperef()));
				pstat.setString(45, getString(pipe.getDistrict2()));
				pstat.setString(46, getString(pipe.getDistrict3()));
				pstat.setString(47, getString(pipe.getHousenum()));
				pstat.executeUpdate(); // 插入管道数据

				for (Item item : pipe.getItems()) {
					pstat = conn.prepareStatement("insert into table2 values(" + str2.toString() + "?)");
					if (item.getCode().length() > 2 && item.getCode().indexOf("-") != -1)
						item.setCode(item.getCode().substring(0, item.getCode().length() - 2));
					pstat.setInt(1, itemNo++);
					pstat.setDouble(2, 0);
					pstat.setString(3, getString(item.getCode()));
					pstat.setString(4, getString(item.getDepict()));
					pstat.setString(5, getString(item.getPhoto()));
					pstat.setInt(6, (int) item.getGrade());
					pstat.setString(7, pipe.getNo() + "");
					pstat.setString(8, getString(item.getVideo()));
					pstat.setDouble(9, AppHelper.getDoule(item.getDist()));
					pstat.setString(10, getString(item.getCont()));
					pstat.setString(11, getString(item.getDiam()));
					pstat.setString(12, getString(item.getClockAt()));
					pstat.setString(13, getString(item.getClockTo()));
					pstat.setString(14, getString(item.getPercent()));
					pstat.setString(15, getString(item.getLengths()));
					pstat.setString(16, getString(item.getRemarks()));
					pstat.setString(17, getString(pipe.getSmanholeno()));
					pstat.setString(18, getString(pipe.getFmanholeno()));
					String filePath = myfile + "ItemImage/" + item.getPicture() + ".png";
					if (item.getPicture() == null || (stream = getStream(filePath)) == null)
						pstat.setNull(19, Types.BIT);
					else {
						pstat.setBinaryStream(19, stream, stream.available());
						File Image = new File(filePath);
						File itemp = new File(path + "/" + ImageName + ".png");
						FileUtils.copyFile(Image, itemp);
						ImageName++;
					}
					pstat.setInt(20, itemNo++);
					pstat.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstat);
		}
	}

	/** 获取链接 */
	private static Connection getConnection(String path) {
		try {
			Connection conn = null;
			Class.forName("com.hxtt.sql.access.AccessDriver");
			String url = "jdbc:Access:///" + path;
			conn = DriverManager.getConnection(url, "", "");
			conn.setAutoCommit(true);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 关闭连接 */
	private static void close(Connection conn, PreparedStatement stat) {
		try {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** 获取文件流对象 */
	private static InputStream getStream(String path) {
		try {
			File file = new File(path);
			InputStream stream = new FileInputStream(file);
			return stream;
		} catch (IOException e) {
			return null;
		}
	}

	/** 获取文本值 */
	private static String getString(String value) {
		if (value != null)
			return value;
		else
			return "";
	}
}
