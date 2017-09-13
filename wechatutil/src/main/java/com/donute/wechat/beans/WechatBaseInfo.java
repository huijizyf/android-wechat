package com.donute.wechat.beans;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.Expose;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by zhouyufei on 2017/9/6.
 */

public class WechatBaseInfo {
    private int Count;
    private List<Contact> ContactList;
    private SyncKey SyncKey;
    private User User;
    private String ChatSet;
    private String SKey;
    private int ClientVersion;
    private int SystemTime;
    private int GrayScale;
    private int InviteStartCount;
    private int MPSubscribeMsgCount;
    private int ClickReportInterval;

    @Expose(serialize = false)
    private String synckey="";

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public List<Contact> getContactList() {
        return ContactList;
    }

    public void setContactList(List<Contact> contactList) {
        ContactList = contactList;
    }

    public SyncKey getSyncKey() {
        return SyncKey;
    }

    public void setSyncKey(SyncKey syncKey) {
        SyncKey = syncKey;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public String getChatSet() {
        return ChatSet;
    }

    public void setChatSet(String chatSet) {
        ChatSet = chatSet;
    }

    public String getSKey() {
        return SKey;
    }

    public void setSKey(String SKey) {
        this.SKey = SKey;
    }

    public int getClientVersion() {
        return ClientVersion;
    }

    public void setClientVersion(int clientVersion) {
        ClientVersion = clientVersion;
    }

    public int getSystemTime() {
        return SystemTime;
    }

    public void setSystemTime(int systemTime) {
        SystemTime = systemTime;
    }

    public int getGrayScale() {
        return GrayScale;
    }

    public void setGrayScale(int grayScale) {
        GrayScale = grayScale;
    }

    public int getInviteStartCount() {
        return InviteStartCount;
    }

    public void setInviteStartCount(int inviteStartCount) {
        InviteStartCount = inviteStartCount;
    }

    public int getMPSubscribeMsgCount() {
        return MPSubscribeMsgCount;
    }

    public void setMPSubscribeMsgCount(int MPSubscribeMsgCount) {
        this.MPSubscribeMsgCount = MPSubscribeMsgCount;
    }

    public int getClickReportInterval() {
        return ClickReportInterval;
    }

    public void setClickReportInterval(int clickReportInterval) {
        ClickReportInterval = clickReportInterval;
    }

    public String getSyncKeyStr(){
        if (TextUtils.isEmpty(synckey)){
            String key="";
            for (com.donute.wechat.beans.SyncKey.KeyValuePair pair:SyncKey.getList()){
                key+=pair.getKey()+"_"+pair.getVal()+"|";
            }
            if (key.length()>1){
                return key.substring(0,key.lastIndexOf("|"));
            }else {
                return key;
            }
        }else {
            Log.e("SyncKey","key");
            return synckey;
        }

    }

    public static void saveState(File parent,WechatBaseInfo baseInfo){
        try {
            FileOutputStream fos = new FileOutputStream(new File(parent,"baseinfo"));
            ObjectOutputStream ooss = new ObjectOutputStream(fos);
            ooss.writeObject(baseInfo);
            ooss.flush();
            ooss.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WechatBaseInfo readState(Context context){
        WechatBaseInfo baseInfo=null;
        try {
            FileInputStream in = new FileInputStream(new File(context.getFilesDir(),"baseinfo"));
            ObjectInputStream s = new ObjectInputStream(in);
            baseInfo= (WechatBaseInfo) s.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (baseInfo==null){
            baseInfo=new WechatBaseInfo();
        }
        return baseInfo;
    }

    public JSONObject getSynckey() throws JSONException {
        JSONObject syk=new JSONObject();
        syk.put("Count",SyncKey.getCount());
        JSONArray list=new JSONArray();
        for (SyncKey.KeyValuePair pair : SyncKey.getList()){
            JSONObject object=new JSONObject();
            object.put("Key",pair.getKey());
            object.put("Val",pair.getVal());
            list.put(object);
        }
        syk.put("List",list);
        return syk;
    }
}
