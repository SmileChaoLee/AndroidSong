package com.smile.dao;

import android.util.Log;

import com.smile.model.Singer;
import com.smile.model.SingerType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetDataByRestApi {
    private static final String Home_Website = new String("http://192.168.0.16:5000");
    // private static final String Home_Website = new String("http://10.0.9.191:5000");
    // private static final String Home_Website = "http://ec2-13-59-195-3.us-east-2.compute.amazonaws.com";

    public static ArrayList<SingerType> getSingerTypes() {
        final String TAG = new String("GetDataByRestApi.getSingerTypes()");
        final String webUrl = Home_Website + "/api/SingArea";
        Log.i(TAG, "WebUrl = " + webUrl);

        ArrayList<SingerType> singerTypes = null;

        URL url = null;
        HttpURLConnection myConnection = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            url = new URL(webUrl);
            myConnection = (HttpURLConnection)url.openConnection();
            myConnection.setConnectTimeout(15000);
            myConnection.setReadTimeout(15000);
            myConnection.setRequestMethod("GET");
            myConnection.setDoInput(true);
            int responseCode = myConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "REST Web Service -> Succeeded to connect.");
                inputStream = myConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                StringBuilder sb = new StringBuilder();
                int readBuff = -1;
                while ( (readBuff=inputStreamReader.read()) != -1) {
                    sb.append((char)readBuff);
                }
                String result = sb.toString();  // the result
                Log.i(TAG, "Web output -> " + result);

                singerTypes = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject;
                SingerType singerType;
                int id;
                String areaNo;
                String areaNa;
                String areaEn;
                for (int i=0; i<jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    id = jsonObject.getInt("Id");
                    areaNo = jsonObject.getString("AreaNo");
                    areaNa = jsonObject.getString("AreaNa");
                    areaEn = jsonObject.getString("AreaEn");

                    singerType = new SingerType();
                    singerType.setId(id);
                    singerType.setAreaNo(areaNo);
                    singerType.setAreaNa(areaNa);
                    singerType.setAreaEn(areaEn);
                    singerType.setSex("0");
                    singerTypes.add(singerType);

                    singerType = new SingerType();
                    singerType.setId(id);
                    singerType.setAreaNo(areaNo);
                    singerType.setAreaNa(areaNa);
                    singerType.setAreaEn(areaEn);
                    singerType.setSex("1");
                    singerTypes.add(singerType);

                    singerType = new SingerType();
                    singerType.setId(id);
                    singerType.setAreaNo(areaNo);
                    singerType.setAreaNa(areaNa);
                    singerType.setAreaEn(areaEn);
                    singerType.setSex("2");
                    singerTypes.add(singerType);
                }
            } else {
                Log.i(TAG, "REST Web Service -> Failed to connect.");
                // singerTypes is null
                singerTypes = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "REST Web Service -> Failed due to exception.");
            // singerTypes is null
            singerTypes = null;
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

        return singerTypes;
    }

    public static ArrayList<Singer> getSingersBySingerType(SingerType singerType, int[] pageSize, int[] pageNo) {
        // using int[] pageSize to return the result of pageSize
        // using int[] pageNo to return the result of pageNo

        final String TAG = new String("GetDataByRestApi.getSingersBySingerType()");

        if (singerType == null) {
            // singerType cannot be null
            Log.d(TAG, "singerType is null.");
            return null;
        }

        // [HttpGet("{areaId}/{sex}/{pageSize}/{pageNo}/orderBy")]
        // GET api/values/5/"1"/10/1
        final String param = "/" + singerType.getId() + "/" + singerType.getSex() + "/" + pageSize[0] + "/" + pageNo[0] +"/" + "SingNa";
        final String webUrl = Home_Website + "/api/Singer" + param;
        Log.i(TAG, "WebUrl = " + webUrl);

        ArrayList<Singer> singers = null;

        URL url = null;
        HttpURLConnection myConnection = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            url = new URL(webUrl);
            myConnection = (HttpURLConnection)url.openConnection();
            myConnection.setConnectTimeout(15000);
            myConnection.setReadTimeout(15000);
            myConnection.setRequestMethod("GET");
            myConnection.setDoInput(true);
            int responseCode = myConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "REST Web Service -> Succeeded to connect.");
                inputStream = myConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                StringBuilder sb = new StringBuilder();
                int readBuff = -1;
                while ( (readBuff=inputStreamReader.read()) != -1) {
                    sb.append((char)readBuff);
                }
                String result = sb.toString();  // the result
                Log.i(TAG, "Web output -> " + result);

                JSONObject json = new JSONObject(result);
                pageNo[0] = json.getInt("PageNo");  // return to calling function
                pageSize[0] = json.getInt("PageSize"); // return to calling function
                singers = new ArrayList<>();
                Singer singer;
                // JSONArray jsonArray = new JSONArray(result);
                JSONArray jsonArray = new JSONArray(json.getString("Singers"));
                JSONObject jsonObject;
                int id;
                String singNo;
                String singNa;
                for (int i=0; i<jsonArray.length(); i++) {

                    jsonObject = jsonArray.getJSONObject(i);
                    id = jsonObject.getInt("Id");
                    singNo = jsonObject.getString("SingNo");
                    singNa = jsonObject.getString("SingNa");

                    singer = new Singer();
                    singer.setId(id);
                    singer.setSingerNo(singNo);
                    singer.setSingerNa(singNa);
                    singers.add(singer);
                }
            } else {
                Log.i(TAG, "REST Web Service -> Failed to connect.");
                // singerTypes is null
                singers = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "REST Web Service -> Failed due to exception.");
            // singerTypes is null
            singers = null;
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

        return singers;
    }
}
