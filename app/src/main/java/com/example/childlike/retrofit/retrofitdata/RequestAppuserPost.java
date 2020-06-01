package com.example.childlike.retrofit.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestAppuserPost {
    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    public RequestAppuserPost(String uid, String age, String gender, String name, String email) {
        this.uid = uid;
        this.age = age;
        this.gender = gender;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
