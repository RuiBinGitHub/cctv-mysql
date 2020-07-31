package com.springboot.util;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

	public String ItemMode(MultipartFile file) {
		try {
			String name = AppHelper.UUIDCode();
			File temp = new File(myfile + "/TempFile/" + name + ".xml");
			AppHelper.move(file, temp);
			Project project = AppHelper.convert(Project.class, temp);
			List<Pipe> pipes = project.getPipes();
			for (int i = 0; pipes != null && i < pipes.size(); i++) {
				Pipe pipe = pipes.get(i);
				pipe.setProject(project);
				computes.computePipe(pipe, project.getStandard());
				List<Item> items = pipe.getItems();
				for (int j = 0; items != null && j < items.size(); j++) {
					Item item = items.get(j);
					item.setPipe(pipe);
				}
			}
			String path = myfile + "/TempFile/" + name;
			helperPDF.initPDF(project, path + ".pdf");
			temp.delete();
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
