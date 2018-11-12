package com.smile.dao;

import com.smile.model.SingerType;

import java.util.ArrayList;

public class GetDataByRestApi {
    private static final String WebSite = "";
    public static ArrayList<SingerType> getSingerTypes() {
        ArrayList<SingerType> singerTypes = new ArrayList<>();
        singerTypes.add(new SingerType("1", "Taiwanese", "Taiwanese", 0));
        singerTypes.add(new SingerType("1", "Taiwanese", "Taiwanese", 1));
        singerTypes.add(new SingerType("1", "Taiwanese", "Taiwanese", 2));
        singerTypes.add(new SingerType("2", "Chinese", "Chinese", 0));
        singerTypes.add(new SingerType("2", "Chinese", "Chinese", 1));
        singerTypes.add(new SingerType("2", "Chinese", "Chinese", 2));
        return singerTypes;
    }
}
