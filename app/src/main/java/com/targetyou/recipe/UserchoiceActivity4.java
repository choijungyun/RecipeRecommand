package com.targetyou.recipe;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class UserchoiceActivity4 extends AppCompatActivity {
    String hate;
    String user_di;
    String user_disease;
    ArrayList<String> List = new ArrayList<>();
    ArrayList<String> List2 = new ArrayList<>();

    Intent intent;
    String email;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userchoice);
        Intent getintent = getIntent();

        email = getintent.getStringExtra("email");
        intent = new Intent(UserchoiceActivity4.this, RecipelistActivity.class);
        intent.putExtra("email", email);


        hate = getintent.getStringExtra("hate");
        user_di = getintent.getStringExtra("user_di");
        user_disease = getintent.getStringExtra("disease");


        dl = findViewById(R.id.activity_userchoice);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(" ");
        getSupportActionBar().setElevation(0f);
        nv = findViewById(R.id.nv);
        View nvview = nv.getHeaderView(0);

        TextView info = nvview.findViewById(R.id.userinfo);
        TextView name = nvview.findViewById(R.id.username);
        TextView info2 = nvview.findViewById(R.id.userinfo2);
        TextView info3 = nvview.findViewById(R.id.userinfo3);

        name.setText(email);

        NetworkUtil.setNetworkPolicy();
        try {

            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/editdetail.php");
            PHPRequest request1 = new PHPRequest("http://naancoco.cafe24.com/and/editdetail2.php");
            PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/editdetail3.php");


            String result = request.PhPInfo(email);
            String result1 = request1.PhPInfo(email);
            String result2 = request2.PhPInfo(email);

            if (result.equals(null) || result1.equals(null) || result2.equals(null)) {
                Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            } else {
                info.setText(result);
                info2.setText(result1);
                info3.setText(result2);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch(id)
                {
                    case R.id.refrigerator:
                        intent = new Intent(getApplicationContext(), RefrigeratorActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        dl.closeDrawers();
                        return true;
                    case R.id.diary:
                        intent = new Intent(getApplicationContext(), DietMainActivity.class);
                        intent.putExtra("email2", email);
                        startActivity(intent);
                        return true;
                    case R.id.calendar:
                        intent = new Intent(getApplicationContext(), CalendarActivity3.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        dl.closeDrawers();
                        return true;
                    case R.id.recommend:
                        intent = new Intent(getApplicationContext(), UserchoiceActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        dl.closeDrawers();
                        return true;
                    case R.id.logout:
                        SharedPreferences auto = getSharedPreferences("appData", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT);
                        finish();
                        return true;

                    default:
                        return true;
                }
            }
        });
        try {
            NetworkUtil.setNetworkPolicy();
            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/storage.php");
            String result1 = request.PhPemail(String.valueOf(email));

            try {

                JSONObject root = new JSONObject(result1);
                JSONArray ja = root.getJSONArray("results");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String st_ma = jo.getString("material");
                    String st_am = jo.getString("amount");
                    List.add(st_ma);
                    List2.add(st_am);
                    System.out.println(List);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        int Listsize = List.size();
        System.out.println(Listsize);




        for(int i =0; i< List.size(); i++) {
            System.out.println("st_material"+i);
            intent.putExtra("st_material"+i, List.get(i).toString());
            intent.putExtra("st_amount"+i, List2.get(i).toString());
        }


        intent.putExtra("user_di", user_di);
        intent.putExtra("user_disease", user_disease);
        intent.putExtra("hate",hate);
        intent.putExtra("st_material_size", Listsize);



        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void editProfile(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Information");
        builder.setMessage("정보를 수정하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), EditDetailActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }



}