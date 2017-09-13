package com.donute.tencenthelper.listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhouyufei on 2017/9/9.
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private GestureDetectorCompat mGestureDetectorCompat;//手势探测器
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mGestureDetectorCompat=new GestureDetectorCompat(mRecyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder,int position);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder,int position);

    class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {


        public ItemTouchHelperGestureListener() {
        }

        //一次单独的轻触抬起手指操作，就是普通的点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                int pos=mRecyclerView.getChildAdapterPosition(childViewUnder);
                onItemClick(childViewHolder,pos);
            }
            return true;
        }

        //长按屏幕超过一定时长，就会触发，就是长按事件
        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                int pos=mRecyclerView.getChildAdapterPosition(childViewUnder);
                onLongClick(childViewHolder,pos);
            }
        }

    }

}
