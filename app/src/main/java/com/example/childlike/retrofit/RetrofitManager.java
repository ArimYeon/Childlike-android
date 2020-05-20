package com.example.childlike.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private final static String BASE_URL = "http://13.209.42.34:8000/api";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public IRetrofitApi createApi(){
        return retrofit.create(IRetrofitApi.class);
    }
}
