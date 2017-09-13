package com.donute.wechat.helpers;


import android.util.Log;

import com.donute.wechat.beans.LoginInfo;

/**
 * Created by zhouyufei on 2017/9/5.
 */

public class UrlManager {
    private  String WECHAT_LOGIN_URL="https://login.weixin.qq.com";
    private  String WECHAT_BASE_URL="https://wx2.qq.com";

    private LoginInfo loginInfo;

    public String baseUrl(){
        return WECHAT_BASE_URL;
    }

    private static final String[] FILE_URL={"https://file.wx2.qq.com","https://file.wx8.qq.com","https://file.wx.qq.com","https://file.web2.wechat.com","https://file.web.wechat.com"};
    private static final String[] SYNC_URL={"https://webpush.wx2.qq.com","https://webpush.wx8.qq.com","https://webpush.wx.qq.com","https://webpush.web2.wechat.com","https://webpush.web.wechat.com"};

    public void setBaseUrl(String url){
        WECHAT_BASE_URL = url;
    }

    protected UrlManager(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public  String fetchUuidUrl(){
        return WECHAT_LOGIN_URL+"/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_="+System.currentTimeMillis()/1000;
    }

    public  String qrCodeUrl(String uuid){
        return WECHAT_LOGIN_URL+"/qrcode/l/"+uuid;
    }

    public  String chekLoginUrl(String uuid){
        long time=System.currentTimeMillis()/1000;
        return String.format(WECHAT_LOGIN_URL+"/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=%s&tip=0&r=%s&_=%s",uuid,time/1579,time);
    }

    public  String initUrl(){
       return WECHAT_BASE_URL+"/webwxinit?r="+System.currentTimeMillis()/1000;
    }

    public  String showMobileLoginUrl(){
        return WECHAT_BASE_URL+"/webwxstatusnotify?lang=zh_CN&pass_ticket="+ loginInfo.getPassTicket();
    }

    public  String contactUrl(){
        long time=System.currentTimeMillis()/1000;
        return String.format(WECHAT_BASE_URL+"/webwxgetcontact?r=%s&seq=%s&skey=%s",time,0,loginInfo.getSkey());
    }

    public  String headerUrl(String subUrl){
        return WECHAT_BASE_URL.substring(0,WECHAT_BASE_URL.indexOf("/cgi"))+subUrl;
    }

    public  String sendRawMsgUrl(){
        return WECHAT_BASE_URL+"/webwxsendmsg";
    }

    public String fileUrl(){
        String t=WECHAT_BASE_URL.replace("https://","");
        String base=t.substring(0,t.indexOf("/"));
        for (String f:FILE_URL){
            if (f.contains(base)){
                return f;
            }
        }
        return WECHAT_BASE_URL;
    }

    public String syncCheckUrl(){
        String base=WECHAT_BASE_URL.replace("https://","");

        for (String sync:SYNC_URL){
            if (sync.contains(base)){
                return sync+"/synccheck";
            }
        }
        return WECHAT_BASE_URL+"/synccheck";
    }

    public String uploadMediaUrl(){
        String mediaUrl=String.format("%s/cgi-bin/mmwebwx-bin/webwxuploadmedia?f=json",fileUrl());
        return mediaUrl;
    }

    public String sendImgUrl(){
        return String.format("%s/webwxsendmsgimg?fun=async&f=json",baseUrl());
    }

    public String sendGifUrl(){
        return String.format("%s/webwxsendemoticon?fun=sys",baseUrl());
    }

    public String sendVideoUrl(){
        return String.format("%s/webwxsendvideomsg?fun=async&f=json&pass_ticket=%s",baseUrl(),loginInfo.getPassTicket());
    }

    public String revokeUrl(){
        return String.format("%s/webwxrevokemsg",WECHAT_BASE_URL);
    }

    public String messageUrl(){
        return String.format("%s/webwxsync?sid=%s&skey=%s&pass_ticket=%s",WECHAT_BASE_URL,loginInfo.getWxsid(),loginInfo.getSkey(),loginInfo.getPassTicket());
    }


    public static String userAgent(){
        return  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    }


}
