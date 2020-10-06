package com.targetyou.recipe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(context,R.layout.notice, null);
        TextView noticeText=(TextView)v.findViewById(R.id.eatNameText);
        TextView nameText=(TextView)v.findViewById(R.id.eatAmountText);
        TextView dateText=(TextView)v.findViewById(R.id.eatDateText);
        noticeText.setText(noticeList.get(i).getEatName());
        nameText.setText(noticeList.get(i).getEatAmount());
        dateText.setText(noticeList.get(i).getEatDate());

        v.setTag(noticeList.get(i).getEatName());
        return v;


    }
}
