package com.springboot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainApplications {

	public static void main(String[] args) {

		List<String> list = new ArrayList<String>();
		DecimalFormat foramt = new DecimalFormat("#000");
		list.add("002");
		list.add("003");
		list.add("04");
		list.add("12");
		list.add("14");
		list.add("41");
		list.add("141");
		list.add("414");
		list.sort((a, b) -> {
			return a.hashCode() - b.hashCode();
		});
		System.out.println(list);
		
		
	}

}
