package com.example.childlike.dataclass;

public class ResultTypeItem {
    String img;
    String date, type;

    public ResultTypeItem(String img, String date, String type) {
        this.img = img;
        this.date = date;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String getImg() {
        return img;
    }

    public String getType() {
        return type;
    }
}
