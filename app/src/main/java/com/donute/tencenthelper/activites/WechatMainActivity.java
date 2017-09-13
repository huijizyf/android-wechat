package com.donute.tencenthelper.activites;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.donute.tencenthelper.MainApp;
import com.donute.tencenthelper.R;
import com.donute.tencenthelper.fragments.ContactFragment;
import com.donute.tencenthelper.fragments.RecentFragment;
import com.donute.tencenthelper.fragments.ToolsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WechatMainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.wechat_content_fl)
    FrameLayout contentFl;
    @BindView(R.id.wechat_tab_rg)
    RadioGroup tabRg;

    @BindView(R.id.wechat_tab_recent)
    RadioButton tabRecent;
    @BindView(R.id.wechat_tab_contact)
    RadioButton tabContact;
    @BindView(R.id.wechat_tab_tool)
    RadioButton tabTool;

    private FragmentManager fragmentManager;

    private ContactFragment contactFragment;
    private RecentFragment recentFragment;
    private ToolsFragment toolsFragment;

    private Fragment currentFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_main);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        tabRg.setOnCheckedChangeListener(this);

        tabRecent.performClick();

        new Thread(){
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                MainApp.getWechatManager().getMsgHelper().startCheckMsg();
            }
        }.start();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment selectedFragment=null;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.wechat_tab_recent:
                if (recentFragment==null){
                    recentFragment=new RecentFragment();
                }
                selectedFragment=recentFragment;
                break;
            case R.id.wechat_tab_contact:
                if (contactFragment==null){
                    contactFragment=new ContactFragment();
                }
                selectedFragment=contactFragment;
                break;
            case R.id.wechat_tab_tool:
                if (toolsFragment==null){
                    toolsFragment=new ToolsFragment();
                }
                selectedFragment=toolsFragment;
                break;
        }
        if (currentFragment==null){
            if (selectedFragment.isAdded()){
                transaction.show(selectedFragment);
            }else {
                transaction.add(R.id.wechat_content_fl,selectedFragment);
            }
        }else {
            if (selectedFragment.isAdded()){
                transaction.hide(currentFragment).show(selectedFragment);
            }else {
                transaction.hide(currentFragment).add(R.id.wechat_content_fl,selectedFragment);
            }
        }
        currentFragment=selectedFragment;
        transaction.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApp.getWechatManager().getMsgHelper().stopCheckMsg();
    }
}
