package com.example.childlike.dataclass;

public class MypageItem {

    String img;
    String name;
    String age;
    String sex;


    public MypageItem(String img, String name, String age, String sex) {
        this.img = img;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }
}
