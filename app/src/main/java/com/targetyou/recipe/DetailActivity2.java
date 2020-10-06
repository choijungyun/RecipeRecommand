package com.targetyou.recipe;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DetailActivity2 extends FontActivity {

    Button btn1;
    ImageButton btn_disease;
    TextView ItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String email, sex, age, high, weight, energy, usercarbo, userfat, usernatrium, calory, activity, item;
    double ener, carbo, fat, natrium;
    private Spinner spinner;
    Intent intent;
    TextView tv;
    private static final String TAG_JSON = "result";
    private static final String TAG_NAME = "listFood";
    private ArrayList<String> mArrayList;
    AutoCompleteTextView autoedit;
    String mJsonString;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        final Intent getintent = getIntent();
        email = getintent.getStringExtra("email");
        sex = getintent.getStringExtra("sex");
        age = getintent.getStringExtra("age");
        high = getintent.getStringExtra("high");
        weight = getintent.getStringExtra("weight");

        intent = new Intent(DetailActivity2.this, SignInActivity.class);
        intent.putExtra("email", email);

        tv = findViewById(R.id.textView4);

        autoedit = (AutoCompleteTextView) findViewById(R.id.autoedit);
        mArrayList = new ArrayList<String>();
        GetData task = new GetData();
        task.execute("http://naancoco.cafe24.com/and/search.php");

        btn_disease = (ImageButton) findViewById(R.id.btn);
        ItemSelected = (TextView) findViewById(R.id.itemSelected);
        btn1 = (Button) findViewById(R.id.button);
        spinner = (Spinner) findViewById(R.id.spinner_act);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.activity, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activity = (String) spinner.getSelectedItem();

                if (activity.equals("저활동적")) {
                    if (sex.equals("남성"))
                        ener = (622-(9.53*Integer.parseInt(age))+(1.1*((15.91*Integer.parseInt(weight))+
                                (536.6*Integer.parseInt(high)))))/1000;
                    else if (sex.equals("여성"))
                        ener = (354-(6.91*Integer.parseInt(age))+(1.2*((9.36*Integer.parseInt(weight))
                                +(726*Integer.parseInt(high)))))/1000;
                }
                else if (activity.equals("매우 활동적")){
                    if (sex.equals("남성"))
                        ener = (622-(9.53*Integer.parseInt(age))+(1.48*((15.91*Integer.parseInt(weight))+
                                (536.6*Integer.parseInt(high)))))/1000;
                    else if (sex.equals("여성"))
                        ener = (354-(6.91*Integer.parseInt(age))+(1.45*((9.36*Integer.parseInt(weight))
                                +(726*Integer.parseInt(high)))))/1000;
                }

                energy = String.valueOf(Double.parseDouble(String.format("%.0f", ener)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        listItems = getResources().getStringArray(R.array.disease_item);
        checkedItems = new boolean[listItems.length];


        btn_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailActivity2.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserItems.contains(pos)) {
                                mUserItems.add(pos);
                            }
                        } else if (mUserItems.contains(pos)) {
                            mUserItems.remove(pos);
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1)
                                item = item + ", ";
                        }
                        ItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            ItemSelected.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        NetworkUtil.setNetworkPolicy();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fat = Double.parseDouble(energy) * 0.7 / 9;
                    userfat = String.valueOf(Double.parseDouble(String.format("%.0f", fat)));
                    natrium = 3700;
                    usernatrium = String.valueOf(Double.parseDouble(String.format("%.0f", natrium)));
                    carbo = Double.parseDouble(energy) * 7 / 4;
                    usercarbo = String.valueOf(Double.parseDouble(String.format("%.0f", carbo)));
                    double cal = Double.parseDouble(energy) *10;
                    calory = String.valueOf(Double.parseDouble(String.format("%.0f", cal)));

                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/energychange.php");


                    String result = request.PhPDetail(sex, age, high, weight, energy, activity, usercarbo,
                            userfat, usernatrium, calory, item, tv.getText().toString(), email);
                    if (result.equals("success")) {
                        Toast.makeText(getApplication(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void prev(View view) {
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DetailActivity2.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null) {

            } else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return "";
            }

        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, mArrayList);

            autoedit.setAdapter(adapter);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String name = item.getString(TAG_NAME);
                mArrayList.add(name);
            }

            autoedit.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        tv.setText(autoedit.getText().toString());
                        autoedit.setText("");
                        return true;
                    }
                    return false;
                }
            });

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
