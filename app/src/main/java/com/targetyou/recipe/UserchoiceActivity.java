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
import android.support.v7.widget.AppCompatSeekBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;


public class UserchoiceActivity extends AppCompatActivity {

    final int max = 150000;
    final int min = 0;
    final int step = 5000;
    final String str_title = "원";
    Intent intent;
    String price;
    String user1;
    String user2;
    String user3;
    String user4;
    String user5;
    String user6;
    String user7;
    String user8;
    String user9;
    String user10;
    String user11;
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

        email = getIntent().getStringExtra("email");
        intent = new Intent(UserchoiceActivity.this, UserchoiceActivity2.class);

        dl = findViewById(R.id.activity_userchoice);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

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
        // final RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
        final RadioButton rb1 = findViewById(R.id.밥);
        final RadioButton rb2 = findViewById(R.id.반찬);
        final RadioButton rb3 = findViewById(R.id.국);
        final RadioButton rb4 = findViewById(R.id.일품);
        final RadioButton rb5 = findViewById(R.id.후식);
        final CheckBox cb1 = findViewById(R.id.끓이기);
        final CheckBox cb2 = findViewById(R.id.찌기);
        final CheckBox cb3 = findViewById(R.id.굽기);
        final CheckBox cb4 = findViewById(R.id.튀기기);
        final CheckBox cb5 = findViewById(R.id.볶기);
        final CheckBox cb6 = findViewById(R.id.기타);
        Button b = findViewById(R.id.완료);
        final TextView tv = findViewById(R.id.pricerange);
        final AppCompatSeekBar sb = findViewById(R.id.price);

        setSeekBarMax(sb, max);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
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


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                int value = min + (progress * step);

                tv.setText(value + str_title);
                price = Integer.toString(value);

                //  Toast.makeText(getApplicationContext(), price, Toast.LENGTH_LONG).show();
                try {
                    NetworkUtil.setNetworkPolicy();
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/choice.php");
                    String result1 = request.PhPChoice0(String.valueOf(price), String.valueOf(email));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            //setSeekBarChange(progress, tv);
            // Toast.makeText(getApplicationContext(),value, Toast.LENGTH_LONG).show();

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        b.setOnClickListener(new View.OnClickListener() {

                                 @Override
                                 public void onClick(View v) {

                                     try {
                                         NetworkUtil.setNetworkPolicy();

                                         if (rb1.isChecked() == true) {
                                             user1 = rb1.getText().toString();
                                             PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/choice2.php");
                                             String result = request.PhPChoice1(String.valueOf(user1), String.valueOf(email));
                                         }
                                         if (rb2.isChecked() == true) {
                                             user2 = rb2.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice3.php");
                                             String result = request2.PhPChoice1(String.valueOf(user2), String.valueOf(email));
                                         }
                                         if (rb3.isChecked() == true) {
                                             user3 = rb3.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice4.php");
                                             String result = request2.PhPChoice1(String.valueOf(user3), String.valueOf(email));
                                         }
                                         if (rb4.isChecked() == true) {
                                             user4 = rb4.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice5.php");
                                             String result = request2.PhPChoice1(String.valueOf(user4), String.valueOf(email));
                                         }
                                         if (rb5.isChecked() == true) {
                                             user5 = rb5.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice6.php");
                                             String result = request2.PhPChoice1(String.valueOf(user5), String.valueOf(email));
                                         }
                                         if (cb1.isChecked() == true) {
                                             user6 = cb1.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice7.php");
                                             String result = request2.PhPChoice1(String.valueOf(user6), String.valueOf(email));
                                         }

                                         if (cb2.isChecked() == true) {
                                             user7 = cb2.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice8.php");
                                             String result = request2.PhPChoice1(String.valueOf(user7), String.valueOf(email));
                                         }
                                         if (cb3.isChecked() == true) {
                                             user8 = cb3.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice9.php");
                                             String result = request2.PhPChoice1(String.valueOf(user8), String.valueOf(email));
                                         }
                                         if (cb4.isChecked() == true) {
                                             user9 = cb4.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice10.php");
                                             String result = request2.PhPChoice1(String.valueOf(user9), String.valueOf(email));
                                         }
                                         if (cb5.isChecked() == true) {
                                             user10 = cb5.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice11.php");
                                             String result = request2.PhPChoice1(String.valueOf(user10), String.valueOf(email));
                                         }
                                         if (cb6.isChecked() == true) {
                                             user11 = cb6.getText().toString();
                                             PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/choice12.php");
                                             String result = request2.PhPChoice1(String.valueOf(user11), String.valueOf(email));
                                         }
                                         Toast.makeText(getApplication(), "저장되었습니다.", Toast.LENGTH_SHORT).show();

                                     } catch (MalformedURLException e) {
                                         e.printStackTrace();
                                     }
                                     intent = new Intent(UserchoiceActivity.this, UserchoiceActivity2.class);
                                     intent.putExtra("email", email);
                                     startActivity(intent);
                                 }


                             }


        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    private void setSeekBarMax(AppCompatSeekBar sb, int max_value) {
        sb.setMax((max_value - min) / step);

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











//

//items 라는 배열에 체크 값들을 저장시킴
//배열들을 json형태로 저장
//json 값을 db로 저장


// food.php 활용하기
//http://webnautes.tistory.com/1159 참고