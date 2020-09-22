package com.springboot;


public class MainApplications {

	public static void main(String[] args) {
		StringBuffer text1 = new StringBuffer();
		StringBuffer text2 = new StringBuffer();
		text2.append("123");
		text1.append("你好吗？" + text2 + ";");
		System.out.println(text1.toString());
	}

}
