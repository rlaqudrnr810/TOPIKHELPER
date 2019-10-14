package com.example.topikhelper;

public class UserInfo {

    public String email = "";
    public String password = "";
    public String nickname = "";
    public String sex = "";
    public String country = "";
    //판매자 생성자
    public UserInfo(String email, String password, String nickname, String sex, String country) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
        this.country = country;
    }
}