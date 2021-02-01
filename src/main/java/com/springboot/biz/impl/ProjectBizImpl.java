package com.springboot.biz.impl;

import cn.hutool.poi.excel.ExcelReader;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.bean.AppBean;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.dao.ProjectDao;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProjectBizImpl implements ProjectBiz {

    @Value(value = "${myfile}")
    private String myfile;

    @Resource
    private ProjectDao projectDao;
    @Resource
    private PipeBiz pipeBiz;
    @Resource
    private ItemBiz itemBiz;
    private Map<String, Object> map = null;

    public void insertProject(Project project) {
        projectDao.insertProject(project);
    }

    public void updateProject(Project project) {
        projectDao.updateProject(project);
    }

    public void deleteProject(Project project) {
        projectDao.deleteProject(project);
    }

    public Project findInfoProject(int id, User user) {
        map = AppUtils.getMap("id", id, "user", user);
        return projectDao.findInfoProject(map);
    }

    public Project findInfoProject(Map<String, Object> map) {
        return projectDao.findInfoProject(map);
    }

    public PageInfo<Project> findListProject(Map<String, Object> map) {
        if (!StringUtils.isEmpty(map.get("name")))
            map.put("name", "%" + map.get("name") + "%");
        List<Project> projects = projectDao.findListProject(map);
        return new PageInfo<>(projects);
    }

    public PageInfo<Project> findViewProject(Map<String, Object> map) {
        String sort = (String) map.get("sort");
        if (!StringUtils.isEmpty(sort))
            sort = sort.replace("1", " desc");
        if (!StringUtils.isEmpty(map.get("name")))
            map.put("name", "%" + map.get("name") + "%");
        if (!StringUtils.isEmpty(map.get("page")))
            PageHelper.startPage((int) map.get("page"), 15, sort);
        List<Project> projects = projectDao.findViewProject(map);
        return new PageInfo<>(projects);
    }

    public List<Project> findRealProject(Map<String, Object> map) {
        return projectDao.findRealProject(map);
    }

    public int appendProject(Project project, User user) {
        project.setUser(user);
        projectDao.insertProject(project);
        Pipe pipe = new Pipe();
        pipe.setNo(1);
        pipe.setOperator(project.getOperator());
        pipe.setPurposes("Structural Defects");
        pipe.setSlope(project.getSlope());
        if ("N".equals(project.getSlope()))
            pipe.setSloperef("N/A");
        pipe.setDate(project.getDate());
        pipe.setTime("00:00");
        pipe.setDivision("--");
        pipe.setAreacode("--");
        pipe.setCategory("Z");
        pipe.setCleaned("N");
        pipe.setWeather("1");
        pipe.setProject(project);
        pipeBiz.appendPipe(pipe);
        return project.getId();
    }

    public void importDepth(Project project, MultipartFile file) {
        DecimalFormat foramt1 = new DecimalFormat("#0.00");
        ExcelReader reader = AppUtils.getReader(file);
        if (StringUtils.isEmpty(reader))
            return; // 上传文件异常
        List<List<Object>> lists = reader.read(2, reader.getRowCount());
        Map<String, AppBean> map = new HashMap<>(32);
        for (int i = 0; lists != null && i < lists.size(); i++) {
            List<Object> list = lists.get(i);
            AppBean appBean = new AppBean();
            String mh = list.get(0) == null ? "" : list.get(0).toString();
            double value1 = list.get(1) == null ? 0.0 : (double) list.get(1);
            double value2 = list.get(2) == null ? 0.0 : (double) list.get(2);
            double value3 = list.get(3) == null ? 0.0 : (double) list.get(3);
            appBean.setDepth(value1 == 0.0 ? "--" : foramt1.format(value1));
            appBean.setClevel(value2 == 0.0 ? "--" : foramt1.format(value2));
            appBean.setIlevel(value3 == 0.0 ? "--" : foramt1.format(value3));
            map.put(mh, appBean);
        }
        for (Pipe pipe : pipeBiz.findListPipe(project)) {
            String smhNo = pipe.getSmanholeno();
            if (map.containsKey(smhNo)) {
                AppBean appBean = map.get(smhNo);
                pipe.setSdepth(appBean.getDepth());
                pipe.setScoverlevel(appBean.getClevel());
                pipe.setSinvertlevel(appBean.getIlevel());
                pipeBiz.updatePipe(pipe);
            }
            String fmhNo = pipe.getFmanholeno();
            if (map.containsKey(fmhNo)) {
                AppBean appBean = map.get(fmhNo);
                pipe.setFdepth(appBean.getDepth());
                pipe.setFcoverlevel(appBean.getClevel());
                pipe.setFinvertlevel(appBean.getIlevel());
                pipeBiz.updatePipe(pipe);
            }
        }
    }

    public void importImage(Project project, MultipartFile[] files) {
        String path = myfile + "/image/";
        List<Item> items = project.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String name = AppUtils.UUIDCode();
            AppUtils.moveFile(files[i], path + name + ".png");
            item.setPicture(name);
            itemBiz.updateItem(item);
        }
        sortImage(project);
    }

    public void removeImage(Project project) {
        map = AppUtils.getMap("project", project, "picture", "");
        List<Item> items = itemBiz.findListItem(map);
        for (int i = 0; items != null && i < items.size(); i++) {
            Item item = items.get(i);
            item.setPhoto("*插入图片");
            item.setPicture("");
            itemBiz.updateItem(item);
        }
    }

    public void sortImage(Project project) {
        DecimalFormat format = new DecimalFormat("#000");
        map = AppUtils.getMap("project", project, "picture", "");
        List<Item> items = itemBiz.findListItem(map);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.setPhoto(format.format(i + 1));
            itemBiz.updateItem(item);
        }
    }

}
