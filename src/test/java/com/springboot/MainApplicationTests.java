package com.springboot;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.biz.CompanyBiz;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.util.Computes;

@Transactional
@SpringBootTest
public class MainApplicationTests {

	@Resource
	private Computes computes;
	@Resource
	private CompanyBiz companyBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;
	

	public Map<String, Object> map = null;

	@Test
	public void contextLoads() throws Exception {
		Project project = projectBiz.findInfoProject(35, null);
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (Pipe pipe : pipes) // 计算管道分数
			pipe.setItems(itemBiz.findListItem(pipe));
		project.setPipes(pipes);
		writr(project, "d:/project.xml");
		
	}
	
	public static void writr(Project project, String path) throws Exception {
		JAXBContext context = JAXBContext.newInstance(project.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", true);
		FileWriter writer = new FileWriter(path);
		marshaller.marshal(project, writer);
		System.out.println("--");
	}

}
