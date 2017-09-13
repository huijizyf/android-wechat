package com.donute.wechat.utils;

import com.donute.wechat.beans.LoginInfo;
import com.donute.wechat.helpers.WechatManager;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zhouyufei on 2017/9/5.
 */

public class NoFollowRequestUtil {

    private LoginInfo loginInfo;

    public NoFollowRequestUtil(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    final OkHttpClient client = new OkHttpClient().newBuilder()
            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            .followSslRedirects(false)
            .cookieJar(new LocalCookieJar())   //为OkHttp设置自动携带Cookie的功能
            .build();

    class LocalCookieJar implements CookieJar {
        List<Cookie> cookies;

        @Override
        public List<Cookie> loadForRequest(HttpUrl arg0) {
            if (cookies != null)
                return cookies;
            return new ArrayList<Cookie>();
        }

        @Override
        public void saveFromResponse(HttpUrl arg0, List<Cookie> cookies) {
            this.cookies = cookies;
            WechatManager.getCookieJar().saveFromResponse(arg0,cookies);
        }

    }

    public void request(String url,Callback callback) {
        final Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(callback);
    }

    public LoginInfo parse(InputStream is) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            sp.parse(is, new Handler(loginInfo));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginInfo;
    }

    class Handler extends DefaultHandler {
        private LoginInfo info;
        private String tempString;

        public Handler(LoginInfo info) {
            this.info = info;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("skey")) {
                info.setSkey(tempString);
            } else if (qName.equals("wxsid")) {
                info.setWxsid(tempString);
            } else if (qName.equals("wxuin")) {
                info.setWxuin(tempString);
            } else if (qName.equals("pass_ticket")) {
                info.setPassTicket(tempString);
            } else if (qName.equals("isgrayscale")) {
                info.setIsgrayscale(tempString);
            }
            super.endElement(uri, localName, qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            tempString = new String(ch, start, length);
            super.characters(ch, start, length);
        }
    }
}
