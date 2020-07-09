package com.springboot.util;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.springboot.biz.CodeBiz;
import com.springboot.biz.ItemBiz;
import com.springboot.entity.Code;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;

@Component
public class Computes {

	@Resource
	private ItemBiz itemBiz;
	@Resource
	private CodeBiz codeBiz;
	private Map<String, Object> map = null;

	public Pipe computePipe(Pipe pipe, String type) {
		int surve[] = new int[20];
		int v = getValue(pipe.getSlope(), pipe.getYearlaid());
		List<Item> items = pipe.getItems();
		for (int i = 0; items != null && i < items.size(); i++)
			computeItem(items.get(i), type); // 计算分数和等级
		String[] slist = new String[10];
		String[] clist = new String[10];
		for (int i = 0; items != null && i < items.size(); i++) {
			Item item = items.get(i);
			if (item.getCont() != null && item.getCont().indexOf("S") != -1) {
				int index = Integer.valueOf(item.getCont().substring(1, 2));
				slist[index] = item.getDist();
			} else if (item.getCont() != null && item.getCont().indexOf("C") != -1) {
				int index = Integer.valueOf(item.getCont().substring(1, 2));
				if (slist[index] != null) {
					int lengths = (int) (Double.valueOf(item.getDist()) - Double.valueOf(slist[index]));
					if (lengths > 1)
						item.setScore(item.getScore() * lengths);
				}
				clist[index] = item.getDist();
			} else if (item.getCont() != null && item.getCont().indexOf("F") != -1) {
				int index = Integer.valueOf(item.getCont().substring(1, 2));
				if (clist[index] != null) {
					int lengths = (int) (Double.valueOf(item.getDist()) - Double.valueOf(clist[index]));
					if (lengths > 1)
						item.setScore(item.getScore() * lengths);
				}
				if (slist[index] != null) {
					int lengths = (int) (Double.valueOf(item.getDist()) - Double.valueOf(slist[index]));
					if (lengths > 1)
						item.setScore(item.getScore() * lengths);
				}
			}

			double peakscore = item.getScore(); // 记录管道分数
			if ("SCG".equals(item.getType1())) {
				pipe.getScore()[2] += item.getScore(); // 计算SCG总分
				for (int j = i + 1; j < items.size(); j++) {
					Item temp = items.get(j);
					double dist1 = Double.valueOf(temp.getDist());
					double dist2 = Double.valueOf(item.getDist());
					if (dist1 - dist2 <= 1 && "SCG".equals(temp.getType1()))
						peakscore += temp.getScore(); // 计算SCG最大值
					else
						break;
				}
				if (pipe.getScore()[0] < peakscore)
					pipe.getScore()[0] = peakscore;
			} else if ("ICG".equals(item.getType1())) { // 计算ICG总分
				pipe.getScore()[5] += item.getScore();
				for (int j = i + 1; j < items.size(); j++) {
					Item temp = items.get(j);
					double dist1 = Double.valueOf(temp.getDist());
					double dist2 = Double.valueOf(item.getDist());
					if (dist1 - dist2 <= 1 && "ICG".equals(temp.getType1()))
						peakscore += temp.getScore(); // 计算SCG最大值
					else
						break;
				}
				if (pipe.getScore()[3] < peakscore)
					pipe.getScore()[3] = peakscore;
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

		double testlength = pipe.getTestlength();
		if (pipe.getTestlength() < 1) // 如果管道长度为设置,则设置为1
			testlength = 1;
		/** 计算管道分数 */
		pipe.getScore()[1] = pipe.getScore()[2] / testlength;
		pipe.getScore()[4] = pipe.getScore()[5] / testlength;
		pipe.getScore()[6] = pipe.getScore()[3];
		pipe.getScore()[7] = pipe.getScore()[4];
		pipe.getScore()[8] = pipe.getScore()[5];

		/** 计算管道等级 */
		if (pipe.getScore()[0] < 1) // 计算SCG最大值
			pipe.getGrade()[0] = 1;
		else if (pipe.getScore()[0] >= 1 && pipe.getScore()[0] < 2)
			pipe.getGrade()[0] = 2;
		else if (pipe.getScore()[0] >= 2 && pipe.getScore()[0] < 5)
			pipe.getGrade()[0] = 3;
		else if (pipe.getScore()[0] >= 5 && pipe.getScore()[0] < 10)
			pipe.getGrade()[0] = 4;
		else if (pipe.getScore()[0] >= 10)
			pipe.getGrade()[0] = 5;

		if (pipe.getScore()[1] < 0.5) // 计算SCG平均值
			pipe.getGrade()[1] = 1;
		else if (pipe.getScore()[1] >= 0.5 && pipe.getScore()[1] < 1)
			pipe.getGrade()[1] = 2;
		else if (pipe.getScore()[1] >= 1.0 && pipe.getScore()[1] < 2.5)
			pipe.getGrade()[1] = 3;
		else if (pipe.getScore()[1] >= 2.5 && pipe.getScore()[1] < 5.0)
			pipe.getGrade()[1] = 4;
		else if (pipe.getScore()[1] >= 5)
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

		// 计算SPG
		for (int i = 0; i < 3; i++) {
			pipe.getGrade()[6 + i] = pipe.getGrade()[3 + i] + v;
			if (pipe.getGrade()[6 + i] > 5)
				pipe.getGrade()[6 + i] = 5;
		}
		return pipe;
	}

	public Item computeItem(Item item, String type) {
		if (type.indexOf("H") != -1)
			map = AppHelper.getMap("hkccec", item.getCode());
		if (type.indexOf("M") != -1)
			map = AppHelper.getMap("mscc", item.getCode());
		Code code = codeBiz.findInfoCode(map);

		int percent = AppHelper.getInt(item.getPercent());
		int lengths = AppHelper.getInt(item.getLengths());
		setItemValue(item, code.getScore(), code.getGrade());
		item.setDepict(code.getExplain2());
		item.setType1(code.getType1());
		item.setType2(code.getType2());
		item.setType3(code.getType3());

		if ("HKCCEC 2009".equals(type)) {
			String value = code.getHkccec();
			if ("DAF".equals(value) || "DAG".equals(value) || "DEC".equals(value) || "DES".equals(value)) {
				if (percent > 5 && percent <= 25)
					setItemValue(item, "1.0", "2");
				else if (percent > 25 && percent <= 50)
					setItemValue(item, "4.0", "3");
				else if (percent > 50 && percent <= 75)
					setItemValue(item, "8.0", "4");
				else if (percent > 75)
					setItemValue(item, "10.0", "5");
			} else if ("DER".equals(value) || "DEZ".equals(value)) {
				if (percent > 5 && percent <= 25)
					setItemValue(item, "2.0", "2");
				else if (percent > 25 && percent <= 50)
					setItemValue(item, "5.0", "3");
				else if (percent > 50 && percent <= 75)
					setItemValue(item, "8.0", "4");
				else if (percent > 75)
					setItemValue(item, "10.0", "5");
			} else if ("RM".equals(value)) {
				if (percent > 5 && percent <= 25)
					setItemValue(item, "5.0", "4");
				else if (percent > 25 && percent <= 50)
					setItemValue(item, "10.0", "5");
				else if (percent > 50 && percent <= 75)
					setItemValue(item, "15.0", "5");
				else if (percent > 75)
					setItemValue(item, "20.0", "5");
			} else if ("DZ".equals(value) || "DH".equals(value) || "DV".equals(value)) {
				if (percent > 5 && percent <= 25)
					setItemValue(item, "80.0", "4");
				else if (percent > 25 && percent <= 50)
					setItemValue(item, "165.0", "5");
			} else if ("DB".equals(value)) {
				if (percent == 0)
					setItemValue(item, "40.0", "3");
				else if (percent > 0 && percent < 50)
					setItemValue(item, "80.0", "4");
				else
					setItemValue(item, "165.0", "5");
			} else if ("MB".equals(value)) {
				if (percent != 0) {
					item.setDepict("Missing Bricks #1");
					if (percent < 50)
						setItemValue(item, "120.0", "4");
					else if (percent > 50)
						setItemValue(item, "165.0", "5");
				}
			} else if ("CXI".equals(value) && percent > 75)
				setItemValue(item, "165.0", "5");
		}

		item.setDepict(item.getDepict().replace("#1", percent + "%"));
		item.setDepict(item.getDepict().replace("#2", lengths + "mm"));
		if (item.getClockAt() != null && !item.getClockAt().equals(""))
			item.setDepict(item.getDepict() + ",at " + item.getClockAt() + " o'clock");
		if (item.getClockTo() != null && !item.getClockTo().equals("")) {
			String s1 = item.getClockTo().substring(0, 2);
			String s2 = item.getClockTo().substring(2, 4);
			item.setDepict(item.getDepict() + ",from " + s1 + " to " + s2 + " o'clock");
		}
		if (item.getRemarks() != null && !item.getRemarks().equals(""))
			item.setDepict(item.getDepict() + ",Remark: " + item.getRemarks());
		if (item.getCont() != null && item.getCont().indexOf("S") != -1)
			item.setDepict(item.getDepict() + ",Start");
		if (item.getCont() != null && item.getCont().indexOf("F") != -1)
			item.setDepict(item.getDepict() + ",Finish");
		return item;
	}

	private void setItemValue(Item item, String score, String grade) {
		item.setScore(AppHelper.getDoule(score));
		item.setGrade(AppHelper.getDoule(grade));
	}

	private int getValue(String slope, String year) {
		int itemp = AppHelper.getInt(year);
		if ("N".equals(slope) && itemp < 30)
			return 0;
		else if ("N".equals(slope) && itemp >= 30 && itemp <= 50)
			return 1;
		else if ("N".equals(slope) && itemp > 50)
			return 2;
		else if ("Y".equals(slope))
			return itemp < 30 ? 1 : 2;
		return 0;
	}
}
