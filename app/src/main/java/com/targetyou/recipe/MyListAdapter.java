package com.targetyou.recipe;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<list_item> list_itemArrayList;
    ViewHolder viewholder;



    class ViewHolder{
        ImageView profile_imageView;
        TextView rename_textview;

    }



    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
//        TextView txtView;
//        txtView = (TextView)findViewByld(R.id.material);
//        final  int pos = position;
        final Context context = parent.getContext();
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_recipelist_custom,parent,false);

            viewholder = new ViewHolder();
            viewholder.rename_textview = (TextView)convertView.findViewById(R.id.rename_textview);
             viewholder.profile_imageView = (ImageView)convertView.findViewById(R.id.profile_imageview);

            convertView.setTag(viewholder);

        }else{

            viewholder = (ViewHolder)convertView.getTag();



        }
//        convertView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //task = new phpDown();
//                //task.execute("https://naancoco.cafe24.com/and/material.php");
//                Toast.makeText(context, "그거 먹어라~" + pos, Toast.LENGTH_SHORT).show();
//
//
//
//
//            }
//        });
        viewholder.rename_textview.setText(list_itemArrayList.get(position).getRe_name());
        Glide.with(context).load(list_itemArrayList.get(position).getImage()).into(viewholder.profile_imageView);
        return convertView;
    }

    public MyListAdapter (Context context, ArrayList<list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

}



