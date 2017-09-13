package com.donute.tencenthelper;

import com.donute.wechat.beans.WechatBaseInfo;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGson() throws FileNotFoundException {
        Gson gson=new Gson();
        WechatBaseInfo info=gson.fromJson(new JsonReader(new FileReader(new File("/Users/zhouyufei/Desktop/result.txt"))),WechatBaseInfo.class);
        System.out.println(info.getUser().getUserName());
        System.out.println(info.getSyncKey().getCount());
        System.out.println(info.getSyncKey().getList());
    }

    @Test
    public void textUrl(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("EEE MMM d yyyy HH:mm:ss", Locale.ENGLISH);
        System.out.println(dateFormat.format(new Date())+" GMT+0800 (CST)");
        System.out.println(new Date().toString());
    }
}