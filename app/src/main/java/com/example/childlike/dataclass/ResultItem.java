package com.example.childlike.dataclass;

public class ResultItem {

    int img;
    String date, result;

    public ResultItem(int img, String date, String result) {
        this.img = img;
        this.date = date;
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public int getImg() {
        return img;
    }

    public String getResult() {
        return result;
    }
}
