package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.biz.CodeBiz;
import com.springboot.dao.CodeDao;
import com.springboot.entity.Code;
import com.springboot.util.AppUtils;

@Service
public class CodeBizImpl implements CodeBiz {

    @Resource
    private CodeDao codeDao;

    public Code findInfoCode(String standard, String code) {
        String option = standard.contains("H") ? "code1" : "code2";
        Map<String, Object> map = AppUtils.getMap(option, code);
        return codeDao.findInfoCode(map);
    }

    public List<Code> findListCode(Map<String, Object> map) {
        return codeDao.findListCode(map);
    }

}
