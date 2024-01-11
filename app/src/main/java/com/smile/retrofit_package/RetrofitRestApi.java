package com.smile.retrofit_package;

import android.util.Log;

import com.smile.model.*;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RetrofitRestApi {
    private static final String TAG = "RetrofitRestApi";
    // implement Retrofit to get results synchronously
    public static LanguagesList getAllLanguages() {
        Retrofit localRetrofit = RetrofitClient.getInstance();
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

    public static SongsList getSongsByLanguage(Language language, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getSongsByLanguage.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)

        Retrofit localRetrofit = RetrofitClient.getInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = retrofitApiInterface.getSongsByLanguageIdOrderBy(languageId, pageSize, pageNo, orderBy);
        } else {
            call = retrofitApiInterface.getSongsByLanguageIdOrderByWithFilter(languageId, pageSize, pageNo, orderBy, filter);
        }

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

    public static SongsList getSongsByLanguageNumOfWords(Language language, int numOfWords, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getSongsByLanguageNumOfWords.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)

        Retrofit localRetrofit = RetrofitClient.getInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = retrofitApiInterface.getSongsByLanguageIdNumOfWords(languageId, numOfWords, pageSize, pageNo, orderBy);
        } else {
            call = retrofitApiInterface.getSongsByLanguageIdNumOfWordsWithFilter(languageId, numOfWords, pageSize, pageNo, orderBy, filter);
        }

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

    public static SongsList getNewSongsByLanguage(Language language, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getNewSongsByLanguage.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "";  // no order. Only the date that the song came in by descending order

        Retrofit localRetrofit = RetrofitClient.getInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = retrofitApiInterface.getNewSongsByLanguageId(languageId, pageSize, pageNo);
        } else {
            call = retrofitApiInterface.getNewSongsByLanguageIdWithFilter(languageId, pageSize, pageNo, filter);
        }

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

    public static SongsList getHotSongsByLanguage(Language language, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getHotSongsByLanguage.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "";  // no order by. Only the number that the song is ordered by descending order

        Retrofit localRetrofit = RetrofitClient.getInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = retrofitApiInterface.getHotSongsByLanguageId(languageId, pageSize, pageNo);
        } else {
            call = retrofitApiInterface.getHotSongsByLanguageIdWithFilter(languageId, pageSize, pageNo, filter);
        }

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }

    public static SingersList getSingersBySingerType(SingerType singerType, int pageSize, int pageNo, String filter) {
        if (singerType == null) {
            // singerType cannot be null
            Log.d(TAG, "getSingersBySingerType.singersList is null.");
            return null;
        }

        int areaId = singerType.getId();
        String sex = singerType.getSex();
        String orderBy = "SingNa";  // singer's name

        Retrofit localRetrofit = RetrofitClient.getInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SingersList> call;
        if ( (filter == null) || (filter.isEmpty()) )  {
            call = retrofitApiInterface.getSingersBySingerTypeId(areaId, sex, pageSize, pageNo, orderBy);
        } else {
            call = retrofitApiInterface.getSingersBySingerTypeIdWithFilter(areaId, sex, pageSize, pageNo, orderBy, filter);
        }

        SingersList singersList = null;
        try {
            singersList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return singersList;
    }

    public static SongsList getSongsBySinger(Singer singer, int pageSize, int pageNo, String filter) {
        if (singer == null) {
            // singer cannot be null
            Log.d(TAG, "getSongsBySinger.songsList is null.");
            return null;
        }

        int singerId = singer.getId();
        // String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)
        String orderBy = "SongNa";  // order by song's name

        Retrofit localRetrofit = RetrofitClient.getInstance();
        RetrofitApiInterface retrofitApiInterface = localRetrofit.create(RetrofitApiInterface.class);
        Call<SongsList> call;
        if ( (filter == null) || (filter.isEmpty()) )  {
            call = retrofitApiInterface.getSongsBySingerId(singerId, pageSize, pageNo, orderBy);
        } else {
            call = retrofitApiInterface.getSongsBySingerIdWithFilter(singerId, pageSize, pageNo, orderBy, filter);
        }

        SongsList songsList = null;
        try {
            songsList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songsList;
    }
}
