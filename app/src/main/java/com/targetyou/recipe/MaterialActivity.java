package com.targetyou.recipe;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MaterialActivity extends AppCompatActivity {



    String re_material;
    String re_name;
    String re_amount;
    String unit;
    String price;
    String st_material;
    String st_amount;
    int st_material_size;
    List<String> list = new ArrayList<>();
    ArrayList<String> st_List = new ArrayList<>();
    Intent intent;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        re_name = intent.getStringExtra("re_name");
        re_material = intent.getStringExtra("re_material");
        re_amount = intent.getStringExtra("re_amount");
        unit = intent.getStringExtra("unit");
        price = intent.getStringExtra("price");
        st_material_size = intent.getIntExtra("st_material_size", 0);
        for (int i = 0; i < st_material_size; i++) {
            st_material = getIntent().getStringExtra("st_material" + i);
            st_List.add(st_material);
        }

        final String re_materialarr[] = re_material.split(",");
        final String re_amountarr[] = re_amount.split(",");
        final String unitarr[] = unit.split(",");
        final String pricearr[] = price.split(",");

        for(int i=0; i<re_materialarr.length; i++){
            list.add(re_materialarr[i]+" "+ re_amountarr[i] + unitarr[i]);

        }
        final int size = list.size();
        // setContentView(R.layout.activity_recipe);
        LinearLayout r1 = new LinearLayout(this);
        r1.setOrientation(LinearLayout.VERTICAL);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT

        );
        r1.setLayoutParams(params);

        TextView textview = new TextView(this);
        textview.setId(View.generateViewId());
        //textview.setGravity(Gravity.CENTER_VERTICAL);
        textview.setText("\n"+"      "+ re_name+ "\n");

        RelativeLayout.LayoutParams topre_materialParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textview.setLayoutParams(topre_materialParams);
        //topre_materialParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //topre_materialParams.addRule(RelativeLayout.CENTER_VERTICAL);
        r1.addView(textview);


        final  List<CheckBox> allCheckbox = new ArrayList<CheckBox>();
        for(int j=0; j<size; j++) {

            CheckBox topCheck = new CheckBox(this);
            allCheckbox.add(topCheck);
            topCheck.setText(list.get(j) + " " +pricearr[j]+"원");


            for(int k=0; k<st_material_size; k++) {
                if (list.get(j).toString().equals(st_List.get(k).toString())) {
                    System.out.println("st_material push");
                    topCheck.setChecked(false);
                }
            }
            topCheck.setId(j);
            topCheck.setChecked(true);
            topCheck.setGravity(Gravity.CENTER_VERTICAL);




            LinearLayout.LayoutParams topButtonParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );



            topCheck.setLayoutParams(topButtonParams);
            r1.addView(topCheck);
        }




        final Button btn = new Button(this);
        btn.setId(View.generateViewId());

        btn.setText("         "+"가격");

        final RelativeLayout.LayoutParams price = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btn.setLayoutParams(price);
        price.addRule(RelativeLayout.BELOW);
        price.addRule(RelativeLayout.ALIGN_RIGHT);
        //price.addRule(RelativeLayout.ALIGN_BOTTOM);
        price.setMargins(300,100,10,100);
        btn.setGravity(Gravity.CENTER_VERTICAL);
        btn.setBackgroundResource(R.drawable.shape);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum =0;
                int k =0;
                for(int i=0; i<pricearr.length; i++) {
                    sum = sum + Integer.parseInt(pricearr[i]);

                    if (allCheckbox.get(i).isChecked() == true) {
                        k = k+ Integer.parseInt(pricearr[i]);
                    }
                }
                Toast.makeText(getApplicationContext(), "선택가격"+ k, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"총 가격은 " + sum + "입니다."  ,Toast.LENGTH_LONG).show();
//
//                for(int j=0; j<size; j++){
//                    if(j.s )

//                try {
//                    //전송
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("01037548529", null, list.toString() , null, null);
//                    Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }

            }
        });

        r1.addView(btn);

        //setContentView(R1);
        setContentView(r1);

    }



}