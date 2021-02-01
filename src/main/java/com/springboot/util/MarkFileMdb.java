package com.springboot.util;

import cn.hutool.core.io.FileUtil;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MarkFileMdb {

    @Value(value = "${myfile}")
    private String myfile;
    private final DecimalFormat foramt = new DecimalFormat("#000");

    @SneakyThrows
    public void initMDB(Project project, String path) {
        String FileName = path + "/" + project.getDate() + "_" + project.getName();
        File file = new File(FileName + "_CCTV.mdb");
        File temp = ResourceUtils.getFile("classpath:Template.mdb");
        FileUtil.copyFile(temp, file);

        Database database = DatabaseBuilder.open(file);

        Table table = database.getTable("project");// 项目表格
        Map<String, Object> map = new HashMap<>(16);
        BeanMap beanMap = BeanMap.create(project);
        for (Object key : beanMap.keySet())
            map.put(key + "", beanMap.get(key));
        table.addRowFromMap(map);

        List<Map<String, Object>> list1 = new ArrayList<>(32);
        List<Map<String, Object>> list2 = new ArrayList<>(64);

        int imgNo = 1;
        List<Pipe> pipes = project.getPipes();
        for (int i = 0; pipes != null && i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            map = new HashMap<>(64);
            beanMap = BeanMap.create(pipe);
            for (Object key : beanMap.keySet())
                map.put(key + "", beanMap.get(key));
            list1.add(map);

            List<Item> items = pipe.getItems();
            for (int j = 0; items != null && j < items.size(); j++) {
                Item item = items.get(j);
                map = new HashMap<>(32);
                beanMap = BeanMap.create(item);
                for (Object key : beanMap.keySet())
                    map.put(key + "", beanMap.get(key));
                map.put("path", getBlob(item, path, imgNo++));
                map.put("pipeid", i + 1);
                list2.add(map);
            }
        }
        Table table1 = database.getTable("table1");// 管道表格
        Table table2 = database.getTable("table2");// 记录表格
        table1.addRowsFromMaps(list1);
        table2.addRowsFromMaps(list2);
    }

    @SneakyThrows
    private Blob getBlob(Item item, String path, int no) {
        if (StringUtils.isEmpty(item.getPicture()))
            return null;
        String name = foramt.format(no);
        File file = new File(myfile + "/image/" + item.getPicture() + ".png");
        File temp = new File(path + "/data/" + name + ".png");
        SerialBlob blob = new SerialBlob(Files.readAllBytes(file.toPath()));
        FileUtil.copyFile(file, temp);
        return blob;
    }
}
