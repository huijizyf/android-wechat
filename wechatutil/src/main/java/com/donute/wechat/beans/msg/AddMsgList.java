package com.donute.wechat.beans.msg;

/**
 * Created by zhouyufei on 2017/9/9.
 */

public class AddMsgList
{
    private String MsgId;

    private String FromUserName;

    private String ToUserName;

    private int MsgType;

    private String Content;

    private int Status;

    private int ImgStatus;

    private int CreateTime;

    private int VoiceLength;

    private int PlayLength;

    private String FileName;

    private String FileSize;

    private String MediaId;

    private String Url;

    private int AppMsgType;

    private int StatusNotifyCode;

    private String StatusNotifyUserName;

    private RecommendInfo RecommendInfo;

    private int ForwardFlag;

    private AppInfo AppInfo;

    private int HasProductId;

    private String Ticket;

    private int ImgHeight;

    private int ImgWidth;

    private int SubMsgType;

    private long NewMsgId;

    private String OriContent;

    public void setMsgId(String MsgId){
        this.MsgId = MsgId;
    }
    public String getMsgId(){
        return this.MsgId;
    }
    public void setFromUserName(String FromUserName){
        this.FromUserName = FromUserName;
    }
    public String getFromUserName(){
        return this.FromUserName;
    }
    public void setToUserName(String ToUserName){
        this.ToUserName = ToUserName;
    }
    public String getToUserName(){
        return this.ToUserName;
    }
    public void setMsgType(int MsgType){
        this.MsgType = MsgType;
    }
    public int getMsgType(){
        return this.MsgType;
    }
    public void setContent(String Content){
        this.Content = Content;
    }
    public String getContent(){
        return this.Content;
    }
    public void setStatus(int Status){
        this.Status = Status;
    }
    public int getStatus(){
        return this.Status;
    }
    public void setImgStatus(int ImgStatus){
        this.ImgStatus = ImgStatus;
    }
    public int getImgStatus(){
        return this.ImgStatus;
    }
    public void setCreateTime(int CreateTime){
        this.CreateTime = CreateTime;
    }
    public int getCreateTime(){
        return this.CreateTime;
    }
    public void setVoiceLength(int VoiceLength){
        this.VoiceLength = VoiceLength;
    }
    public int getVoiceLength(){
        return this.VoiceLength;
    }
    public void setPlayLength(int PlayLength){
        this.PlayLength = PlayLength;
    }
    public int getPlayLength(){
        return this.PlayLength;
    }
    public void setFileName(String FileName){
        this.FileName = FileName;
    }
    public String getFileName(){
        return this.FileName;
    }
    public void setFileSize(String FileSize){
        this.FileSize = FileSize;
    }
    public String getFileSize(){
        return this.FileSize;
    }
    public void setMediaId(String MediaId){
        this.MediaId = MediaId;
    }
    public String getMediaId(){
        return this.MediaId;
    }
    public void setUrl(String Url){
        this.Url = Url;
    }
    public String getUrl(){
        return this.Url;
    }
    public void setAppMsgType(int AppMsgType){
        this.AppMsgType = AppMsgType;
    }
    public int getAppMsgType(){
        return this.AppMsgType;
    }
    public void setStatusNotifyCode(int StatusNotifyCode){
        this.StatusNotifyCode = StatusNotifyCode;
    }
    public int getStatusNotifyCode(){
        return this.StatusNotifyCode;
    }
    public void setStatusNotifyUserName(String StatusNotifyUserName){
        this.StatusNotifyUserName = StatusNotifyUserName;
    }
    public String getStatusNotifyUserName(){
        return this.StatusNotifyUserName;
    }
    public void setRecommendInfo(RecommendInfo RecommendInfo){
        this.RecommendInfo = RecommendInfo;
    }
    public RecommendInfo getRecommendInfo(){
        return this.RecommendInfo;
    }
    public void setForwardFlag(int ForwardFlag){
        this.ForwardFlag = ForwardFlag;
    }
    public int getForwardFlag(){
        return this.ForwardFlag;
    }
    public void setAppInfo(AppInfo AppInfo){
        this.AppInfo = AppInfo;
    }
    public AppInfo getAppInfo(){
        return this.AppInfo;
    }
    public void setHasProductId(int HasProductId){
        this.HasProductId = HasProductId;
    }
    public int getHasProductId(){
        return this.HasProductId;
    }
    public void setTicket(String Ticket){
        this.Ticket = Ticket;
    }
    public String getTicket(){
        return this.Ticket;
    }
    public void setImgHeight(int ImgHeight){
        this.ImgHeight = ImgHeight;
    }
    public int getImgHeight(){
        return this.ImgHeight;
    }
    public void setImgWidth(int ImgWidth){
        this.ImgWidth = ImgWidth;
    }
    public int getImgWidth(){
        return this.ImgWidth;
    }
    public void setSubMsgType(int SubMsgType){
        this.SubMsgType = SubMsgType;
    }
    public int getSubMsgType(){
        return this.SubMsgType;
    }
    public void setNewMsgId(long NewMsgId){
        this.NewMsgId = NewMsgId;
    }
    public long getNewMsgId(){
        return this.NewMsgId;
    }
    public void setOriContent(String OriContent){
        this.OriContent = OriContent;
    }
    public String getOriContent(){
        return this.OriContent;
    }
}