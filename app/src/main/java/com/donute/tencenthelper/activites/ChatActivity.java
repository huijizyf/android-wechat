package com.donute.tencenthelper.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.donute.tencenthelper.R;
import com.donute.tencenthelper.utils.Constant;
import com.donute.wechat.beans.Contact;

public class ChatActivity extends AppCompatActivity {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        Intent intent=getIntent();
        contact=intent.getParcelableExtra(Constant.CONTACT_KEY);
    }
}
