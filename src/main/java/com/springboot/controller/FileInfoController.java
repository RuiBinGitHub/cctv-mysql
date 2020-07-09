package com.springboot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.CompanyBiz;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.util.AppHelper;
import com.springboot.util.Computes;
import com.springboot.util.HelperDOC;
import com.springboot.util.HelperMDB;
import com.springboot.util.HelperPDF;
import com.springboot.util.ZipFileHelper;

@RestController
public class FileInfoController {

	@Value(value = "${myfile}")
	private String path;

	@Resource
	private CompanyBiz companyBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;
	@Resource
	private Computes computes;
	@Resource
	private HelperDOC helperDOC;
	@Resource
	private HelperMDB helperMDB;
	@Resource
	private HelperPDF helperPDF;

	@RequestMapping(value = "/download")
	public void download(@RequestParam(defaultValue = "0") int id) throws IOException {
		Project project = projectBiz.findInfoProject(id, null);
		if (StringUtils.isEmpty(project))
			return; // 查询结果为空
		String srcPath = path + "/report/";
		String zipPath = path + "/compre/";
		String name = AppHelper.UUIDCode();
		File report = new File(srcPath + name + "/report/");
		File vedio = new File(srcPath + name + "/vedio/");
		File data = new File(srcPath + name + "/data/");
		report.mkdirs();
		vedio.mkdirs();
		data.mkdirs();

		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (int i = 0; pipes != null && i < pipes.size(); i++) { // 计算管道分数
			Pipe pipe = pipes.get(i);
			pipe.setItems(itemBiz.findListItem(pipe));
			computes.computePipe(pipe, project.getStandard());
		}
		project.setPipes(pipes);
		String file = srcPath + name + "/" + project.getDate() + "_" + project.getName();
		helperMDB.initMDB(project, srcPath + name + "/");
		helperDOC.initDOC(project, srcPath + name + "/");
		helperPDF.initPDF(project, file + "_CCTV.pdf");

		for (int i = 0; pipes != null && i < pipes.size(); i++) { // 计算管道分数
			Pipe pipe = pipes.get(i);
			pipe.setProject(null);
			pipe.setSurve(null);
			pipe.setScore(null);
			pipe.setGrade(null);
			List<Item> items = pipe.getItems();
			for (int j = 0; items != null && j < items.size(); j++) {
				Item item = items.get(j);
				item.setPipe(null);
				item.setType1(null);
				item.setType2(null);
				item.setType3(null);
				item.setPicture(null);
				item.setDefine(null);
				item.setDepict(null);
			}
		}
		AppHelper.convert(project, data.getPath() + "/" + project.getName() + ".xml");

		HttpServletResponse response = AppHelper.getResponse();
		String fileName = project.getDate() + "_" + project.getName();
		File zipFile = ZipFileHelper.toZip(srcPath + name, zipPath, fileName);
		response.addHeader("Content-Disposition", "attachment;fileName=" + fileName + ".zip");
		response.setContentType("application/force-download");

		int len = -1;
		byte[] buffer = new byte[1024];
		InputStream fstream = new FileInputStream(zipFile.getPath());
		InputStream bstream = new BufferedInputStream(fstream);
		OutputStream outputStream = response.getOutputStream();
		while ((len = bstream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
			outputStream.flush();
		}
		bstream.close();
		zipFile.delete();
	}
}
