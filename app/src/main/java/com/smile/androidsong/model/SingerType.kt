package com.smile.androidsong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SingerType implements Parcelable {

	@SerializedName("id")
	private int id;
	@SerializedName("areaNo")
	private String  areaNo;
	@SerializedName("areaNa")
	private String  areaNa;
	@SerializedName("areaEn")
	private String  areaEn;
	@SerializedName("sex")
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
	public SingerType(Parcel in) {
		this.id = in.readInt();
		this.areaNo = in.readString();
		this.areaNa = in.readString();
		this.areaEn = in.readString();
		this.sex = in.readString();
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
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		out.writeInt(this.id);
		out.writeString(this.areaNo);
		out.writeString(this.areaNa);
		out.writeString(this.areaEn);
		out.writeString(this.sex);
	}

	public static final Parcelable.Creator<SingerType> CREATOR = new Parcelable.Creator<SingerType>() {
		@Override
		public SingerType createFromParcel(Parcel in) {
			return new SingerType(in);
		}
		@Override
		public SingerType[] newArray(int size) {
			return new SingerType[size];
		}
	};

	@Override
	public String toString() {
		return "SingerType{" +
				"id=" + id +
				", areaNo='" + areaNo + '\'' +
				", areaNa='" + areaNa + '\'' +
				", areaEn='" + areaEn + '\'' +
				", sex='" + sex + '\'' +
				'}';
	}
}
