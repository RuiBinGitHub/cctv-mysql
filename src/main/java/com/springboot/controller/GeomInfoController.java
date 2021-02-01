package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.springboot.entity.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.biz.GeomItemBiz;
import com.springboot.biz.GeomPipeBiz;
import com.springboot.biz.ItemBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.util.AppUtils;
import com.springboot.util.CameHelper;
import com.springboot.util.Computes;

@RestController
@RequestMapping(value = "/geominfo")
public class GeomInfoController {

    @Resource
    private Computes computes;
    @Resource
    private GeomItemBiz geomItemBiz;
    @Resource
    private GeomPipeBiz geomPipeBiz;
    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private ItemBiz itemBiz;
    private Map<String, Object> map = null;

    /**
     * 查看坐标录入
     */
    @RequestMapping(value = "/findinfo", method = RequestMethod.GET)
    public ModelAndView findInfo(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return view;
        GeomItem geomItem = geomItemBiz.findInfoGeomItem(project);
        if (StringUtils.isEmpty(geomItem))
            geomItemBiz.appendGeomItem(project);
        List<GeomPipe> geomPipes = geomPipeBiz.findListGeomPipe(project, user);
        view.setViewName("geominfo/findinfo");
        view.addObject("geomItem", geomItem);
        view.addObject("geomPipes", geomPipes);
        return view;
    }

    /**
     * 地图展示
     */
    @RequestMapping(value = "/showlist", method = RequestMethod.GET)
    public ModelAndView showList() {
        ModelAndView view = new ModelAndView("geominfo/showlist");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("company", user.getCompany());
        List<GeomItem> geomItems = geomItemBiz.findListGeomItem(map);
        List<GeomPipe> geomPipes = geomPipeBiz.findListGeomPipe(map);
        for (GeomPipe geomPipe : geomPipes) {
            Pipe pipe = geomPipe.getPipe();
            pipe.setItems(itemBiz.findListItem(pipe));
            computes.computePipe(pipe, "HKCCEC 2009");
            double grade = Math.max(pipe.getGrade()[0], pipe.getGrade()[3]);
            geomPipe.setGrade(grade);
        }
        view.addObject("geomItems", geomItems);
        view.addObject("geomPipes", geomPipes);
        return view;
    }

    /**
     * 管道对比
     */
    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public ModelAndView compare(@RequestParam(defaultValue = "0") int id) {
        ModelAndView view = new ModelAndView("common/failure");
        User user = (User) AppUtils.getValue("user");
        GeomPipe geomPipe = geomPipeBiz.findInfoGeomPipe(id, null);
        if (StringUtils.isEmpty(geomPipe))
            return view;
        String smhGradeA = geomPipe.getSmhGradeA();
        String fmhGradeA = geomPipe.getFmhGradeA();
        if (StringUtils.isEmpty(smhGradeA) || StringUtils.isEmpty(fmhGradeA)) {
            view.setViewName("common/message");
            view.addObject("text", "请输入该管道的坐标信息！");
            return view;
        }
        map = AppUtils.getMap("company", user.getCompany(), "smhGradeA", smhGradeA, "fmhGradeA", fmhGradeA);
        List<GeomPipe> geomPipes = geomPipeBiz.findListGeomPipe(map);
        for (GeomPipe geomTemp : geomPipes) {
            Pipe pipe = geomTemp.getPipe();
            pipe.setItems(itemBiz.findListItem(pipe));
        }
        view.setViewName("geominfo/compare");
        view.addObject("geomPipes", geomPipes);
        return view;
    }

    @RequestMapping(value = "/geompipe", method = RequestMethod.GET)
    public GeomPipe getGeomPipe(@RequestParam(defaultValue = "0") int id) {
        GeomPipe geomPipe = geomPipeBiz.findInfoGeomPipe(id, null);
        if (StringUtils.isEmpty(geomPipe))
            return null;
        geomPipe.getPipe().setItems(itemBiz.findListItem(geomPipe.getPipe()));
        Pipe pipe = computes.computePipe(geomPipe.getPipe(), "HKCCEC 2009");
        pipe.setMater(CameHelper.getCameText(pipe.getMater(), "mat"));
        pipe.setShape(CameHelper.getCameText(pipe.getShape(), "sha"));
        return geomPipe;
    }

    /**
     * 输入管道坐标
     */
    @RequestMapping(value = "/inputvalue", method = RequestMethod.POST)
    public boolean inputValue(GeomPipe geomPipe) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", geomPipe.getId(), "user", user);
        GeomPipe geomTemp = geomPipeBiz.findInfoGeomPipe(map);
        if (!StringUtils.isEmpty(geomTemp)) {
            geomTemp.setSmhx(geomPipe.getSmhx());
            geomTemp.setSmhy(geomPipe.getSmhy());
            geomTemp.setSmhh(geomPipe.getSmhh());
            geomTemp.setFmhx(geomPipe.getFmhx());
            geomTemp.setFmhy(geomPipe.getFmhy());
            geomTemp.setFmhh(geomPipe.getFmhh());
            geomPipeBiz.replaceGeomPipe(geomTemp);
        }
        return true;
    }

    /**
     * 导入管道坐标
     */
    @RequestMapping(value = "/importvalue", method = RequestMethod.POST)
    public ModelAndView importValue(int id, MultipartFile file) {
        ModelAndView view = new ModelAndView("common/success");
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "user", user);
        GeomItem geomItem = geomItemBiz.findInfoGeomItem(map);
        if (!StringUtils.isEmpty(geomItem))
            geomPipeBiz.importValue(geomItem, file);
        return view;
    }

    /**
     * 修改管道编码
     */
    @RequestMapping(value = "/updategrade", method = RequestMethod.POST)
    public boolean updateGrade(GeomPipe geomPipe) {
        User user = (User) AppUtils.getValue("user");
        GeomPipe geomTemp = geomPipeBiz.findInfoGeomPipe(geomPipe.getId(), user);
        if (!StringUtils.isEmpty(geomTemp)) {
            geomTemp.setSmhGradeA(geomPipe.getSmhGradeA());
            geomTemp.setSmhGradeB(geomPipe.getSmhGradeB());
            geomTemp.setFmhGradeA(geomPipe.getFmhGradeA());
            geomTemp.setFmhGradeB(geomPipe.getFmhGradeB());
            geomPipeBiz.updateGeomPipe(geomTemp);
        }
        return true;
    }

}
