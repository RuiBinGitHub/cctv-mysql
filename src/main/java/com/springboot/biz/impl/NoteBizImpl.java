package com.springboot.biz.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.springboot.entity.Pipe;
import com.springboot.entity.User;
import org.springframework.stereotype.Service;

import com.springboot.biz.NoteBiz;
import com.springboot.dao.NoteDao;
import com.springboot.entity.Note;
import com.springboot.util.AppUtils;

@Service
public class NoteBizImpl implements NoteBiz {

    @Resource
    private NoteDao noteDao;
    private Map<String, Object> map = null;

    public void insertNote(Note note) {
        noteDao.insertNote(note);
    }

    public List<Note> findListNote(int id) {
        map = AppUtils.getMap("id", id);
        return noteDao.findListNote(map);
    }

    public void appendNote(String context, User user, Pipe pipe) {
        Note note = new Note();
        note.setContext(context);
        note.setDate(LocalDate.now().toString());
        note.setUser(user);
        note.setPipe(pipe);
        noteDao.insertNote(note);
    }

}
