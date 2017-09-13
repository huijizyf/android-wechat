package com.donute.tencenthelper.beans;

/**
 * Created by zhouyufei on 2017/9/10.
 */

public class Function {
    private String name;
    private int code;

    public static final int sendToAllFriend=0;
    public static final int sendToAllGroup=1;
    public static final int sendToAll=2;

    public Function() {
    }

    public Function(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
