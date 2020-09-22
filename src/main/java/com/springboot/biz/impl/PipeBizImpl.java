package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.LoginfoBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.dao.PipeDao;
import com.springboot.entity.Item;
import com.springboot.entity.Loginfo;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppHelper;
import com.springboot.util.CameHelper;

@Service
@Transactional
public class PipeBizImpl implements PipeBiz {

	@Resource
	private LoginfoBiz loginfoBiz;
	@Resource
	private PipeDao pipeDao;
	@Resource
	private ItemBiz itemBiz;

	private Map<String, Object> map = null;

	public void insertPipe(Pipe pipe) {
		pipeDao.insertPipe(pipe);
	}

	public void updatePipe(Pipe pipe) {
		pipeDao.updatePipe(pipe);
	}

	public void deletePipe(Pipe pipe) {
		pipeDao.deletePipe(pipe);
	}

	public void combinPipe(int id, List<?> list) {
		map = AppHelper.getMap("id", id, "list", list);
		pipeDao.combinPipe(map);
	}

	public Pipe findInfoPipe(int id, User user) {
		map = AppHelper.getMap("id", id, "user", user);
		return pipeDao.findInfoPipe(map);
	}

	public Pipe findInfoPipe(Map<String, Object> map) {
		return pipeDao.findInfoPipe(map);
	}

	public List<Pipe> findListPipe(Project project) {
		map = AppHelper.getMap("project", project);
		return pipeDao.findListPipe(map);
	}

	public List<Pipe> findListPipe(Map<String, Object> map) {
		return pipeDao.findListPipe(map);
	}

	public int getCount(Map<String, Object> map) {
		return pipeDao.getCount(map);
	}

