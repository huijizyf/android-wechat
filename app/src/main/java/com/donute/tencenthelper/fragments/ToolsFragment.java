package com.donute.tencenthelper.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.donute.tencenthelper.R;
import com.donute.tencenthelper.adapters.FunctionAdapter;
import com.donute.tencenthelper.beans.Function;
import com.donute.tencenthelper.chatui.ui.activity.WeChatActivity;
import com.donute.tencenthelper.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends Fragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.function_lv)
    ListViewCompat lvFunction;

    private Unbinder unbinder;
    private FunctionAdapter functionAdapter;
    private List<Function> functions;


    public ToolsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_tools, container, false);
        unbinder=ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        functions=new ArrayList<>();
        Function sendToAllFriend=new Function("给所有好友发送消息",Function.sendToAllFriend);
        functions.add(sendToAllFriend);

        Function sendToAllGroup=new Function("给所有群组发送消息",Function.sendToAllGroup);
        functions.add(sendToAllGroup);

        Function sendToAll=new Function("给所有好友和群组发送消息",Function.sendToAll);
        functions.add(sendToAll);

        Function autoReply=new Function("设置消息自动回复",3);
        functions.add(autoReply);

        Function msgTimer=new Function("设置定时发送消息",4);
        functions.add(msgTimer);

        functionAdapter=new FunctionAdapter(getActivity(),functions);
        lvFunction.setAdapter(functionAdapter);
        lvFunction.setOnItemClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Function function=functions.get(i);
        switch (function.getCode()){
            case 0:
                ;
            case 1:
                ;
            case 2:
                Intent intent=new Intent(getActivity(), WeChatActivity.class);
                intent.putExtra(Constant.FUNCTION_CODE,function.getCode());
                startActivity(intent);
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }
}
