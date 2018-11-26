package com.smile.retrofit_package;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = new String("http://192.168.0.11:5000/");
    // private static final String BASE_URL = new String("http://10.0.9.191:5000/");
    // private static final String BASE_URL = "http://ec2-13-59-195-3.us-east-2.compute.amazonaws.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
