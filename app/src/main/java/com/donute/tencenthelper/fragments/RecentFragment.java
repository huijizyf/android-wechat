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
import com.donute.tencenthelper.listener.OnRecyclerItemClickListener;
import com.donute.tencenthelper.utils.Constant;
import com.donute.tencenthelper.views.ContactDecoration;
import com.donute.wechat.beans.Contact;
import com.donute.wechat.helpers.LoginHelper;
import com.donute.wechat.helpers.WechatManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class RecentFragment extends Fragment{
    @BindView(R.id.wechat_recent_list)
    RecyclerView recentRv;

    private ContactAdapter adapter;
    private Unbinder unbinder;
    VirtualLayoutManager layoutManager;

    private List<Contact> recentList;
    private WechatManager wechatManager;
    private LoginHelper loginHelper;

    public RecentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_recent, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init() {
        wechatManager= MainApp.getWechatManager();
        loginHelper=wechatManager.getLoginHelper();

        layoutManager = new VirtualLayoutManager(getActivity());
        recentRv.setLayoutManager(layoutManager);
        recentRv.setHasFixedSize(true);
        recentRv.addItemDecoration(new ContactDecoration(getActivity(),true,recentList));
        recentRv.addOnItemTouchListener(new OnRecyclerItemClickListener(recentRv) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                Contact contact=recentList.get(position);
                Intent intent=new Intent(getActivity(), WeChatActivity.class);
                intent.putExtra(Constant.CONTACT_KEY,contact);
                startActivity(intent);
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });

        recentList=loginHelper.getBaseInfo().getContactList();
        adapter=new ContactAdapter(layoutManager,recentList);
        recentRv.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

}
