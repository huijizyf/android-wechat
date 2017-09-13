package com.donute.wechat.helpers;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import com.donute.wechat.beans.Contact;
import com.donute.wechat.beans.LoginInfo;
import com.donute.wechat.beans.SyncKey;
import com.donute.wechat.beans.User;
import com.donute.wechat.beans.WechatBaseInfo;
import com.donute.wechat.events.ContactUpdateEvent;
import com.donute.wechat.utils.NoFollowRequestUtil;
import com.donute.wechat.utils.WeChatDataUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by zhouyufei on 2017/9/6.
 */

public class LoginHelper {
    private String uuid;
    private Gson gson;
    private WechatBaseInfo baseInfo;
    private LoginInfo info;

    private List<Contact> contacts;
    private FileHelper fhelper;
    private UrlManager urlManager;
    public WeChatDataUtil dataUtil;

    protected LoginHelper(WechatBaseInfo baseInfo,LoginInfo info,FileHelper fhelper,UrlManager urlManager){
        gson=new Gson();
        contacts=new ArrayList<>();
        this.baseInfo=baseInfo;
        this.info=info;
        this.fhelper=fhelper;
        this.urlManager=urlManager;
    }


    public void login(final LoginListener listener){
        OkGo.<String>get(urlManager.fetchUuidUrl())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        listener.onError(response.getException());
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        Pattern pattern = Pattern.compile("window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";");
                        Matcher matcher = pattern.matcher(result);
                        while (matcher.find()) {
                            if (matcher.group(1).equals("200")) {
                                uuid = matcher.group(2);
                                if (listener!=null){
                                    listener.onUUid(uuid);
                                }
                                checkLogin(listener);
                            }
                        }
                    }
                });
    }


    public void checkLogin(final LoginListener listener) {
        OkGo.<String>get(urlManager.chekLoginUrl(uuid))
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        if (listener!=null){
                            listener.onError(response.getException());
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        if (result.contains("window.code=201;")) {
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    SystemClock.sleep(1000);
                                    checkLogin(listener);
                                }
                            }.start();
                        } else if (result.equals("window.code=408;")) {
                            if (listener!=null){
                                listener.onRefetchUUid();
                            }
                            login(listener);
                        } else {
                            Pattern pattern = Pattern.compile("window.redirect_uri=\"(\\S+)\";");
                            Matcher matcher = pattern.matcher(result);
                            while (matcher.find()) {
                                final String url = matcher.group(1);
                                urlManager.setBaseUrl(url.substring(0,url.lastIndexOf("/")));
                                final NoFollowRequestUtil util = new NoFollowRequestUtil(info);
                                util.request(url, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        if (listener!=null){
                                            listener.onError(e);
                                        }
                                    }
                                    @Override
                                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                        util.parse(response.body().byteStream());
                                        LoginInfo.saveState(fhelper.getDataFileDir(),info);
                                        if (listener!=null){
                                            listener.onSuccess();
                                            initLogin();
                                        }
                                    }
                                });
                            }
                        }

                    }
                });
    }

    public void initLogin(){
        OkGo.<String>post(urlManager.initUrl())
                .upJson(info.getBaseRequest())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        WechatBaseInfo bi=gson.fromJson(response.body(),WechatBaseInfo.class);
                        baseInfo.setCount(bi.getCount());
                        baseInfo.setContactList(bi.getContactList());
                        baseInfo.setSyncKey(bi.getSyncKey());
                        baseInfo.setUser(bi.getUser());
                        baseInfo.setChatSet(bi.getChatSet());
                        baseInfo.setSKey(bi.getSKey());
                        baseInfo.setClientVersion(bi.getClientVersion());
                        baseInfo.setSystemTime(bi.getSystemTime());
                        baseInfo.setGrayScale(bi.getGrayScale());
                        baseInfo.setInviteStartCount(bi.getGrayScale());
                        baseInfo.setMPSubscribeMsgCount(bi.getMPSubscribeMsgCount());
                        baseInfo.setClickReportInterval(bi.getClickReportInterval());
                        bi=null;
                        downSelfHeader(baseInfo.getUser().getHeadImgUrl());
                        WechatBaseInfo.saveState(fhelper.getDataFileDir(),baseInfo);
                        showMoblieLogin();
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void showMoblieLogin(){
        dataUtil=new WeChatDataUtil(this,info);
        OkGo.<String>post(urlManager.showMobileLoginUrl())
                .upJson(dataUtil.genMoblieLoginData())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        updateContact();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void updateContact(){
        OkGo.<String>get(urlManager.contactUrl()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject object=new JSONObject(response.body());
                    JSONArray members=object.getJSONArray("MemberList");
                    contacts.clear();
                    for (int i=0;i<members.length();i++){
                        Contact contact=gson.fromJson(members.get(i).toString(),Contact.class);
                        contacts.add(contact);
                    }
                    EventBus.getDefault().post(new ContactUpdateEvent());
                    downloadAvater(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downloadAvater(boolean isUpdate){
        for(Contact contact:contacts){
            final String name=contact.getUserName();
            final File header=new File(fhelper.headerPath,name);
            if (header.exists()){
                if (!isUpdate)
                    continue;
            }
            updateSingleHeader(contact);
        }
    }

    public void updateSingleHeader(Contact contact){
        String url=urlManager.headerUrl(contact.getHeadImgUrl());
        final String name=contact.getUserName();
        final File header=new File(fhelper.headerPath,name);
        OkGo.<Bitmap>get(url).execute(new BitmapCallback(){
            @Override
            public void onSuccess(Response<Bitmap> response) {
                try {
                    FileOutputStream stream=new FileOutputStream(header);
                    response.body().compress(Bitmap.CompressFormat.JPEG,100,stream);
                    stream.flush();
                    stream.close();
                    Log.e("save",name);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downSelfHeader(String subUrl){
        String url=urlManager.headerUrl(subUrl);
        final String name=baseInfo.getUser().getUserName();
        final File header=new File(fhelper.headerPath,name);
        OkGo.<Bitmap>get(url).execute(new BitmapCallback(){
            @Override
            public void onSuccess(Response<Bitmap> response) {
                try {
                    FileOutputStream stream=new FileOutputStream(header);
                    response.body().compress(Bitmap.CompressFormat.JPEG,100,stream);
                    stream.flush();
                    stream.close();
                    Log.e("save",name);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateAllHeaher(){
        downloadAvater(true);
    }

    public void syncCheck(final SyncCheckListener listener){
        if (info==null||info.getSkey()==null||baseInfo==null||baseInfo.getSyncKey()==null){
            listener.onReturn(false,"");
            return;
        }
        HttpParams params=new HttpParams();
        params.put("r",System.currentTimeMillis());
        params.put("skey",info.getSkey());
        params.put("sid",info.getWxsid());
        params.put("uin",info.getWxuin());
        params.put("deviceid",info.getPassTicket());
        params.put("synckey",baseInfo.getSyncKeyStr());
        params.put("_",System.currentTimeMillis());
        OkGo.<String>get(urlManager.syncCheckUrl()).params(params).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    String[] result=response.body().split("=");
                    JSONObject object=new JSONObject(result[1]);
                    String  retcode=object.optString("retcode");
                    String selector=object.optString("selector");
                    if (retcode.equals("0")){
                        listener.onReturn(true,selector);
                    }else {
                        listener.onReturn(false,"");
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                    listener.onReturn(false,"");
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                listener.onReturn(false,"");
            }
        });
    }

    public List<Contact> getContacts(){
        return new ArrayList<>(contacts);
    }

    public interface LoginListener{
        void onUUid(String uuid);
        void onSuccess();
        void onRefetchUUid();
        void onError(Throwable th);
    }

    public interface SyncCheckListener{
        void onReturn(boolean isSuccess,String selector);
    }

    public WechatBaseInfo getBaseInfo() {
        return baseInfo;
    }
}
