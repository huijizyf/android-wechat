package com.donute.tencenthelper.chatui.util;

import com.donute.wechat.beans.MessageInfo;
import com.donute.wechat.helpers.FileHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyufei on 2017/9/10.
 */

public class FileDataUtil {
    public static void saveMsg(List<MessageInfo> allMsg, String userName, FileHelper fileHelper){
        try {
            FileOutputStream fos = new FileOutputStream(new File(fileHelper.msgCacheDir(),userName));
            ObjectOutputStream ooss = new ObjectOutputStream(fos);
            ooss.writeObject(allMsg);
            ooss.flush();
            ooss.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<MessageInfo> readMsg(String userName,FileHelper fileHelper){
        List<MessageInfo> allMsg=null;
        try {
            FileInputStream in = new FileInputStream(new File(fileHelper.msgCacheDir(),userName));
            ObjectInputStream s = new ObjectInputStream(in);
            allMsg= (List<MessageInfo>) s.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (allMsg==null){
            allMsg=new ArrayList<>();
        }
        return allMsg;
    }
}
