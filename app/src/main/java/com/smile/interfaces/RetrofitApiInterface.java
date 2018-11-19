package com.smile.interfaces;


import com.smile.model.SingerType;
import com.smile.model.SingerTypesList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiInterface {
    @GET("api/SingerType")
    Call<SingerTypesList> getAllSingerTypes();
}
