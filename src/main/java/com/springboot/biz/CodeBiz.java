package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Code;

public interface CodeBiz {

    Code findInfoCode(String standard, String code);

    List<Code> findListCode(Map<String, Object> map);

}
