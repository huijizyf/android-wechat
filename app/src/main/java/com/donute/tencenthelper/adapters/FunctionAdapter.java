package com.donute.tencenthelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.donute.tencenthelper.R;
import com.donute.tencenthelper.beans.Function;
import com.donute.wechat.beans.Contact;

import java.util.List;

/**
 * Created by zhouyufei on 2017/9/10.
 */

public class FunctionAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private List<Function> functions;

    public FunctionAdapter(Context context, List<Function> functions) {
        this.context = context;
        this.functions = functions;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return functions.size();
    }

    @Override
    public Object getItem(int i) {
        return functions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Function function=functions.get(i);
        if (view==null){
            view=inflater.inflate(R.layout.item_function,null);
        }
        TextView tvName= (TextView) view.findViewById(R.id.tv_function_name);
        tvName.setText(function.getName());
        return view;
    }
}
