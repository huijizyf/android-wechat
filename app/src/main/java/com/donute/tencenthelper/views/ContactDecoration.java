package com.donute.tencenthelper.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.donute.tencenthelper.R;
import com.donute.tencenthelper.adapters.ContactAdapter;
import com.donute.wechat.beans.Contact;

import java.util.List;

/**
 * Created by zhouyufei on 2017/9/7.
 */

public class ContactDecoration extends RecyclerView.ItemDecoration {
    private boolean showHeader;
    private Context mContext;
    private Drawable mDivider;
    private List<Contact> contacts;

    private TextPaint textPaint;
    private Paint paint;
    private int topGap;

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS = new int[]{
            android.R.attr.listDivider
    };

    public ContactDecoration(Context context, boolean showHeader, List<Contact> contacts) {
        this.mContext = context;
        final TypedArray ta = context.obtainStyledAttributes(ATRRS);
        this.mDivider = ta.getDrawable(0);
        this.showHeader = showHeader;
        ta.recycle();

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);

        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.bg_content));
        topGap = context.getResources().getDimensionPixelSize(R.dimen.sectioned_top);
        this.contacts = contacts;

    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontalLine(c, parent, state);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            if (contacts == null || contacts.size() < 1) {
                return false;
            }
            String aName = TextUtils.isEmpty(contacts.get(pos - 1).getRemarkPYQuanPin()) ? contacts.get(pos - 1).getPYQuanPin() : contacts.get(pos - 1).getRemarkPYQuanPin();
            String bName = TextUtils.isEmpty(contacts.get(pos).getRemarkPYQuanPin()) ? contacts.get(pos).getPYQuanPin() : contacts.get(pos).getRemarkPYQuanPin();
            return !aName.substring(0, 1).equals(bName.substring(0, 1));
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

            if (showHeader) {
                int position = parent.getChildAdapterPosition(child);
                if (contacts!=null&&contacts.size()>0&&isFirstInGroup(position)) {
                    String aName = TextUtils.isEmpty(contacts.get(position).getRemarkPYQuanPin()) ? contacts.get(position).getPYQuanPin() : contacts.get(position).getRemarkPYQuanPin();
                    c.drawRect(left, child.getTop() - topGap, right, child.getTop(), paint);
                    c.drawText(aName.substring(0, 1), left, child.getTop(), textPaint);
                }
            }
        }
    }


    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if (isFirstInGroup(pos)) {
            outRect.set(0,topGap, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }

    }
}
