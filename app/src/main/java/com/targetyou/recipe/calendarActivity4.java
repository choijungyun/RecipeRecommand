package com.targetyou.recipe;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import org.achartengine.GraphicalView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.widget.LinearLayout;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class calendarActivity4 extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    String email;
    String date;
    String usercalory;
    String usercarbo;
    String userprotein;
    String userfat;
    String usernatrium;
    int key;
    String result;
    String result0;
    String result1;
    String result2;
    String result3;
    String result4;
    String result5;
    String result6;
    String result7;
    //ArrayList<Integer> natrium_re = new ArrayList<Integer>();
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;

    private TextView text;
    private TextView text2;
    private Button btn;
    private LineChart lineChart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar4);

        dl = (DrawerLayout) findViewById(R.id.activity_calendar4);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        Intent intent = new Intent(this.getIntent());
        email = intent.getStringExtra("email");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(" ");
        getSupportActionBar().setElevation(0f);
        nv = (NavigationView) findViewById(R.id.nv);
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

            ImageView warning = (ImageView) findViewById(R.id.imageView3);
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(warning);
            Glide.with(this).load(R.drawable.warning).into(gifImage);

            lineChart = (LineChart) findViewById(R.id.chart);
            btn = (Button) findViewById(R.id.button9);
            text = (TextView) findViewById(R.id.textView12);
            text2 = (TextView) findViewById(R.id.textView3);

            date = intent.getStringExtra("date");
            usercalory = intent.getStringExtra("usercalory");
            usercarbo = intent.getStringExtra("usercarbo");
            userprotein = intent.getStringExtra("userprotein");
            userfat = intent.getStringExtra("userfat");
            usernatrium = intent.getStringExtra("usernatrium");
            key = intent.getIntExtra("key", 1);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(calendarActivity4.this, CalendarActivity2.class);
                    intent.putExtra("email", email);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            });

            String date2 = date.replaceAll("-", "");
            long intDate = Long.parseLong(date2);
            long intDate1 = intDate - 1;
            long intDate2 = intDate - 2;
            long intDate3 = intDate - 3;
            long intDate4 = intDate - 4;
            long intDate5 = intDate - 5;
            long intDate6 = intDate - 6;
            long intDate7 = intDate - 7;
            String s = String.valueOf(intDate1);
            String s1 = String.valueOf(intDate2);
            String s2 = String.valueOf(intDate3);
            String s3 = String.valueOf(intDate4);
            String s4 = String.valueOf(intDate5);
            String s5 = String.valueOf(intDate6);
            String s6 = String.valueOf(intDate7);
            String f1 = s.substring(0, 4) + '-' + s.substring(4, 6) + '-' + s.substring(6);
            String f2 = s1.substring(0, 4) + '-' + s1.substring(4, 6) + '-' + s1.substring(6);
            String f3 = s2.substring(0, 4) + '-' + s2.substring(4, 6) + '-' + s2.substring(6);
            String f4 = s3.substring(0, 4) + '-' + s3.substring(4, 6) + '-' + s3.substring(6);
            String f5 = s4.substring(0, 4) + '-' + s4.substring(4, 6) + '-' + s4.substring(6);
            String f6 = s5.substring(0, 4) + '-' + s5.substring(4, 6) + '-' + s5.substring(6);
            String f7 = s6.substring(0, 4) + '-' + s6.substring(4, 6) + '-' + s6.substring(6);
            Log.i("ddddddddddddddddddd", f1 + f2 + f3 + f4 + f5 + f6 + f7);

            if (key == 6) { //나트륨
                NetworkUtil.setNetworkPolicy();
                try {
                    // Log.i("dddddddddddddddddddddd", date);
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/natrium.php");
                    //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
                    result = request.phpCalendar3(email, date, f1, f2, f3, f4, f5, f6, f7);
                    //theDate.setText(result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        Log.i("result_natrium", result);
                        //응답부분을 처리하도록 하면 된다.
                        JSONArray jsonArray = jsonObject.getJSONArray("natrium_re");
                        Log.i("jsonArray", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONObject object1 = jsonArray.getJSONObject(i + 1);
                            JSONObject object2 = jsonArray.getJSONObject(i + 2);
                            JSONObject object3 = jsonArray.getJSONObject(i + 3);
                            JSONObject object4 = jsonArray.getJSONObject(i + 4);
                            JSONObject object5 = jsonArray.getJSONObject(i + 5);
                            JSONObject object6 = jsonArray.getJSONObject(i + 6);
                            if (object.getString("natrium_re").length() == 0) {
                                a = "0";
                            } else {
                                a = object.getString("natrium_re");
                            }
                            b = object1.getString("natrium_re1");
                            c = object2.getString("natrium_re2");
                            d = object3.getString("natrium_re3");
                            e = object4.getString("natrium_re4");
                            f = object5.getString("natrium_re5");
                            g = object6.getString("natrium_re6");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                int sumNatrium = (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) + Integer.valueOf(d) + Integer.valueOf(e)
                        + Integer.valueOf(f) + Integer.valueOf(g)) / 7;
                if (sumNatrium > Integer.valueOf(usernatrium)) {
                    int sumNatrium2 = sumNatrium / Integer.valueOf(usernatrium) * 100;
                    text.setText("         <" + date + " 기준 전 7일 동안의 나트륨 섭취량>\t\t");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비" + sumNatrium2 + "%만큼 더 섭취하였습니다'");
                    warning.setVisibility(View.VISIBLE);
                } else {
                    int sumNatrium2 = Integer.valueOf(usernatrium) / sumNatrium * 100;
                    text.setText("     <" + date + " 기준 전 7일 동안의 나트륨 섭취량>\n");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumNatrium2 + "%만큼 덜 섭취하였습니다'");
                    warning.setVisibility(View.GONE);
                }
                setData();

            } else if (key == 2) { //탄수화물
                NetworkUtil.setNetworkPolicy();
                try {
                    // Log.i("dddddddddddddddddddddd", date);
                    PHPRequest request1 = new PHPRequest("http://naancoco.cafe24.com/calory.php");
                    //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
                    result1 = request1.phpCalendar3(email, date, f1, f2, f3, f4, f5, f6, f7);
                    //theDate.setText(result);
                    try {
                        JSONObject jsonObject = new JSONObject(result1);
                        Log.i("result_natrium", result1);
                        //응답부분을 처리하도록 하면 된다.
                        JSONArray jsonArray = jsonObject.getJSONArray("calory_re");
                        Log.i("jsonArray", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONObject object1 = jsonArray.getJSONObject(i + 1);
                            JSONObject object2 = jsonArray.getJSONObject(i + 2);
                            JSONObject object3 = jsonArray.getJSONObject(i + 3);
                            JSONObject object4 = jsonArray.getJSONObject(i + 4);
                            JSONObject object5 = jsonArray.getJSONObject(i + 5);
                            JSONObject object6 = jsonArray.getJSONObject(i + 6);
                            a = object.getString("calory_re");
                            b = object1.getString("calory_re1");
                            c = object2.getString("calory_re2");
                            d = object3.getString("calory_re3");
                            e = object4.getString("calory_re4");
                            f = object5.getString("calory_re5");
                            g = object6.getString("calory_re6");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                setData();
                int sumCarbo = (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) + Integer.valueOf(d) + Integer.valueOf(e)
                        + Integer.valueOf(f) + Integer.valueOf(g)) / 7;
                if (sumCarbo > Integer.valueOf(usercalory)) {
                    int sumCarbo2 = sumCarbo / Integer.valueOf(usercalory) * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 열량 섭취량>\n");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumCarbo2 + "%만큼 더 섭취하였습니다'");
                    text2.setTextColor(Color.rgb(204, 0, 0));
                    warning.setVisibility(View.VISIBLE);
                } else {
                    int sumCarbo2 = Integer.valueOf(usercalory) / sumCarbo * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 열량 섭취량>\n");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumCarbo2 + "%만큼 덜 섭취하였습니다'");
                    warning.setVisibility(View.GONE);
                }
            } else if (key == 3) { //열량
                NetworkUtil.setNetworkPolicy();
                try {
                    // Log.i("dddddddddddddddddddddd", date);
                    PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/energy.php");
                    //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
                    result2 = request2.phpCalendar3(email, date, f1, f2, f3, f4, f5, f6, f7);
                    //theDate.setText(result);
                    try {
                        JSONObject jsonObject = new JSONObject(result2);
                        Log.i("result_natrium", result2);
                        //응답부분을 처리하도록 하면 된다.
                        JSONArray jsonArray = jsonObject.getJSONArray("energy_re");
                        Log.i("jsonArray", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONObject object1 = jsonArray.getJSONObject(i + 1);
                            JSONObject object2 = jsonArray.getJSONObject(i + 2);
                            JSONObject object3 = jsonArray.getJSONObject(i + 3);
                            JSONObject object4 = jsonArray.getJSONObject(i + 4);
                            JSONObject object5 = jsonArray.getJSONObject(i + 5);
                            JSONObject object6 = jsonArray.getJSONObject(i + 6);
                            a = object.getString("energy_re");
                            b = object1.getString("energy_re1");
                            c = object2.getString("energy_re2");
                            d = object3.getString("energy_re3");
                            e = object4.getString("energy_re4");
                            f = object5.getString("energy_re5");
                            g = object6.getString("energy_re6");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                setData();
                int sumCalory = (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) + Integer.valueOf(d) + Integer.valueOf(e)
                        + Integer.valueOf(f) + Integer.valueOf(g)) / 7;
                if (sumCalory > Integer.valueOf(usercarbo)) {
                    int sumCalory2 = sumCalory / Integer.valueOf(usercarbo) * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 탄수화물 섭취량>");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumCalory2 + "%만큼 더 섭취하였습니다'");
                    text2.setTextColor(Color.rgb(204, 0, 0));
                    warning.setVisibility(View.VISIBLE);
                } else {
                    int sumCalory2 = Integer.valueOf(usercarbo) / sumCalory * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 탄수화물 섭취량>");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumCalory2 + "%만큼 덜 섭취하였습니다'");
                    warning.setVisibility(View.GONE);
                }
            } else if (key == 4) { // 단백질
                NetworkUtil.setNetworkPolicy();
                try {
                    // Log.i("dddddddddddddddddddddd", date);
                    PHPRequest request3 = new PHPRequest("http://naancoco.cafe24.com/protein.php");
                    //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
                    result3 = request3.phpCalendar3(email, date, f1, f2, f3, f4, f5, f6, f7);
                    //theDate.setText(result);
                    try {
                        JSONObject jsonObject = new JSONObject(result3);
                        Log.i("result_natrium", result3);
                        //응답부분을 처리하도록 하면 된다.
                        JSONArray jsonArray = jsonObject.getJSONArray("protein_re");
                        Log.i("jsonArray", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONObject object1 = jsonArray.getJSONObject(i + 1);
                            JSONObject object2 = jsonArray.getJSONObject(i + 2);
                            JSONObject object3 = jsonArray.getJSONObject(i + 3);
                            JSONObject object4 = jsonArray.getJSONObject(i + 4);
                            JSONObject object5 = jsonArray.getJSONObject(i + 5);
                            JSONObject object6 = jsonArray.getJSONObject(i + 6);
                            a = object.getString("protein_re");
                            b = object1.getString("protein_re1");
                            c = object2.getString("protein_re2");
                            d = object3.getString("protein_re3");
                            e = object4.getString("protein_re4");
                            f = object5.getString("protein_re5");
                            g = object6.getString("protein_re6");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                setData();
                int sumProtein = (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) + Integer.valueOf(d) + Integer.valueOf(e)
                        + Integer.valueOf(f) + Integer.valueOf(g)) / 7;
                if (sumProtein > Integer.valueOf(userprotein)) {
                    int sumProtein2 = sumProtein / Integer.valueOf(userprotein) * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 단백질 섭취량>");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumProtein2 + "%만큼 더 섭취하였습니다'");
                    text2.setTextColor(Color.rgb(204, 0, 0));
                    warning.setVisibility(View.VISIBLE);
                } else {
                    int sumProtein2 = Integer.valueOf(userprotein) / sumProtein * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 단백질 섭취량>");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumProtein2 + "%만큼 덜 섭취하였습니다'");
                    warning.setVisibility(View.GONE);
                }
            } else if (key == 5) { //지방
                NetworkUtil.setNetworkPolicy();
                try {
                    // Log.i("dddddddddddddddddddddd", date);
                    PHPRequest request4 = new PHPRequest("http://naancoco.cafe24.com/fat.php");
                    //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
                    result4 = request4.phpCalendar3(email, date, f1, f2, f3, f4, f5, f6, f7);
                    //theDate.setText(result);
                    try {
                        JSONObject jsonObject = new JSONObject(result4);
                        Log.i("result_natrium", result4);
                        //응답부분을 처리하도록 하면 된다.
                        JSONArray jsonArray = jsonObject.getJSONArray("fat_re");
                        Log.i("jsonArray", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONObject object1 = jsonArray.getJSONObject(i + 1);
                            JSONObject object2 = jsonArray.getJSONObject(i + 2);
                            JSONObject object3 = jsonArray.getJSONObject(i + 3);
                            JSONObject object4 = jsonArray.getJSONObject(i + 4);
                            JSONObject object5 = jsonArray.getJSONObject(i + 5);
                            JSONObject object6 = jsonArray.getJSONObject(i + 6);
                            a = object.getString("fat_re");
                            b = object1.getString("fat_re1");
                            c = object2.getString("fat_re2");
                            d = object3.getString("fat_re3");
                            e = object4.getString("fat_re4");
                            f = object5.getString("fat_re5");
                            g = object6.getString("fat_re6");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                int sumFat = (Integer.valueOf(a) + Integer.valueOf(b) + Integer.valueOf(c) + Integer.valueOf(d) + Integer.valueOf(e)
                        + Integer.valueOf(f) + Integer.valueOf(g)) / 7;
                if (sumFat > Integer.valueOf(userfat)) {
                    int sumFat2 = sumFat / Integer.valueOf(userfat) * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 지방 섭취량>");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumFat2 + "%만큼 더 섭취하였습니다'");
                    text2.setTextColor(Color.rgb(204, 0, 0));
                    warning.setVisibility(View.VISIBLE);
                } else {
                    int sumFat2 = Integer.valueOf(userfat) / sumFat * 100;
                    text.setText("      <" + date + " 기준 전 7일 동안의 지방 섭취량>");
                    text.setTextColor(Color.rgb(0, 0, 0));
                    text2.setText("'권장량 대비 " + sumFat2 + "%만큼 덜 섭취하였습니다'");
                    warning.setVisibility(View.GONE);
                }
                setData();
            }
        }

        private void setData () {

            int colors[] = new int[]{Color.BLUE, Color.YELLOW};

            ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry(0, Integer.valueOf(g)));
            entries.add(new Entry(1, Integer.valueOf(f)));
            entries.add(new Entry(2, Integer.valueOf(e)));
            entries.add(new Entry(3, Integer.valueOf(d)));
            entries.add(new Entry(4, Integer.valueOf(c)));
            entries.add(new Entry(5, Integer.valueOf(b)));
            entries.add(new Entry(6, Integer.valueOf(a)));

            LineDataSet dataSet = new LineDataSet(entries, "섭취량");
            dataSet.setColor(colors[1]);

            if (key == 2) {
                ArrayList<Entry> entries2 = new ArrayList<>();
                entries2.add(new Entry(0, Integer.valueOf(usercalory)));
                entries2.add(new Entry(1, Integer.valueOf(usercalory)));
                entries2.add(new Entry(2, Integer.valueOf(usercalory)));
                entries2.add(new Entry(3, Integer.valueOf(usercalory)));
                entries2.add(new Entry(4, Integer.valueOf(usercalory)));
                entries2.add(new Entry(5, Integer.valueOf(usercalory)));
                entries2.add(new Entry(6, Integer.valueOf(usercalory)));

                LineDataSet dataSet2 = new LineDataSet(entries2, "권장열량");
                dataSet2.setColor(colors[0]);

                LineData data = new LineData(dataSet, dataSet2);

                lineChart.setData(data);
                lineChart.animateY(5000);

            } else if (key == 3) {
                ArrayList<Entry> entries3 = new ArrayList<>();
                entries3.add(new Entry(0, Integer.valueOf(usercarbo)));
                entries3.add(new Entry(1, Integer.valueOf(usercarbo)));
                entries3.add(new Entry(2, Integer.valueOf(usercarbo)));
                entries3.add(new Entry(3, Integer.valueOf(usercarbo)));
                entries3.add(new Entry(4, Integer.valueOf(usercarbo)));
                entries3.add(new Entry(5, Integer.valueOf(usercarbo)));
                entries3.add(new Entry(6, Integer.valueOf(usercarbo)));

                LineDataSet dataSet3 = new LineDataSet(entries3, "권장탄수화물량");
                dataSet3.setColor(colors[0]);

                LineData data = new LineData(dataSet, dataSet3);

                lineChart.setData(data);
                lineChart.animateY(5000);

            } else if (key == 4) {
                ArrayList<Entry> entries4 = new ArrayList<>();
                entries4.add(new Entry(0, Integer.valueOf(userprotein)));
                entries4.add(new Entry(1, Integer.valueOf(userprotein)));
                entries4.add(new Entry(2, Integer.valueOf(userprotein)));
                entries4.add(new Entry(3, Integer.valueOf(userprotein)));
                entries4.add(new Entry(4, Integer.valueOf(userprotein)));
                entries4.add(new Entry(5, Integer.valueOf(userprotein)));
                entries4.add(new Entry(6, Integer.valueOf(userprotein)));

                LineDataSet dataSet4 = new LineDataSet(entries4, "권장단백질량");
                dataSet4.setColor(colors[0]);

                LineData data = new LineData(dataSet, dataSet4);

                lineChart.setData(data);
                lineChart.animateY(5000);

            } else if (key == 5) {
                ArrayList<Entry> entries5 = new ArrayList<>();
                entries5.add(new Entry(0, Integer.valueOf(userfat)));
                entries5.add(new Entry(1, Integer.valueOf(userfat)));
                entries5.add(new Entry(2, Integer.valueOf(userfat)));
                entries5.add(new Entry(3, Integer.valueOf(userfat)));
                entries5.add(new Entry(4, Integer.valueOf(userfat)));
                entries5.add(new Entry(5, Integer.valueOf(userfat)));
                entries5.add(new Entry(6, Integer.valueOf(userfat)));

                LineDataSet dataSet5 = new LineDataSet(entries5, "권장지방량");
                dataSet5.setColor(colors[0]);

                LineData data = new LineData(dataSet, dataSet5);

                lineChart.setData(data);
                lineChart.animateY(5000);
            } else if (key == 6) {
                ArrayList<Entry> entries6 = new ArrayList<>();
                entries6.add(new Entry(0, Integer.valueOf(usernatrium)));
                entries6.add(new Entry(1, Integer.valueOf(usernatrium)));
                entries6.add(new Entry(2, Integer.valueOf(usernatrium)));
                entries6.add(new Entry(3, Integer.valueOf(usernatrium)));
                entries6.add(new Entry(4, Integer.valueOf(usernatrium)));
                entries6.add(new Entry(5, Integer.valueOf(usernatrium)));
                entries6.add(new Entry(6, Integer.valueOf(usernatrium)));

                LineDataSet dataSet6 = new LineDataSet(entries6, "권장나트륨량");
                dataSet6.setColor(colors[0]);

                LineData data = new LineData(dataSet, dataSet6);

                lineChart.setData(data);
                lineChart.animateY(5000);
            }
        }

    public boolean onOptionsItemSelected (MenuItem item){
        if (t.onOptionsItemSelected(item))
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

























