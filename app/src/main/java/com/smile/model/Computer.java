package com.smile.model;

/**
 * Created by lee on 9/3/2014.
 */
public class Computer {
    private String computer_id = new String("");
    private String branch_id = new String("");
    private String room_no = new String("");
    private String song_no  = new String("");

    public Computer() {
        initiateRecordData();
    }

    public void initiateRecordData() {
        setComputer_id("");
        setBranch_id("");
        setRoom_no("");
        setSong_no("");
    }

    public void setComputer_id(String computer_id) {
        this.computer_id = computer_id;
    }
    public String getComputer_id() {
        return this.computer_id;
    }
    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }
    public String getBranch_id() {
        return this.branch_id;
    }
    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
    public String getRoom_no() {
        return this.room_no;
    }
    public void setSong_no(String song_no) {
        this.song_no = song_no;
    }
    public String getSong_no() {
        return this.song_no;
    }
}
