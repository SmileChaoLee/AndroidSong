package com.smile.model;

import java.sql.Date;

public class Song {
	
		private String  song_no = new String("");
		private String  song_na = new String("");
		private String  lang_no = new String("");
        private String  lang_na= new String("");
		private Integer s_num_word=0;
		private Integer num_fw=0;
		private String  num_pw = new String("");
		private String  sing_no1 = new String("");
        private String  sing_na1 = new String("");
		private String  sing_no2 = new String("");
        private String  sing_na2 = new String("");
		private String  sele_tf = new String("");
		private String  chor = new String("");
		private String  n_mpeg = new String("");
		private String  m_mpeg = new String("");
		private String  vod_yn = new String("");
		private String  vod_no = new String("");
		private String  pathname = new String("");
		private Integer ord_no=0;
		private Integer order_num=0;
		private Integer ord_old_n=0;
		private Date    in_date= new Date(new java.util.Date().getTime());
		
		public Song() {
			initiateSong2record();
		}
		
		public void initiateSong2record() {
			setSong_no("");
			setSong_na("");
			setLang_no("");
            setLang_na("");
			setS_num_word(0);
			setNum_fw(0);
			setNum_pw("");
			setSing_no1("");
            setSing_na1("");
			setSing_no2("");
            setSing_na2("");
			setSele_tf("");
			setChor("");
			setN_mpeg("");
			setM_mpeg("");
			setVod_yn("");
			setVod_no("");
			setPathname("");
			setOrd_no(0);
			setOrder_num(0);
			setOrd_old_n(0);
			setIn_date(new Date(new java.util.Date().getTime()));
		}
		
		public void setSong_no(String song_no) {
			this.song_no = song_no;	
		}
		public String getSong_no() {
			return this.song_no;
		}
		public void setSong_na(String song_na) {
			this.song_na = song_na;
		}
		public String getSong_na() {
			return this.song_na;
		}
		public void setLang_no(String lang_no) {
			this.lang_no = lang_no;
		}
		public String getLang_no() {
			return this.lang_no;
		}
        public void setLang_na(String lang_na) { this.lang_na = lang_na; }
        public String getLang_na() { return this.lang_na; }
		public void setS_num_word(Integer s_num_word) {
			this.s_num_word = s_num_word;
		}
		public Integer getS_num_word() {
			return this.s_num_word;
		}
		public void setNum_fw(Integer num_fw) {
			this.num_fw = num_fw;
		}
		public Integer getNum_fw() {
			return this.num_fw;
		}
		public void setNum_pw(String num_pw) {
			this.num_pw = num_pw;
		}
		public String getNum_pw() {
			return this.num_pw;
		}
		public void setSing_no1(String sing_no1) {
			this.sing_no1 = sing_no1;
		}
		public String getSing_no1() {
			return this.sing_no1;
		}
        public void setSing_na1(String sing_na1) { this.sing_na1 = sing_na1; }
        public String getSing_na1() { return this.sing_na1; }
		public void setSing_no2(String sing_no2) {
			this.sing_no2 = sing_no2;
		}
		public String getSing_no2() {
			return this.sing_no2;
		}
        public void setSing_na2(String sing_na2) { this.sing_na2 = sing_na2; }
        public String getSing_na2() { return this.sing_na2; }
		public void setSele_tf(String sele_tf) {
			this.sele_tf = sele_tf;
		}
		public String getSele_tf() {
			return this.sele_tf;
		}
		public void setChor(String chor) {
			this.chor = chor;
		}
		public String getChor() {
			return this.chor;
		}
		public void setN_mpeg(String n_mpeg) {
			this.n_mpeg = n_mpeg;
		}
		public String getN_mpeg() {
			return this.n_mpeg;
		}
		public void setM_mpeg(String m_mpeg) {
			this.m_mpeg = m_mpeg;
		}
		public String getM_mpeg() {
			return this.m_mpeg;
		}
		public void setVod_yn(String vod_yn) {
			this.vod_yn = vod_yn;
		}
		public String getVod_yn() {
			return this.vod_yn;
		}
		public void setVod_no(String vod_no) {
			this.vod_no = vod_no;
		}
		public String getVod_no() {
			return this.vod_no;
		}
		public void setPathname(String pathname) {
			this.pathname = pathname;
		}
		public String getPathname() {
			return this.pathname;
		}
		public void setOrd_no(Integer ord_no) {
			this.ord_no = ord_no;
		}
		public Integer getOrd_no() {
			return this.ord_no;
		}
		public void setOrder_num(Integer order_num) {
			this.order_num = order_num;
		}
		public Integer getOrder_num() {
			return this.order_num;
		}
		public void setOrd_old_n(Integer ord_old_n) {
			this.ord_old_n = ord_old_n;
		}
		public Integer getOrd_old_n() {
			return this.ord_old_n;
		}
		public void setIn_date(Date in_date) {
			this.in_date = in_date;
		}
		public Date getIn_date() {
			return this.in_date;
		}
	}
