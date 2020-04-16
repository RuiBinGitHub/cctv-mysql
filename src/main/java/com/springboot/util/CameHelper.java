package com.springboot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.springboot.bean.Came;

public class CameHelper {

	private static Map<String, List<Came>> map = null;

	private static Map<String, List<Came>> initMap() {
		if (!StringUtils.isEmpty(map))
			return map;
		map = new HashMap<String, List<Came>>();
		
		List<Came> loclist = new ArrayList<Came>();
		loclist.add(new Came("BL", "Bus Lane"));
		loclist.add(new Came("BLG", "Under Permament Building"));
		loclist.add(new Came("CR", "Cross Road"));
		loclist.add(new Came("DIF", "Difficult Access Area"));
		loclist.add(new Came("EX", "Expressway"));
		loclist.add(new Came("FLD", "Fields"));
		loclist.add(new Came("FWY", "Footway"));
		loclist.add(new Came("RD", "Road"));
		loclist.add(new Came("PD", "Other Pedestrian Area"));
		loclist.add(new Came("WWY", "Under a Waterway"));
		loclist.add(new Came("Z", "Other"));

		List<Came> catlist = new ArrayList<Came>();
		catlist.add(new Came("Z", "Z"));

		List<Came> shalist = new ArrayList<Came>();
		shalist.add(new Came("A", "Arched with Flat Bottom"));
		shalist.add(new Came("B", "Barrel e.g. Beer Barrel Shape"));
		shalist.add(new Came("C", "Circular"));
		shalist.add(new Came("E", "Egg Shape"));
		shalist.add(new Came("H", "Horseshoe"));
		shalist.add(new Came("O", "Oval"));
		shalist.add(new Came("K", "Kerb Block"));
		shalist.add(new Came("R", "Rectangular"));
		shalist.add(new Came("S", "Square"));
		shalist.add(new Came("T", "Trapezoidal"));
		shalist.add(new Came("U", "U-shaped with Flat Top"));
		shalist.add(new Came("Z", "Other"));

		List<Came> matlist = new ArrayList<Came>();
		matlist.add(new Came("AC", "Asbestos Cement"));
		matlist.add(new Came("BL", "Bitumen Lining"));
		matlist.add(new Came("BR", "Brick"));
		matlist.add(new Came("CI", "Cast Iron"));
		matlist.add(new Came("CL", "Cement Mortar Lining"));
		matlist.add(new Came("CO", "Concrete"));
		matlist.add(new Came("CS", "Concrete Segments"));
		matlist.add(new Came("DI", "Ductile Iron"));
		matlist.add(new Came("EP", "Epoxy"));
		matlist.add(new Came("FC", "Fibre Cement"));
		matlist.add(new Came("FRP", "Fibre Reinforced Plastics"));
		matlist.add(new Came("GI", "Galvanised Iron"));
		matlist.add(new Came("MAC", "Masonry in Regular Courses"));
		matlist.add(new Came("MAR", "Masonry in Randomly Coursed"));
		matlist.add(new Came("PVC", "Polyvinyl Chloride"));
		matlist.add(new Came("PE", "Polyethylene"));
		matlist.add(new Came("PF", "Pitch Fibre"));
		matlist.add(new Came("PP", "Polypropylene"));
		matlist.add(new Came("PS", "Polyester"));
		matlist.add(new Came("RC", "Reinforced Concrete"));
		matlist.add(new Came("SPC", "Sprayed Concrete"));
		matlist.add(new Came("ST", "Steel"));
		matlist.add(new Came("VC", "Vitrified Clay"));
		matlist.add(new Came("X", "Unidentified Material"));
		matlist.add(new Came("XI", "Unidentified Type of Iron or Steel"));
		matlist.add(new Came("XP", "Unidentified Type of Plastics"));
		matlist.add(new Came("Z", "Other"));

		List<Came> linlist = new ArrayList<Came>();
		linlist.add(new Came("CF", "Close Fit Lining"));
		linlist.add(new Came("CIP", "Cured In Place Lining"));
		linlist.add(new Came("CP", "Lining With Continuous Pipes"));
		linlist.add(new Came("DP", "Lining With Discrete Pipes"));
		linlist.add(new Came("M", "Lining Inserted During Manufacture"));
		linlist.add(new Came("N", "No Lining"));
		linlist.add(new Came("SEG", "Segmental Linings"));
		linlist.add(new Came("SP", "Sprayed Lining"));
		linlist.add(new Came("SW", "Spirally Wound Lining"));
		linlist.add(new Came("Z", "Other"));

		List<Came> uselist = new ArrayList<Came>();
		uselist.add(new Came("F", "Foul"));
		uselist.add(new Came("S", "Surface water"));
		uselist.add(new Came("C", "Combined"));
		uselist.add(new Came("T", "Trade effluent"));
		uselist.add(new Came("W", "Watercourse"));
		uselist.add(new Came("O", "Others"));
		uselist.add(new Came("U", "Unknown"));

		List<Came> dirlist = new ArrayList<Came>();
		dirlist.add(new Came("U", "Upstream"));
		dirlist.add(new Came("D", "Downstream"));

		List<Came> wealist = new ArrayList<Came>();
		wealist.add(new Came("1", "Dry"));
		wealist.add(new Came("2", "Heavy Rain"));
		wealist.add(new Came("3", "Light Rain"));
		wealist.add(new Came("4", "Showers"));

		map.put("loc", loclist);
		map.put("cat", catlist);
		map.put("sha", shalist);
		map.put("mat", matlist);
		map.put("lin", linlist);
		map.put("use", uselist);
		map.put("dir", dirlist);
		map.put("wea", wealist);
		return map;
	}

	public static String getCameName(String text, String model) {
		Map<String, List<Came>> map = initMap();
		List<Came> list = map.get(model);
		for (int i = 0; list != null && i < list.size(); i++) {
			if (list.get(i).getText().equals(text))
				return list.get(i).getName();
		}
		return "";
	}

	public static String getCameText(String name, String model) {
		Map<String, List<Came>> map = initMap();
		List<Came> list = map.get(model);
		for (int i = 0; list != null && i < list.size(); i++) {
			if (list.get(i).getName().equals(name))
				return list.get(i).getText();
		}
		return "";
	}

}
