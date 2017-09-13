package com.donute.wechat.beans.msg;

/**
 * Created by zhouyufei on 2017/9/9.
 */

public class BaseResponse
{
    private int Ret;

    private String ErrMsg;

    public void setRet(int Ret){
        this.Ret = Ret;
    }
    public int getRet(){
        return this.Ret;
    }
    public void setErrMsg(String ErrMsg){
        this.ErrMsg = ErrMsg;
    }
    public String getErrMsg(){
        return this.ErrMsg;
    }
}