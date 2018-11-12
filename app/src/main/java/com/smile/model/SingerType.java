package com.smile.model;

public class SingerType {

	private String  areaNo;
	private String  areaNa;
	private String  areaEn;
	private int sex;	// 0-> not specified, 1->male, 2->female

	public SingerType() {
		setAreaNo("");
		setAreaNa("");
		setAreaEn("");
		setSex(0);
	}
	public SingerType(String areaNo, String areaNa, String areaEn, int sex) {
		setAreaNo(areaNo);
		setAreaNa(areaNa);
		setAreaEn(areaEn);
		setSex(sex);
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
	public int getSex() {
		return this.sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
}
