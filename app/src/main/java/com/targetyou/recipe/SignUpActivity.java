package com.targetyou.recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignUpActivity extends FontActivity {

    private EditText editTextId;
    private EditText editTextPw;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        editTextId = findViewById(R.id.new_id);
        editTextPw = findViewById(R.id.new_pw);
    }

    public void insert(View view)
    {
        String Id = editTextId.getText().toString();
        String Pw = editTextPw.getText().toString();

        if (Id.getBytes().length <= 0) {
            Toast.makeText(getApplication(), "ID를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if (Pw.getBytes().length <= 0) {
            Toast.makeText(getApplication(), "PW를 입력하세요.", Toast.LENGTH_SHORT).show();
        }

        else {
            insertToDatabase(Id, Pw);

            intent = new Intent(SignUpActivity.this, DetailActivity.class);

            intent.putExtra("email", Id);

            startActivity(intent);
        }
    }

    private void insertToDatabase(String Id, String Pw) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUpActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];
                    String Pw = (String) params[1];

                    String link = "http://naancoco.cafe24.com/and/signup.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id, Pw);
    }
}