	public void check(Pipe cust, Pipe pipe) {
		StringBuffer context = new StringBuffer("");
		if (cust.getNo() != pipe.getNo())
			context.append("*.修改Survey ID为：" + pipe.getNo() + "\n");
		if (!cust.getOperator().equals(pipe.getOperator()))
			context.append("*.修改Operator为：" + pipe.getOperator() + "\n");
		if (!cust.getWorkorder().equals(pipe.getWorkorder()))
			context.append("*.修改Work Order No为：" + pipe.getWorkorder() + "\n");
		if (!cust.getReference().equals(pipe.getReference()))
			context.append("*.修改Pipe Length Reference为：" + pipe.getReference() + "\n");
		if (!cust.getPurposes().equals(pipe.getPurposes()))
			context.append("*.修改Purpose为：" + pipe.getPurposes() + "\n");
		if (!cust.getSlope().equals(pipe.getSlope()))
			context.append("*.修改Slope为：" + pipe.getSlope() + "\n");
		if (!cust.getSloperef().equals(pipe.getSloperef()))
			context.append("*.修改Slope Reference No为：" + pipe.getSloperef() + "\n");
		if (!cust.getYearlaid().equals(pipe.getYearlaid()))
			context.append("*.修改Year Laid为：" + pipe.getYearlaid() + "\n");
		if (!cust.getDate().equals(pipe.getDate()))
			context.append("*.修改Date为：" + pipe.getDate() + "\n");
		if (!cust.getTime().equals(pipe.getTime()))
			context.append("*.修改Time为：" + pipe.getTime() + "\n");

		if (!cust.getDistrict1().equals(pipe.getDistrict1()))
			context.append("*.修改District1为：" + pipe.getDistrict1() + "\n");
		if (!cust.getDistrict2().equals(pipe.getDistrict2()))
			context.append("*.修改District2为：" + pipe.getDistrict2() + "\n");
		if (!cust.getDistrict3().equals(pipe.getDistrict3()))
			context.append("*.修改District3为：" + pipe.getDistrict3() + "\n");
		if (!cust.getRoadname().equals(pipe.getRoadname()))
			context.append("*.修改Road Name为：" + pipe.getRoadname() + "\n");
		if (!cust.getHousenum().equals(pipe.getHousenum()))
			context.append("*.修改House Number为：" + pipe.getHousenum() + "\n");
		if (!cust.getBuilding().equals(pipe.getBuilding()))
			context.append("*.修改Building为：" + pipe.getBuilding() + "\n");
		if (!cust.getDivision().equals(pipe.getDivision()))
			context.append("*.修改Division为：" + pipe.getDivision() + "\n");
		if (!cust.getAreacode().equals(pipe.getAreacode()))
			context.append("*.修改Drainage Area Code为：" + pipe.getAreacode() + "\n");

		if (!cust.getSmanholeno().equals(pipe.getSmanholeno()))
			context.append("*.修改Start MH为：" + pipe.getSmanholeno() + "\n");
		if (!cust.getFmanholeno().equals(pipe.getFmanholeno()))
			context.append("*.修改Finish MH为：" + pipe.getFmanholeno() + "\n");
		if (!cust.getUses().equals(pipe.getUses()))
			context.append("*.修改Use为：" + CameHelper.getCameText(pipe.getUses(), "use") + "\n");
		if (!cust.getDire().equals(pipe.getDire()))
			context.append("*.修改Direction为：" + CameHelper.getCameText(pipe.getDire(), "dir") + "\n");
		if (!cust.getHsize().equals(pipe.getHsize()))
			context.append("*.修改Size(Dia)H为：" + pipe.getHsize() + "\n");
		if (!cust.getWsize().equals(pipe.getWsize()))
			context.append("*.修改Size(Dia)W为：" + pipe.getWsize() + "\n");
		if (!cust.getShape().equals(pipe.getShape()))
			context.append("*.修改Shape为：" + CameHelper.getCameText(pipe.getShape(), "sha") + "\n");
		if (!cust.getMater().equals(pipe.getMater()))
			context.append("*.修改Material为：" + CameHelper.getCameText(pipe.getMater(), "mat") + "\n");
		if (!cust.getLining().equals(pipe.getLining()))
			context.append("*.修改Lining为：" + CameHelper.getCameText(pipe.getLining(), "lin") + "\n");
		if (cust.getPipelength() != pipe.getPipelength())
			context.append("*.修改Pipe Length为：" + pipe.getPipelength() + "\n");
		if (cust.getTestlength() != pipe.getTestlength())
			context.append("*.修改Total Length为：" + pipe.getTestlength() + "\n");

		if (!cust.getSdepth().equals(pipe.getSdepth()))
			context.append("*.修改Start Depth为：" + pipe.getSdepth() + "\n");
		if (!cust.getScoverlevel().equals(pipe.getScoverlevel()))
			context.append("*.修改Start Cover Level为：" + pipe.getScoverlevel() + "\n");
		if (!cust.getSinvertlevel().equals(pipe.getSinvertlevel()))
			context.append("*.修改Start Invert Level为：" + pipe.getSinvertlevel() + "\n");
		if (!cust.getFdepth().equals(pipe.getFdepth()))
			context.append("*.修改Finish Depth为：" + pipe.getFdepth() + "\n");
		if (!cust.getFcoverlevel().equals(pipe.getFcoverlevel()))
			context.append("*.修改Finish Cover Level为：" + pipe.getFcoverlevel() + "\n");
		if (!cust.getFinvertlevel().equals(pipe.getFinvertlevel()))
			context.append("*.修改Finish Invert Level为：" + pipe.getFinvertlevel() + "\n");
		if (!cust.getCategory().equals(pipe.getCategory()))
			context.append("*.修改Category为：" + CameHelper.getCameText(pipe.getCategory(), "cat") + "\n");
		if (!cust.getCleaned().equals(pipe.getCleaned()))
			context.append("*.修改Cleaned为：" + pipe.getCleaned() + "\n");
		if (!cust.getWeather().equals(pipe.getWeather()))
			context.append("*.修改Weather为：" + CameHelper.getCameText(pipe.getWeather(), "wea") + "\n");
		if (!cust.getVideono().equals(pipe.getVideono()))
			context.append("*.修改Video No/File Name为：" + pipe.getVideono() + "\n");
		if (!cust.getComment().equals(pipe.getComment()))
			context.append("*.修改Comments General为：" + pipe.getComment() + "\n");

		List<Item> items1 = itemBiz.findListItem(cust);
		List<Item> items2 = pipe.getItems();
		for (int i = 0, j = 0; i < items1.size() && j < items2.size(); i++) {
			Item item1 = items1.get(i);
			Item item2 = items2.get(j);
			if (item1.getId() != item2.getId())
				continue; // 继续
			StringBuffer text = new StringBuffer("");
			if (!item1.getDist().equals(item2.getDist()))
				text.append("  - 修改Distance为：" + item2.getDist() + ";\n");
			if (!item1.getCont().equals(item2.getCont()))
				text.append("  - 修改Cont/Def为：" + item2.getCont() + ";\n");
			if (!item1.getCode().equals(item2.getCode()))
				text.append("  - 修改Cont/Def为：" + item2.getCode() + ";\n");
			if (!item1.getDiam().equals(item2.getDiam()))
				text.append("  - 修改Dia./Dim为：" + item2.getDiam() + ";\n");
			if (!item1.getClockAt().equals(item2.getClockAt()))
				text.append("  - 修改Clock At为：" + item2.getClockAt() + ";\n");
			if (!item1.getClockTo().equals(item2.getClockTo()))
				text.append("  - 修改Clock To为：" + item2.getClockTo() + ";\n");
			if (!item1.getPercent().equals(item2.getPercent()))
				text.append("  - 修改Intrusion %为：" + item2.getPercent() + ";\n");
			if (!item1.getLengths().equals(item2.getLengths()))
				text.append("  - 修改Intrusion mm为：" + item2.getLengths() + ";\n");
			if (!item1.getRemarks().equals(item2.getRemarks()))
				text.append("  - 修改Remarks为：" + item2.getRemarks() + ";\n");
			if (!item1.getPicture().equals(item2.getPicture()))
				text.append("  - 修改记录截图;\n");
			if (!StringUtils.isEmpty(text.toString()))
				context.append("*.编辑管道记录列表第" + (i + 1) + "条记录：\n" + text);
			j++;
		}
		// 保存日志
		if (!StringUtils.isEmpty(context.toString())) {
			User user = (User) AppHelper.findMap("user");
			Loginfo loginfo = new Loginfo();
			loginfo.setContext(context.toString());
			loginfo.setDate(AppHelper.getDate(null));
			loginfo.setUser(user);
			loginfo.setPipe(pipe);
			loginfoBiz.insertLoginfo(loginfo);
		}
	}

	public void appendPipe(Pipe pipe) {
		insertPipe(pipe);
		String standard = pipe.getProject().getStandard();
		if ("HKCCEC 2009".equals(standard)) {
			Item item1 = new Item();
			Item item2 = new Item();
			Item item3 = new Item();
			item1.setNo(0);
			item1.setDist("0.0");
			item1.setCode("ST");
			item1.setPipe(pipe);

			item2.setNo(1);
			item2.setDist("0.0");
			item2.setCode("MH");
			item2.setPipe(pipe);

			item3.setNo(2);
			item3.setDist("0.0");
			item3.setCode("WL");
			item3.setPipe(pipe);

			itemBiz.insertItem(item1);
			itemBiz.insertItem(item2);
			itemBiz.insertItem(item3);
		} else {
			Item item = new Item();
			item.setNo(0);
			item.setDist("0.0");
			item.setPipe(pipe);
			itemBiz.insertItem(item);
		}
	}

}
