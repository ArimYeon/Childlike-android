package com.example.childlike.retrofit.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestResultGet {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("uimage")
    @Expose
    private String uimage;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("itype")
    @Expose
    private String itype;

    public RequestResultGet(String id, String name, String uimage, String date, String uid, String itype) {
        this.id = id;
        this.name = name;
        this.uimage = uimage;
        this.date = date;
        this.uid = uid;
        this.itype = itype;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUimage() {
        return uimage;
    }

    public String getDate() {
        return date;
    }

    public String getUid() {
        return uid;
    }

    public String getItype() {
        return itype;
    }
}
