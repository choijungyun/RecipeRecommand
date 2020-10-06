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


public class UserchoiceActivity3 extends AppCompatActivity {
    String hate;
    String disease;
    ArrayList<String> List = new ArrayList<>();
    Intent intent;
    String email;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("시작");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userchoice);
        Intent getintent = getIntent();
        email = getintent.getStringExtra("email");
        intent = new Intent(UserchoiceActivity3.this, UserchoiceActivity4.class);
        intent.putExtra("email", email);
        hate = getintent.getStringExtra("hate");
        disease = getIntent().getStringExtra("disease");

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

        String go = new String("고혈압");
        String dang = new String("당뇨");
        String gogi = new String("고지혈증");
        String bi = new String("비만");
        System.out.println("disease" + disease);
        if(disease.equals(go)) {
            try {
                NetworkUtil.setNetworkPolicy();
                PHPRequest request = new PHPRequest("https://naancoco.cafe24.com/and/USER_go.php");
                String result1 = request.PhPemail(String.valueOf(email));
                try {

                    JSONObject root = new JSONObject(result1);
                    JSONArray ja = root.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String user = jo.getString("user");
                        List.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String user_di = List.get(0).toString();
            intent.putExtra("user_di", user_di);
            intent.putExtra("disease", "고혈압");
            intent.putExtra("hate",hate);
            startActivity(intent);


        }
        if(disease.equals(dang)) {
            try {
                NetworkUtil.setNetworkPolicy();
                PHPRequest request = new PHPRequest("https://naancoco.cafe24.com/and/USER_dang.php");
                String result1 = request.PhPemail(String.valueOf(email));
                try {

                    JSONObject root = new JSONObject(result1);
                    JSONArray ja = root.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String user = jo.getString("user");
                        List.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String user_di = List.get(0).toString();
            intent.putExtra("user_di", user_di);
            intent.putExtra("hate",hate);
            intent.putExtra("disease", "당뇨");
            startActivity(intent);
        }

        if(disease.equals(gogi)) {
            try {
                NetworkUtil.setNetworkPolicy();
                PHPRequest request = new PHPRequest("https://naancoco.cafe24.com/and/USER_gogi.php");
                String result1 = request.PhPemail(String.valueOf(email));
                try {

                    JSONObject root = new JSONObject(result1);
                    JSONArray ja = root.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String user = jo.getString("user");
                        List.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String user_di = List.get(0).toString();
            intent.putExtra("user_di", user_di);
            intent.putExtra("hate",hate);
            intent.putExtra("disease", "고지혈증");
            startActivity(intent);
        }
        if(disease.equals(bi)) {
            try {
                NetworkUtil.setNetworkPolicy();
                PHPRequest request = new PHPRequest("https://naancoco.cafe24.com/and/USER_bi.php");
                String result1 = request.PhPemail(String.valueOf(email));
                try {

                    JSONObject root = new JSONObject(result1);
                    JSONArray ja = root.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String user = jo.getString("user");
                        List.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String user_di = "1340";
            intent.putExtra("user_di", user_di);
            intent.putExtra("disease", "비만");
            intent.putExtra("hate",hate);
            startActivity(intent);
        }


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
