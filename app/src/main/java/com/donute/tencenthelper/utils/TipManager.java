package com.donute.tencenthelper.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * 提示管理类
 * Created by zhj-plusplus on 3/13/16.
 */
public class TipManager {

    /**
     * Toast提示
     *
     * @param context 上下文
     * @param message 信息
     */
    public static void showToast(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showDialog(Context context,String title,String msg){
        AlertDialog dialog=new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setPositiveButton("好的",null).create();
        dialog.show();
    }
    public static void showDialog(Context context,String title,int msg){
        AlertDialog dialog=new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setPositiveButton("好的",null).create();
        dialog.show();
    }
    public static void showDialog(Context context, String title, String msg, DialogInterface.OnClickListener listener){
        AlertDialog dialog=new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setPositiveButton("好的",listener).create();
        dialog.show();
    }
    public static void showDialog(Context context, String title, String msg, DialogInterface.OnClickListener pListener,DialogInterface.OnClickListener nListener){
        AlertDialog dialog=new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setPositiveButton("确定",pListener).setNegativeButton("取消",nListener).create();
        dialog.show();
    }

}
