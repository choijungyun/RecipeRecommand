package com.targetyou.recipe;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends FontActivity {

    Button BtnSignIn, BtnSignUp;
    EditText inputID, inputPW;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    URL phpUrl;
    StringBuilder jsonHtml;

    Intent intent;
    String email;
    Intent intent2;
    String email2;

    private boolean saveLoginData;
    private String id, pw;
    private CheckBox auto;
    private SharedPreferences appData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Intent getintent = getIntent();
        email = getintent.getStringExtra("email");

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        BtnSignUp = findViewById(R.id.btn_signup);
        BtnSignIn = findViewById(R.id.login);
        inputID = findViewById(R.id.user_id);
        inputPW = findViewById(R.id.user_pw);
        auto = findViewById(R.id.autoLogin);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (saveLoginData) {
            intent = new Intent(SignInActivity.this, RefrigeratorActivity.class);
            intent.putExtra("email", id);
            startActivity(intent);
            finish();
        }



        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(SignInActivity.this, "",
                        "Validating user...", true);
                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();
            }
        });
    }

    private void load() {
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pw = appData.getString("PW", "");
    }

    void login() {
        try {
            httpclient = new DefaultHttpClient();
            phpUrl = new URL("http://naancoco.cafe24.com/and/signin.php");
            httppost = new HttpPost(String.valueOf(phpUrl));
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("useremail", inputID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("userpass", inputPW.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    jsonHtml = new StringBuilder();
                    try {
                        HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();
                        if ( conn != null ) {
                            conn.setConnectTimeout(10000);
                            conn.setUseCaches(false);

                            if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                                while ( true ) {
                                    String line = br.readLine();
                                    if ( line == null )
                                        break;
                                    jsonHtml.append(line + "\n");
                                }
                                br.close();
                            }
                            conn.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (response.equalsIgnoreCase("null")) {
                Toast.makeText(SignInActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        save();
                        Toast.makeText(SignInActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        intent = new Intent(SignInActivity.this, RefrigeratorActivity.class);
                        intent.putExtra("email", inputID.getText().toString());
                        startActivity(intent);

                        intent2 = new Intent(SignInActivity.this, DietMainActivity.class);
//editText에 입력된 문자열을 넘겨줄 때 putExtra를 사용합니다. 넘겨줄 데이터의 이름과 데이터를 적어주면 된다.
//이때, 사용한 이름은 데이터를 넘겨받는 액티비티에서도 똑같이 써줘야 한다.
//에디트텍스트에서 텍스트를 불러오는 부분에서는 그냥 쓰게 되면 String형으로 반환되지 않아서 String형으로 변환시켜줌.
                        intent2.putExtra("email2",email2);

                    }
                });

            }
        }
        catch(Exception e)
        {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }}

    private void save() {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("SAVE_LOGIN_DATA", auto.isChecked());
        editor.putString("ID", inputID.getText().toString().trim());
        editor.putString("PW", inputPW.getText().toString().trim());

        editor.apply();
    }


    public void CliSignUp(View view)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void insert(View view) {
    }
}


