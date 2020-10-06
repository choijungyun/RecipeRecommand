package com.targetyou.recipe;

import java.util.Calendar;
import java.util.Date;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import org.achartengine.GraphicalView;

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

public class CalendarActivity extends AppCompatActivity {

    HorizontalBarChart chart;
    String myJSON;

    private static final String RESULTS = "result";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADD ="address";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        HorizontalCalendar horizontalCalendar;

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, -1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

            }
        });

        int colors[] = new int[] {Color.GREEN, Color.YELLOW};

        chart = (HorizontalBarChart) findViewById(R.id.chart1);

        BarDataSet set1;
        BarDataSet set2;
        set1 = new BarDataSet(getDataSet(), "영양소량");
        set2 = new BarDataSet(getDataSet1(), "권장량");

        set1.setColor(colors[0]);
        //set1.setFormLineWidth(20f);
        set2.setColor(colors[1]);
        //set2.setFormLineWidth(20f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.3f);
        chart.setData(data);

        // hide Y-axis
        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false);

        // custom X-axis labels
        String[] values = new String[] { "나트륨", "당류", "지방", "단백질", "탄수화물", "열량"};
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyXAisValueFormatter(values));


        // custom description
        Description description = new Description();
        description.setText("Rating");
        chart.setDescription(description);

        // hide legend
        //chart.getLegend().setEnabled(false);

        chart.animateXY(2000,2000);
        //chart.invalidate();

    }


    public class MyXAisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }

    private ArrayList<BarEntry> getDataSet() {
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        BarEntry v1e2 = new BarEntry(0f, 40f);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(0.8f, 31f);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(2f, 55f);
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(2.8f, 60f);
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(3.6f, 27f);
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(4.5f, 27f);
        valueSet1.add(v1e7);

        return valueSet1;
    }

    private ArrayList<BarEntry> getDataSet1() {
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        BarEntry v1e2 = new BarEntry(0.4f, 40f);
        valueSet2.add(v1e2);
        BarEntry v1e3 = new BarEntry(1.4f, 40f);
        valueSet2.add(v1e3);
        BarEntry v1e4 = new BarEntry(2.4f, 40f);
        valueSet2.add(v1e4);
        BarEntry v1e5 = new BarEntry(3.2f, 40f);
        valueSet2.add(v1e5);
        BarEntry v1e6 = new BarEntry(4f, 40f);
        valueSet2.add(v1e6);
        BarEntry v1e7 = new BarEntry(5f, 40f);
        valueSet2.add(v1e7);

        return valueSet2;

    }


}
