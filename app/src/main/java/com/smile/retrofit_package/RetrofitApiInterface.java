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

    @GET("api/Singareas/{id}/Singers/{sex}/{pageSize}/{pageNo}/{orderBy}/{filter}")
    Call<SingersList> getSingersBySingerTypeWithFilter(@Path("id") int id, @Path("sex") String sex, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy, @Path("filter") String filter);

    // [HttpGet("{id}/[Action]/{pageSize}/{pageNo}/{orderBy}")] from ASP.NET Core API
    @GET("api/Singers/{id}/Songs/{pageSize}/{pageNo}/{orderBy}")
    Call<SongsList> getSongsBySingerId(@Path("id") int id, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy);

    @GET("api/Languages")
    Call<LanguagesList> getAllLanguages();

    @GET("api/Languages/{id}/Songs/{pageSize}/{pageNo}/{orderBy}")
    Call<SongsList> getSongsByLanguageId(@Path("id") int id, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy);

    @GET("api/Languages/{id}/{numOfWords}/Songs/{pageSize}/{pageNo}/{orderBy}")
    Call<SongsList> getSongsByLanguageIdNumOfWords(@Path("id") int id, @Path("numOfWords") int numOfWords, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo, @Path("orderBy") String orderBy);

    // no order by, Only the date that the song came by descending order
    @GET("api/Languages/{id}/NewSongs/{pageSize}/{pageNo}")
    Call<SongsList> getNewSongsByLanguageId(@Path("id") int id, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo);

    // no order by, Only the number that the song is ordered by descending order
    @GET("api/Languages/{id}/HotSongs/{pageSize}/{pageNo}")
    Call<SongsList> getHotSongsByLanguageId(@Path("id") int id, @Path("pageSize") int pageSize, @Path("pageNo") int pageNo);
}
