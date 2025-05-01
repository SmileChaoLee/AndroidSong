package com.smile.androidsong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Language implements Parcelable {

	@SerializedName("id")
	private int id;
	@SerializedName("langNo")
	private String  langNo;
	@SerializedName("langNa")
	private String  langNa;
	@SerializedName("langEn")
	private String  langEn;

	public Language() {
		setId(0);
		setLangNo("");
		setLangNa("");
		setLangEn("");
	}

	public Language(int id, String langNo, String langNa, String langEn) {
		setId(id);
		setLangNo(langNo);
		setLangNa(langNa);
		setLangEn(langEn);
	}

	public Language(Parcel in) {
		id = in.readInt();
		langNo = in.readString();
		langNa = in.readString();
		langEn = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLangNo() {
		return langNo;
	}

	public void setLangNo(String langNo) {
		this.langNo = langNo;
	}

	public String getLangNa() {
		return langNa;
	}

	public void setLangNa(String langNa) {
		this.langNa = langNa;
	}

	public String getLangEn() {
		return langEn;
	}

	public void setLangEn(String langEn) {
		this.langEn = langEn;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		out.writeInt(id);
		out.writeString(langNo);
		out.writeString(langNa);
		out.writeString(langEn);
	}

	public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
		@Override
		public Language createFromParcel(Parcel in) {
			return new Language(in);
		}
		@Override
		public Language[] newArray(int size) {
			return new Language[size];
		}
	};

	@Override
	public String toString() {
		return "Language{" +
				"id=" + id +
				", langNo='" + langNo + '\'' +
				", langNa='" + langNa + '\'' +
				", langEn='" + langEn + '\'' +
				'}';
	}
}
