package com.example.childlike.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

public class RetrofitAppuserResponse {

    @SerializedName("uid")
    String uid;

    @SerializedName("age")
    int age;

    @SerializedName("gender")
    String gender;

    @SerializedName("uname")
    String uname;

    @SerializedName("email")
    String email;
}
