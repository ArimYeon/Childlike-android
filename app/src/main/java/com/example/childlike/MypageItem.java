package com.example.childlike;

public class MypageItem {

    int img;
    String name;
    String age;
    String sex;


    public MypageItem(int img, String name, String age, String sex) {
        this.img = img;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }
}
