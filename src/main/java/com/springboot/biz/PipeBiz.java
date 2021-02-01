package com.springboot.biz;

import java.util.List;
import java.util.Map;

import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;

public interface PipeBiz {

    void insertPipe(Pipe pipe);

    void updatePipe(Pipe pipe);

    void deletePipe(Pipe pipe);

    Pipe findInfoPipe(int id, User user);

    Pipe findInfoPipe(Map<String, Object> map);

    List<Pipe> findListPipe(Project project);

    List<Pipe> findListPipe(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void appendPipe(Pipe pipe);

    void replacPipe(Pipe pipe);

    void removePipe(Pipe pipe);

    void checkPipe(Pipe pipe1, Pipe pipe2, User user);
}
