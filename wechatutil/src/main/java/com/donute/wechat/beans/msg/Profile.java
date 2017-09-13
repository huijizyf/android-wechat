package com.donute.wechat.beans.msg;

/**
 * Created by zhouyufei on 2017/9/9.
 */

public class Profile
{
    private int BitFlag;

    private UserName UserName;

    private NickName NickName;

    private int BindUin;

    private BindEmail BindEmail;

    private BindMobile BindMobile;

    private int Status;

    private int Sex;

    private int PersonalCard;

    private String Alias;

    private int HeadImgUpdateFlag;

    private String HeadImgUrl;

    private String Signature;

    public void setBitFlag(int BitFlag){
        this.BitFlag = BitFlag;
    }
    public int getBitFlag(){
        return this.BitFlag;
    }
    public void setUserName(UserName UserName){
        this.UserName = UserName;
    }
    public UserName getUserName(){
        return this.UserName;
    }
    public void setNickName(NickName NickName){
        this.NickName = NickName;
    }
    public NickName getNickName(){
        return this.NickName;
    }
    public void setBindUin(int BindUin){
        this.BindUin = BindUin;
    }
    public int getBindUin(){
        return this.BindUin;
    }
    public void setBindEmail(BindEmail BindEmail){
        this.BindEmail = BindEmail;
    }
    public BindEmail getBindEmail(){
        return this.BindEmail;
    }
    public void setBindMobile(BindMobile BindMobile){
        this.BindMobile = BindMobile;
    }
    public BindMobile getBindMobile(){
        return this.BindMobile;
    }
    public void setStatus(int Status){
        this.Status = Status;
    }
    public int getStatus(){
        return this.Status;
    }
    public void setSex(int Sex){
        this.Sex = Sex;
    }
    public int getSex(){
        return this.Sex;
    }
    public void setPersonalCard(int PersonalCard){
        this.PersonalCard = PersonalCard;
    }
    public int getPersonalCard(){
        return this.PersonalCard;
    }
    public void setAlias(String Alias){
        this.Alias = Alias;
    }
    public String getAlias(){
        return this.Alias;
    }
    public void setHeadImgUpdateFlag(int HeadImgUpdateFlag){
        this.HeadImgUpdateFlag = HeadImgUpdateFlag;
    }
    public int getHeadImgUpdateFlag(){
        return this.HeadImgUpdateFlag;
    }
    public void setHeadImgUrl(String HeadImgUrl){
        this.HeadImgUrl = HeadImgUrl;
    }
    public String getHeadImgUrl(){
        return this.HeadImgUrl;
    }
    public void setSignature(String Signature){
        this.Signature = Signature;
    }
    public String getSignature(){
        return this.Signature;
    }
}
