package com.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.springboot.biz.NoteBiz;
import com.springboot.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.util.AppUtils;

@RestController
@RequestMapping(value = "/pipe")
public class PipeController {

    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private PipeBiz pipeBiz;
    @Resource
    private NoteBiz noteBiz;
    private Map<String, Object> map = null;

    /**
     * 插入数据
     */
    @RequestMapping(value = "/insert")
    public boolean insert(int id, int no) {
        User user = (User) AppUtils.getValue("user");
        Project project = projectBiz.findInfoProject(id, user);
        if (StringUtils.isEmpty(project))
            return false;
        Pipe pipe = new Pipe();
        Pipe temp = pipeBiz.findInfoPipe(no, user);
        if (!StringUtils.isEmpty(temp)) {
            pipe.setNo(temp.getNo() + 1);
            pipe.setWorkorder(temp.getWorkorder());
            pipe.setSlope(temp.getSlope());
            pipe.setSloperef(temp.getSloperef());
            pipe.setYearlaid(temp.getYearlaid());
            pipe.setDate(temp.getDate());
            pipe.setDistrict1(temp.getDistrict1());
            pipe.setDistrict2(temp.getDistrict2());
            pipe.setDistrict3(temp.getDistrict3());
            pipe.setRoadname(temp.getRoadname());
            pipe.setHousenum(temp.getHousenum());
            pipe.setBuilding(temp.getBuilding());
            pipe.setDivision(temp.getDivision());
            pipe.setAreacode(temp.getAreacode());
            pipe.setPipelength(temp.getPipelength());
        } else {
            pipe.setNo(1);
            pipe.setSlope(project.getSlope());
            pipe.setSloperef("N/A");
            pipe.setYearlaid("0");
            pipe.setDate(project.getDate());
            pipe.setDivision("--");
            pipe.setAreacode("--");
        }
        pipe.setOperator(project.getOperator());
        pipe.setPurposes("Structural defects");
        pipe.setCategory("Z");
        pipe.setCleaned("N");
        pipe.setWeather("1");
        pipe.setProject(project);
        pipeBiz.appendPipe(pipe);
        return true;
    }


    @RequestMapping(value = "/append")
    public boolean append(int id, int no) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Project project = projectBiz.findInfoProject(map);
        if (StringUtils.isEmpty(project))
            return false;
        Pipe pipe = new Pipe();
        Pipe temp = pipeBiz.findInfoPipe(no, null);
        if (!StringUtils.isEmpty(temp)) {
            pipe.setNo(temp.getNo() + 1);
            pipe.setWorkorder(temp.getWorkorder());
            pipe.setSlope(temp.getSlope());
            pipe.setSloperef(temp.getSloperef());
            pipe.setYearlaid(temp.getYearlaid());
            pipe.setDate(temp.getDate());
            pipe.setDistrict1(temp.getDistrict1());
            pipe.setDistrict2(temp.getDistrict2());
            pipe.setDistrict3(temp.getDistrict3());
            pipe.setRoadname(temp.getRoadname());
            pipe.setHousenum(temp.getHousenum());
            pipe.setBuilding(temp.getBuilding());
            pipe.setDivision(temp.getDivision());
            pipe.setAreacode(temp.getAreacode());
            pipe.setPipelength(temp.getPipelength());
        } else {
            pipe.setNo(1);
            pipe.setSlope(project.getSlope());
            pipe.setSloperef("N/A");
            pipe.setYearlaid("0");
            pipe.setDate(project.getDate());
            pipe.setDivision("--");
            pipe.setAreacode("--");
        }
        pipe.setOperator(project.getOperator());
        pipe.setPurposes("Structural defects");
        pipe.setCategory("Z");
        pipe.setCleaned("N");
        pipe.setWeather("1");
        pipe.setProject(project);
        pipeBiz.appendPipe(pipe);

        String context = "*.新增管道数据，管道编号：" + pipe.getNo();
        Note note = new Note();
        note.setContext(context);
        note.setDate(AppUtils.getDate(null));
        note.setUser(user);
        note.setPipe(pipe);
        noteBiz.insertNote(note);
        return true;
    }

    /**
     * 更新数据
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean update(Pipe pipe) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", pipe.getId(), "user", user);
        if (pipeBiz.findInfoPipe(map) != null)
            pipeBiz.replacPipe(pipe);
        return true;
    }

    /**
     * 更新数据
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public boolean commit(Pipe pipe) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", pipe.getId(), "company", user.getCompany());
        Pipe temp = pipeBiz.findInfoPipe(map);
        if (!StringUtils.isEmpty(temp)) {
            pipeBiz.checkPipe(temp, pipe, user);
            pipeBiz.replacPipe(pipe);
        }
        return true;
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "/delete")
    public boolean delete(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        Pipe pipe = pipeBiz.findInfoPipe(id, user);
        if (!StringUtils.isEmpty(pipe))
            pipeBiz.removePipe(pipe);
        return true;
    }

    /**
     * 移除数据
     */
    @RequestMapping(value = "/remove")
    public boolean remove(@RequestParam(defaultValue = "0") int id) {
        User user = (User) AppUtils.getValue("user");
        map = AppUtils.getMap("id", id, "company", user.getCompany());
        Pipe pipe = pipeBiz.findInfoPipe(map);
        if (!StringUtils.isEmpty(pipe))
            pipeBiz.removePipe(pipe);
        return true;
    }
}
