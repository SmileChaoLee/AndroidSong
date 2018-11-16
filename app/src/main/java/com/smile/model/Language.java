package com.smile.model;

public class Language {

		private String  lang_no = new String("");
		private String  lang_na = new String("");
        private String  lang_en = new String("");

		public Language() {
			initiateLangurecord();
		}
		
		public void initiateLangurecord() {
            setLang_no("");
            setLang_na("");
            setLang_en("");
		}
		
		public void setLang_no(String lang_no) {
			this.lang_no = lang_no;
		}
		public String getLang_no() {
			return this.lang_no;
		}
		public void setLang_na(String lang_na) {
			this.lang_na = lang_na;
		}
		public String getLang_na() {
			return this.lang_na;
		}
        public void setLang_en(String lang_en) {
            this.lang_en = lang_en;
        }
        public String getLang_en() {
            return this.lang_en;
        }
	}
