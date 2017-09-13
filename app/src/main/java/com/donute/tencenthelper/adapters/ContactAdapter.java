package com.donute.tencenthelper.adapters;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;
import com.donute.tencenthelper.MainApp;
import com.donute.tencenthelper.R;
import com.donute.tencenthelper.utils.ContactComparator;
import com.donute.wechat.beans.Contact;
import com.donute.wechat.helpers.FileHelper;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhouyufei on 2017/9/6.
 */

public class ContactAdapter extends VirtualLayoutAdapter<ContactViewHolder>{
    public List<Contact> contacts;
    private VirtualLayoutManager layoutManager;
    private FileHelper fileHelper;

    public ContactAdapter(@NonNull VirtualLayoutManager layoutManager, List<Contact> contacts) {
        super(layoutManager);
        this.layoutManager=layoutManager;
        Collections.sort(contacts,new ContactComparator());
        this.contacts= contacts;
        fileHelper=MainApp.getWechatManager().getFileHelper();
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact=contacts.get(position);
        holder.tvTime.setText("");
        holder.tvSignature.setText(contact.getSignature());
        if (TextUtils.isEmpty(contact.getRemarkName())){
            holder.tvName.setText(contact.getNickName());
        }else {
            holder.tvName.setText(contact.getRemarkName());
        }
        Glide.with(MainApp.getContext()).load(new File(fileHelper.headerPath,contact.getUserName())).into(holder.ivHeader);
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

}
