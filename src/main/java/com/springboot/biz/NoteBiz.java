package com.springboot.biz;

import java.util.List;

import com.springboot.entity.Note;
import com.springboot.entity.Pipe;
import com.springboot.entity.User;

public interface NoteBiz {

	void insertNote(Note note);

	List<Note> findListNote(int id);

	void appendNote(String context, User user, Pipe pipe);

}
