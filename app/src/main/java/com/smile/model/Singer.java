package com.smile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Singer implements Parcelable {

    private int id;
    private String singerNo;
    private String singerNa;

    public Singer() {
        setId(0);
        setSingerNo("");
        setSingerNa("");
    }

    public Singer(int id, String singerNo, String singerNa) {
        setId(id);
        setSingerNo(singerNo);
        setSingerNa(singerNa);
    }

    public Singer(Parcel in) {
        id = in.readInt();
        singerNo = in.readString();
        singerNa = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSingerNo() {
        return singerNo;
    }

    public void setSingerNo(String singerNo) {
        this.singerNo = singerNo;
    }

    public String getSingerNa() {
        return singerNa;
    }

    public void setSingerNa(String singerNa) {
        this.singerNa = singerNa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(singerNo);
        out.writeString(singerNa);
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

    @Override
    public String toString() {
        return "Singer{" +
                "id=" + id +
                ", singerNo='" + singerNo + '\'' +
                ", singerNa='" + singerNa + '\'' +
                '}';
    }
}
