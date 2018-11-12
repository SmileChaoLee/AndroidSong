package com.smile.model;

public class SingerData {

		private String  sing_no = new String("");
		private String  sing_na = new String("");
        private Integer num_fw=0;
        private String  num_pw = new String("");
        private String  sex = new String("");
        private String  chor = new String("");
        private String  hot = new String("");
        private String  area_ty = new String("");
        private String  pic_file = new String("");
        private String  area_na = new String("");

		public SingerData() {
			initiateSingerrecord();
		}
		
		public void initiateSingerrecord() {
            setSing_no("");
            setSing_na("");
            setNum_fw(0);
            setNum_pw("");
            setSex("1");
            setChor("N");
            setHot("N");
            setArea_ty("2");
            setPic_file("");
            setArea_na("台港歌手");
		}
		
		public void setSing_no(String sing_no) {
			this.sing_no = sing_no;
		}
		public String getSing_no() {
			return this.sing_no;
		}
		public void setSing_na(String song_na) {
			this.sing_na = song_na;
		}
		public String getSing_na() {
			return this.sing_na;
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
        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getSex() {
            return this.sex;
        }
		public void setChor(String chor) {
			this.chor = chor;
		}
		public String getChor() {
			return this.chor;
		}
        public void setHot(String hot) {
            this.hot = hot;
        }
        public String getHot() {
            return this.hot;
        }
        public void setArea_ty(String area_ty) {
            this.area_ty = area_ty;
        }
        public String getArea_ty() {
            return this.area_ty;
        }
        public void setPic_file(String pic_file) {
            this.pic_file = pic_file;
        }
        public String getPic_file() {
            return pic_file;
        }
        public void setArea_na(String area_na) {
        this.area_na = area_na;
    }
        public String getArea_na() {
        return this.area_na;
    }
	}
