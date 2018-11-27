package com.smile.retrofit_package;

import com.smile.model.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitApiInterface {
    @GET("api/Singareas/SingerTypes")
    Call<SingerTypesList> getAllSingerTypes();

    @GET("api/Singareas/{id}/Singers/{sex}/{pageSize}/{pageNo}/{orderBy}")
    Call<SingersList> getSingersBySingerTypeId(@Path("id") int id, @Path("sex") String sex, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy);

    // [HttpGet("{id}/[Action]/{pageSize}/{pageNo}/{orderBy}")] from ASP.NET Core API
    @GET("api/Singers/{id}/Songs/{pageSize}/{pageNo}/{orderBy}")
    Call<SongsList> getSongsBySingerId(@Path("id") int id, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy);

    @GET("api/Languages")
    Call<LanguagesList> getAllLanguages();

    @GET("api/Languages/{id}/Songs/{pageSize}/{pageNo}/{orderBy}")
    Call<LanguagesList> getSongsByLanguageId(@Path("id") int id, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy);
}
