package com.springboot.util;

import java.text.DecimalFormat;
import java.util.Arrays;
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
    private ProjectBiz projectBiz;
    @Resource
    private PipeBiz pipeBiz;
    @Resource
    private ItemBiz itemBiz;
    @Resource
    private CodeBiz codeBiz;

    public void ItemTran(Project project) {
        int photo = 1;
        DecimalFormat foramt = new DecimalFormat("#000");
        List<String> list = Arrays.asList("CPF", "GYF", "ICF", "LHF", "BRF", "MHF", "OSF", "OCF", "OFF", "REF", "SKF");

        List<Pipe> pipes = pipeBiz.findListPipe(project);
        for (int i = 0; pipes != null && i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            List<Item> items = itemBiz.findListItem(pipe);
            Iterator<Item> iterator = items.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if ("ST".equals(item.getCode())) {
                    iterator.remove();
                    continue;
                }
                if ("WL".equals(item.getCode()))
                    item.setCode("WLC");
                if (item.getCode().endsWith("J") || list.contains(item.getCode()))
                    item.setCode(item.getCode().substring(0, item.getCode().length() - 1));
                Code code = codeBiz.findInfoCode(project.getStandard(), item.getCode());
                if (code == null || "-".equals(code.getCode2())) {
                    iterator.remove();
                    continue;
                }
                item.setCode(code.getCode2());
                if (!StringUtils.isEmpty(item.getPicture()))
                    item.setPhoto(foramt.format(photo++));
            }
            pipe.setItems(items);
        }

        project.setStandard("MSCC 2004");
        projectBiz.insertProject(project);
        for (int i = 0; pipes != null && i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setProject(project);
            pipeBiz.insertPipe(pipe);
            for (Item item : pipe.getItems()) {
                item.setPipe(pipe);
                itemBiz.insertItem(item);
            }
        }
    }

}
