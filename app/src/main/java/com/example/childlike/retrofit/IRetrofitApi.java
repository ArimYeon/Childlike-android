package com.example.childlike.retrofit;

import com.example.childlike.retrofit.retrofitdata.RequestAppuserPost;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IRetrofitApi {

    @POST("appuser/")
    Call<RequestAppuserPost> postAppuser(
            @Body RequestAppuserPost appuser
    );
}
