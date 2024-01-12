package com.smile.retrofit_package;

import android.util.Log;

import com.smile.model.*;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RestApi {
    private static final String TAG = "RestApi";
    // implement Retrofit to get results synchronously
    public static LanguageList getAllLanguages() {
        Retrofit localRetrofit = Client.getInstance();
        ApiInterface apiInterface = localRetrofit.create(ApiInterface.class);
        Call<LanguageList> call = apiInterface.getAllLanguages();

        LanguageList languageList = null;
        try {
            languageList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return languageList;
    }

    public static SongList getSongsByLanguage(Language language, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getSongsByLanguage.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)

        Retrofit localRetrofit = Client.getInstance();
        ApiInterface apiInterface = localRetrofit.create(ApiInterface.class);
        Call<SongList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = apiInterface.getSongsByLanguageIdOrderBy(languageId, pageSize, pageNo, orderBy);
        } else {
            call = apiInterface.getSongsByLanguageIdOrderByWithFilter(languageId, pageSize, pageNo, orderBy, filter);
        }

        SongList songList = null;
        try {
            songList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songList;
    }

    public static SongList getSongsByLanguageNumOfWords(Language language, int numOfWords, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getSongsByLanguageNumOfWords.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)

        Retrofit localRetrofit = Client.getInstance();
        ApiInterface apiInterface = localRetrofit.create(ApiInterface.class);
        Call<SongList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = apiInterface.getSongsByLanguageIdNumOfWords(languageId, numOfWords, pageSize, pageNo, orderBy);
        } else {
            call = apiInterface.getSongsByLanguageIdNumOfWordsWithFilter(languageId, numOfWords, pageSize, pageNo, orderBy, filter);
        }

        SongList songList = null;
        try {
            songList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songList;
    }

    public static SongList getNewSongsByLanguage(Language language, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getNewSongsByLanguage.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "";  // no order. Only the date that the song came in by descending order

        Retrofit localRetrofit = Client.getInstance();
        ApiInterface apiInterface = localRetrofit.create(ApiInterface.class);
        Call<SongList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = apiInterface.getNewSongsByLanguageId(languageId, pageSize, pageNo);
        } else {
            call = apiInterface.getNewSongsByLanguageIdWithFilter(languageId, pageSize, pageNo, filter);
        }

        SongList songList = null;
        try {
            songList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songList;
    }

    public static SongList getHotSongsByLanguage(Language language, int pageSize, int pageNo, String filter) {
        if (language == null) {
            // singer cannot be null
            Log.d(TAG, "getHotSongsByLanguage.songsList is null.");
            return null;
        }

        int languageId = language.getId();
        String orderBy = "";  // no order by. Only the number that the song is ordered by descending order

        Retrofit localRetrofit = Client.getInstance();
        ApiInterface apiInterface = localRetrofit.create(ApiInterface.class);
        Call<SongList> call;
        if ( (filter == null) || (filter.isEmpty()) ) {
            call = apiInterface.getHotSongsByLanguageId(languageId, pageSize, pageNo);
        } else {
            call = apiInterface.getHotSongsByLanguageIdWithFilter(languageId, pageSize, pageNo, filter);
        }

        SongList songList = null;
        try {
            songList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songList;
    }

    public static SingerList getSingersBySingerType(SingerType singerType, int pageSize, int pageNo, String filter) {
        if (singerType == null) {
            // singerType cannot be null
            Log.d(TAG, "getSingersBySingerType.singersList is null.");
            return null;
        }

        int areaId = singerType.getId();
        String sex = singerType.getSex();
        String orderBy = "SingNa";  // singer's name

        Retrofit localRetrofit = Client.getInstance();
        ApiInterface apiInterface = localRetrofit.create(ApiInterface.class);
        Call<SingerList> call;
        if ( (filter == null) || (filter.isEmpty()) )  {
            call = apiInterface.getSingersBySingerTypeId(areaId, sex, pageSize, pageNo, orderBy);
        } else {
            call = apiInterface.getSingersBySingerTypeIdWithFilter(areaId, sex, pageSize, pageNo, orderBy, filter);
        }

        SingerList singerList = null;
        try {
            singerList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return singerList;
    }

    public static SongList getSongsBySinger(Singer singer, int pageSize, int pageNo, String filter) {
        if (singer == null) {
            // singer cannot be null
            Log.d(TAG, "getSongsBySinger.songsList is null.");
            return null;
        }

        int singerId = singer.getId();
        // String orderBy = "NumWordsSongNa";  // order by (number of words + song's name)
        String orderBy = "SongNa";  // order by song's name

        Retrofit localRetrofit = Client.getInstance();
        ApiInterface apiInterface = localRetrofit.create(ApiInterface.class);
        Call<SongList> call;
        if ( (filter == null) || (filter.isEmpty()) )  {
            call = apiInterface.getSongsBySingerId(singerId, pageSize, pageNo, orderBy);
        } else {
            call = apiInterface.getSongsBySingerIdWithFilter(singerId, pageSize, pageNo, orderBy, filter);
        }

        SongList songList = null;
        try {
            songList = call.execute().body();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return songList;
    }
}
