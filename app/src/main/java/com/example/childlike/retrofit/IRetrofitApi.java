package com.example.childlike.retrofit;

import com.example.childlike.retrofit.retrofitdata.RequestAppuserPost;
import com.example.childlike.retrofit.retrofitdata.RequestChildrenGet;
import com.example.childlike.retrofit.retrofitdata.RequestChildrenPost;
import com.example.childlike.retrofit.retrofitdata.RequestCommentsGet;
import com.example.childlike.retrofit.retrofitdata.RequestItypeGet;
import com.example.childlike.retrofit.retrofitdata.RequestResultGet;
import com.example.childlike.retrofit.retrofitdata.RequestResultPost;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IRetrofitApi {

    //로그인 시 회원 정보 저장
    @POST("api/appuser/")
    Call<RequestAppuserPost> postAppuser(
            @Body RequestAppuserPost appuser
    );

    //회원 탈퇴 시 정보 삭제
    @DELETE("api/appuser/{uid}")
    Call<ResponseBody> deleteAppuser(
            @Path("uid") String uid
    );

    //테스트 이미지 업로드
    @Multipart
    @POST("image/userimage/")
    Call<RequestBody> uploadFile(
            @Part MultipartBody.Part file
    );

    //어린이 프로필 사진 업로드
    @Multipart
    @POST("image/userprofile/")
    Call<RequestBody> uploadProfile(
            @Part MultipartBody.Part file
    );

    //어린이 추가하기
    @POST("api/children/")
    Call<RequestChildrenPost> postChildren(
            @Body RequestChildrenPost children
    );

    //유저의 어린이 리스트 가져오기
    @GET("api/children/{uid}")
    Call<ArrayList<RequestChildrenGet>> getChildren(
            @Path("uid") String userId
    );

    //어린이 한 명의 결과 리스트로 받기
    @GET("api/result/{uid}/{name}")
    Call<ArrayList<RequestResultGet>> getResult(
            @Path("uid") String uid,
            @Path("name") String name
    );

    //테스트 결과(itype) 과 이미지명, 정보 등 저장
    @POST("api/result/")
    Call<RequestResultPost> postResult(
            @Body RequestResultPost result
    );

    //itype에 맞는 코멘트 가져오기
    @GET("api/comments/{itype}")
    Call<RequestCommentsGet> getComment(
            @Path("itype") String itype
    );

    //테스트 이미지명 넣으면 모델 돌려서 분석 후 타입값 반환받는 api
    @GET("api/treemodel/call/{imgname}")
    Call<RequestItypeGet> getItype(
            @Path("imgname") String imgname
    );
}
