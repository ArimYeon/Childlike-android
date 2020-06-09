package com.example.childlike.retrofit;

import com.example.childlike.retrofit.retrofitdata.RequestAppuserPost;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IRetrofitApi {

    @POST("api/appuser/")
    Call<RequestAppuserPost> postAppuser(
            @Body RequestAppuserPost appuser
    );

    @DELETE("api/appuser/{uid}")
    Call<ResponseBody> deleteAppuser(
            @Path("uid") String uid
    );

    @Multipart
    @POST("image/upload/")
    Call<RequestBody> uploadFile(
            @Part MultipartBody.Part file
    );
}
