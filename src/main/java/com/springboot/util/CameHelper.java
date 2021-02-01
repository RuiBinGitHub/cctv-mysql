package com.springboot.util;

import com.springboot.bean.Came;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CameHelper {

    private static List<Came> list = null;

    private static List<Came> initList() {
        if (!StringUtils.isEmpty(list))
            return list;
        list = new ArrayList<>(128);

        list.add(Came.getInstance("loc", "BL", "Bus Lane"));
        list.add(Came.getInstance("loc", "BLG", "Under Permament Building"));
        list.add(Came.getInstance("loc", "CR", "Cross Road"));
        list.add(Came.getInstance("loc", "DIF", "Difficult Access Area"));
        list.add(Came.getInstance("loc", "EX", "Expressway"));
        list.add(Came.getInstance("loc", "FLD", "Fields"));
        list.add(Came.getInstance("loc", "FWY", "Footway"));
        list.add(Came.getInstance("loc", "RD", "Road"));
        list.add(Came.getInstance("loc", "PD", "Other Pedestrian Area"));
        list.add(Came.getInstance("loc", "WWY", "Under a Waterway"));
        list.add(Came.getInstance("loc", "Z", "Other"));

        list.add(Came.getInstance("cat", "Z", "Z"));

        list.add(Came.getInstance("sha", "A", "Arched with Flat Bottom"));
        list.add(Came.getInstance("sha", "B", "Barrel e.g. Beer Barrel Shape"));
        list.add(Came.getInstance("sha", "C", "Circular"));
        list.add(Came.getInstance("sha", "E", "Egg Shape"));
        list.add(Came.getInstance("sha", "H", "Horseshoe"));
        list.add(Came.getInstance("sha", "O", "Oval"));
        list.add(Came.getInstance("sha", "K", "Kerb Block"));
        list.add(Came.getInstance("sha", "R", "Rectangular"));
        list.add(Came.getInstance("sha", "S", "Square"));
        list.add(Came.getInstance("sha", "T", "Trapezoidal"));
        list.add(Came.getInstance("sha", "U", "U-shaped with Flat Top"));
        list.add(Came.getInstance("sha", "Z", "Other"));

        list.add(Came.getInstance("mat", "AC", "Asbestos Cement"));
        list.add(Came.getInstance("mat", "BL", "Bitumen Lining"));
        list.add(Came.getInstance("mat", "BR", "Brick"));
        list.add(Came.getInstance("mat", "CI", "Cast Iron"));
        list.add(Came.getInstance("mat", "CL", "Cement Mortar Lining"));
        list.add(Came.getInstance("mat", "CO", "Concrete"));
        list.add(Came.getInstance("mat", "CS", "Concrete Segments"));
        list.add(Came.getInstance("mat", "DI", "Ductile Iron"));
        list.add(Came.getInstance("mat", "EP", "Epoxy"));
        list.add(Came.getInstance("mat", "FC", "Fibre Cement"));
        list.add(Came.getInstance("mat", "FRP", "Fibre Reinforced Plastics"));
        list.add(Came.getInstance("mat", "GI", "Galvanised Iron"));
        list.add(Came.getInstance("mat", "MAC", "Masonry in Regular Courses"));
        list.add(Came.getInstance("mat", "MAR", "Masonry in Randomly Coursed"));
        list.add(Came.getInstance("mat", "PVC", "Polyvinyl Chloride"));
        list.add(Came.getInstance("mat", "PE", "Polyethylene"));
        list.add(Came.getInstance("mat", "PF", "Pitch Fibre"));
        list.add(Came.getInstance("mat", "PP", "Polypropylene"));
        list.add(Came.getInstance("mat", "PS", "Polyester"));
        list.add(Came.getInstance("mat", "RC", "Reinforced Concrete"));
        list.add(Came.getInstance("mat", "SPC", "Sprayed Concrete"));
        list.add(Came.getInstance("mat", "ST", "Steel"));
        list.add(Came.getInstance("mat", "VC", "Vitrified Clay"));
        list.add(Came.getInstance("mat", "X", "Unidentified Material"));
        list.add(Came.getInstance("mat", "XI", "Unidentified Type of Iron or Steel"));
        list.add(Came.getInstance("mat", "XP", "Unidentified Type of Plastics"));
        list.add(Came.getInstance("mat", "Z", "Other"));

        list.add(Came.getInstance("lin", "CF", "Close Fit Lining"));
        list.add(Came.getInstance("lin", "CIP", "Cured In Place Lining"));
        list.add(Came.getInstance("lin", "CP", "Lining With Continuous Pipes"));
        list.add(Came.getInstance("lin", "DP", "Lining With Discrete Pipes"));
        list.add(Came.getInstance("lin", "M", "Lining Inserted During Manufacture"));
        list.add(Came.getInstance("lin", "N", "No Lining"));
        list.add(Came.getInstance("lin", "SEG", "Segmental Linings"));
        list.add(Came.getInstance("lin", "SP", "Sprayed Lining"));
        list.add(Came.getInstance("lin", "SW", "Spirally Wound Lining"));
        list.add(Came.getInstance("lin", "Z", "Other"));

        list.add(Came.getInstance("use", "F", "Foul"));
        list.add(Came.getInstance("use", "S", "Surface water"));
        list.add(Came.getInstance("use", "C", "Combined"));
        list.add(Came.getInstance("use", "T", "Trade effluent"));
        list.add(Came.getInstance("use", "W", "Watercourse"));
        list.add(Came.getInstance("use", "O", "Others"));
        list.add(Came.getInstance("use", "U", "Unknown"));

        list.add(Came.getInstance("dir", "U", "Upstream"));
        list.add(Came.getInstance("dir", "D", "Downstream"));

        list.add(Came.getInstance("wea", "1", "Dry"));
        list.add(Came.getInstance("wea", "2", "Heavy Rain"));
        list.add(Came.getInstance("wea", "3", "Light Rain"));
        list.add(Came.getInstance("wea", "4", "Showers"));

        // 返回数据列表
        return list;
    }

    public static String getCameName(String text, String model) {
        List<Came> list = initList();
        Optional<Came> optional = list.stream().filter(came -> came.getModel().equals(model) && came.getText().equals(text)).findFirst();
        if (optional.isPresent())
            return optional.get().getName();
        return "";
    }

    public static String getCameText(String name, String model) {
        List<Came> list = initList();
        Optional<Came> optional = list.stream().filter(came -> came.getModel().equals(model) && came.getName().equals(name)).findFirst();
        if (optional.isPresent())
            return optional.get().getText();
        return "";
    }

}
