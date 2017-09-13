package com.donute.wechat.utils;

import com.donute.wechat.beans.msg.AddMsgList;
import com.donute.wechat.beans.msg.MsgType;
import com.donute.wechat.beans.msg.WeMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhouyufei on 2017/9/9.
 */

public class MessageParser {
    public static void parse(WeMessage message){
        for(AddMsgList msg :message.getAddMsgList()){
            if (msg.getFromUserName().contains("@@")||msg.getToUserName().contains("@@")){
                handleGroupChat(msg);
            }else {
                handleSingleChat(msg);
            }
        }
    }

    private static void handleSingleChat(AddMsgList msg){
        boolean isContain=false;
        int msgType=msg.getMsgType();
        switch (msgType){
            case MsgType.WORDS:
                break;
            case MsgType.PICTURE:
            case MsgType.PICTURE_:
                break;
            case MsgType.VOICE:
                break;
            case MsgType.FRIENDS:
                break;
            case MsgType.NEME_CARD:
                break;
            case MsgType.TINY_VIDEO:
            case MsgType.TINY_VIDEO_:
                break;
            case MsgType.SHARING:
                break;
            case MsgType.PHONE_INIT:
                break;
            case MsgType.C:
                break;
            case MsgType.D:
                break;
            default:
                for (int type :MsgType.E){
                    if (msgType==type){
                        //Todo
                        isContain=true;
                        break;
                    }

                }
                if (!isContain){

                }
        }
        EventBus.getDefault().post(msg);
    }

    private static void handleGroupChat(AddMsgList msg){

    }
}
