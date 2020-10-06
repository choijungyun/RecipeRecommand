package com.targetyou.recipe;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.net.MalformedURLException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Ref_AddActivity extends FontActivity implements DatePickerDialog.OnDateSetListener, ZXingScannerView.ResultHandler {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private EditText material, amount;
    private TextView unit;
    private Button addBtn;
    private String result, email;

    private ZXingScannerView zXingScannerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref__add);

        Intent getintent = getIntent();
        email = getintent.getStringExtra("email");

        intent = new Intent(this, Ref_AddActivity2.class);
        intent.putExtra("email", email);

        dl = (DrawerLayout) findViewById(R.id.activity_ref_add);
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




        final EditText dText = (EditText) findViewById(R.id.editText);


        material = (EditText) findViewById(R.id.add_material);
        amount = (EditText) findViewById(R.id.add_amount);
        unit = (TextView) findViewById(R.id.add_unit);
        addBtn = (Button) findViewById(R.id.addbtn);

//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        String getTime = sdf.format(date);
//
//        dText.setText(getTime);

        NetworkUtil.setNetworkPolicy();
        material.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    try {
                        PHPRequest request1 = new PHPRequest("http://naancoco.cafe24.com/and/unit.php");
                        String response = request1.PhPunit(material.getText().toString());
                        unit.setText(response);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });


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
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("result", "OK");
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

    public void scan(View view) {
        zXingScannerView =new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

//        Intent resultIntent = new Intent(getApplicationContext(), Ref_AddActivity2.class);
//        resultIntent.putExtra("email", email);
//        resultIntent.putExtra("barcode", "8801097150010");
//        startActivityForResult(resultIntent, 1);

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        zXingScannerView.stopCamera();
//    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        // zXingScannerView.resumeCameraPreview(this);

        Intent resultIntent = new Intent(getApplicationContext(), Ref_AddActivity2.class);
        resultIntent.putExtra("email", email);
        resultIntent.putExtra("barcode", result.getText());
        startActivityForResult(resultIntent, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "canceled", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case 1:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", "OK");
                setResult(RESULT_OK, resultIntent);
                finish();
                break;
        }
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