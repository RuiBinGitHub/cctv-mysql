package com.springboot.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class HelperDOC {

	@Resource
	private Computes computes;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;

	public void initDOC(Project project, String path) {
		try {
			Configuration config = new Configuration(Configuration.VERSION_2_3_22);
			config.setDefaultEncoding("utf-8");
			config.setClassForTemplateLoading(HelperDOC.class, "/");
			Template template = config.getTemplate("Template.xml");
			String FileName = path + "/" + project.getDate() + "_" + project.getName();
			OutputStream stream = new FileOutputStream(FileName + "_CCTV.doc");
			OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8");
			BufferedWriter bwriter = new BufferedWriter(writer);

			double length = 0;
			List<Pipe> pipes = project.getPipes();
			for (int i = 0; pipes != null && i < pipes.size(); i++) {
				Pipe pipe = pipes.get(i);

				String value = pipe.getVideono();
				value = value.replaceAll("&", "&amp;");
				value = value.replaceAll("<", "&lt;");
				value = value.replaceAll(">", "&gt;");
				pipe.setVideono(value);

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

				List<Item> items = pipe.getItems();
				if (project.getStandard().indexOf("H") != -1 && items.size() >= 3) {
					items.remove(0);
					items.remove(0);
					items.remove(0);
				}
				for (int j = 0; items != null && j < items.size(); j++) {
					Item item = items.get(j);
					String code = item.getCode();
					if (code.length() > 2 && code.indexOf("-") != -1)
						item.setCode(code.substring(0, code.length() - 2));
					if (code != null && code.indexOf("SA") == 0 || code.equals("#4"))
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
			data.put("sum", length);
			template.process(data, bwriter);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
	}
}
