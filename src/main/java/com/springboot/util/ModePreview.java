package com.springboot.util;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

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
	private HelperPDF helperPDF;
	@Resource
	private Computes computes;

	public String ItemMode(MultipartFile[] files) {
		try {
			String name = AppHelper.UUIDCode();
			File file = new File(myfile + "/TempFile/" + name + ".xml");
			for (int i = 0; files != null && i < files.length; i++) {
				if ("text/xml".equals(files[i].getContentType())) {
					files[i].transferTo(file);
					break;
				}
			}
			Project project = AppHelper.convert(Project.class, file);
			List<Pipe> pipes = project.getPipes();
			for (int i = 0; pipes != null && i < pipes.size(); i++) {
				Pipe pipe = pipes.get(i);
				pipe.setProject(project);
				computes.computePipe(pipe, project.getStandard());
				List<Item> items = pipe.getItems();
				for (int j = 0; items != null && j < items.size(); j++) {
					Item item = items.get(j);
					item.setPipe(pipe);
					if (StringUtils.isEmpty(item.getPhoto()))
						continue;
					item.setPicture(getPicture(files, item.getPhoto()));
				}
			}
			String path = myfile + "/TempFile/" + name;
			helperPDF.initPDF(project, path + ".pdf");
			file.delete();
			return name;
		} catch (Exception e) {
			return null;
		}
	}

	private String getPicture(MultipartFile[] files, String photo) {
		for (int i = 0; files != null && i < files.length; i++) {
			String name = files[i].getOriginalFilename();
			int start = 0;
			if (name.contains("/"))
				start = name.lastIndexOf("/");
			if (name.contains("\\"))
				start = name.lastIndexOf("\\");
			int end = name.lastIndexOf(".");
			if (!photo.equals(name.substring(start + 1, end)))
				continue;
			String picture = AppHelper.UUIDCode();
			File dest = new File(myfile + "/ItemImage/" + picture + ".png");
			AppHelper.move(files[i], dest);
			return picture;
		}
		return "";
	}

}
