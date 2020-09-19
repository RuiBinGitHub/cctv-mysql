package com.springboot.util;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springboot.biz.CodeBiz;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Code;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;

@Component
@Transactional
public class TranHelper {

	@Resource
	private CodeBiz codeBiz;
	@Resource
	private Computes computes;

	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;

	public void trans(Project project) {
		int photo = 1;
		Item item = null;
		Pipe pipe = null;
		Code code = null;
		String codes = "CPF, GYF, ICF, LHF, BRF, MHF, OSF, OCF, OFF, REF, SKF";
		DecimalFormat foramt = new DecimalFormat("#000");

		List<Pipe> pipes = pipeBiz.findListPipe(project);
		for (int i = 0; pipes != null && i < pipes.size(); i++) {
			pipe = pipes.get(i);
			List<Item> items = itemBiz.findListItem(pipe);
			Iterator<Item> iterator = items.iterator();
			while (iterator.hasNext()) {
				item = iterator.next();
				if ("ST".equals(item.getCode())) {
					iterator.remove();
					continue;
				}
				if ("WL".equals(item.getCode()))
					item.setCode("WLC");
				if (item.getCode().endsWith("J") || codes.contains(item.getCode()))
					item.getCode().substring(0, item.getCode().length() - 1);
				code = codeBiz.findInfoCode(project.getStandard(), item.getCode());
				if ("-".equals(code.getMscc()))
					iterator.remove();
				item.setCode(code.getMscc());
				item.setPhoto("");
				if (!StringUtils.isEmpty(item.getPicture()))
					item.setPhoto(foramt.format(photo++));
			}
			pipe.setItems(items);
		}

		project.setStandard("MSCC 2004");
		projectBiz.insertProject(project);
		for (int i = 0; pipes != null && i < pipes.size(); i++) {
			pipes.get(i).setProject(project);
			pipeBiz.insertPipe(pipes.get(i));
			List<Item> items = pipes.get(i).getItems();
			for (int j = 0; items != null && j < items.size(); j++) {
				items.get(j).setPipe(pipes.get(i));
				itemBiz.insertItem(items.get(j));
			}
		}
	}
}
