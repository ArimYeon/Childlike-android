package com.example.childlike.retrofit.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestItypeGet {
    @SerializedName("itype")
    @Expose
    private String itype;

    @SerializedName("ctext")
    @Expose
    private String ctext;

    public RequestItypeGet(String itype, String ctext) {
        this.itype = itype;
        this.ctext = ctext;
    }

    public String getItype() {
        return itype;
    }

    public String getCtext() {
        return ctext;
    }
}
