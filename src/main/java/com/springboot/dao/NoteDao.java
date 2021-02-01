package com.springboot.dao;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Note;

public interface NoteDao {

	void insertNote(Note note);

	List<Note> findListNote(Map<String, Object> map);

}
