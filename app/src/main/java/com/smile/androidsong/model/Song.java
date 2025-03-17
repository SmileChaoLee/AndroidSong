package com.smile.androidsong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Song implements Parcelable {
	@SerializedName("id")
	private int id;
	@SerializedName("songNo")
    private String songNo;
	@SerializedName("songNa")
    private String songNa;
	@SerializedName("sNumWord")
    private int sNumWord;
	@SerializedName("numFw")
    private int numFw;
	@SerializedName("numPw")
    private String numPw;
	@SerializedName("chor")
    private String chor;
	@SerializedName("nMpeg")
    private String nMpeg;
	@SerializedName("mMpeg")
    private String mMpeg;
	@SerializedName("vodYn")
    private String vodYn;
	@SerializedName("vodNo")
    private String vodNo;
	@SerializedName("pathname")
    private String pathname;
	@SerializedName("ordNo")
    private int ordNo;
	@SerializedName("orderNum")
    private int orderNum;
	@SerializedName("ordOldN")
    private int ordOldN;
	@SerializedName("languageId")
    private int languageId;
	@SerializedName("languageNo")
	private String languageNo;
	@SerializedName("languageNa")
	private String languageNa;
	@SerializedName("singer1Id")
	private int singer1Id;
	@SerializedName("singer1No")
    private String singer1No;
	@SerializedName("singer1Na")
    private String singer1Na;
	@SerializedName("singer2Id")
    private int singer2Id;
	@SerializedName("singer2No")
    private String singer2No;
	@SerializedName("singer2Na")
    private String singer2Na;
	@SerializedName("inDate")
	// private Date inDate;
	private java.util.Date inDate;

	public Song() {
		setId(0);
		setSongNo("");
		setSongNa("");
		setsNumWord(0);
		setNumFw(0);
		setNumPw("0");
		setChor("N");
		setnMpeg("11");
		setmMpeg("12");
		setVodYn("Y");
		setVodNo("");
		setPathname("");
		setOrdNo(0);
		setOrderNum(0);
		setOrdOldN(0);
		setLanguageId(0);
		setLanguageNo("");
		setLanguageNa("");
		setSinger1Id(0);
		setSinger1No("");
		setSinger1Na("");
		setSinger2Id(0);
		setSinger2No("");
		setSinger2Na("");
		setInDate(new java.util.Date());
	}

	public Song(int id, String songNo, String songNa, int sNumWord, int numFw, String numPw, String chor, String nMpeg, String mMpeg, String vodYn, String vodNo, String pathname, int ordNo, int orderNum, int ordOldN, int languageId, String languageNo, String languageNa, int singer1Id, String singer1No, String singer1Na, int singer2Id, String singer2No, String singer2Na, java.util.Date inDate) {
		setId(id);
		setSongNo(songNo);
		setSongNa(songNa);
		setsNumWord(sNumWord);
		setNumFw(numFw);
		setNumPw(numPw);
		setChor(chor);
		setnMpeg(nMpeg);
		setmMpeg(mMpeg);
		setVodYn(vodYn);
		setVodNo(vodNo);
		setPathname(pathname);
		setOrdNo(ordNo);
		setOrderNum(orderNum);
		setOrdOldN(ordOldN);
		setLanguageId(languageId);
		setLanguageNo(languageNo);
		setLanguageNa(languageNa);
		setSinger1Id(singer1Id);
		setSinger1No(singer1No);
		setSinger1Na(singer1Na);
		setSinger2Id(singer2Id);
		setSinger2No(singer2No);
		setSinger2Na(singer2Na);
		setInDate(inDate);
	}

	public Song(Parcel in) {
		id = in.readInt();
		songNo = in.readString();
		songNa = in.readString();
		sNumWord = in.readInt();
		numFw = in.readInt();
		numPw = in.readString();
		chor = in.readString();
		nMpeg = in.readString();
		mMpeg = in.readString();
		vodYn = in.readString();
		vodNo = in.readString();
		pathname = in.readString();
		ordNo = in.readInt();
		orderNum = in.readInt();
		ordOldN = in.readInt();
		languageId = in.readInt();
		languageNo = in.readString();
		languageNa = in.readString();
		singer1Id = in.readInt();
		singer1No = in.readString();
		singer1Na = in.readString();
		singer2Id = in.readInt();
		singer2No = in.readString();
		singer2Na = in.readString();
		inDate = (java.util.Date)in.readSerializable();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSongNo() {
		return songNo;
	}

	public void setSongNo(String songNo) {
		this.songNo = songNo;
	}

	public String getSongNa() {
		return songNa;
	}

	public void setSongNa(String songNa) {
		this.songNa = songNa;
	}

	public int getsNumWord() {
		return sNumWord;
	}

	public void setsNumWord(int sNumWord) {
		this.sNumWord = sNumWord;
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

	public String getChor() {
		return chor;
	}

	public void setChor(String chor) {
		this.chor = chor;
	}

	public String getnMpeg() {
		return nMpeg;
	}

	public void setnMpeg(String nMpeg) {
		this.nMpeg = nMpeg;
	}

	public String getmMpeg() {
		return mMpeg;
	}

	public void setmMpeg(String mMpeg) {
		this.mMpeg = mMpeg;
	}

	public String getVodYn() {
		return vodYn;
	}

	public void setVodYn(String vodYn) {
		this.vodYn = vodYn;
	}

	public String getVodNo() {
		return vodNo;
	}

	public void setVodNo(String vodNo) {
		this.vodNo = vodNo;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public int getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(int ordNo) {
		this.ordNo = ordNo;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getOrdOldN() {
		return ordOldN;
	}

	public void setOrdOldN(int ordOldN) {
		this.ordOldN = ordOldN;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getLanguageNo() {
		return languageNo;
	}

	public void setLanguageNo(String languageNo) {
		this.languageNo = languageNo;
	}

	public String getLanguageNa() {
		return languageNa;
	}

	public void setLanguageNa(String languageNa) {
		this.languageNa = languageNa;
	}

	public int getSinger1Id() {
		return singer1Id;
	}

	public void setSinger1Id(int singer1Id) {
		this.singer1Id = singer1Id;
	}

	public String getSinger1No() {
		return singer1No;
	}

	public void setSinger1No(String singer1No) {
		this.singer1No = singer1No;
	}

	public String getSinger1Na() {
		return singer1Na;
	}

	public void setSinger1Na(String singer1Na) {
		this.singer1Na = singer1Na;
	}

	public int getSinger2Id() {
		return singer2Id;
	}

	public void setSinger2Id(int singer2Id) {
		this.singer2Id = singer2Id;
	}

	public String getSinger2No() {
		return singer2No;
	}

	public void setSinger2No(String singer2No) {
		this.singer2No = singer2No;
	}

	public String getSinger2Na() {
		return singer2Na;
	}

	public void setSinger2Na(String singer2Na) {
		this.singer2Na = singer2Na;
	}

	// public Date getInDate() {
	public java.util.Date getInDate() {
		return inDate;
	}

	// public void setInDate(Date inDate)
	public void setInDate(java.util.Date inDate) {
		this.inDate = inDate;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int i) {
		out.writeInt(id);
		out.writeString(songNo);
		out.writeString(songNa);
		out.writeInt(sNumWord);
		out.writeInt(numFw);
		out.writeString(numPw);
		out.writeString(chor);
		out.writeString(nMpeg);
		out.writeString(mMpeg);
		out.writeString(vodYn);
		out.writeString(vodNo);
		out.writeString(pathname);
		out.writeInt(ordNo);
		out.writeInt(orderNum);
		out.writeInt(ordOldN);
		out.writeInt(languageId);
		out.writeString(languageNo);
		out.writeString(languageNa);
		out.writeInt(singer1Id);
		out.writeString(singer1No);
		out.writeString(singer1Na);
		out.writeInt(singer2Id);
		out.writeString(singer2No);
		out.writeString(singer2Na);
		out.writeSerializable(inDate);
	}

	public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>()
	{
		@Override
		public Song createFromParcel(Parcel in) {
			return new Song(in);
		}

		@Override
		public Song[] newArray(int size) {
			return new Song[size];
		}
	};

	@Override
	public String toString() {
		return "Song{" +
				"id=" + id +
				", songNo='" + songNo + '\'' +
				", songNa='" + songNa + '\'' +
				", sNumWord=" + sNumWord +
				", numFw=" + numFw +
				", numPw='" + numPw + '\'' +
				", chor='" + chor + '\'' +
				", nMpeg='" + nMpeg + '\'' +
				", mMpeg='" + mMpeg + '\'' +
				", vodYn='" + vodYn + '\'' +
				", vodNo='" + vodNo + '\'' +
				", pathname='" + pathname + '\'' +
				", ordNo=" + ordNo +
				", orderNum=" + orderNum +
				", ordOldN=" + ordOldN +
				", languageId=" + languageId +
				", languageNo='" + languageNo + '\'' +
				", languageNa='" + languageNa + '\'' +
				", singer1Id=" + singer1Id +
				", singer1No='" + singer1No + '\'' +
				", singer1Na='" + singer1Na + '\'' +
				", singer2Id=" + singer2Id +
				", singer2No='" + singer2No + '\'' +
				", singer2Na='" + singer2Na + '\'' +
				", inDate=" + inDate +
				'}';
	}
}
