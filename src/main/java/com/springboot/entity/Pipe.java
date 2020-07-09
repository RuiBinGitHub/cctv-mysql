package com.springboot.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Pipe")
public class Pipe implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int no;
	private String operator;
	private String workorder;
	private String reference;
	private String purposes;
	private String slope;
	private String sloperef;
	private String yearlaid;
	private String date;
	private String time;

	private String district1;
	private String district2;
	private String district3;
	private String roadname;
	private String housenum;
	private String building;
	private String division;
	private String areacode;

	private String smanholeno;
	private String fmanholeno;
	private String uses;
	private String dire;
	private String hsize;
	private String wsize;
	private String shape;
	private String mater;
	private String lining;
	private double pipelength;
	private double testlength;

	private String sdepth;
	private String scoverlevel;
	private String sinvertlevel;
	private String fdepth;
	private String fcoverlevel;
	private String finvertlevel;
	private String category;
	private String cleaned;
	private String weather;
	private String videono;
	private String comment;
	private Project project;

	private List<Item> items;
	private int surve[] = new int[20];
	private double score[] = new double[9];
	private double grade[] = new double[9];

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getWorkorder() {
		return workorder;
	}

	public void setWorkorder(String workorder) {
		this.workorder = workorder;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPurposes() {
		return purposes;
	}

	public void setPurposes(String purposes) {
		this.purposes = purposes;
	}

	public String getSlope() {
		return slope;
	}

	public void setSlope(String slope) {
		this.slope = slope;
	}

	public String getSloperef() {
		return sloperef;
	}

	public void setSloperef(String sloperef) {
		this.sloperef = sloperef;
	}

	public String getYearlaid() {
		return yearlaid;
	}

	public void setYearlaid(String yearlaid) {
		this.yearlaid = yearlaid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDistrict1() {
		return district1;
	}

	public void setDistrict1(String district1) {
		this.district1 = district1;
	}

	public String getDistrict2() {
		return district2;
	}

	public void setDistrict2(String district2) {
		this.district2 = district2;
	}

	public String getDistrict3() {
		return district3;
	}

	public void setDistrict3(String district3) {
		this.district3 = district3;
	}

	public String getRoadname() {
		return roadname;
	}

	public void setRoadname(String roadname) {
		this.roadname = roadname;
	}

	public String getHousenum() {
		return housenum;
	}

	public void setHousenum(String housenum) {
		this.housenum = housenum;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getSmanholeno() {
		return smanholeno;
	}

	public void setSmanholeno(String smanholeno) {
		this.smanholeno = smanholeno;
	}

	public String getFmanholeno() {
		return fmanholeno;
	}

	public void setFmanholeno(String fmanholeno) {
		this.fmanholeno = fmanholeno;
	}

	public String getUses() {
		return uses;
	}

	public void setUses(String uses) {
		this.uses = uses;
	}

	public String getDire() {
		return dire;
	}

	public void setDire(String dire) {
		this.dire = dire;
	}

	public String getHsize() {
		return hsize;
	}

	public void setHsize(String hsize) {
		this.hsize = hsize;
	}

	public String getWsize() {
		return wsize;
	}

	public void setWsize(String wsize) {
		this.wsize = wsize;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getMater() {
		return mater;
	}

	public void setMater(String mater) {
		this.mater = mater;
	}

	public String getLining() {
		return lining;
	}

	public void setLining(String lining) {
		this.lining = lining;
	}

	public double getPipelength() {
		return pipelength;
	}

	public void setPipelength(double pipelength) {
		this.pipelength = pipelength;
	}

	public double getTestlength() {
		return testlength;
	}

	public void setTestlength(double testlength) {
		this.testlength = testlength;
	}

	public String getSdepth() {
		return sdepth;
	}

	public void setSdepth(String sdepth) {
		this.sdepth = sdepth;
	}

	public String getScoverlevel() {
		return scoverlevel;
	}

	public void setScoverlevel(String scoverlevel) {
		this.scoverlevel = scoverlevel;
	}

	public String getSinvertlevel() {
		return sinvertlevel;
	}

	public void setSinvertlevel(String sinvertlevel) {
		this.sinvertlevel = sinvertlevel;
	}

	public String getFdepth() {
		return fdepth;
	}

	public void setFdepth(String fdepth) {
		this.fdepth = fdepth;
	}

	public String getFcoverlevel() {
		return fcoverlevel;
	}

	public void setFcoverlevel(String fcoverlevel) {
		this.fcoverlevel = fcoverlevel;
	}

	public String getFinvertlevel() {
		return finvertlevel;
	}

	public void setFinvertlevel(String finvertlevel) {
		this.finvertlevel = finvertlevel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCleaned() {
		return cleaned;
	}

	public void setCleaned(String cleaned) {
		this.cleaned = cleaned;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getVideono() {
		return videono;
	}

	public void setVideono(String videono) {
		this.videono = videono;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int[] getSurve() {
		return surve;
	}

	public void setSurve(int[] surve) {
		this.surve = surve;
	}

	public double[] getScore() {
		return score;
	}

	public void setScore(double[] score) {
		this.score = score;
	}

	public double[] getGrade() {
		return grade;
	}

	public void setGrade(double[] grade) {
		this.grade = grade;
	}

}
