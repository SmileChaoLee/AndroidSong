package com.smile.retrofit_package;

import android.util.Log;

import com.smile.model.SingerType;
import com.smile.model.SingersList;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetDataByRetrofitRestApi {

    // implement Retrofit to get results synchronously
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
}
