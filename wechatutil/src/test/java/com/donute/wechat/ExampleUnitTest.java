package com.donute.wechat;

import android.webkit.MimeTypeMap;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String fileName="adasas.py";
        String mime= fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println(mime);
    }
}