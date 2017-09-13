package com.donute.tencenthelper.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.donute.tencenthelper.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,WeChatLoginActivity.class));
//        startActivity(new Intent(this,WechatMainActivity.class));
        finish();
    }
}
