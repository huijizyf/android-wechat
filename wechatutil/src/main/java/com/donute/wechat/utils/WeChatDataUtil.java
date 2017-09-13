package com.donute.wechat.utils;


import com.donute.wechat.beans.LoginInfo;
import com.donute.wechat.helpers.LoginHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhouyufei on 2017/9/6.
 */

public class WeChatDataUtil {
    private LoginHelper loginHelper;
    private LoginInfo loginInfo;
    private String username;

    public WeChatDataUtil(LoginHelper loginHelper, LoginInfo loginInfo) {
        this.loginHelper = loginHelper;
        this.loginInfo = loginInfo;
        this.username = loginHelper.getBaseInfo().getUser().getUserName();
    }

    public JSONObject genMoblieLoginData() {
        JSONObject object = new JSONObject();
        try {
            JSONObject baseRequest = new JSONObject();
            baseRequest.put("Skey", loginInfo.getSkey());
            baseRequest.put("Sid", loginInfo.getWxsid());
            baseRequest.put("Uin", loginInfo.getWxuin());
            baseRequest.put("DeviceID", loginInfo.getPassTicket());
            object.put("BaseRequest", baseRequest);

            object.put("Code", 3);
            object.put("FromUserName", loginHelper.getBaseInfo().getUser().getUserName());
            object.put("ToUserName", loginHelper.getBaseInfo().getUser().getUserName());
            object.put("ClientMsgId", System.currentTimeMillis() / 1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public JSONObject genSendMsgObject(String mediaId, int msgType, String toUserName,boolean isGif) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("BaseRequest", loginInfo.getBaseObject());
            JSONObject msg = new JSONObject();
            msg.put("Type", msgType)
                    .put("MediaId", mediaId)
                    .put("FromUserName", username)
                    .put("ToUserName", toUserName)
                    .put("LocalID", System.currentTimeMillis() / 1000 * 1e4)
                    .put("ClientMsgId", System.currentTimeMillis() / 1000 * 1e4);
            if (isGif){
                msg.put("Type",47).put("EmojiFlag",2);
            }
            obj.put("Msg", msg)
                    .put("Scene", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject genFailedResponse(){
        JSONObject jsonObject=new JSONObject();
        JSONObject base=new JSONObject();
        try {
            base.put("Ret",-1);
            jsonObject.put("BaseResponse",base);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
