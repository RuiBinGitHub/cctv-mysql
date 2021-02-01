package com.springboot.util;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.springboot.entity.Project;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Base64.Decoder;

public class AppUtils {

    /**
     * 获取参数列表
     */
    public static Map<String, Object> getMap(Object... values) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < values.length; i += 2)
            map.put((String) values[i], values[i + 1]);
        return map;
    }

    /**
     * 以指定格式获取当前时间格式字符串
     */
    public static String getDate(String format) {
        Date date = new Date();
        if (format == null)
            format = "yyyy-MM-dd";
        Format simple = new SimpleDateFormat(format);
        return simple.format(date);
    }

    /**
     * 获取随机序列码
     */
    public static String UUIDCode() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * 获取request
     */
    @NotNull
    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (!StringUtils.isEmpty(attributes))
            return ((ServletRequestAttributes) attributes).getRequest();
        return null;
    }

    /**
     * 获取response
     */
    @NotNull
    public static HttpServletResponse getResponse() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (!StringUtils.isEmpty(attributes))
            return ((ServletRequestAttributes) attributes).getResponse();
        return null;
    }

    /**
     * 获取session
     */
    @NotNull
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (!StringUtils.isEmpty(request))
            return request.getSession(true);
        return null;
    }

    /**
     * 添加数据至session
     */
    public static void putValue(String key, Object value) {
        HttpSession session = getSession();
        if (!StringUtils.isEmpty(session))
            session.setAttribute(key, value);
    }

    /**
     * 从session获取数据
     */
    @NotNull
    public static Object getValue(String key) {
        HttpSession session = getSession();
        if (!StringUtils.isEmpty(session))
            return session.getAttribute(key);
        return null;
    }

    /**
     * 从session移除数据
     */
    public static void removeValue(String key) {
        HttpSession session = getSession();
        if (!StringUtils.isEmpty(session))
            session.removeAttribute(key);
    }

    public static ExcelReader getReader(MultipartFile file) {
        try {
            return ExcelUtil.getReader(file.getInputStream());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 保存图片
     */
    public static void saveImage(String data, String path, String name) {
        try {
            Decoder decoder = Base64.getDecoder();
            data = data.substring(22);
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

    /**
     * 拷贝文件至指定路径
     */
    public static void moveFile(MultipartFile file, String path) {
        try {
            File dest = new File(path);
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象转换为xml文件
     */
    public static void convert(Project project, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(project.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            String fileName = path + project.getName() + ".xml";
            FileWriter writer = new FileWriter(fileName);
            marshaller.marshal(project, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xml文件转换为对象
     */
    public static Project convert(Class<?> iclass, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(iclass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Project) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断字符串是否相等
     */
    public static boolean equals(String text1, String text2) {
        text1 = text1 == null ? "" : text1;
        text2 = text2 == null ? "" : text2;
        return text1.equals(text2);
    }

    public static int getInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

}
