package com.donute.wechat.helpers;

import android.os.SystemClock;

import com.donute.wechat.beans.Contact;
import com.donute.wechat.beans.LoginInfo;
import com.donute.wechat.beans.MessageInfo;
import com.donute.wechat.beans.WechatBaseInfo;
import com.donute.wechat.beans.msg.WeMessage;
import com.donute.wechat.utils.FileUploadUtil;
import com.donute.wechat.utils.MessageParser;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Created by zhouyufei on 2017/9/8.
 */

public class MsgHelper {
    private LoginInfo loginInfo;
    private WechatBaseInfo baseInfo;
    private UrlManager urlManager;
    private FileHelper fileHelper;
    private LoginHelper loginHelper;

    private boolean isChecking = false;
    private int failedCount = 0;
    private Gson gson;

    protected MsgHelper(WechatBaseInfo baseInfo, LoginInfo loginInfo, FileHelper fileHelper, UrlManager urlManager, LoginHelper loginHelper) {
        this.baseInfo = baseInfo;
        this.loginInfo = loginInfo;
        this.urlManager = urlManager;
        this.fileHelper = fileHelper;
        this.loginHelper = loginHelper;
        gson = new Gson();
    }

    public void sendRawMsg(int msgType, final MessageInfo msgInfo, Contact toUser, final SendMsgListener listener) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("BaseRequest", loginInfo.getBaseRequest());
            JSONObject msg = new JSONObject();
            msg.put("Type", msgType);
            msg.put("Content", msgInfo.getContent());
            msg.put("FromUserName", baseInfo.getUser().getUserName());
            msg.put("ToUserName", toUser.getUserName());
            msg.put("LocalID", System.currentTimeMillis() / 1000 * 1e4);
            msg.put("ClientMsgId", System.currentTimeMillis() / 1000 * 1e4);
            obj.put("Msg", msg);
            obj.put("Scene", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(urlManager.sendRawMsgUrl()).upJson(obj).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (listener != null) {
                    listener.onSended(true, response.body(), msgInfo);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (listener != null) {
                    listener.onSended(false, response.getException().getMessage(), msgInfo);
                }
            }
        });
    }

    public void sendTextMsg(MessageInfo msgInfo, Contact toUser, SendMsgListener listener) {
        sendRawMsg(1, msgInfo, toUser, listener);
    }


    public void sendFile(MessageInfo msgInfo, Contact toUser, SendMsgListener listener) throws Exception {
        throw new Exception("NotImplementedException");
    }

    public void sendVideo(final MessageInfo msgInfo, Contact toUser, final SendMsgListener listener) {
        final File file = new File(msgInfo.getImageUrl());
        final String toUserName = toUser.getUserName();
        Observable.fromCallable(new Callable<okhttp3.Response>() {
            @Override
            public okhttp3.Response call() throws Exception {
                okhttp3.Response res = null;
                Request req=new Request.Builder().url("http://example.com").build();

                ResponseBody failedBody = ResponseBody.create(null, loginHelper.dataUtil.genFailedResponse().toString());
                okhttp3.Response.Builder builder = new okhttp3.Response.Builder().body(failedBody).request(req).protocol(Protocol.HTTP_1_1).message("failed").code(500);
                okhttp3.Response failedResponse = builder.build();

                FileUploadUtil uploadUtil = new FileUploadUtil(file, loginInfo, baseInfo, toUserName, urlManager);
                String response = uploadUtil.upload();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONObject baseResponse = result.optJSONObject("BaseResponse");
                    int ret = baseResponse.optInt("Ret");
                    String mediaId = result.optString("MediaId");
                    String startPos = result.optString("StartPos");
                    if (ret != 0) {
                        return failedResponse;
                    }

                    res = OkGo.<String>post(urlManager.sendVideoUrl()).upJson(loginHelper.dataUtil.genSendMsgObject(mediaId, 43, toUserName, false)).execute();

                    return res;
                } catch (Exception e) {
                    e.printStackTrace();
                    return failedResponse;
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<okhttp3.Response>() {
                    @Override
                    public void accept(okhttp3.Response response) throws Exception {

                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            int ret = object.getJSONObject("BaseResponse").getInt("Ret");
                            if (ret == 0) {
                                listener.onSended(true, "", msgInfo);
                            } else {
                                listener.onSended(false, "", msgInfo);
                            }
                        } catch (Exception e) {
                            if (listener != null) {
                                listener.onSended(false, "", msgInfo);
                            }
                        }

                    }
                });

    }

    public void sendImage(final MessageInfo msgInfo, Contact toUser, final SendMsgListener listener) {

        final File file = new File(msgInfo.getImageUrl());
        final String toUserName = toUser.getUserName();
        Observable.fromCallable(new Callable<okhttp3.Response>() {
            @Override
            public okhttp3.Response call() throws Exception {
                okhttp3.Response res = null;
                Request req=new Request.Builder().url("http://example.com").build();

                ResponseBody failedBody = ResponseBody.create(null, loginHelper.dataUtil.genFailedResponse().toString());
                okhttp3.Response.Builder builder = new okhttp3.Response.Builder().body(failedBody).request(req).protocol(Protocol.HTTP_1_1).message("failed").code(500);
                okhttp3.Response failedResponse = builder.build();

                FileUploadUtil uploadUtil = new FileUploadUtil(file, loginInfo, baseInfo, toUserName, urlManager);
                String response = uploadUtil.upload();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONObject baseResponse = result.optJSONObject("BaseResponse");
                    int ret = baseResponse.optInt("Ret");
                    String mediaId = result.optString("MediaId");
                    String startPos = result.optString("StartPos");
                    if (ret != 0) {
                        return failedResponse;
                    }
                    if (file.getName().endsWith(".gif")) {
                        res = OkGo.<String>post(urlManager.sendGifUrl()).upJson(loginHelper.dataUtil.genSendMsgObject(mediaId, 3, toUserName, true)).execute();
                    } else {
                        res = OkGo.<String>post(urlManager.sendImgUrl()).upJson(loginHelper.dataUtil.genSendMsgObject(mediaId, 3, toUserName, false)).execute();
                    }
                    return res;
                } catch (Exception e) {
                    e.printStackTrace();
                    return failedResponse;
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<okhttp3.Response>() {
                    @Override
                    public void accept(okhttp3.Response response) throws Exception {

                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            int ret = object.getJSONObject("BaseResponse").getInt("Ret");
                            if (ret == 0) {
                                listener.onSended(true, "", msgInfo);
                            } else {
                                listener.onSended(false, "", msgInfo);
                            }
                        } catch (Exception e) {
                            if (listener != null) {
                                listener.onSended(false, "", msgInfo);
                            }
                        }

                    }
                });
    }

    public void revoke() throws Exception {
        throw new Exception("NotImplementedException");
    }

    public void startCheckMsg() {
        if (!isChecking) {
            isChecking = true;
            checkMsg();
        }

    }

    public void stopCheckMsg() {
        isChecking = false;
    }


    private void checkMsg() {
        loginHelper.syncCheck(new LoginHelper.SyncCheckListener() {
            @Override
            public void onReturn(boolean isSuccess, String selector) {
                if (isSuccess) {
                    if (isChecking) {
                        if (selector.equals("0")) {
                            delayCheck(1000);
                        } else {
                            getMessage();
                        }
                    }
                } else {
                    failedCount++;
                    if (failedCount < 3) {
                        delayCheck(1000);
                    } else if (failedCount < 10) {
                        delayCheck(2000);
                    } else {
                        //Todo 消息检查失败
                        stopCheckMsg();
                    }

                }
            }
        });
    }

    private void getMessage() {
        JSONObject object = loginInfo.getBaseRequest();
        try {
            object.put("BaseRequest", loginInfo.getBaseObject());
            object.put("SyncKey", baseInfo.getSynckey());
            object.put("rr", ~(System.currentTimeMillis() / 1000));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(urlManager.messageUrl()).upJson(object).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                WeMessage message = gson.fromJson(response.body(), WeMessage.class);
                baseInfo.setSyncKey(message.getSyncKey());
                MessageParser.parse(message);
                delayCheck(1000);
                failedCount = 0;
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                delayCheck(1000);
            }
        });
    }

    private void delayCheck(final int time) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(time);
                if (isChecking) {
                    checkMsg();
                }
            }
        }.start();
    }

    public interface SendMsgListener {
        void onSended(boolean isSuccess, String result, MessageInfo msgInfo);
    }


}
