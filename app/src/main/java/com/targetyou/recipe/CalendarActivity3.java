package com.targetyou.recipe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;
import com.squareup.timessquare.CalendarPickerView;

import org.apache.http.HttpEntity;
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
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity3 extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

    ProgressDialog dialog = null;
    URL phpUrl;
    StringBuilder jsonHtml;

    // private static final String TAG = "CalendarActivity3";
    //private static final String jsonurl = "http://naancoco.cafe24.com/calendar.php";
    private CalendarView mCalendarView;
    String selectedDate;
    String date;
    String tvJSON;
    //String result;
    String email;
    Intent intent2;
    String energy;
    String calory;
    String fat;
    String protein;
    String natrium;

    private List<BarEntry> noticeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar3);

        Intent intent = new Intent(this.getIntent());
        email = intent.getStringExtra("email");


        dl = (DrawerLayout) findViewById(R.id.activity_calendar3);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

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

            Date today = new Date();
            Calendar lastYear = Calendar.getInstance();
            Calendar nextYear = Calendar.getInstance();
            nextYear.add(Calendar.YEAR, 1);
            lastYear.add(Calendar.YEAR, -1);

            CalendarPickerView datePicker = findViewById(R.id.calendar);
            datePicker.init(lastYear.getTime(), nextYear.getTime()).withSelectedDate(today);

            datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
                @Override
                public void onDateSelected(Date date) {
                    Calendar calSelected = Calendar.getInstance();
                    calSelected.setTime(date);

                    selectedDate = calSelected.get(Calendar.YEAR) + "-" + (calSelected.get(Calendar.MONTH) + 1) + "-" + calSelected.get(Calendar.DAY_OF_MONTH);
                    if (selectedDate.substring(5, 6).equals("8") || selectedDate.substring(5, 6).equals("9")) {
                        selectedDate = calSelected.get(Calendar.YEAR) + "-0" + (calSelected.get(Calendar.MONTH) + 1) + "-" + calSelected.get(Calendar.DAY_OF_MONTH);
                    }


                    //Toast.makeText(CalendarActivity3.this, selectedDate, Toast.LENGTH_SHORT).show();

                    intent2 = new Intent(getApplicationContext(), CalendarActivity2.class);
                    intent2.putExtra("email", email);
                    intent2.putExtra("date", selectedDate);
                    startActivity(intent2);
                }

                @Override
                public void onDateUnselected(Date date) {

                }

            });
               /* NetworkUtil.setNetworkPolicy();
                try {
                    Log.i("dddddddddddddddddddddd", date);
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/calendar.php");
                    //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
                    String result = request.phpCalendar(email, date);
                    intent2 = new Intent(CalendarActivity3.this, CalendarActivity2.class);
                    intent2.putExtra("date1", result);
                    //intent2.putExtra("date2",calory);
                    //intent2.putExtra("date3",protein);
                    //intent2.putExtra("date4",fat);
                    //intent2.putExtra("date5",natrium);
                    intent2.putExtra("email", email);
                    startActivity(intent2);*/

            /*           Log.i("result", result);
             *//*if (result != null) {
                        try {

                            //응답부분을 처리하도록 하면 된다.
                            JSONObject jsonObject = new JSONObject(result);
                            Log.i("json", result);
                            //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            //int count = 0;
                            if(count!=0){
                                for(int i=0;i<count;i++){
                                    adapter.remove(adapter.getItem(i));
                                }
                            }
                            int count = 0;

                            //nanoticeList.clear();
                            //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                            //adapter.notifyDataSetChanged();
                            //adapter.notifyDataSetInvalidated();
                           if(jsonArray.length()==0){
                                noText.setVisibility(View.VISIBLE);
                                noImage.setVisibility(View.VISIBLE);
                            }
                            while (count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);
                                energy = object.getString("energy_re");
                                calory = object.getString("calory_re");
                                protein = object.getString("protein_re");
                                fat = object.getString("fat_re");
                                natrium = object.getString("natrium_re");

                               // String noticeName = object.getString("amount") + "인분";
                               // String noticeDate = object.getString("time");
                                //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용


                               Notice notice = new Notice(noticeContent, noticeName,noticeDate);
                                //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                                noticeList.add(notice);
                                adapter.notifyDataSetChanged();
                                count++;


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }*//*
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/
                /*try {
                    Log.i("erroreeeeeee", date);
                    result = getJSON("http://naancoco.cafe24.com/calendar.php");
                    Log.i("errorddddddddd", result);
                } catch (Exception e) {
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connect();
                    }
                }).start();*/

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


















/*    public void getJSON(String url, final String date) throws IOException {
        class GeJSON extends AsyncTask<String, Void, String> {
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                   httpclient = new DefaultHttpClient();
                   URL url = new URL(uri);
                   httppost = new HttpPost(params[0]);
                   nameValuePairs = new ArrayList<NameValuePair>(1);
                   Log.i("dateeeeeeeeeeeeee",date);
                   nameValuePairs.add(new BasicNameValuePair("date", date));
                   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                   HttpResponse response = httpclient.execute(httppost);
                   HttpURLConnection con = (HttpURLConnection) url.openConnection();
                   Log.i("successssssssssssssss", response.toString());
                   StringBuilder sb = new StringBuilder();
                   bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent(CalendarActivity3.this, CalendarActivity2.class);
                intent.putExtra("date", s);
                startActivity(intent);
            }
        }
        GeJSON gj = new GeJSON();
        gj.execute(url);
    }*/
       /* StringBuilder sb = new StringBuilder();

        URL url = new URL(uri);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));

            String inputLine;
            while ((inputLine = bin.readLine()) != null) {
                sb.append(inputLine);
            }
        } finally {
            urlConnection.disconnect();
        }
        return sb.toString();
    }*/


