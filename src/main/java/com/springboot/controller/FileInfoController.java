package com.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.util.*;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class FileInfoController {

    @Value(value = "${myfile}")
    private String path;

    @Resource
    private MarkFileDoc markFileDoc;
    @Resource
    private MarkFileMdb markFileMdb;
    @Resource
    private MarkFilePdf markFilePdf;
    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private Computes computes;
    @Resource
    private PipeBiz pipeBiz;
    @Resource
    private ItemBiz itemBiz;

    @SneakyThrows
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(int id, HttpServletResponse response) {
        Project project = projectBiz.findInfoProject(id, null);
        if (StringUtils.isEmpty(project))
            return; // 查询结果为空
        String name = AppUtils.UUIDCode();
        String srcPath = path + "/report/";
        FileUtil.mkdir(srcPath + name + "/data/");
        FileUtil.mkdir(srcPath + name + "/report/");
        FileUtil.mkdir(srcPath + name + "/video/");

        List<Pipe> pipes = pipeBiz.findListPipe(project);
        for (Pipe pipe : pipes) {
            pipe.setItems(itemBiz.findListItem(pipe));
            computes.computePipe(pipe, project.getStandard());
        }
        project.setPipes(pipes);

        AppUtils.convert(project, srcPath + name + "/data/");
        markFilePdf.initPDF(project, srcPath + name + "/");
        markFileMdb.initMDB(project, srcPath + name + "/");
        markFileDoc.initDOC(project, srcPath + name + "/");

        File zipFile = new File(path + "/compre/" + name + ".zip");
        ZipUtil.zip(srcPath + name, zipFile.getPath());

        String fileName = project.getDate() + "_" + project.getName() + ".zip";
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setContentType("application/force-download");

        int len;
        byte[] bytes = new byte[1024];
        @Cleanup InputStream fstream = new FileInputStream(zipFile);
        @Cleanup InputStream bstream = new BufferedInputStream(fstream);
        @Cleanup OutputStream outputStream = response.getOutputStream();
        while ((len = bstream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }
        // 删除下载文件
        FileUtil.del(srcPath + name);
        FileUtil.del(zipFile.getPath());
    }
}
