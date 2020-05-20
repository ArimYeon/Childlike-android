package com.example.childlike.retrofit;

import com.example.childlike.retrofit.retrofitmodel.RetrofitAppuserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRetrofitApi {

    @GET("appuser/{uid}")
    public Call<ArrayList<RetrofitAppuserResponse>> getAppuser(
            @Path("uid") String uid
    );
}
