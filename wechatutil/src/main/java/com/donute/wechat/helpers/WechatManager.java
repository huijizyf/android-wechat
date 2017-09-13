package com.donute.wechat.helpers;

import android.app.Application;
import android.content.Context;

import com.donute.wechat.beans.LoginInfo;
import com.donute.wechat.beans.WechatBaseInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by zhouyufei on 2017/9/8.
 */

public class WechatManager {
    private static CookieJarImpl cookieJar;

    private LoginInfo loginInfo;
    private WechatBaseInfo baseInfo;

    private LoginHelper loginHelper;
    private MsgHelper msgHelper;
    private FileHelper fileHelper;
    private UrlManager urlManager;

    private static WechatManager wechatManager;


    private WechatManager(Context context) {
        loginInfo= LoginInfo.readState(context);
        baseInfo=WechatBaseInfo.readState(context);
        fileHelper=new FileHelper(context);
        urlManager=new UrlManager(loginInfo);

        loginHelper=new LoginHelper(baseInfo,loginInfo,fileHelper,urlManager);
        msgHelper=new MsgHelper(baseInfo,loginInfo,fileHelper,urlManager,loginHelper);
    }

    public static WechatManager init(Application context){
        if (wechatManager==null){
            wechatManager=new WechatManager(context);
        }

        wechatManager.initOkGo(context);

        return wechatManager;
    }

    private void initOkGo(Application context) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", UrlManager.userAgent());

        cookieJar=new CookieJarImpl(new DBCookieStore(context));
        builder.cookieJar(cookieJar);              //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.followRedirects(false);
        builder.followSslRedirects(false);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        OkGo.getInstance().init(context)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .addCommonHeaders(headers)
                .setRetryCount(3);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

    /******************************getter*****************************/

    public static CookieJarImpl getCookieJar() {
        return cookieJar;
    }

    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    public MsgHelper getMsgHelper() {
        return msgHelper;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public WechatBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public FileHelper getFileHelper() {
        return fileHelper;
    }

    public UrlManager getUrlManager() {
        return urlManager;
    }
}
