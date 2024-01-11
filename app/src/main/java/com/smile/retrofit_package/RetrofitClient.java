package com.smile.retrofit_package;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";
    private static final String BASE_URL = "http://137.184.120.171/";
    // For emulator
    // private static final String BASE_URL = "http://10.0.2.2:5000/";
    // For local computer with the same Ethernet (have to set port 5000 to be listening in Firewall)
    // private static final String BASE_URL = "http://192.168.0.108:5000/";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        Log.d(TAG, "getInstance");
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS).build();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
