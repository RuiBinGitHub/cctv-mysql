package com.springboot.util;

import com.springboot.biz.CodeBiz;
import com.springboot.entity.Code;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class Computes {

    @Resource
    private CodeBiz codeBiz;

    public Pipe computePipe(Pipe pipe, String standard) {
        int[] surve = new int[20];
        int v = getYearValue(pipe.getSlope(), pipe.getYearlaid());
        String[] slist = new String[10];
        String[] clist = new String[10];

        for (Item item : pipe.getItems())
            computeItem(item, standard); // 计算分数和等级
        List<Item> items = pipe.getItems();
        for (int i = 0; items != null && i < items.size(); i++) {
            Item item = items.get(i);
            double peakscore = item.getScore(); // 记录管道当前分数

            if (item.getCont() != null && item.getCont().contains("S")) {
                int index = AppUtils.getInt(item.getCont().substring(1, 2));
                slist[index] = item.getDist();
            }
            if (item.getCont() != null && item.getCont().contains("C")) {
                int index = AppUtils.getInt(item.getCont().substring(1, 2));
                if (index < slist.length && slist[index] != null) {
                    long lengths = Math.round(AppUtils.getDouble(item.getDist()) - AppUtils.getDouble(slist[index]));
                    if (lengths > 1)
                        item.setScore(item.getScore() * lengths);
                }
                clist[index] = item.getDist();
            }
            if (item.getCont() != null && item.getCont().contains("F")) {
                int index = AppUtils.getInt(item.getCont().substring(1, 2));
                if (index < clist.length && clist[index] != null) {
                    long lengths = Math.round(AppUtils.getDouble(item.getDist()) - AppUtils.getDouble(clist[index]));
                    if (lengths > 1)
                        item.setScore(item.getScore() * lengths);
                }
                if (index < slist.length && slist[index] != null) {
                    long lengths = Math.round(AppUtils.getDouble(item.getDist()) - AppUtils.getDouble(slist[index]));
                    if (lengths > 1)
                        item.setScore(item.getScore() * lengths);
                }
            }

            double dist = AppUtils.getDouble(item.getDist());
            if ("SCG".equals(item.getType1())) {
                for (int j = i + 1; j < items.size(); j++) {
                    Item temp = items.get(j);
                    if (AppUtils.getDouble(temp.getDist()) - dist <= 1) {
                        if ("SCG".equals(temp.getType1()))
                            peakscore += temp.getScore(); // 计算SCG分数最大值
                    } else
                        break;
                }
                if (pipe.getScore()[0] < peakscore)
                    pipe.getScore()[0] = peakscore;
                pipe.getScore()[2] += item.getScore(); // 计算SCG总分
            }

            if ("ICG".equals(item.getType1())) {
                for (int j = i + 1; j < items.size(); j++) {
                    Item temp = items.get(j);
                    if (AppUtils.getDouble(temp.getDist()) - dist <= 1) {
                        if ("ICG".equals(temp.getType1()))
                            peakscore += temp.getScore(); // 计算ICG分数最大值
                    } else
                        break;
                }
                if (pipe.getScore()[3] < peakscore)
                    pipe.getScore()[3] = peakscore;
                pipe.getScore()[5] += item.getScore(); // 计算ICG总分
            }

            if (item.getGrade() == 4 || item.getGrade() == 5)
                surve[0]++;
            if ("Cracked".equals(item.getType2()))
                surve[1]++;
            else if ("Fractured".equals(item.getType2()))
                surve[2]++;
            else if ("Broken".equals(item.getType2()))
                surve[3]++;
            else if ("Deformed".equals(item.getType2()))
                surve[4]++;
            else if ("Collapsed".equals(item.getType2()))
                surve[5]++;
            else if ("Hole".equals(item.getType2()))
                surve[6]++;
            else if ("Surface Spalling/Wear".equals(item.getType2()))
                surve[7]++;
            else if ("Joint Displaced".equals(item.getType2()))
                surve[8]++;
            else if ("Open Joint".equals(item.getType2()))
                surve[9]++;
            else if ("Roots".equals(item.getType2()))
                surve[10]++;
            else if ("Infiltration".equals(item.getType2()))
                surve[11]++;
            else if ("Encrustation".equals(item.getType2()))
                surve[12]++;
            else if ("Silt".equals(item.getType2()))
                surve[13]++;
            else if ("Grease".equals(item.getType2()))
                surve[14]++;
            else if ("Obstruction".equals(item.getType2()))
                surve[15]++;
            else if ("Water Line".equals(item.getType2()))
                surve[16]++;
            else if ("Line".equals(item.getType2()))
                surve[17]++;
            else if ("Survey Abandoned".equals(item.getType2()))
                surve[18]++;
            else if ("Camera Under Water".equals(item.getType2()))
                surve[19]++;
        }
        pipe.setSurve(surve);
        pipe.setItems(items);

        // 如果管道长度为0，则设置为1
        double testlength = Math.max(1, pipe.getTestlength());
        // 计算管道分数
        pipe.getScore()[1] = pipe.getScore()[2] / testlength;
        pipe.getScore()[4] = pipe.getScore()[5] / testlength;
        pipe.getScore()[6] = pipe.getScore()[3];
        pipe.getScore()[7] = pipe.getScore()[4];
        pipe.getScore()[8] = pipe.getScore()[5];

        // 计算SCG管道等级
        if (pipe.getScore()[0] < 1.0) // 计算SCG最大值
            pipe.getGrade()[0] = 1;
        else if (pipe.getScore()[0] >= 1.0 && pipe.getScore()[0] < 2.0)
            pipe.getGrade()[0] = 2;
        else if (pipe.getScore()[0] >= 2.0 && pipe.getScore()[0] < 5.0)
            pipe.getGrade()[0] = 3;
        else if (pipe.getScore()[0] >= 5.0 && pipe.getScore()[0] < 10.0)
            pipe.getGrade()[0] = 4;
        else if (pipe.getScore()[0] >= 10)
            pipe.getGrade()[0] = 5;

        if (pipe.getScore()[1] < 0.5) // 计算SCG平均值
            pipe.getGrade()[1] = 1;
        else if (pipe.getScore()[1] >= 0.5 && pipe.getScore()[1] < 1.0)
            pipe.getGrade()[1] = 2;
        else if (pipe.getScore()[1] >= 1.0 && pipe.getScore()[1] < 2.5)
            pipe.getGrade()[1] = 3;
        else if (pipe.getScore()[1] >= 2.5 && pipe.getScore()[1] < 5.0)
            pipe.getGrade()[1] = 4;
        else if (pipe.getScore()[1] >= 5.0)
            pipe.getGrade()[1] = 5;

        if (pipe.getScore()[2] < 1) // 计算SCG总值
            pipe.getGrade()[2] = 1;
        else if (pipe.getScore()[2] >= 1 && pipe.getScore()[2] < 2)
            pipe.getGrade()[2] = 2;
        else if (pipe.getScore()[2] >= 2 && pipe.getScore()[2] < 5)
            pipe.getGrade()[2] = 3;
        else if (pipe.getScore()[2] >= 5 && pipe.getScore()[2] < 10)
            pipe.getGrade()[2] = 4;
        else if (pipe.getScore()[2] >= 10)
            pipe.getGrade()[2] = 5;

        // 计算ICG管道等级
        if (pipe.getScore()[3] < 10) // 计算ICG最大值
            pipe.getGrade()[3] = 1;
        else if (pipe.getScore()[3] >= 10 && pipe.getScore()[3] < 40)
            pipe.getGrade()[3] = 2;
        else if (pipe.getScore()[3] >= 40 && pipe.getScore()[3] < 80)
            pipe.getGrade()[3] = 3;
        else if (pipe.getScore()[3] >= 80 && pipe.getScore()[3] < 165)
            pipe.getGrade()[3] = 4;
        else if (pipe.getScore()[3] >= 165)
            pipe.getGrade()[3] = 5;

        if (pipe.getScore()[4] < 5) // 计算ICG平均值
            pipe.getGrade()[4] = 1;
        else if (pipe.getScore()[4] >= 5 && pipe.getScore()[4] < 20)
            pipe.getGrade()[4] = 2;
        else if (pipe.getScore()[4] >= 20 && pipe.getScore()[4] < 40)
            pipe.getGrade()[4] = 3;
        else if (pipe.getScore()[4] >= 40 && pipe.getScore()[4] < 82)
            pipe.getGrade()[4] = 4;
        else if (pipe.getScore()[4] >= 82)
            pipe.getGrade()[4] = 5;

        if (pipe.getScore()[5] < 10) // 计算ICG总值
            pipe.getGrade()[5] = 1;
        else if (pipe.getScore()[5] >= 10 && pipe.getScore()[5] < 40)
            pipe.getGrade()[5] = 2;
        else if (pipe.getScore()[5] >= 40 && pipe.getScore()[5] < 80)
            pipe.getGrade()[5] = 3;
        else if (pipe.getScore()[5] >= 80 && pipe.getScore()[5] < 165)
            pipe.getGrade()[5] = 4;
        else if (pipe.getScore()[5] >= 165)
            pipe.getGrade()[5] = 5;

        // 计算SPG等级
        for (int i = 0; i < 3; i++)
            pipe.getGrade()[6 + i] = Math.min(5, pipe.getGrade()[3 + i] + v);

        return pipe;
    }

    public void computeItem(Item item, String standard) {
        Code code = codeBiz.findInfoCode(standard, item.getCode());
        int percent = AppUtils.getInt(item.getPercent());
        int lengths = AppUtils.getInt(item.getLengths());
        setItemValue(item, code.getScore(), code.getGrade());
        item.setDepict(code.getExplain2());
        item.setType1(code.getType1());
        item.setType2(code.getType2());
        item.setType3(code.getType3());

        String list1 = "DAF, DE F, DAG, DE G, DAZ, DE Z, DEC, DE C, DES, DE S";
        String list2 = "DER, DE R, DEZ, DE X";
        String list3 = "DZ, DH, D H, DV, D V";
        if (list1.contains(item.getCode())) {
            if (percent > 5 && percent <= 25)
                setItemValue(item, "1.0", "2");
            else if (percent > 25 && percent <= 50)
                setItemValue(item, "4.0", "3");
            else if (percent > 50 && percent <= 75)
                setItemValue(item, "8.0", "4");
            else if (percent > 75)
                setItemValue(item, "10.0", "5");
        } else if (list2.contains(item.getCode())) {
            if (percent > 5 && percent <= 25)
                setItemValue(item, "2.0", "2");
            else if (percent > 25 && percent <= 50)
                setItemValue(item, "5.0", "3");
            else if (percent > 50 && percent <= 75)
                setItemValue(item, "8.0", "4");
            else if (percent > 75)
                setItemValue(item, "10.0", "5");
        } else if (list3.contains(item.getCode())) {
            if (percent > 5 && percent < 25)
                setItemValue(item, "80.0", "4");
            else if (percent >= 25)
                setItemValue(item, "165.0", "5");
        } else if ("RM".equals(item.getCode()) || "R M".equals(item.getCode())) {
            if (percent > 5 && percent <= 25)
                setItemValue(item, "5.0", "4");
            else if (percent > 25 && percent <= 50)
                setItemValue(item, "10.0", "5");
            else if (percent > 50 && percent <= 75)
                setItemValue(item, "15.0", "5");
            else if (percent > 75)
                setItemValue(item, "20.0", "5");
        } else if ("DB".equals(item.getCode())) {
            if (percent != 0)
                item.setDepict("Displaced Bricks #1");
            if (percent > 0 && percent < 50)
                setItemValue(item, "80.0", "4");
            else
                setItemValue(item, "165.0", "5");
        } else if ("MB".equals(item.getCode())) {
            if (percent != 0)
                item.setDepict("Missing Bricks #1");
            if (percent < 50)
                setItemValue(item, "120.0", "4");
            else
                setItemValue(item, "165.0", "5");
        } else if ("CXI".equals(item.getCode()) || "CX I".equals(item.getCode()) && percent > 75)
            setItemValue(item, "165.0", "5");

        item.setDepict(item.getDepict().replace("#1", percent + "%"));
        item.setDepict(item.getDepict().replace("#2", lengths + "mm"));

        if (item.getClockAt() != null && item.getClockAt().length() == 2)
            item.setDepict(item.getDepict() + ",at " + item.getClockAt() + " o'clock");
        if (item.getClockTo() != null && item.getClockAt().length() == 4) {
            String s1 = item.getClockTo().substring(0, 2);
            String s2 = item.getClockTo().substring(2, 4);
            item.setDepict(item.getDepict() + ",from " + s1 + " to " + s2 + " o'clock");
        }
        if (item.getRemarks() != null && !item.getRemarks().equals(""))
            item.setDepict(item.getDepict() + ",Remark: " + item.getRemarks());
        if (item.getCont() != null && item.getCont().contains("S"))
            item.setDepict(item.getDepict() + ",Start");
        if (item.getCont() != null && item.getCont().contains("F"))
            item.setDepict(item.getDepict() + ",Finish");
    }

    private void setItemValue(Item item, String score, String grade) {
        item.setScore(AppUtils.getDouble(score));
        item.setGrade(AppUtils.getDouble(grade));
    }

    private int getYearValue(String slope, String year) {
        int itemp = AppUtils.getInt(year);
        if ("N".equals(slope) && itemp < 30)
            return 0;
        else if ("N".equals(slope) && itemp <= 50)
            return 1;
        else if ("N".equals(slope))
            return 2;
        else if ("Y".equals(slope))
            return itemp < 30 ? 1 : 2;
        return 0;
    }

}

