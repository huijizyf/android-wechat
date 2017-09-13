package com.donute.tencenthelper.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.donute.tencenthelper.MainApp;
import com.donute.tencenthelper.R;
import com.donute.tencenthelper.adapters.ContactAdapter;
import com.donute.tencenthelper.chatui.ui.activity.WeChatActivity;
import com.donute.tencenthelper.events.ContactUpdateEvent;
import com.donute.tencenthelper.listener.OnRecyclerItemClickListener;
import com.donute.tencenthelper.utils.Constant;
import com.donute.tencenthelper.utils.TipManager;
import com.donute.tencenthelper.views.ContactDecoration;
import com.donute.wechat.beans.Contact;
import com.donute.wechat.beans.msg.AddMsgList;
import com.donute.wechat.helpers.LoginHelper;
import com.donute.wechat.helpers.WechatManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class ContactFragment extends Fragment{
    @BindView(R.id.wechat_contact_list)
    RecyclerView rvContact;

    private ContactAdapter adapter;
    private Unbinder unbinder;
    private LoginHelper loginHelper;
    VirtualLayoutManager layoutManager;

    private List<Contact> contactList;
    private WechatManager wechatManager;

    public ContactFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_contact, container, false);
        unbinder= ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        EventBus.getDefault().register(this);
        wechatManager= MainApp.getWechatManager();
        loginHelper =wechatManager.getLoginHelper();
        contactList= loginHelper.getContacts();
        layoutManager = new VirtualLayoutManager(getActivity());
        rvContact.setLayoutManager(layoutManager);
        rvContact.setHasFixedSize(true);
        rvContact.addItemDecoration(new ContactDecoration(getActivity(),true,contactList));
        rvContact.addOnItemTouchListener(new OnRecyclerItemClickListener(rvContact) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                Contact contact=contactList.get(position);
                Intent intent=new Intent(getActivity(), WeChatActivity.class);
                intent.putExtra(Constant.CONTACT_KEY,contact);
                startActivity(intent);
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });

        adapter=new ContactAdapter(layoutManager,contactList);
        rvContact.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ContactUpdateEvent event) {
        contactList= loginHelper.getContacts();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

}
