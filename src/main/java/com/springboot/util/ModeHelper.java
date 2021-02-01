package com.springboot.util;

import cn.hutool.core.io.FileUtil;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.util.OleBlob;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

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

    @SneakyThrows
    public void ItemMode(MultipartFile file, User user) {
        String name = AppUtils.UUIDCode();
        File temp = new File(myfile + "/TempFile/" + name + ".mdb");
        AppUtils.moveFile(file, temp.getPath());
        Database database = DatabaseBuilder.open(temp);

        Project project = new Project();
        Map<Integer, Pipe> pipes = new TreeMap<>();
        Map<Integer, Item> items = new TreeMap<>();
        Pipe pipe = new Pipe();
        Item item = new Item();

        Table table = database.getTable("project");
        BeanMap.create(project).putAll(table.getNextRow());

        Table table1 = database.getTable("table1");
        for (Row row : table1) {
            pipe = new Pipe();
            BeanMap.create(pipe).putAll(row);
            pipe.setProject(project);
            pipes.put(row.getInt("id"), pipe);
        }

        Table table2 = database.getTable("table2");
        for (Row row : table2) {
            item = new Item();
            BeanMap.create(item).putAll(row);
            item.setPicture(getPicture(row));
            item.setPipe(pipes.get(row.getInt("pipeid")));
            items.put(row.getInt("id"), item);
        }

        project.setState("未提交");
        project.setUser(user);
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
        FileUtil.del(temp);
    }

    @SneakyThrows
    private String getPicture(Row row) {
        OleBlob blob = row.getBlob("path");
        if (StringUtils.isEmpty(blob))
            return "";
        String name = AppUtils.UUIDCode();
        File temp = new File(myfile + "/image/" + name + ".png");
        @Cleanup OutputStream outputStream = new FileOutputStream(temp);
        blob.writeTo(outputStream);
        return name;
    }

}
