package com.targetyou.recipe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SearchListAdapter extends BaseAdapter{

    private Context context;
    private List<Search> searchList;

    public SearchListAdapter(Context context, List<Search> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Override
    public Object getItem(int i) {
        return searchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
//하나의 뷰로 만들어줄 수 있도록 해준다.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(context, R.layout.search, null);
        TextView materialText=(TextView)v.findViewById(R.id.materialText);
        TextView amountText=(TextView)v.findViewById(R.id.amountText);
        TextView unitText=(TextView)v.findViewById(R.id.unitText);
        TextView dateText=(TextView)v.findViewById(R.id.dateText);

        materialText.setText(searchList.get(i).getMaterial());
        amountText.setText(searchList.get(i).getAmount());
        unitText.setText(searchList.get(i).getUnit());
        dateText.setText(searchList.get(i).getDate());

        v.setTag(searchList.get(i).getMaterial());
        return v;

    }
}
