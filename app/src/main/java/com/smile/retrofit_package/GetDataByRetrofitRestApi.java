package com.smile.retrofit_package;

import android.util.Log;

import com.smile.model.*;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetDataByRetrofitRestApi {

    // implement Retrofit to get results synchronously
    public static LanguagesList getAllLanguages() {
        final String TAG = new String("GetDataByRestApi.getAllLanguages()");

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<LanguagesList> call = retrofitApiInterface.getAllLanguages();

        LanguagesList languagesList = null;
        try {
            languagesList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return languagesList;
    }

    public static SongsList getSongsByLanguage(Language language, int pageSize, int pageNo) {
        final String TAG = new String("GetDataByRestApi.getSongsByLanguage()");

        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call = retrofitApiInterface.getSongsByLanguageId(languageId, pageSize, pageNo, orderBy);

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

    public static SongsList getSongsByLanguageNumOfWords(Language language, int numOfWords, int pageSize, int pageNo) {
        final String TAG = new String("GetDataByRestApi.getSongsByLanguageNumOfWords()");

        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call = retrofitApiInterface.getSongsByLanguageIdNumOfWords(languageId, numOfWords, pageSize, pageNo, orderBy);

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

    public static SongsList getNewSongsByLanguage(Language language, int pageSize, int pageNo) {
        final String TAG = new String("GetDataByRestApi.getNewSongsByLanguage()");

        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "";  // no order. Only the date that the song came in by descending order

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call = retrofitApiInterface.getNewSongsByLanguageId(languageId, pageSize, pageNo);

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

    public static SongsList getHotSongsByLanguage(Language language, int pageSize, int pageNo) {
        final String TAG = new String("GetDataByRestApi.getHotSongsByLanguage()");

        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "";  // no order by. Only the number that the song is ordered by descending order

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call = retrofitApiInterface.getHotSongsByLanguageId(languageId, pageSize, pageNo);

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

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
        Call<SingersList> call = retrofitApiInterface.getSingersBySingerTypeId(areaId, sex, pageSize, pageNo, orderBy);

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
        String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)

        Retrofit localRetrofit = RetrofitClient.getRetrofitInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call = retrofitApiInterface.getSongsBySingerId(singerId, pageSize, pageNo, orderBy);

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }
}
