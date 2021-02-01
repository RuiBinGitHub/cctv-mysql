package com.springboot.util;

import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MarkFileDoc {

    @SneakyThrows
    public void initDOC(Project project, String path) {
        Configuration config = new Configuration(Configuration.VERSION_2_3_22);
        config.setDefaultEncoding("utf-8");
        config.setClassForTemplateLoading(MarkFileDoc.class, "/");
        Template template = config.getTemplate("Template.xml");

        String FileName = path + "/" + project.getDate() + "_" + project.getName();
        File file = new File(FileName + "_CCTV.doc");
        @Cleanup OutputStream stream = new FileOutputStream(file);
        @Cleanup OutputStreamWriter writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        @Cleanup BufferedWriter bwriter = new BufferedWriter(writer);

        String value;
        double length = 0;
        List<Pipe> pipes = project.getPipes();
        for (int i = 0; pipes != null && i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);

            value = pipe.getSmanholeno();
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("<", "&lt;");
            value = value.replaceAll(">", "&gt;");
            pipe.setSmanholeno(value);

            value = pipe.getFmanholeno();
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("<", "&lt;");
            value = value.replaceAll(">", "&gt;");
            pipe.setFmanholeno(value);

            value = pipe.getVideono();
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("<", "&lt;");
            value = value.replaceAll(">", "&gt;");
            pipe.setVideono(value);

            List<Item> items = pipe.getItems();
            if (project.getStandard().contains("H") && items.size() >= 3) {
                items.remove(0);
                items.remove(0);
                items.remove(0);
            }
            for (Item item : items) {
                String code = item.getCode();
                if (code.length() > 2 && code.contains("-"))
                    item.setCode(code.substring(0, code.length() - 2));
                if (code.indexOf("SA") == 0 || code.equals("#4"))
                    item.setDefine("Y");
                else
                    item.setDefine("N");

                value = item.getDepict();
                value = value.replaceAll("&", "&amp;");
                value = value.replaceAll("<", "&lt;");
                value = value.replaceAll(">", "&gt;");
                item.setDepict(value);
            }
            length += pipe.getTestlength();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", pipes);
        data.put("size", length);
        template.process(data, bwriter);
    }
}
