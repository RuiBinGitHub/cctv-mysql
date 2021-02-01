package com.springboot.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.NoteBiz;
import com.springboot.entity.Note;

@RestController
@RequestMapping(value = "/note")
public class NoteController {

    @Resource
    private NoteBiz noteBiz;

    @RequestMapping(value = "/findlist")
    public List<Note> findList(@RequestParam(defaultValue = "0") int id) {
        return noteBiz.findListNote(id);
    }

}
