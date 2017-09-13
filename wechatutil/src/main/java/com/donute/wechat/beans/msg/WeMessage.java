package com.donute.wechat.beans.msg;

/**
 * Created by zhouyufei on 2017/9/9.
 */

import java.util.ArrayList;
import java.util.List;
import com.donute.wechat.beans.SyncKey;

public class WeMessage
{
    private BaseResponse BaseResponse;

    private int AddMsgCount;

    private List<AddMsgList> AddMsgList;

    private int ModContactCount;

    private List<String> ModContactList;

    private int DelContactCount;

    private List<String> DelContactList;

    private int ModChatRoomMemberCount;

    private List<String> ModChatRoomMemberList;

    private Profile Profile;

    private int ContinueFlag;

    private SyncKey SyncKey;

    private String SKey;

    private SyncKey SyncCheckKey;

    public void setBaseResponse(BaseResponse BaseResponse){
        this.BaseResponse = BaseResponse;
    }
    public BaseResponse getBaseResponse(){
        return this.BaseResponse;
    }
    public void setAddMsgCount(int AddMsgCount){
        this.AddMsgCount = AddMsgCount;
    }
    public int getAddMsgCount(){
        return this.AddMsgCount;
    }
    public void setAddMsgList(List<AddMsgList> AddMsgList){
        this.AddMsgList = AddMsgList;
    }
    public List<AddMsgList> getAddMsgList(){
        return this.AddMsgList;
    }
    public void setModContactCount(int ModContactCount){
        this.ModContactCount = ModContactCount;
    }
    public int getModContactCount(){
        return this.ModContactCount;
    }
    public void setModContactList(List<String> ModContactList){
        this.ModContactList = ModContactList;
    }
    public List<String> getModContactList(){
        return this.ModContactList;
    }
    public void setDelContactCount(int DelContactCount){
        this.DelContactCount = DelContactCount;
    }
    public int getDelContactCount(){
        return this.DelContactCount;
    }
    public void setDelContactList(List<String> DelContactList){
        this.DelContactList = DelContactList;
    }
    public List<String> getDelContactList(){
        return this.DelContactList;
    }
    public void setModChatRoomMemberCount(int ModChatRoomMemberCount){
        this.ModChatRoomMemberCount = ModChatRoomMemberCount;
    }
    public int getModChatRoomMemberCount(){
        return this.ModChatRoomMemberCount;
    }
    public void setModChatRoomMemberList(List<String> ModChatRoomMemberList){
        this.ModChatRoomMemberList = ModChatRoomMemberList;
    }
    public List<String> getModChatRoomMemberList(){
        return this.ModChatRoomMemberList;
    }
    public void setProfile(Profile Profile){
        this.Profile = Profile;
    }
    public Profile getProfile(){
        return this.Profile;
    }
    public void setContinueFlag(int ContinueFlag){
        this.ContinueFlag = ContinueFlag;
    }
    public int getContinueFlag(){
        return this.ContinueFlag;
    }
    public void setSyncKey(SyncKey SyncKey){
        this.SyncKey = SyncKey;
    }
    public SyncKey getSyncKey(){
        return this.SyncKey;
    }
    public void setSKey(String SKey){
        this.SKey = SKey;
    }
    public String getSKey(){
        return this.SKey;
    }
    public void setSyncCheckKey(SyncKey SyncCheckKey){
        this.SyncCheckKey = SyncCheckKey;
    }
    public SyncKey getSyncCheckKey(){
        return this.SyncCheckKey;
    }
}
