package com.springboot.util;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;

@Component
@Transactional
public class ModePreview {

    @Value(value = "${myfile}")
    private String myfile;

    @Resource
    private MarkFilePdf markFilePdf;
    @Resource
    private Computes computes;

    @SneakyThrows
    public String ItemPrev(MultipartFile file) {
        String name = AppUtils.UUIDCode();
        File temp = new File(myfile + "/TempFile/" + name + ".xml");
        AppUtils.moveFile(file, temp.getPath());

        Project project = AppUtils.convert(Project.class, temp);
        if (StringUtils.isEmpty(project))
            return null;
        List<Pipe> pipes = project.getPipes();
        for (Pipe pipe : pipes) {
            pipe.setProject(project);
            computes.computePipe(pipe, project.getStandard());
            List<Item> items = pipe.getItems();
            for (Item item : items)
                item.setPipe(pipe);
        }

        FileUtil.mkdir(myfile + "/compre/" + name);
        markFilePdf.initPDF(project, myfile + "/compre/" + name);
        String FileName = project.getDate() + "_" + project.getName();
        FileUtil.del(temp);
        return name + "/" + FileName + "_CCTV.pdf";
    }

}
