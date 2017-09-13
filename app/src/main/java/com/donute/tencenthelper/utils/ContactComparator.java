package com.donute.tencenthelper.utils;

import android.text.TextUtils;

import com.donute.wechat.beans.Contact;

import java.util.Comparator;

/**
 * Created by zhouyufei on 2017/9/8.
 */

public class ContactComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact contact, Contact t1) {
        String aName=TextUtils.isEmpty(contact.getRemarkPYQuanPin())?contact.getPYQuanPin():contact.getRemarkPYQuanPin();
        String bName=TextUtils.isEmpty(t1.getRemarkPYQuanPin())?t1.getPYQuanPin():t1.getRemarkPYQuanPin();
        return aName.compareTo(bName);
    }
}
