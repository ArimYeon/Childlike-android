package com.example.childlike.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private final static String BASE_URL = "http://52.79.242.93:8000/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static IRetrofitApi createApi(){
        return retrofit.create(IRetrofitApi.class);
    }
}
