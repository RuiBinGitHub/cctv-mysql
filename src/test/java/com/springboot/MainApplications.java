package com.springboot;

import java.util.ArrayList;
import java.util.List;

public class MainApplications {

	public static void main(String[] args) {
//		Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");  //正则表达式
//		Matcher m = p.matcher("wanhui@email111@qq..com");
//		System.out.println(m.matches());
//		System.out.println(Math.pow(2, 8));
		
		List<String> list = new ArrayList<String>();
//		list.add(0, "aa");
//		list.add(1, "bb");
//		list.add(2, "cc");
//		list.add(4, "dd");
//		System.out.println(list);
		String a = "abcddfdfgf";
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i< a.length(); i++) {
			buffer.append(a.charAt(i));
			if (buffer.length() == 2) {
				list.add(buffer.toString());
				buffer = new StringBuffer("");
			}
		}
		System.out.println(list);
	}
	
	
}
