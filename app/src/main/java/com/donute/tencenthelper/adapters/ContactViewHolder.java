package com.donute.tencenthelper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.donute.tencenthelper.R;

/**
 * Created by zhouyufei on 2017/9/7.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivHeader;
    public TextView tvName;
    public TextView tvSignature;
    public TextView tvTime;

    public ContactViewHolder(View itemView) {
        super(itemView);
        ivHeader= (ImageView) itemView.findViewById(R.id.header_iv);
        tvName= (TextView) itemView.findViewById(R.id.nick_name_tv);
        tvSignature= (TextView) itemView.findViewById(R.id.signature_tv);
        tvTime= (TextView) itemView.findViewById(R.id.chat_time);
    }
}
