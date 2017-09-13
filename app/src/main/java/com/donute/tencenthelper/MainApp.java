package com.donute.tencenthelper;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.donute.wechat.helpers.WechatManager;


/**
 * Created by zhouyufei on 2017/9/4.
 */

public class MainApp extends Application {
    private static Context instance;
    private static WechatManager wechatManager;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initScreenSize();
        wechatManager=WechatManager.init(this);

    }



    public static Context getContext(){
        return instance;
    }

    public static WechatManager getWechatManager() {
        return wechatManager;
    }

    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
}
