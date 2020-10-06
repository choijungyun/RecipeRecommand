package com.targetyou.recipe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecommendListAdapter extends BaseAdapter{
    private Context context;
    private List<Recommend> RecommendList;

    public RecommendListAdapter(Context context, List<Recommend> recommendList) {
        this.context = context;
        RecommendList = recommendList;
    }

    @Override
    public int getCount()  {     return RecommendList.size();}


    @Override
    public Object getItem(int position)  {
        return RecommendList.get(position);
    }

    @Override
    public long getItemId(int position) {

            return position;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(context, R.layout.recommend, null);
        TextView foodnameText=(TextView)v.findViewById(R.id.foodnameText);

        TextView gramText=(TextView)v.findViewById(R.id.gramText);
        TextView carboText=(TextView)v.findViewById(R.id.carboText);
        TextView proteinText=(TextView)v.findViewById(R.id.proteinText);
        TextView fatText=(TextView)v.findViewById(R.id.fatText);
        TextView natriumText=(TextView)v.findViewById(R.id.natriumText);
        TextView sugarText=(TextView)v.findViewById(R.id.sugarText);

        TextView calText=(TextView)v.findViewById(R.id.calText);
        TextView resultText=(TextView)v.findViewById(R.id.resultText);

        foodnameText.setText(RecommendList.get(i).getFoodName());

        gramText.setText(RecommendList.get(i).getGram());
        carboText.setText(RecommendList.get(i).getCarbo());
        proteinText.setText(RecommendList.get(i).getProtein());
        fatText.setText(RecommendList.get(i).getFat());
        natriumText.setText(RecommendList.get(i).getNatrium());
        sugarText.setText(RecommendList.get(i).getSugar());
        calText.setText(RecommendList.get(i).getCal());
        resultText.setText(RecommendList.get(i).getResult());

        v.setTag(RecommendList.get(i).getFoodName());
        return v;
    }
}
