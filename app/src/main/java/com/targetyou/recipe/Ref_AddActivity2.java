package com.targetyou.recipe;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Ref_AddActivity2 extends FontActivity implements DatePickerDialog.OnDateSetListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private EditText material, amount;
    private TextView unit;
    private Button addBtn;
    private String result, barcode, email;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref__add2);

        Intent getintent = getIntent();
        email = getintent.getStringExtra("email");
        barcode = getIntent().getStringExtra("barcode");

        final EditText dText = (EditText) findViewById(R.id.editText);
        material = (EditText) findViewById(R.id.add_material);
        amount = (EditText) findViewById(R.id.add_amount);
        unit = (TextView) findViewById(R.id.add_unit);
        addBtn = (Button) findViewById(R.id.addbtn);

        dl = (DrawerLayout) findViewById(R.id.activity_ref_add2);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0f);

        nv = (NavigationView)findViewById(R.id.nv);
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


        nv = (NavigationView) findViewById(R.id.nv);
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


        Toast.makeText(this, barcode, Toast.LENGTH_SHORT).show();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        HttpResponse respond;
        HttpClient client = new DefaultHttpClient();
        InputStream IS;

        nameValuePairs.add(new BasicNameValuePair("userbarcode", barcode));

        NetworkUtil.setNetworkPolicy();

        try {
            HttpPost post = new HttpPost("http://naancoco.cafe24.com/and/barcode.php");
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            respond = client.execute(post);
            HttpEntity responseResultEntity = respond.getEntity();
            if (responseResultEntity != null) {
                IS = responseResultEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(IS, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line + "\n");
                IS.close();
                String response = sb.toString();

                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                String m = null;

                for ( int i = 0; i < jsonArray.length(); ++i ) {
                    JSONObject JS = jsonArray.getJSONObject(i);
                    m = JS.getString("material");
                }
                material.setText(m);
                unit.setText("g");
                try {
                    PHPRequest request1 = new PHPRequest("http://naancoco.cafe24.com/and/unit.php");
                    String res = request1.PhPunit(material.getText().toString());
                    unit.setText(res);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

//
//                Date date = new Date(System.currentTimeMillis());
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//                String getTime = sdf.format(date);
//
//                dText.setText(getTime);

            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }



        dText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                EditText editText = (EditText) findViewById(R.id.editText);
                DialogPicker dp = new DialogPicker(editText);
                dp.show(getFragmentManager(), "DatePicker");
            }
        });


        NetworkUtil.setNetworkPolicy();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((material.getText().toString()).getBytes().length <= 0)
                    Toast.makeText(getApplication(), "재료명을 입력하세요.", Toast.LENGTH_SHORT).show();
                else if ((amount.getText().toString()).getBytes().length <= 0)
                    Toast.makeText(getApplication(), "수량을 입력하세요.", Toast.LENGTH_SHORT).show();
                else if ((dText.getText().toString()).getBytes().length <= 0)
                    Toast.makeText(getApplication(), "구매일자를 입력하세요.", Toast.LENGTH_SHORT).show();
                else {

                    try {
                        PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/ref_add.php");

                        String m, a, d;
                        m = material.getText().toString();
                        a = amount.getText().toString();
                        d = dText.getText().toString();

                        String response = request.PhPRef(email, m, a, d);
                        if (response.equals("success")) {

                            Toast.makeText(getApplication(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                            result = m + " " + a + unit.getText();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("result", result);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

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