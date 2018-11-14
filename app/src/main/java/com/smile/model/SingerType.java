package com.smile.model;

public class SingerType {

	private int id;
	private String  areaNo;
	private String  areaNa;
	private String  areaEn;
	private String sex;	// 0-> not specified, 1->male, 2->female

	public SingerType() {
		setId(0);
		setAreaNo("");
		setAreaNa("");
		setAreaEn("");
		setSex("0");
	}
	public SingerType(int id, String areaNo, String areaNa, String areaEn, String sex) {
		setId(id);
		setAreaNo(areaNo);
		setAreaNa(areaNa);
		setAreaEn(areaEn);
		setSex(sex);
	}

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaNo() {
		return this.areaNo;
	}
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
	public String getAreaNa() {
		return this.areaNa;
	}
	public void setAreaNa(String areaNa) {
		this.areaNa = areaNa;
	}
	public String getAreaEn() {
		return this.areaEn;
	}
	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}
	public String getSex() {
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		String str = areaNo + " " + areaNa + " " + areaEn + " " + sex;
		return str;
	}
}
