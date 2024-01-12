package com.smile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SingerList {
    @SerializedName("pageNo")
    private int pageNo;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("totalRecords")
    private int totalRecords;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("singers")
    private ArrayList<Singer> singers;

    public SingerList() {
        singers = new ArrayList<>();
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Singer> getSingers() {
        return singers;
    }

    public void setSingers(ArrayList<Singer> singers) {
        this.singers = singers;
    }
}
