package com.androidlo.wearing.model;

/**
 * Created by 62361 on 2019/4/4.
 */

public class User {
    String uriStr;
    String name;
    String sign;

    public User(String uriStr,String name,String sign){
        this.uriStr = uriStr;
        this.name = name;
        this.sign = sign;
    }

    public String getUriStr() {
        return uriStr;
    }

    public void setUriStr(String uriStr) {
        this.uriStr = uriStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
