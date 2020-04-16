package com.springboot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.biz.CompanyBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.util.Computes;
import com.springboot.util.HelperDOC;
import com.springboot.util.HelperMDB;
import com.springboot.util.HelperPDF;

@Transactional
@SpringBootTest
public class MainApplicationTests {

	@Resource
	private CompanyBiz companyBiz;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private Computes computes;
	@Resource
	private HelperDOC helperDOC;
	@Resource
	private HelperMDB helperMDB;
	@Resource
	private HelperPDF helperPDF;

	public Map<String, Object> map = null;

	@Test
	public void contextLoads() {
		Project project = projectBiz.findInfoProject(61, null);
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (Pipe pipe : pipes) // 计算管道分数
			computes.computePipe(pipe, project.getStandard());
		project.setPipes(pipes);
		helperPDF.initPDF(project, "d:/");
		// helperDOC.initDOC(project, "d:/");
	}

	@Test
	public void contextLoad1() {
		Map<String, List<Pipe>> pipeMap = new TreeMap<String, List<Pipe>>();
		Project project = projectBiz.findInfoProject(61, null);
		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (Pipe pipe : pipes) { // 计算管道分数
			if (pipe.getWorkorder() == null)
				pipe.setWorkorder("");
			if (pipe.getSmanholeno() == null)
				pipe.setSmanholeno("");
			if (pipe.getFmanholeno() == null)
				pipe.setFmanholeno("");
			if (pipe.getUses() == null)
				pipe.setUses("");
			if (pipe.getDire() == null)
				pipe.setDire("");
			if (pipe.getHsize() == null)
				pipe.setHsize("");
			if (pipe.getShape() == null)
				pipe.setShape("");
			if (pipe.getMater() == null)
				pipe.setMater("");
			if (pipe.getVideono() == null)
				pipe.setVideono("");
			String key = pipe.getWorkorder() + pipe.getUses();
			if (!pipeMap.containsKey(key)) {
				List<Pipe> list = new ArrayList<Pipe>();
				pipeMap.put(key, list);
			}
			pipeMap.get(key).add(pipe);
		}
		for (String key : pipeMap.keySet()) {
			List<Pipe> list = pipeMap.get(key);
			System.out.println("====" + key + "====");
			for (Pipe pipe : list) {
				System.out.println(pipe.getNo() + pipe.getWorkorder() + pipe.getUses());
			}
		}
	}

}
