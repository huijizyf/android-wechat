package com.donute.tencenthelper.chatui.ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.donute.tencenthelper.MainApp;
import com.donute.tencenthelper.R;
import com.donute.tencenthelper.beans.Function;
import com.donute.tencenthelper.chatui.adapter.ChatAdapter;
import com.donute.tencenthelper.chatui.adapter.CommonFragmentPagerAdapter;
import com.donute.tencenthelper.chatui.enity.FullImageInfo;
import com.donute.tencenthelper.chatui.ui.fragment.ChatEmotionFragment;
import com.donute.tencenthelper.chatui.ui.fragment.ChatFunctionFragment;
import com.donute.tencenthelper.chatui.util.Constants;
import com.donute.tencenthelper.chatui.util.FileDataUtil;
import com.donute.tencenthelper.chatui.util.GlobalOnItemClickManagerUtils;
import com.donute.tencenthelper.chatui.util.MediaManager;
import com.donute.tencenthelper.chatui.widget.EmotionInputDetector;
import com.donute.tencenthelper.chatui.widget.NoScrollViewPager;
import com.donute.tencenthelper.chatui.widget.StateButton;
import com.donute.tencenthelper.utils.Constant;
import com.donute.tencenthelper.utils.TipManager;
import com.donute.wechat.beans.Contact;
import com.donute.wechat.beans.MessageInfo;
import com.donute.wechat.beans.User;
import com.donute.wechat.beans.msg.AddMsgList;
import com.donute.wechat.beans.msg.MsgType;
import com.donute.wechat.helpers.FileHelper;
import com.donute.wechat.helpers.MsgHelper;
import com.jude.easyrecyclerview.EasyRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class WeChatActivity extends AppCompatActivity implements MsgHelper.SendMsgListener {

    @BindView(R.id.chat_list)
    EasyRecyclerView chatList;
    @BindView(R.id.emotion_voice)
    ImageView emotionVoice;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.voice_text)
    TextView voiceText;
    @BindView(R.id.emotion_button)
    ImageView emotionButton;
    @BindView(R.id.emotion_add)
    ImageView emotionAdd;
    @BindView(R.id.emotion_send)
    StateButton emotionSend;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.emotion_layout)
    RelativeLayout emotionLayout;

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;
    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;

    private Contact contact;
    private FileHelper fileHelper;
    private User user;
    private MsgHelper msgHelper;

    private int functionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);
        user = MainApp.getWechatManager().getBaseInfo().getUser();
        msgHelper = MainApp.getWechatManager().getMsgHelper();
        Contact savedContact = null;
        if (savedInstanceState != null) {
            savedContact = savedInstanceState.getParcelable(Constant.CONTACT_KEY);
        }
        contact = (savedContact == null) ? (Contact) getIntent().getParcelableExtra(Constant.CONTACT_KEY) : savedContact;
        functionCode = getIntent().getIntExtra(Constant.FUNCTION_CODE, -1);
        fileHelper = MainApp.getWechatManager().getFileHelper();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initWidget();
    }

    private void initWidget() {
        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(chatList)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);

        chatAdapter = new ChatAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(chatAdapter);
        chatList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
        LoadData();
    }

    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener = new ChatAdapter.onItemClickListener() {
        @Override
        public void onHeaderClick(int position) {
            Toast.makeText(WeChatActivity.this, "onHeaderClick", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onImageClick(View view, int position) {
            int location[] = new int[2];
            view.getLocationOnScreen(location);
            FullImageInfo fullImageInfo = new FullImageInfo();
            fullImageInfo.setLocationX(location[0]);
            fullImageInfo.setLocationY(location[1]);
            fullImageInfo.setWidth(view.getWidth());
            fullImageInfo.setHeight(view.getHeight());
            fullImageInfo.setImageUrl(messageInfos.get(position).getImageUrl());
            EventBus.getDefault().postSticky(fullImageInfo);
            startActivity(new Intent(WeChatActivity.this, FullImageActivity.class));
            overridePendingTransition(0, 0);
        }

        @Override
        public void onVoiceClick(final ImageView imageView, final int position) {
            if (animView != null) {
                animView.setImageResource(res);
                animView = null;
            }
            switch (messageInfos.get(position).getType()) {
                case 1:
                    animationRes = R.drawable.voice_left;
                    res = R.mipmap.icon_voice_left3;
                    break;
                case 2:
                    animationRes = R.drawable.voice_right;
                    res = R.mipmap.icon_voice_right3;
                    break;
            }
            animView = imageView;
            animView.setImageResource(animationRes);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            MediaManager.playSound(messageInfos.get(position).getFilepath(), new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animView.setImageResource(res);
                }
            });
        }
    };

    /**
     * 构造聊天数据
     */
    private void LoadData() {
        if (contact != null) {
            messageInfos = FileDataUtil.readMsg(contact.getUserName(), fileHelper);
            chatAdapter.addAll(messageInfos);
        } else {
            messageInfos = new ArrayList<>();
            chatAdapter.addAll(messageInfos);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        messageInfo.setHeader(fileHelper.headerDir(user.getUserName()));
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfos.add(messageInfo);
        chatAdapter.add(messageInfo);
        chatList.scrollToPosition(chatAdapter.getCount() - 1);

        switch (messageInfo.getMsgType()) {
            case MsgType.WORDS:
                if (functionCode == -1) {
                    msgHelper.sendTextMsg(messageInfo, contact, this);
                } else {
                    for (Contact con : MainApp.getWechatManager().getBaseInfo().getContactList()) {
                        if (functionCode == Function.sendToAll) {
                            msgHelper.sendTextMsg(messageInfo, con, this);
                        } else if (functionCode == Function.sendToAllFriend && con.getUserName().contains("@")) {
                            msgHelper.sendTextMsg(messageInfo, con, this);
                        } else if (functionCode == Function.sendToAllGroup && con.getUserName().contains("@@")) {
                            msgHelper.sendTextMsg(messageInfo, con, this);
                        }
                    }
                }
                break;
            case MsgType.PICTURE:
                ;
            case MsgType.PICTURE_:
                if (functionCode == -1) {
                    msgHelper.sendImage(messageInfo, contact, this);
                } else {
                    for (Contact con : MainApp.getWechatManager().getBaseInfo().getContactList()) {
                        if (functionCode == Function.sendToAll) {
                            msgHelper.sendImage(messageInfo, con, this);
                        } else if (functionCode == Function.sendToAllFriend && con.getUserName().contains("@")) {
                            msgHelper.sendImage(messageInfo, con, this);
                        } else if (functionCode == Function.sendToAllGroup && con.getUserName().contains("@@")) {
                            msgHelper.sendImage(messageInfo, con, this);
                        }
                    }
                }
                break;
            case MsgType.TINY_VIDEO:
                break;
            case MsgType.TINY_VIDEO_:
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddMsgList message) {
        String fromName = message.getFromUserName();
        if (fromName.equals(contact.getUserName())) {
            MessageInfo msg = new MessageInfo();
            msg.setContent(message.getContent());
            msg.setType(Constants.CHAT_ITEM_TYPE_LEFT);
            msg.setHeader(fileHelper.headerDir(fromName));
            messageInfos.add(msg);
            chatAdapter.add(msg);
            chatAdapter.notifyDataSetChanged();
            chatList.scrollToPosition(chatAdapter.getCount() - 1);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constant.CONTACT_KEY, contact);
    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
        if (functionCode == -1)
            FileDataUtil.saveMsg(messageInfos, contact.getUserName(), fileHelper);
    }

    @Override
    public void onSended(boolean isSuccess, String result, MessageInfo msgInfo) {
        if (isSuccess) {
            msgInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        } else {
            msgInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
        }
        chatAdapter.notifyDataSetChanged();
    }
}
