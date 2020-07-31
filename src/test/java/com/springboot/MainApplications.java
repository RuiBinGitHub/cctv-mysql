package com.springboot;

import java.util.ArrayList;
import java.util.List;

import com.springboot.entity.Pipe;


public class MainApplications {

	public static void main(String[] args) {
		
		List<Pipe> list1 = new ArrayList<Pipe>();
		List<Pipe> list2 = new ArrayList<Pipe>();
		Pipe pipe1 = new Pipe();
		Pipe pipe2 = new Pipe();
		Pipe pipe3 = new Pipe();
		pipe1.setNo(1);
		pipe2.setNo(2);
		pipe3.setNo(3);
		list1.add(pipe1);
		list1.add(pipe2);
		list1.add(pipe3);
		System.out.println(list1.get(0).getNo());
		System.out.println(list2.get(0).getNo());
	}

}
