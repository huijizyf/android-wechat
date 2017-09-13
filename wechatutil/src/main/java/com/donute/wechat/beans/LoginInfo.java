package com.donute.wechat.beans;

import android.content.Context;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zhouyufei on 2017/9/5.
 */

public class LoginInfo {
    protected LoginInfo(){

    }


    private String skey;
    private String wxsid;
    private String wxuin;
    private String passTicket;
    private String isgrayscale;

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getWxsid() {
        return wxsid;
    }

    public void setWxsid(String wxsid) {
        this.wxsid = wxsid;
    }

    public String getWxuin() {
        return wxuin;
    }

    public void setWxuin(String wxuin) {
        this.wxuin = wxuin;
    }

    public String getPassTicket() {
        return passTicket;
    }

    public void setPassTicket(String passTicket) {
        this.passTicket = passTicket;
    }

    public String getIsgrayscale() {
        return isgrayscale;
    }

    public void setIsgrayscale(String isgrayscale) {
        this.isgrayscale = isgrayscale;
    }

    public JSONObject getBaseRequest(){
        JSONObject object=new JSONObject();
        try {
            JSONObject baseRequest=new JSONObject();
            baseRequest.put("Skey",getSkey());
            baseRequest.put("Sid",getWxsid());
            baseRequest.put("Uin",getWxuin());
            baseRequest.put("DeviceID",getPassTicket());
            object.put("BaseRequest",baseRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject getBaseObject(){
        JSONObject baseRequest=new JSONObject();
        try {
            baseRequest.put("Skey",getSkey());
            baseRequest.put("Sid",getWxsid());
            baseRequest.put("Uin",getWxuin());
            baseRequest.put("DeviceID",getPassTicket());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return baseRequest;
    }

    public static void saveState(File parent,LoginInfo loginInfo){
        try {
            FileOutputStream fos = new FileOutputStream(new File(parent,"loginInfo"));
            ObjectOutputStream ooss = new ObjectOutputStream(fos);
            ooss.writeObject(loginInfo);
            ooss.flush();
            ooss.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LoginInfo readState(Context context){
        LoginInfo info=null;
        try {
            FileInputStream in = new FileInputStream(new File(context.getFilesDir(),"loginInfo"));
            ObjectInputStream s = new ObjectInputStream(in);
            info= (LoginInfo) s.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (info==null){
            info=new LoginInfo();
        }
        return info;
    }
}
