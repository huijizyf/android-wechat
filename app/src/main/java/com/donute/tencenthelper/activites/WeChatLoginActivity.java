package com.donute.tencenthelper.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donute.tencenthelper.MainApp;
import com.donute.tencenthelper.R;
import com.donute.wechat.helpers.LoginHelper;
import com.donute.wechat.helpers.UrlManager;
import com.donute.wechat.helpers.WechatManager;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatLoginActivity extends BaseActivity {

    @BindView(R.id.we_chat_login_qr_iv)
    ImageView qrIv;

    private LoginHelper weChatLoginHelper;
    private WechatManager wechatManager;
    private UrlManager urlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_qr_code);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        wechatManager= MainApp.getWechatManager();
        weChatLoginHelper =wechatManager.getLoginHelper();
        urlManager=wechatManager.getUrlManager();
        weChatLoginHelper.syncCheck(new LoginHelper.SyncCheckListener() {
            @Override
            public void onReturn(boolean isSuccess,String selector) {
                if (isSuccess){
                    onLoginSuccess();
                }else {
                    fetchQr();
                }
            }
        });
    }
    private void onLoginSuccess(){
        startActivity(new Intent(WeChatLoginActivity.this,WechatMainActivity.class));
        finish();
    }
    private void fetchQr(){
        weChatLoginHelper.login(new LoginHelper.LoginListener() {
            @Override
            public void onUUid(String uuid) {
                Glide.with(WeChatLoginActivity.this).load(urlManager.qrCodeUrl(uuid)).into(qrIv);
            }

            @Override
            public void onSuccess() {
                onLoginSuccess();
            }

            @Override
            public void onRefetchUUid() {

            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
