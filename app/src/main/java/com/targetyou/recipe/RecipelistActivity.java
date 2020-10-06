package com.targetyou.recipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class RecipelistActivity extends AppCompatActivity {

    String hate;
    String user_di;
    String disease;
    int st_material_size;
    String st_material;
    String st_amount;
    String email;
    String user_disease;

    TextView txtView;
    ImageView imageView;
    Intent intent;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> nameList2 = new ArrayList<>();
    ArrayList<String> materialList = new ArrayList<>();
    ArrayList<String> materialList2 = new ArrayList<>();
    ArrayList<String> amountList = new ArrayList<>();
    ArrayList<String> amountList2 = new ArrayList<>();
    ArrayList<String> eList = new ArrayList<>();
    ArrayList<String> eList2 = new ArrayList<>();
    ArrayList<String> calList = new ArrayList<>();
    ArrayList<String> calList2 = new ArrayList<>();
    ArrayList<String> proList = new ArrayList<>();
    ArrayList<String> proList2 = new ArrayList<>();
    ArrayList<String> naList = new ArrayList<>();
    ArrayList<String> naList2 = new ArrayList<>();
    ArrayList<String> fatList = new ArrayList<>();
    ArrayList<String> fatList2 = new ArrayList<>();
    ArrayList<String> methodList = new ArrayList<>();
    ArrayList<String> methodList2 = new ArrayList<>();
    ArrayList<String> method2List = new ArrayList<>();
    ArrayList<String> method2List2 = new ArrayList<>();
    ArrayList<String> unitList = new ArrayList<>();
    ArrayList<String> unitList2 = new ArrayList<>();
    ArrayList<String> priceList = new ArrayList<>();
    ArrayList<String> priceList2 = new ArrayList<>();
    ArrayList<String> imgList = new ArrayList<>();
    ArrayList<String> imgList2 = new ArrayList<>();
    ArrayList<String> st_List = new ArrayList<>();
    ArrayList<String> st_List2 = new ArrayList<>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist_custom);
        imageView = findViewById(R.id.profile_imageview);
        txtView = findViewById(R.id.rename_textview);
        Intent getintent = getIntent();
        email = getintent.getStringExtra("email");
        hate = getintent.getStringExtra("hate");
        user_di = getintent.getStringExtra("user_di");
        user_disease = getintent.getStringExtra("user_disease");
        st_material_size = getintent.getIntExtra("st_material_size", 0);
        System.out.println(st_material_size + "size");


        for (int i = 0; i < st_material_size; i++) {
            st_material = getintent.getStringExtra("st_material" + i);
            st_amount = getintent.getStringExtra("st_amount" + i);
            st_List.add(st_material);
            st_List2.add(st_amount);

        }

        intent = new Intent(RecipelistActivity.this, RecipelistActivity_2.class);


        try {

            NetworkUtil.setNetworkPolicy();
            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/test.php");
            String result1 = request.PhPdisease(String.valueOf(user_disease));

            try {

                JSONObject root = new JSONObject(result1);
                JSONArray ja = root.getJSONArray("results");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String name = jo.getString("name");
                    String img = jo.getString("image");
                    String material = jo.getString("material");
                    String amount = jo.getString("amount");
                    String energy = jo.getString("energy");
                    String calory = jo.getString("calory");
                    String natrium = jo.getString("natrium");
                    String fat = jo.getString("fat");
                    String protein = jo.getString("protein");
                    String method = jo.getString("cooking_method");
                    String method2 = jo.getString("cooking_method2");
                    String uni = jo.getString("unit");
                    String pri = jo.getString("price");
                    nameList.add(name);
                    materialList.add(material);
                    amountList.add(amount);
                    eList.add(energy);
                    calList.add(calory);
                    naList.add(natrium);
                    fatList.add(fat);
                    proList.add(protein);
                    methodList.add(method);
                    method2List.add(method2);
                    unitList.add(uni);
                    priceList.add(pri);
                    imgList.add(img);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        int re_size = nameList.size();
        System.out.println(re_size + "size");


        for (int i = 0; i < re_size; i++) {
            String unit = unitList.get(i).toString();
            String price = priceList.get(i).toString();
            String cooking_met = methodList.get(i).toString();
            String cooking2_met = method2List.get(i).toString();
            String re_name = nameList.get(i).toString();
            String re_material = materialList.get(i).toString();
            String re_amount = amountList.get(i).toString();
            String e = eList.get(i).toString();
            String c = calList.get(i).toString();
            String n = naList.get(i).toString();
            String f = fatList.get(i).toString();
            String p = proList.get(i).toString();
            String img = "http://" + imgList.get(i).toString();

            unitList2.add(unit);
            priceList2.add(price);
            methodList2.add(cooking_met);
            method2List2.add(cooking2_met);
            nameList2.add(re_name);
            materialList2.add(re_material);
            amountList2.add(re_amount);
            eList2.add(e);
            calList2.add(c);
            naList2.add(n);
            fatList2.add(f);
            proList2.add(p);
            imgList2.add(img);



        }
        intent.putExtra("st_material_size", st_material_size);
        intent.putExtra("email", email);
        intent.putExtra("re_size",re_size);

        for (int k = 0; k < st_material_size; k++) {
            intent.putExtra("st_material" + k, st_List.get(k).toString());
            intent.putExtra("st_amount" + k, st_List2.get(k).toString());
        }


        for (int j = 0; j < re_size; j++) {
            intent.putExtra("cook_method" + j, methodList2.get(j).toString());
            intent.putExtra("cook2_method" + j, method2List2.get(j).toString());
            intent.putExtra("e" + j, eList2.get(j).toString());
            intent.putExtra("c" + j, calList2.get(j).toString());
            intent.putExtra("n" + j, naList2.get(j).toString());
            intent.putExtra("f" + j, fatList2.get(j).toString());
            intent.putExtra("p" + j, proList2.get(j).toString());
            intent.putExtra("re_amount" + j, amountList2.get(j).toString());
            intent.putExtra("re_name" + j, nameList2.get(j).toString());
            intent.putExtra("re_material" + j, materialList2.get(j).toString());
            intent.putExtra("img" + j, imgList2.get(j).toString());
            intent.putExtra("user_di", user_di);
            intent.putExtra("user_disease", user_disease);
            intent.putExtra("hate", hate);
            intent.putExtra("unit" + j, unitList2.get(j).toString());
            intent.putExtra("price" + j, priceList2.get(j).toString());


        }

        startActivity(intent);
    }

}
