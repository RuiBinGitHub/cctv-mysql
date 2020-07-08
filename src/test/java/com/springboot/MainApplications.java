package com.springboot;

public class MainApplications {

	public static void main(String[] args) {

		int i, j;
		i = 100;
		while (i > 0) {
			j = i * 2;
			System.out.println("xxx" + j);
		}
	}

}

class A {
	public A(int x) {
		System.out.println(x);
	}

	public A(int x, int y) {
		this(x);
	}

}