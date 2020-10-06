package com.targetyou.recipe;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class RecipelistActivity_2 extends Activity {
    int re_size;
    String img;
    String re_name;
    String re_material;
    String re_amount;
    String e;
    String c;
    String n;
    String f;
    String p;
    String user_di;
    String hate;
    String st_material;
    String st_amount;
    String cook_method;
    String cook2_method;
    String unit;
    String price;
    String email;
    String user_disease;
    Integer st_material_size;
    ArrayList<String> st_List = new ArrayList<>();
    ArrayList<String> st_List2 = new ArrayList<>();
    Intent intent;
    ListView listView;
    MyListAdapter myListAdapter;
    ArrayList<list_item> list_itemArrayList;

    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> materialList = new ArrayList<>();
    ArrayList<String> amountList = new ArrayList<>();
    ArrayList<String> eList = new ArrayList<>();
    ArrayList<String> calList = new ArrayList<>();
    ArrayList<String> proList = new ArrayList<>();
    ArrayList<String> naList = new ArrayList<>();
    ArrayList<String> fatList = new ArrayList<>();
    ArrayList<String> methodList = new ArrayList<>();
    ArrayList<String> method2List = new ArrayList<>();
    ArrayList<String> unitList = new ArrayList<>();
    ArrayList<String> priceList = new ArrayList<>();
    ArrayList<String> imgList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipelist);
        List<String> re_choice = new ArrayList<>();
        List<Double> disease = new ArrayList<>();
        List<String> delete_mt = new ArrayList<>();

        final List<String> fin_rename = new ArrayList<>();
        final List<String> fin_material = new ArrayList<>();
        final List<String> fin_amount = new ArrayList<>();
        final List<String> fin_e = new ArrayList<>();
        final List<String> fin_c = new ArrayList<>();
        final List<String> fin_n = new ArrayList<>();
        final List<String> fin_f = new ArrayList<>();
        final List<String> fin_p = new ArrayList<>();
        final List<String> fin_method = new ArrayList<>();
        final List<String> fin_method2 = new ArrayList<>();
        final List<String> fin_unit = new ArrayList<>();
        final List<String> fin_price = new ArrayList<>();

        intent = getIntent();

        email = intent.getStringExtra("email");
        re_size = intent.getIntExtra("re_size", 0);
        st_material_size = intent.getIntExtra("st_material_size", 0);

        for (int i = 0; i < st_material_size; i++) {
            st_material = intent.getStringExtra("st_material" + i);
            st_amount = intent.getStringExtra("st_amount" + i);
            st_List.add(st_material);
            st_List2.add(st_amount);

        }
        user_di = intent.getStringExtra("user_di");
        hate = intent.getStringExtra("hate");


        for (int k = 0; k < re_size; k++) {

            unit = intent.getStringExtra("unit" + k);
            price = intent.getStringExtra("price" + k);
            cook_method = intent.getStringExtra("cook_method" + k);
            cook2_method = intent.getStringExtra("cook2_method" + k);

            img = intent.getStringExtra("img" + k);
            e = intent.getStringExtra("e" + k);
            c = intent.getStringExtra("c" + k);
            n = intent.getStringExtra("n" + k);
            f = intent.getStringExtra("f" + k);
            p = intent.getStringExtra("p" + k);
            re_name = intent.getStringExtra("re_name" + k);
            re_material = intent.getStringExtra("re_material" + k);
            re_amount = intent.getStringExtra("re_amount" + k);
            unitList.add(unit);
            priceList.add(price);
            methodList.add(cook_method);
            method2List.add(cook2_method);
            imgList.add(img);

            eList.add(e);
            calList.add(c);
            naList.add(n);
            fatList.add(f);
            proList.add(p);
            nameList.add(re_name);
            materialList.add(re_material);
            amountList.add(re_amount);


        }

        user_disease = intent.getStringExtra("user_disease");

        System.out.println(user_disease);
        System.out.println(nameList.get(0));
        System.out.println(nameList);

        listView = findViewById(R.id.listView);
        list_itemArrayList = new ArrayList<list_item>();


        try {
            NetworkUtil.setNetworkPolicy();
            PHPRequest request = new PHPRequest("https://naancoco.cafe24.com/and/re_choice.php");//선택한 가격 가져오기
            String result1 = request.PhPemail(String.valueOf(email));
            try {

                JSONObject root = new JSONObject(result1);
                JSONArray ja = root.getJSONArray("results");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String re_price = jo.getString("price");
                    re_choice.add(re_price);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        String choice = re_choice.get(0).toString();


        if (user_disease.toString().equals("고혈압")) {
            for (int p = 0; p < re_size; p++) {
                Double re_c = Double.parseDouble(n + p);
                disease.add(re_c + p);


            }
        }


        if (user_disease.toString().equals("당뇨")) {
            for (int p = 0; p < re_size; p++) {
                Double re_c = Double.parseDouble(e + p);
                disease.add(re_c);

            }
        }

        if (user_disease.toString().equals("고지혈증")) {
            for (int p = 0; p < re_size; p++) {
                Double re_c = Double.parseDouble(f + p);
                disease.add(re_c);
            }
        }


        if (user_disease.toString().equals("비만")) {
            for (int p = 0; p < re_size; p++) {
                Double re_c = Double.parseDouble(c + p);
                disease.add(re_c);
            }
        }

        Double re_di = Double.parseDouble(user_di);

        System.out.println("re_di" + re_di);
        System.out.println("re_di" + disease.get(0));


//가격추가
        //disease.get re의 영양성분
        //레시피 개수만큼 반복
        for(int k=0; k<re_size ; k++)
        {
            if (Double.parseDouble(disease.get(k).toString()) / 8 < re_di / 3) {
                List<String> list = new ArrayList<>();
                final List<String> list2 = new ArrayList<>();
                final List<String> list_re = new ArrayList<>();
                final List<String> list_price = new ArrayList<>();
                List<Integer> list_sum = new ArrayList<>();


                final String re_materialarr[] = materialList.get(k).toString().split(",");
                //레시피 재료 개수만큼 반복


                int sum = 0;
                System.out.println(re_materialarr[0]);
                for (int i = 0; i < re_materialarr.length; i++) {
                    final String re_amountarr[] = amountList.get(i).toString().split(",");
                    final String re_pricearr[] = priceList.get(i).toString().split(",");
                    list.add(re_materialarr[i]);
                    list2.add(re_amountarr[i]);
                    sum += Integer.parseInt(re_pricearr[i]);
                }
                //sum OK
                final int size = list.size();
                int count = 0;

                for (int j = 0; j < size; j++) {
                    if (hate.equals(list.get(j).toString())) {
                        count = 1;
                    }

                    if (count == 0) {
                        for (int h = 0; h < st_material_size; h++) {
                            System.out.println("hahaha" + list.get(j) + "ghgh" + st_List.get(h) + "price" + list_sum);
                            if (list.get(j).equals(st_List.get(h).toString())) {//저장된 재료와 비교
                                if (Integer.parseInt(list2.get(j).toString()) < Integer.parseInt(st_List2.get(h).toString())) {
                                    list_re.add(list.get(j).toString());
                                    System.out.println("list_re1" + list_re);
                                    try {
                                        NetworkUtil.setNetworkPolicy();
                                        PHPRequest request = new PHPRequest("https://naancoco.cafe24.com/and/re_price.php");//re가격 가져오기
                                        String result1 = request.PhPprice(String.valueOf(list.get(j).toString()));

                                        try {

                                            JSONObject root = new JSONObject(result1);
                                            JSONArray ja = root.getJSONArray("results");
                                            for (int i = 0; i < ja.length(); i++) {
                                                JSONObject jo = ja.getJSONObject(i);
                                                String re_price = jo.getString("price");
                                                list_price.add(re_price);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(sum);
                                    int re_price1 = Integer.parseInt(list_price.get(0).toString());
                                    sum -= re_price1;
                                    list_sum.add(sum);
                                    System.out.println(list_sum);
                                }
                            }
                            list_sum.add(sum);
                        }
                    }
                    System.out.println("1" + count);
                    System.out.println("1" + nameList);

                    if (count == 0 && Integer.parseInt(list_sum.get(0).toString()) < Integer.parseInt(choice)) {
                        fin_rename.add(nameList.get(j).toString());
                        fin_material.add(materialList.get(j).toString());
                        fin_amount.add(amountList.get(j).toString());
                        fin_e.add(eList.get(j).toString());
                        fin_c.add(calList.get(j).toString());
                        fin_n.add(naList.get(j).toString());
                        fin_f.add(fatList.get(j).toString());
                        fin_p.add(proList.get(j).toString());
                        fin_method.add(methodList.get(j).toString());
                        fin_method2.add(method2List.get(j).toString());
                        fin_unit.add(unitList.get(j).toString());
                        fin_price.add(priceList.get(j).toString());
                        list_itemArrayList.add(
                                new list_item(imgList.get(j).toString(), nameList.get(j).toString()));
                        System.out.println("first" + re_name + j);
                        //re_material 파싱후에 hate food 가 없고
                        //re_amount 파싱 후 더 높으면
                        //있는 재료 빼고 재료마다 가격 더하기 가격 따로 담기
                    }  // 리스트에 add<img, re_name>
                }

                myListAdapter = new MyListAdapter(RecipelistActivity_2.this, list_itemArrayList);
                listView.setAdapter(myListAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        //여기서 부터 RE

                        {

                            for(int k=0; k<size ; k++) {

                                if (position == k) {
                                    Intent menu = new Intent(RecipelistActivity_2.this, RecipeActivity.class);
                                    //   System.out.println(re_name);
                                    //  System.out.println(re_material);
                                    menu.putExtra("re_name", fin_rename.get(k).toString());
                                    menu.putExtra("re_material", fin_material.get(k).toString());
                                    menu.putExtra("re_amount", fin_amount.get(k).toString());
                                    menu.putExtra("e", fin_e.get(k).toString());
                                    menu.putExtra("c", fin_c.get(k).toString());
                                    menu.putExtra("n", fin_n.get(k).toString());
                                    menu.putExtra("f", fin_f.get(k).toString());
                                    menu.putExtra("p", fin_p.get(k).toString());
                                    menu.putExtra("cook_method", fin_method.get(k).toString());
                                    menu.putExtra("cook2_method", fin_method2.get(k).toString());
                                    menu.putExtra("unit", fin_unit.get(k).toString());
                                    menu.putExtra("price", fin_price.get(k).toString());
                                    menu.putExtra("email", email);
                                    menu.putExtra("st_material_size", st_material_size);
                                    for (int s = 0; s < st_material_size; s++) {
                                        menu.putExtra("st_material" + s, st_List.get(s).toString());
                                        menu.putExtra("st_amount" + s, st_List2.get(s).toString());

                                    }
                                    startActivity(menu);
                                }
                            }

                        }}
                });


            }
        }
    }
}