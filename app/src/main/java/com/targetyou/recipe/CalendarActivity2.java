package com.targetyou.recipe;

import java.net.MalformedURLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import org.achartengine.GraphicalView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;


import java.util.ArrayList;
import java.util.List;

public class CalendarActivity2 extends AppCompatActivity {

    private static final String TAG = "CalendarActivity2";

    private TextView material;
    private TextView theDate;
    private Button btnGoCalendar;
    private Button btncalory;
    private Button btncarbo;
    private Button btnprotein;
    private Button btnfat;
    private Button btnnatrium;

    HorizontalBarChart mChart;

    /*   HttpPost httppost;
       HttpResponse response;
       HttpClient httpclient;
       List<NameValuePair> nameValuePairs;
       URL phpUrl;
       StringBuilder jsonHtml;
       String email;
       Intent intent;
       String myJSON;

       private static final String RESULTS = "result";
       private static final String ID = "id";
       private static final String NAME = "name";
       private static final String ADD = "address";

       JSONArray peoples = null;

       ArrayList<HashMap<String, String>> personList;

       ListView list;*/
    String email;
    String date;
    String energy;
    String calory;
    String fat;
    String protein;
    String natrium;
    String result;
    String result1;
    String usercalory;
    String userfat;
    String usercarbo;
    String userprotein;
    String usernatrium;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar2);

        mChart = (HorizontalBarChart) findViewById(R.id.chart1);

        Intent intent = new Intent(this.getIntent());
        email = intent.getStringExtra("email");
        date = intent.getStringExtra("date");

        //material = (TextView) findViewById(R.id.textView12);
        //theDate = (TextView) findViewById(R.id.date);
        btnGoCalendar = (Button) findViewById(R.id.btnGoCalendar);
        btncalory = (Button) findViewById(R.id.button4);
        btncarbo = (Button) findViewById(R.id.button5);
        btnprotein = (Button) findViewById(R.id.button6);
        btnfat = (Button) findViewById(R.id.button7);
        btnnatrium = (Button) findViewById(R.id.button8);

        NetworkUtil.setNetworkPolicy();
        try {
           // Log.i("dddddddddddddddddddddd", date);
            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/calendar.php");
            PHPRequest request1 = new PHPRequest("http://naancoco.cafe24.com/calendar2.php");
            //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
            result = request.phpCalendar(email, date);
            result1 = request1.phpCalendar2(email);
            Log.i("result", result1);
            //theDate.setText(result);

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //응답부분을 처리하도록 하면 된다.
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        calory = object.getString("energy_re"); //탄수화물
                        energy = object.getString("calory_re"); //열량
                        protein = object.getString("protein_re"); //7
                        fat = object.getString("fat_re"); //11
                        natrium = object.getString("natrium_re"); //118
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }

            if (result1 != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result1);
                    //응답부분을 처리하도록 하면 된다.
                    JSONArray jsonArray = jsonObject.getJSONArray("response2");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        usercalory = object.getString("usercalory");
                        if(object.getString("usernatrium").length() == 0){
                            usernatrium = "0";
                        }else{
                            usernatrium = object.getString("usernatrium");
                        }
                        Log.i("Ddddddddddddddddddd",usernatrium);
                        userfat = object.getString("userfat");
                        userprotein = object.getString("userprotein");
                        usercarbo = object.getString("usercarbo");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setData();

        //theDate.setText(calory);
        //material.setText(usercarbo);
        //theDate.setText(protein);
        //theDate.setText(fat);
        //theDate.setText(natrium);

        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity2.this, CalendarActivity3.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        btncalory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity2.this, calendarActivity4.class);
                intent.putExtra("email", email);
                intent.putExtra("date", date);
                intent.putExtra("usercalory", usercalory);
                intent.putExtra("key", 2);
                startActivity(intent);
            }
        });
        btncarbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity2.this, calendarActivity4.class);
                intent.putExtra("email",email);
                intent.putExtra("date", date);
                intent.putExtra("usercarbo", usercarbo);
                intent.putExtra("key", 3);
                startActivity(intent);
            }
        });
        btnprotein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity2.this, calendarActivity4.class);
                intent.putExtra("email",email);
                intent.putExtra("date", date);
                intent.putExtra("userprotein",userprotein);
                intent.putExtra("key",4);
                startActivity(intent);
            }
        });
        btnfat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity2.this, calendarActivity4.class);
                intent.putExtra("email",email);
                intent.putExtra("date", date);
                intent.putExtra("userfat",userfat);
                intent.putExtra("key", 5);
                startActivity(intent);
            }
        });
        btnnatrium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity2.this, calendarActivity4.class);
                intent.putExtra("email",email);
                intent.putExtra("date", date);
                intent.putExtra("usernatrium", usernatrium);
                intent.putExtra("key", 6);
                startActivity(intent);

            }
        });
    }

    private void setData() {

        //int colors[] = new int[] {Color.GRAY, new Color(240,215,210)};

        BarDataSet set1;
        BarDataSet set2;

        set1 = new BarDataSet(getDataSet(), "영양소량");
        set2 = new BarDataSet(getDataSet1(), "권장량");

        set1.setColor(Color.parseColor("#F0D7D2"));
        //set1.setFormLineWidth(20f);
        set2.setColor(Color.parseColor("#88878C"));
        //set2.setFormLineWidth(20f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.4f);
        mChart.setData(data);

        // hide Y-axis
        YAxis left = mChart.getAxisLeft();
        left.setDrawLabels(false);

        // custom X-axis label


        // custom description
        Description description = new Description();
        description.setText("Rating");
        mChart.setDescription(description);

        // hide legend
        //chart.getLegend().setEnabled(false);

        mChart.animateXY(2000,2000);
        //chart.invalidate();

        /*String[] values = new String[] { "나트륨", "지방", "단백질", "탄수화물", "열량"};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new MyXAisValueFormatter(values));

        int colors[] = new int[] {Color.BLUE, Color.YELLOW};

        ArrayList<BarEntry> yVals = new ArrayList<>();
        ArrayList<BarEntry> xVals = new ArrayList<>();

        yVals.add(new BarEntry(0f, Integer.valueOf(energy))); //218
        yVals.add(new BarEntry(2f, Integer.valueOf(calory)));  //22
        yVals.add(new BarEntry(4f, Integer.valueOf(protein)));
        yVals.add(new BarEntry(6f, Integer.valueOf(fat)));
        yVals.add(new BarEntry(8f, Integer.valueOf(natrium)));

        xVals.add(new BarEntry(1f, Integer.valueOf(userenergy)));
        xVals.add(new BarEntry(3f, Integer.valueOf(usercalory)));
        xVals.add(new BarEntry(5f, Integer.valueOf(usercarbo)));
        xVals.add(new BarEntry(7f, Integer.valueOf(userfat)));
        xVals.add(new BarEntry(9f, Integer.valueOf(usernatrium)));


        BarDataSet set1;
        BarDataSet set2;

        set1 = new BarDataSet(yVals, "섭취량");
        set2 = new BarDataSet(xVals, "권장량");
        set1.setColor(colors[0]);
        set2.setColor(colors[1]);

        BarData data = new BarData(set1,set2);
        mChart.setData(data);*/
    }

    private ArrayList<BarEntry> getDataSet() {
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        BarEntry v1e2 = new BarEntry(0f, Integer.valueOf(natrium));
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(1f, Integer.valueOf(fat));
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(2f, Integer.valueOf(protein));
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(3f, Integer.valueOf(calory));
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(4f, Integer.valueOf(energy));
        valueSet1.add(v1e6);

        return valueSet1;
    }

    private ArrayList<BarEntry> getDataSet1() {
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        BarEntry v1e2 = new BarEntry(0.5f, Integer.valueOf(usernatrium));
        valueSet2.add(v1e2);
        BarEntry v1e3 = new BarEntry(1.5f, Integer.valueOf(userfat));
        valueSet2.add(v1e3);
        BarEntry v1e4 = new BarEntry(2.5f, Integer.valueOf(userprotein));
        valueSet2.add(v1e4);
        BarEntry v1e5 = new BarEntry(3.5f, Integer.valueOf(usercarbo));
        valueSet2.add(v1e5);
        BarEntry v1e6 = new BarEntry(4.5f, Integer.valueOf(usercalory));
        valueSet2.add(v1e6);

        return valueSet2;

    }

}










