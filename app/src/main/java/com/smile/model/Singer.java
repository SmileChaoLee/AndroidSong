package com.smile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Singer implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("singNo")
    private String singNo;
    @SerializedName("singNa")
    private String singNa;
    @SerializedName("sex")
    private String sex;
    @SerializedName("chor")
    private String chor;
    @SerializedName("hot")
    private String hot;
    @SerializedName("numFw")
    private int numFw;
    @SerializedName("numPw")
    private String numPw;
    @SerializedName("picFile")
    private String picFile;

    public Singer() {
        setId(0);
        setSingNo("");
        setSingNa("");
        setSex("");
        setChor("");
        setHot("");
        setNumFw(0);
        setNumPw("");
        setPicFile("");
    }

    public Singer(int id, String singNo, String singNa, String sex, String chor, String hot, int numFw, String numPw, String picFile) {
        setId(id);
        setSingNo(singNo);
        setSingNa(singNa);
        setSex(sex);
        setChor(chor);
        setHot(hot);
        setNumFw(numFw);
        setNumPw(numPw);
        setPicFile(picFile);
    }

    public Singer(Parcel in) {
        id = in.readInt();
        singNo = in.readString();
        singNa = in.readString();
        sex = in.readString();
        chor = in.readString();
        hot = in.readString();
        numFw = in.readInt();
        numPw = in.readString();
        picFile = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(singNo);
        out.writeString(singNa);
        out.writeString(sex);
        out.writeString(chor);
        out.writeString(hot);
        out.writeInt(numFw);
        out.writeString(numPw);
        out.writeString(picFile);
    }

    public static final Parcelable.Creator<Singer> CREATOR = new Parcelable.Creator<Singer>() {
        @Override
        public Singer createFromParcel(Parcel in) {
            return new Singer(in);
        }
        @Override
        public Singer[] newArray(int size) {
            return new Singer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSingNo() {
        return singNo;
    }

    public void setSingNo(String singNo) {
        this.singNo = singNo;
    }

    public String getSingNa() {
        return singNa;
    }

    public void setSingNa(String singNa) {
        this.singNa = singNa;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getChor() {
        return chor;
    }

    public void setChor(String chor) {
        this.chor = chor;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public int getNumFw() {
        return numFw;
    }

    public void setNumFw(int numFw) {
        this.numFw = numFw;
    }

    public String getNumPw() {
        return numPw;
    }

    public void setNumPw(String numPw) {
        this.numPw = numPw;
    }

    public String getPicFile() {
        return picFile;
    }

    public void setPicFile(String picFile) {
        this.picFile = picFile;
    }
}
