package com.targetyou.recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DietSearchActivity extends AppCompatActivity {
    //private로 해당 클래스에 멤버 변수를 만들어준 상태
    private ListView searchListView;
    private SearchListAdapter adapter;
    private List<Search> searchList;
    String email2,newdate,dietTime,result;
    EditText refriName;

    //new추가된 변수들
    private static String TAG = "recipe";


    private static final String TAG_JSON = "response";

    private static final String TAG_ID = "material";

    private static final String TAG_NAME = "amount";

    private static final String TAG_ADDRESS = "purchase_date";


    EditText mEditTextSearchKeyword;


    ArrayList<HashMap<String, String>> mArrayList;

    ListView mListViewList;



    String mJsonString;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_search);
        //writing페이지에섯 받아온 intent들
        Intent intent = new Intent(this.getIntent());
        email2=intent.getStringExtra("email2");
        newdate=intent.getStringExtra("newdate");
        dietTime=intent.getStringExtra("dietTime");
        final TextView textView=(TextView) findViewById(R.id.textView14);
        final Button searchRefButton = (Button) findViewById(R.id.searchRefButton);
        final Button completeRefButton = (Button)findViewById(R.id.completeButton_Search);



        searchListView = (ListView) findViewById(R.id.searchListView);
        searchList = new ArrayList<Search>();
        /*searchList.add(new Search("오이","1","개","2017-01-01"));
        searchList.add(new Search("오이","1","개","2017-01-01"));
        searchList.add(new Search("오이","1","개","2017-01-01"));
        searchList.add(new Search("오이","1","개","2017-01-01"));
        searchList.add(new Search("오이","1","개","2017-01-01"));
        searchList.add(new Search("오이","1","개","2017-01-01"));
        searchList.add(new Search("오이","1","개","2017-01-01"));*/
        adapter = new SearchListAdapter(getApplicationContext(), searchList);
        searchListView.setAdapter(adapter);
        mEditTextSearchKeyword = (EditText) findViewById(R.id.RefriNameText);
        NetworkUtil.setNetworkPolicy();
        //냉장고 검색시
        searchRefButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietRefriSelect.php");
                    //String result = request.PhPDietFood(mEditTextSearchKeyword.getText().toString());
                    result = request.PhPDietSearchSelect(email2,mEditTextSearchKeyword.getText().toString());
                    if (result != null) {
                        try {
                            //응답부분을 처리하도록 하면 된다.
                            JSONObject jsonObject = new JSONObject(result);
                            //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            int count = 0;
                            searchList.clear();
                            //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                            //adapter.notifyDataSetChanged();
                            adapter.notifyDataSetInvalidated();
                            while (count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);

                                String materialText = object.getString("material");
                                String amountText = object.getString("amount");
                                String unit = "개";
                                String dateText = object.getString("purchase_date");
                                //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                                Search search = new Search(materialText, amountText, unit, dateText);
                                //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                                searchList.add(search);
                                adapter.notifyDataSetChanged();
                                count++;
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
            }

        });
        completeRefButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), DietMainActivity.class);

                ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                intent3.putExtra("email2", email2);
                intent3.putExtra("newdate", newdate);
                startActivity(intent3);
            }

        });

        //그냥 띄워줄때
        try {
            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietRefrigerator2.php");
            String result = request.PhPDietSearch(email2);
            if (result != null) {
                try {
                    //응답부분을 처리하도록 하면 된다.
                    JSONObject jsonObject = new JSONObject(result);
                    //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    searchList.clear();
                    //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                    //adapter.notifyDataSetChanged();
                    adapter.notifyDataSetInvalidated();

                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);

                        String materialText = object.getString("material");
                        String amountText = object.getString("amount");
                        String unit = null;
                        try {
                            PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/prac.php");
                            String result2 = request2.PhPDietSearchUnit(materialText);
                            if (result2 != null) {
                                try {
                                    //응답부분을 처리하도록 하면 된다.
                                    JSONObject jsonObject2 = new JSONObject(result2);
                                    //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                                    JSONArray jsonArray2 = jsonObject2.getJSONArray("response");
                                    int count2 = 0;

                                    //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                                    //adapter.notifyDataSetChanged();


                                    while (count2 < jsonArray.length()) {
                                        JSONObject object2 = jsonArray2.getJSONObject(count2);
                                        unit = object2.getString("unit");
                                        count2++;
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
                        String dateText = object.getString("purchase_date");
                        //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                        Search search = new Search(materialText, amountText, unit, dateText);
                        //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                        searchList.add(search);
                        adapter.notifyDataSetChanged();
                        count++;
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
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                Search selected_item = (Search) adapterView.getItemAtPosition(position);
                String selectedFood = selected_item.getMaterial();

                //텍스트뷰에 출력(확인작업)
                textView.setVisibility(View.GONE);


                textView.setText(selected_item.getMaterial());
                CustomDialog customDialog = new CustomDialog(DietSearchActivity.this, email2, selectedFood, newdate, dietTime);
                customDialog.callFunction2(textView);

                ///food 이제 진짜 insert하는 상황.
                if (textView != null) {
                    try {
                        searchList.clear();
                        adapter.notifyDataSetInvalidated();
                        PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietRefrigerator2.php");
                        String result = request.PhPDietSearch(email2);
                        if (result != null) {
                            try {
                                //응답부분을 처리하도록 하면 된다.
                                JSONObject jsonObject = new JSONObject(result);
                                //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                int count = 0;

                                //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                                //adapter.notifyDataSetChanged();


                                while (count < jsonArray.length()) {
                                    JSONObject object = jsonArray.getJSONObject(count);

                                    String materialText = object.getString("material");
                                    String amountText = object.getString("amount");
                                    String unit = "개";
                                    String dateText = object.getString("purchase_date");
                                    //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                                    Search search = new Search(materialText, amountText, unit, dateText);
                                    //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                                    searchList.add(search);
                                    adapter.notifyDataSetChanged();
                                    count++;
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
                }
            }
        });

        // new DietSearchActivity.BackgroundTask().execute();
        dl = (DrawerLayout) findViewById(R.id.activity_search);
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

        name.setText(email2);

        NetworkUtil.setNetworkPolicy();
        try {

            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/and/editdetail.php");
            PHPRequest request1 = new PHPRequest("http://naancoco.cafe24.com/and/editdetail2.php");
            PHPRequest request2 = new PHPRequest("http://naancoco.cafe24.com/and/editdetail3.php");


            String result = request.PhPInfo(email2);
            String result1 = request1.PhPInfo(email2);
            String result2 = request2.PhPInfo(email2);

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
                        intent.putExtra("email", email2);
                        startActivity(intent);
                        dl.closeDrawers();
                        return true;
                    case R.id.diary:
                        intent = new Intent(getApplicationContext(), DietMainActivity.class);
                        intent.putExtra("email2", email2);
                        startActivity(intent);
                        return true;
                    case R.id.calendar:
                        intent = new Intent(getApplicationContext(), CalendarActivity3.class);
                        intent.putExtra("email", email2);
                        startActivity(intent);
                        dl.closeDrawers();
                        return true;
                    case R.id.recommend:
                        intent = new Intent(getApplicationContext(), UserchoiceActivity.class);
                        intent.putExtra("email", email2);
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
                        intent.putExtra("email", email2);
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
//Async_Prepare();
      /*  try {
            URL url = new URL("http://naancoco.cafe24.com/DietRefrigerator2.php");
            HttpsURLConnection connection=(HttpURLConnection)url.openConnection();
            String urlParameters="email=naancoco";
            connection.setRequestMethod("POST");
            connection.setRequestProperty();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        searchRefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    /*
    public void Async_Prepare(){
        Async_test async_test =new Async_test();
        async_test.execute("hello","rabbit");
    }
    class Async_test extends AsyncTask<String, Void,String>{
        ProgressDialog dialog;
        int cnt=0;
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=ProgressDialog.show(URLConnection.this,"이것은 URLConnection Test입니다...",null,true,true);



        }
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }*/
//send email method부분
    /*
    private class SendPost extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... unused) {
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            try {
                //응답부분을 처리하도록 하면 된다.
                JSONObject jsonObject = new JSONObject(result);
                //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    String materialText = object.getString("material");
                    String amountText = object.getString("amount");
                    String unit = "1";
                    String dateText = object.getString("purchase_date");
                    //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                    Search search = new Search(materialText, amountText, unit, dateText);
                    //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                    searchList.add(search);
                    adapter.notifyDataSetChanged();
                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 실제 전송하는 부분
        public String executeClient() {
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("email", "naancoco"));


            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            // Post객체 생성
            HttpPost httpPost = new HttpPost("http://naancoco.cafe24.com/DietRefrigerator2.php");

            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
                httpPost.setEntity(entity);
                client.execute(httpPost);
                return EntityUtils.getContentCharSet(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/
/*send성공한 부분
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        //target:접속할 홈페이지의 주소
        String target;

        protected void onPreExecute() {
            target = "http://naancoco.cafe24.com/DietRefrigerator2.php";

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //input stream을 만들어서 넘어오는 결과값들을 그대로 저장하면 된다.
                InputStream inputStream = httpURLConnection.getInputStream();
                //해당 inputstream에 있는 내용들을 buffer에 담아서 읽을 수 있도록 하면된다.
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                //buffer에서 받아온 값을 한 줄 씩 읽으면서 temp에 넣고, 그것을 문자형태로 저장하면 된다.
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... Values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                //응답부분을 처리하도록 하면 된다.
                JSONObject jsonObject = new JSONObject(result);
                //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    String materialText = object.getString("material");
                    String amountText = object.getString("amount");
                    String unit = "개";
                    String dateText = object.getString("purchase_date");
                    //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                    Search search = new Search(materialText, amountText, unit, dateText);
                    //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                    searchList.add(search);
                    adapter.notifyDataSetChanged();
                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}*/

