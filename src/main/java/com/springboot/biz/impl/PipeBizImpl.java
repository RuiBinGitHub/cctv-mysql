package com.springboot.biz.impl;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.NoteBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.dao.PipeDao;
import com.springboot.entity.Item;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.entity.User;
import com.springboot.util.AppUtils;
import com.springboot.util.CameHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PipeBizImpl implements PipeBiz {

    @Value("${myfile}")
    private String myfile;

    @Resource
    private ProjectBiz projectBiz;
    @Resource
    private PipeDao pipeDao;
    @Resource
    private ItemBiz itemBiz;
    @Resource
    private NoteBiz noteBiz;
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

    public Pipe findInfoPipe(int id, User user) {
        map = AppUtils.getMap("id", id, "user", user);
        return pipeDao.findInfoPipe(map);
    }

    public Pipe findInfoPipe(Map<String, Object> map) {
        return pipeDao.findInfoPipe(map);
    }

    public List<Pipe> findListPipe(Project project) {
        map = AppUtils.getMap("project", project);
        return pipeDao.findListPipe(map);
    }

    public List<Pipe> findListPipe(Map<String, Object> map) {
        return pipeDao.findListPipe(map);
    }

    public int getCount(Map<String, Object> map) {
        return pipeDao.getCount(map);
    }

    public void appendPipe(Pipe pipe) {
        pipeDao.insertPipe(pipe);
        String standard = pipe.getProject().getStandard();
        if (standard != null && standard.contains("H")) {
            Item item1 = new Item(0, "0.00", "ST", pipe);
            Item item2 = new Item(1, "0.00", "MH", pipe);
            Item item3 = new Item(2, "0.00", "WL", pipe);
            itemBiz.insertItem(item1);
            itemBiz.insertItem(item2);
            itemBiz.insertItem(item3);
        } else {
            Item item1 = new Item(0, "0.00", "MH", pipe);
            itemBiz.insertItem(item1);
        }
    }

    public void replacPipe(Pipe pipe) {
        pipeDao.updatePipe(pipe);
        int no = 0;
        for (Item item : pipe.getItems()) {
            item.setNo(no++);
            if ("#已移除#".equals(item.getPhoto()))
                item.setPhoto("");
            String data = item.getPicture();
            if (data != null && data.length() > 40) {
                String name = AppUtils.UUIDCode();
                AppUtils.saveImage(data, myfile + "/image/", name);
                item.setPicture(name);
            }
            item.setPipe(pipe);
            if (item.getId() == 0)
                itemBiz.insertItem(item);
            else
                itemBiz.updateItem(item);
        }

        // 管道重新排序
        Project project = pipe.getProject();
        List<Pipe> pipes = findListPipe(project);
        pipes.sort((Pipe p1, Pipe p2) -> {
            if (p1.getNo() == p2.getNo()) {
                if (p1.getId() == pipe.getId())
                    return -1;
                if (p2.getId() == pipe.getId())
                    return +1;
                return 0;
            }
            return p1.getNo() - p2.getNo();
        });
        for (int i = 0; i < pipes.size(); i++) {
            Pipe temp = pipes.get(i);
            temp.setNo(i + 1);
            pipeDao.updatePipe(temp);
        }
        // 图片重新排序
        projectBiz.sortImage(project);
    }

    public void removePipe(Pipe pipe) {
        pipeDao.deletePipe(pipe);
        List<Pipe> pipes = findListPipe(pipe.getProject());
        for (int i = 0; i < pipes.size(); i++) {
            Pipe temp = pipes.get(i);
            temp.setNo(i + 1);
            pipeDao.updatePipe(temp);
        }
        projectBiz.sortImage(pipe.getProject());
    }

    public void checkPipe(Pipe pipe1, Pipe pipe2, User user) {
        StringBuilder context = new StringBuilder();
        if (pipe1.getNo() != pipe2.getNo())
            context.append("*.修改Survey ID为：").append(pipe2.getNo()).append("\n");
        if (AppUtils.equals(pipe1.getOperator(), pipe2.getOperator()))
            context.append("*.修改Operator为：").append(pipe2.getOperator()).append("\n");
        if (AppUtils.equals(pipe1.getWorkorder(), pipe2.getWorkorder()))
            context.append("*.修改Work Order No为：").append(pipe2.getWorkorder()).append("\n");
        if (AppUtils.equals(pipe1.getReference(), pipe2.getReference()))
            context.append("*.修改Pipe Length Reference为：").append(pipe2.getReference()).append("\n");
        if (AppUtils.equals(pipe1.getPurposes(), pipe2.getPurposes()))
            context.append("*.修改Purpose为：").append(pipe2.getPurposes()).append("\n");
        if (AppUtils.equals(pipe1.getSlope(), pipe2.getSlope()))
            context.append("*.修改Slope为：").append(pipe2.getSlope()).append("\n");
        if (AppUtils.equals(pipe1.getSloperef(), pipe2.getSloperef()))
            context.append("*.修改Slope Reference No为：").append(pipe2.getSloperef()).append("\n");
        if (AppUtils.equals(pipe1.getYearlaid(), pipe2.getYearlaid()))
            context.append("*.修改Year Laid为：").append(pipe2.getYearlaid()).append("\n");
        if (AppUtils.equals(pipe1.getDate(), pipe2.getDate()))
            context.append("*.修改Date为：").append(pipe2.getDate()).append("\n");
        if (AppUtils.equals(pipe1.getTime(), pipe2.getTime()))
            context.append("*.修改Time为：").append(pipe2.getTime()).append("\n");

        if (AppUtils.equals(pipe1.getDistrict1(), pipe2.getDistrict1()))
            context.append("*.修改District1为：").append(pipe2.getDistrict1()).append("\n");
        if (AppUtils.equals(pipe1.getDistrict2(), pipe2.getDistrict2()))
            context.append("*.修改District2为：").append(pipe2.getDistrict2()).append("\n");
        if (AppUtils.equals(pipe1.getDistrict3(), pipe2.getDistrict3()))
            context.append("*.修改District3为：").append(pipe2.getDistrict3()).append("\n");
        if (AppUtils.equals(pipe1.getRoadname(), pipe2.getRoadname()))
            context.append("*.修改Road Name为：").append(pipe2.getRoadname()).append("\n");
        if (AppUtils.equals(pipe1.getHousenum(), pipe2.getHousenum()))
            context.append("*.修改House Number为：").append(pipe2.getHousenum()).append("\n");
        if (AppUtils.equals(pipe1.getBuilding(), pipe2.getBuilding()))
            context.append("*.修改Building为：").append(pipe2.getBuilding()).append("\n");
        if (AppUtils.equals(pipe1.getDivision(), pipe2.getDivision()))
            context.append("*.修改Division为：").append(pipe2.getDivision()).append("\n");
        if (AppUtils.equals(pipe1.getAreacode(), pipe2.getAreacode()))
            context.append("*.修改Drainage Area Code为：").append(pipe2.getAreacode()).append("\n");

        if (AppUtils.equals(pipe1.getSmanholeno(), pipe2.getSmanholeno()))
            context.append("*.修改Start MH为：").append(pipe2.getSmanholeno()).append("\n");
        if (AppUtils.equals(pipe1.getFmanholeno(), pipe2.getFmanholeno()))
            context.append("*.修改Finish MH为：").append(pipe2.getFmanholeno()).append("\n");
        if (AppUtils.equals(pipe1.getUses(), pipe2.getUses()))
            context.append("*.修改Use为：").append(CameHelper.getCameText(pipe2.getUses(), "use")).append("\n");
        if (AppUtils.equals(pipe1.getDire(), pipe2.getDire()))
            context.append("*.修改Direction为：").append(CameHelper.getCameText(pipe2.getDire(), "dir")).append("\n");
        if (AppUtils.equals(pipe1.getHsize(), pipe2.getHsize()))
            context.append("*.修改Size(Dia)H为：").append(pipe2.getHsize()).append("\n");
        if (AppUtils.equals(pipe1.getWsize(), pipe2.getWsize()))
            context.append("*.修改Size(Dia)W为：").append(pipe2.getWsize()).append("\n");
        if (AppUtils.equals(pipe1.getShape(), pipe2.getShape()))
            context.append("*.修改Shape为：").append(CameHelper.getCameText(pipe2.getShape(), "sha")).append("\n");
        if (AppUtils.equals(pipe1.getMater(), pipe2.getMater()))
            context.append("*.修改Material为：").append(CameHelper.getCameText(pipe2.getMater(), "mat")).append("\n");
        if (AppUtils.equals(pipe1.getLining(), pipe2.getLining()))
            context.append("*.修改Lining为：").append(CameHelper.getCameText(pipe2.getLining(), "lin")).append("\n");
        if (pipe1.getPipelength() != pipe2.getPipelength())
            context.append("*.修改Pipe Length为：").append(pipe2.getPipelength()).append("\n");
        if (pipe1.getTestlength() != pipe2.getTestlength())
            context.append("*.修改Total Length为：").append(pipe2.getTestlength()).append("\n");

        if (AppUtils.equals(pipe1.getSdepth(), pipe2.getSdepth()))
            context.append("*.修改Start Depth为：").append(pipe2.getSdepth()).append("\n");
        if (AppUtils.equals(pipe1.getScoverlevel(), pipe2.getScoverlevel()))
            context.append("*.修改Start Cover Level为：").append(pipe2.getScoverlevel()).append("\n");
        if (AppUtils.equals(pipe1.getSinvertlevel(), pipe2.getSinvertlevel()))
            context.append("*.修改Start Invert Level为：").append(pipe2.getSinvertlevel()).append("\n");
        if (AppUtils.equals(pipe1.getFdepth(), pipe2.getFdepth()))
            context.append("*.修改Finish Depth为：").append(pipe2.getFdepth()).append("\n");
        if (AppUtils.equals(pipe1.getFcoverlevel(), pipe2.getFcoverlevel()))
            context.append("*.修改Finish Cover Level为：").append(pipe2.getFcoverlevel()).append("\n");
        if (AppUtils.equals(pipe1.getFinvertlevel(), pipe2.getFinvertlevel()))
            context.append("*.修改Finish Invert Level为：").append(pipe2.getFinvertlevel()).append("\n");
        if (AppUtils.equals(pipe1.getCategory(), pipe2.getCategory()))
            context.append("*.修改Category为：").append(CameHelper.getCameText(pipe2.getCategory(), "cat")).append("\n");
        if (AppUtils.equals(pipe1.getCleaned(), pipe2.getCleaned()))
            context.append("*.修改Cleaned为：").append(pipe2.getCleaned()).append("\n");
        if (AppUtils.equals(pipe1.getWeather(), pipe2.getWeather()))
            context.append("*.修改Weather为：").append(CameHelper.getCameText(pipe2.getWeather(), "wea")).append("\n");
        if (AppUtils.equals(pipe1.getVideono(), pipe2.getVideono()))
            context.append("*.修改Video No/File Name为：").append(pipe2.getVideono()).append("\n");
        if (AppUtils.equals(pipe1.getComment(), pipe2.getComment()))
            context.append("*.修改Comments General为：").append(pipe2.getComment()).append("\n");

        List<Item> items1 = itemBiz.findListItem(pipe1);
        List<Item> items2 = pipe2.getItems();
        for (int i = 0; i < items2.size(); i++) {
            Item item1 = items2.get(i);
            if (item1.getId() == 0) {
                context.append("*.新增管道记录>> Distance：").append(item1.getDist()).append("  \tCode：").append(item1.getCode()).append("\n");
                continue; // 继续
            }
            Item item2 = getItem(items1, item1.getId());
            if (item2 == null)
                continue; // 继续
            StringBuilder text = new StringBuilder();
            if (AppUtils.getDouble(item1.getDist()) != AppUtils.getDouble(item2.getDist()))
                text.append("  - 修改Distance为：").append(item1.getDist()).append(";\n");
            if (AppUtils.equals(item1.getCont(), item2.getCont()))
                text.append("  - 修改Cont/Def为：").append(item1.getCont()).append(";\n");
            if (AppUtils.equals(item1.getCode(), item2.getCode()))
                text.append("  - 修改Code为：").append(item1.getCode()).append(";\n");
            if (AppUtils.equals(item1.getDiam(), item2.getDiam()))
                text.append("  - 修改Dia./Dim为：").append(item1.getDiam()).append(";\n");
            if (AppUtils.equals(item1.getClockAt(), item2.getClockAt()))
                text.append("  - 修改Clock At为：").append(item1.getClockAt()).append(";\n");
            if (AppUtils.equals(item1.getClockTo(), item2.getClockTo()))
                text.append("  - 修改Clock To为：").append(item1.getClockTo()).append(";\n");
            if (AppUtils.equals(item1.getPercent(), item2.getPercent()))
                text.append("  - 修改Intrusion %为：").append(item1.getPercent()).append(";\n");
            if (AppUtils.equals(item1.getLengths(), item2.getLengths()))
                text.append("  - 修改Intrusion mm为：").append(item1.getLengths()).append(";\n");
            if (AppUtils.equals(item1.getRemarks(), item2.getRemarks()))
                text.append("  - 修改Remarks为：").append(item1.getRemarks()).append(";\n");
            if (AppUtils.equals(item1.getPicture(), item1.getPicture()))
                text.append("  - 修改记录截图;\n");
            if (!StringUtils.isEmpty(text.toString()))
                context.append("*.编辑管道记录列表第").append(i + 1).append("条记录：\n").append(text);
        }
        // 保存日志
        if (!StringUtils.isEmpty(context.toString()))
            noteBiz.appendNote(context.toString(), user, pipe2);
    }

    /**
     * 获取指定记录
     */
    private Item getItem(List<Item> list, int id) {
        Optional<Item> optional = list.stream().filter(item -> item.getId() == id).findFirst();
        return optional.orElse(null);
    }

}
