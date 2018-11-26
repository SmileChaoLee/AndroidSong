package com.smile.retrofit_package;

import android.util.Log;

import com.smile.model.Singer;
import com.smile.model.SingerType;
import com.smile.model.SingerTypesList;
import com.smile.model.SingersList;
import com.smile.model.SongsList;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetDataByRetrofitRestApi {

    // implement Retrofit to get results synchronously
    public static SingerTypesList getAllSingerTypes() {
        final String TAG = new String("GetDataByRestApi.getAllSingerTypes()");

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SingerTypesList> call = retrofitApiInterface.getAllSingerTypes();

        SingerTypesList singerTypesList = null;
        try {
            singerTypesList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return singerTypesList;
    }

    public static SingersList getSingersBySingerType(SingerType singerType, int pageSize, int pageNo) {
        final String TAG = new String("GetDataByRestApi.getSingersBySingerType()");

        if (singerType == null) {
            // singerType cannot be null
            Log.d(TAG, "singersList is null.");
            return null;
        }

        int areaId = singerType.getId();
        String sex = singerType.getSex();
        String orderBy = "SingNa";  // singer's name

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SingersList> call = retrofitApiInterface.getSingersBySingerType(areaId, sex, pageSize, pageNo, orderBy);

        SingersList singersList = null;
        try {
            singersList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return singersList;
    }

    public static SongsList getSongsBySinger(Singer singer, int pageSize, int pageNo) {
        final String TAG = new String("GetDataByRestApi.getSongsBySinger()");

        if (singer == null) {
            // singer cannot be null
            Log.d(TAG, "songsList is null.");
            return null;
        }

        int singerId = singer.getId();
        String orderBy = "SongNa";  // song's name

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call = retrofitApiInterface.getSongsBySinger(singerId, pageSize, pageNo, orderBy);

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }
}
