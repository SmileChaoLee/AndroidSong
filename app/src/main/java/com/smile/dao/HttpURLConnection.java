package com.smile.dao;

import android.util.Log;

import com.smile.model.Singer;
import com.smile.model.SingerType;
import com.smile.model.SingerTypesList;
import com.smile.model.SingersList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpURLConnection {
    private static final String TAG = "HttpURLConnection";
    private static final String BASE_URL = "http://137.184.120.171/";
    // private static final String BASE_URL = "http://192.168.0.35:5000/";

    public static SingerTypesList getAllSingerTypes() {
        final String webUrl = BASE_URL + "api/SingerType";
        Log.i(TAG, "WebUrl = " + webUrl);

        SingerTypesList singerTypesList;

        URL url;
        java.net.HttpURLConnection myConnection = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            url = new URL(webUrl);
            myConnection = (java.net.HttpURLConnection)url.openConnection();
            myConnection.setConnectTimeout(15000);
            myConnection.setReadTimeout(15000);
            myConnection.setRequestMethod("GET");
            myConnection.setDoInput(true);
            int responseCode = myConnection.getResponseCode();
            if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "REST Web Service -> Succeeded to connect.");
                inputStream = myConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                int readBuff;
                while ( (readBuff=inputStreamReader.read()) != -1) {
                    sb.append((char)readBuff);
                }
                String result = sb.toString();  // the result
                // Log.i(TAG, "Web output -> " + result);

                JSONObject json = new JSONObject(result);

                singerTypesList = new SingerTypesList();

                singerTypesList.setPageNo(json.getInt("pageNo"));
                singerTypesList.setPageSize(json.getInt("pageSize"));
                singerTypesList.setTotalRecords(json.getInt("totalRecords"));
                singerTypesList.setTotalPages(json.getInt("totalPages"));
                JSONArray jsonArray = new JSONArray(json.getString("singerTypes"));

                ArrayList<SingerType> singerTypes = new ArrayList<>();
                SingerType singerType;
                int id;
                String areaNo;
                String areaNa;
                String areaEn;
                String sex;

                for (int i=0; i<jsonArray.length(); i++) {
                    json = jsonArray.getJSONObject(i);
                    id = json.getInt("id");
                    areaNo = json.getString("areaNo");
                    areaNa = json.getString("areaNa");
                    areaEn = json.getString("areaEn");
                    sex = json.getString("sex");

                    singerType = new SingerType();
                    singerType.setId(id);
                    singerType.setAreaNo(areaNo);
                    singerType.setAreaNa(areaNa);
                    singerType.setAreaEn(areaEn);
                    singerType.setSex(sex);

                    singerTypes.add(singerType);
                }
                singerTypesList.setSingerTypes(singerTypes);
            } else {
                Log.i(TAG, "REST Web Service -> Failed to connect.");
                // singerTypes is null
                singerTypesList = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "REST Web Service -> Failed due to exception.");
            // singerTypes is null
            singerTypesList = null;
        }
        finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                  inputStream.close();
                }
                if (myConnection != null) {
                    myConnection.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return singerTypesList;
    }

    public static SingersList getSingersBySingerType(SingerType singerType, int pageSize, int pageNo) {
        // using int[] pageSize to return the result of pageSize
        // using int[] pageNo to return the result of pageNo
        if (singerType == null) {
            // singerType cannot be null
            Log.d(TAG, "singersList is null.");
            return null;
        }

        // [HttpGet("{areaId}/{sex}/{pageSize}/{pageNo}/orderBy")]
        // GET api/values/5/"1"/10/1/SingNa
        final String param = "/" + singerType.getId() + "/" + singerType.getSex() + "/" + pageSize + "/" + pageNo +"/" + "SingNa";
        final String webUrl = BASE_URL + "api/Singer" + param;
        Log.i(TAG, "WebUrl = " + webUrl);

        SingersList singersList;

        URL url;
        java.net.HttpURLConnection myConnection = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            url = new URL(webUrl);
            myConnection = (java.net.HttpURLConnection)url.openConnection();
            myConnection.setConnectTimeout(15000);
            myConnection.setReadTimeout(15000);
            myConnection.setRequestMethod("GET");
            myConnection.setDoInput(true);
            int responseCode = myConnection.getResponseCode();
            if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "REST Web Service -> Succeeded to connect.");
                inputStream = myConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                int readBuff;
                while ( (readBuff=inputStreamReader.read()) != -1) {
                    sb.append((char)readBuff);
                }
                String result = sb.toString();  // the result
                // Log.i(TAG, "Web output -> " + result);

                JSONObject json = new JSONObject(result);

                singersList = new SingersList();

                singersList.setPageNo(json.getInt("pageNo"));
                singersList.setPageSize(json.getInt("pageSize"));
                singersList.setTotalRecords(json.getInt("totalRecords"));
                singersList.setTotalPages(json.getInt("totalPages"));

                JSONArray jsonArray = new JSONArray(json.getString("singers"));
                // or
                // JSONArray jsonArray = (JSONArray) json.opt("singers");

                ArrayList<Singer> singers = new ArrayList<>();
                Singer singer;
                JSONObject jsonObject;
                for (int i=0; i<jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    singer = new Singer();
                    singer.setId(jsonObject.getInt("id"));
                    singer.setSingNo(jsonObject.getString("singNo"));
                    singer.setSingNa(jsonObject.getString("singNa"));
                    singer.setSex(jsonObject.getString("sex"));
                    singer.setChor(jsonObject.getString("chor"));
                    singer.setHot(jsonObject.getString("hot"));
                    singer.setNumFw(jsonObject.getInt("numFw"));
                    singer.setNumPw(jsonObject.getString("numPw"));
                    singer.setPicFile(jsonObject.getString("picFile"));

                    singers.add(singer);
                }
                singersList.setSingers(singers);
            } else {
                Log.i(TAG, "REST Web Service -> Failed to connect.");
                // singerTypes is null
                singersList = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "REST Web Service -> Failed due to exception.");
            // singerTypes is null
            singersList = null;
        }
        finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (myConnection != null) {
                    myConnection.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return singersList;
    }
}
