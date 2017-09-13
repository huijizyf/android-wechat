package com.donute.wechat.beans.msg;

/**
 * Created by zhouyufei on 2017/9/9.
 */

public class RecommendInfo
{
    private String UserName;

    private String NickName;

    private int QQNum;

    private String Province;

    private String City;

    private String Content;

    private String Signature;

    private String Alias;

    private int Scene;

    private int VerifyFlag;

    private int AttrStatus;

    private int Sex;

    private String Ticket;

    private int OpCode;

    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    public String getUserName(){
        return this.UserName;
    }
    public void setNickName(String NickName){
        this.NickName = NickName;
    }
    public String getNickName(){
        return this.NickName;
    }
    public void setQQNum(int QQNum){
        this.QQNum = QQNum;
    }
    public int getQQNum(){
        return this.QQNum;
    }
    public void setProvince(String Province){
        this.Province = Province;
    }
    public String getProvince(){
        return this.Province;
    }
    public void setCity(String City){
        this.City = City;
    }
    public String getCity(){
        return this.City;
    }
    public void setContent(String Content){
        this.Content = Content;
    }
    public String getContent(){
        return this.Content;
    }
    public void setSignature(String Signature){
        this.Signature = Signature;
    }
    public String getSignature(){
        return this.Signature;
    }
    public void setAlias(String Alias){
        this.Alias = Alias;
    }
    public String getAlias(){
        return this.Alias;
    }
    public void setScene(int Scene){
        this.Scene = Scene;
    }
    public int getScene(){
        return this.Scene;
    }
    public void setVerifyFlag(int VerifyFlag){
        this.VerifyFlag = VerifyFlag;
    }
    public int getVerifyFlag(){
        return this.VerifyFlag;
    }
    public void setAttrStatus(int AttrStatus){
        this.AttrStatus = AttrStatus;
    }
    public int getAttrStatus(){
        return this.AttrStatus;
    }
    public void setSex(int Sex){
        this.Sex = Sex;
    }
    public int getSex(){
        return this.Sex;
    }
    public void setTicket(String Ticket){
        this.Ticket = Ticket;
    }
    public String getTicket(){
        return this.Ticket;
    }
    public void setOpCode(int OpCode){
        this.OpCode = OpCode;
    }
    public int getOpCode(){
        return this.OpCode;
    }
}
