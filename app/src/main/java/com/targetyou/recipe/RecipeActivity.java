package com.targetyou.recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {


    private RadarChart Chart;
    public static final float MAX = 60, MIN=5;
    List<String> list = new ArrayList<>();
    Intent intent;
    String re_name;
    String re_material;
    String re_amount;
    String e;
    String c;
    String n;
    String f;
    String p;
    String cook_method;
    String cook2_method;
    String price;
    String unit;
    String email;
    String amount;
    TextView textView;
    TextView textView2;
    TextView textView3;
    String st_material;
    String st_amount;
    Integer st_material_size;
    ArrayList<String> st_List = new ArrayList<>();
    ArrayList<String> st_List2 = new ArrayList<>();
    ArrayList<String> materialList = new ArrayList<>();
    ArrayList<String> amountList = new ArrayList<>();

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe);



        dl = findViewById(R.id.activity_recipe);


        final  EditText add_text = findViewById(R.id.add2);
        textView = findViewById(R.id.re_material);
        textView2 = findViewById(R.id.cooking_method);
        textView3 = findViewById(R.id.cooking_method2);
        Button re_button = findViewById(R.id.re_button);
        Button add_button = findViewById(R.id.add);
        re_name = getIntent().getStringExtra("re_name");
        re_material = getIntent().getStringExtra("re_material");
        re_amount = getIntent().getStringExtra("re_amount");
        cook_method = getIntent().getStringExtra("cook_method");
        cook2_method = getIntent().getStringExtra("cook2_method");
        unit = getIntent().getStringExtra("unit");
        price = getIntent().getStringExtra("price");
        email = getIntent().getStringExtra("email");
        st_material_size = getIntent().getIntExtra("st_material_size", 0);

        for (int i = 0; i < st_material_size; i++) {
            st_material = getIntent().getStringExtra("st_material" + i);
            st_amount = getIntent().getStringExtra("st_amount" + i);
            st_List.add(st_material);
            st_List2.add(st_amount);
        }
        e = getIntent().getStringExtra("e");
        c = getIntent().getStringExtra("c");
        n = getIntent().getStringExtra("n");
        f = getIntent().getStringExtra("f");
        p = getIntent().getStringExtra("p");
        Chart = findViewById(R.id.chart1);
        Chart.setBackgroundColor(Color.rgb(240, 215, 210));
        Chart.getDescription().setEnabled(false);
        Chart.setWebLineWidth(1f);
        Chart.setWebColor(Color.LTGRAY);
        Chart.setWebLineWidthInner(1f);
        Chart.setWebColorInner(Color.LTGRAY);
        Chart.setWebAlpha(100);
        setData();
        Chart.animateXY(1400, 1400, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = Chart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0);
        xAxis.setXOffset(0);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"ENERGY"+" " +e,
                    "CALORY" +" " +c, "PROTEIN" +" "+p, "NATRIUM" + " "+ n, "FAT" + " " + f};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.DKGRAY);

        YAxis yAxis = Chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(80);
        yAxis.setAxisMaximum(180);
        yAxis.setDrawLabels(false);

        Legend l = Chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.DKGRAY);


        textView.setText(re_material);
        textView2.setText(cook_method);
        textView3.setText(cook2_method);

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






        final Intent intent = new Intent(getApplicationContext(),MaterialActivity.class);
        final Intent intent1 = new Intent(getApplicationContext(),DietMainActivity.class);
        final String re_materialarr[] = re_material.split(",");
        final String re_amountarr[] = re_amount.split(",");

//레시피 재료 파싱 이랑 냉장고 재료랑 비교 같다면 양 비교
        for(int i=0; i<re_materialarr.length; i++){
            materialList.add(re_materialarr[i]);
            amountList.add(re_amountarr[i]);

        }
        final int k = materialList.size();

//        Log.d("amount",amount);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = add_text.getText().toString();
                try {
                    for(int j=0;j< k ; j++){
                        System.out.println("1"+materialList.get(j).toString());
                        System.out.println("1"+st_material);
                        for(int k=0; k<st_material_size; k++){
                            if(materialList.get(j).toString().equals(st_List.get(k).toString())) {
                                try {
                                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/material_del.php");
                                    String result1 = request.PhPdel(email, st_List.get(k).toString(), amountList.get(j).toString());
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }



                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/re_add.php");
                    String result = request.PhPadd(email,re_name,amount);
                    if (result != null) {
                        Toast.makeText(getApplicationContext(),   re_name+ amount.toString() + "인분이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        intent1.putExtra("email2",email);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(getApplicationContext(), "입력이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });



        re_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("re_material",re_material);
                intent.putExtra("re_name", re_name);
                intent.putExtra("re_amount", re_amount);
                intent.putExtra("unit",unit);
                intent.putExtra("price", price);
                intent.putExtra("st_material_size", st_material_size);
                for (int k = 0; k < st_material_size; k++) {
                    intent.putExtra("st_material" + k, st_List.get(k).toString());
                }
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }





    private void setData() {




        ArrayList<RadarEntry> employee1 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

//차트구성

//        int val1 = Integer.parseInt(list.get(0));
//            employee1.add(new RadarEntry(val1));
        Float val1 = (Float.parseFloat(c)/900* MAX ) +MIN ;
        employee1.add(new RadarEntry(val1));
        System.out.println(val1);
        Float val2 = (Float.parseFloat(e)/900 * MAX)+MIN ;
        employee1.add(new RadarEntry(val2));
        System.out.println(val2);
        Float val3 = (Float.parseFloat(p)/900 *MAX)+MIN ;
        employee1.add(new RadarEntry(val3));
        System.out.println(val3);
        Float val4 = (Float.parseFloat(n)/900 *MAX)+MIN ;
        employee1.add(new RadarEntry(val4));
        System.out.println(val4);
        Float val5 = (Float.parseFloat(f)/900 *MAX)+MIN ;
        //float val5 = (float) (Math.random() * MAX) + MIN;
        employee1.add(new RadarEntry(val5));
        System.out.println(val5);
//            float val2 = (float) (Math.random() * MAX) + MIN;
//            employee2.add(new RadarEntry(val2));


        RadarDataSet set1 = new RadarDataSet(employee1, null);
        set1.setColor(Color.rgb(240, 215, 210));
        set1.setFillColor(Color.rgb(234, 234, 234));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);


        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(80);
        data.setDrawValues(false);
        data.setValueTextColor(Color.DKGRAY);

        Chart.setData(data);
        Chart.invalidate();
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