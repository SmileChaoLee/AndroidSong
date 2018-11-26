package com.smile.retrofit_package;

import com.smile.model.SingerTypesList;
import com.smile.model.SingersList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitApiInterface {
    @GET("api/Singareas/SingerTypes")
    Call<SingerTypesList> getAllSingerTypes();

    @GET("api/Singareas/{id}/Singers/{sex}/{pageSize}/{pageNo}/{orderBy}")
    Call<SingersList> getSingersBySingerType(@Path("id") int id, @Path("sex") String sex, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy);
}
