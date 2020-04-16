package com.springboot;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
	public void contextLoads() {
		Project project = projectBiz.findInfoProject(61, null);
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (Pipe pipe : pipes) // 计算管道分数
			computes.computePipe(pipe, project.getStandard());
		project.setPipes(pipes);
		// helperPDF.initPDF(project, "d:/");
	}

}
