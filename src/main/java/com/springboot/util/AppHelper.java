package com.springboot.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Base64.Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Project;

public class AppHelper {

	/** 获取参数列表 */
	public static Map<String, Object> getMap(Object... values) {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < values.length; i += 2)
			map.put((String) values[i], values[i + 1]);
		return map;
	}

	/** 以指定格式获取当前时间格式字符串 */
	public static String getDate(String format) {
		Date date = new Date();
		if (format == null)
			format = "yyyy-MM-dd";
		Format simple = new SimpleDateFormat(format);
		return simple.format(date);
	}

	/** 获取随机序列码 */
	public static String UUIDCode() {
		UUID uuid = UUID.randomUUID();
		String code = uuid.toString();
		return code.toUpperCase();
	}

	/** 创建公司人员密码 */
	public static String findPass() {
		String code = "", list = "1,2,3,4,5,6,7,8,9,0";
		for (char c = 'A'; c <= 'Z'; c++)
			list += String.valueOf("," + c);
		String codeArray[] = list.split(",");
		for (int i = 0; i < 12; i++)
			code += codeArray[(int) (Math.random() * 35)];
		return code;
	}

	/** 获取request */
	public static HttpServletRequest getRequest() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
		return request;
	}

	/** 获取response */
	public static HttpServletResponse getResponse() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = ((ServletRequestAttributes) attributes).getResponse();
		return response;
	}

	/** 获取session */
	public static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession(true);
		return session;
	}

	/** 添加数据至session */
	public static void pushMap(String key, Object value) {
		HttpSession session = getSession();
		session.setAttribute(key, value);
	}

	/** 从session获取数据 */
	public static Object findMap(String key) {
		HttpSession session = getSession();
		return session.getAttribute(key);
	}

	/** 从session移除数据 */
	public static void removeMap(String key) {
		HttpSession session = getSession();
		session.removeAttribute(key);
	}

	/** 保存图片 */
	public static void saveImage(String data, String path, String name) {
		try {
			Decoder decoder = Base64.getDecoder();
			data = data.substring(22, data.length());
			byte[] bytes = decoder.decode(data);
			for (int i = 0; i < bytes.length; i++)
				bytes[i] = bytes[i] < 0 ? bytes[i] += 256 : bytes[i];
			OutputStream output = new FileOutputStream(path + name + ".png");
			output.write(bytes);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void move(MultipartFile file, File dest) {
		try {
			file.transferTo(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 对象转换为xml文件 */
	public static void convert(Project project, String path) {
		try {
			JAXBContext context = JAXBContext.newInstance(project.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", true);
			FileWriter writer = new FileWriter(path);
			marshaller.marshal(project, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** xml文件转换为对象 */
	public static Project convert(Class<?> iclass, File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(iclass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Project project = (Project) unmarshaller.unmarshal(file);
			return project;
		} catch (Exception e) {
			return null;
		}
	}

	public static int getInt(String text) {
		try {
			return Integer.valueOf(text);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double getDoule(String text) {
		try {
			return Double.valueOf(text);
		} catch (Exception e) {
			return 0;
		}
	}

	public static XSSFWorkbook getWorkbook(MultipartFile file) {
		try {
			InputStream stream = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(stream);
			return workbook;
		} catch (IOException e) {
			return null;
		}
	}

	public static String getString(XSSFCell cell) {
		try {
			return cell.getStringCellValue();
		} catch (Exception e) {
			return "";
		}
	}

	public static double getNumber(XSSFCell cell) {
		try {
			return cell.getNumericCellValue();
		} catch (Exception e) {
			return 0;
		}
	}
}
