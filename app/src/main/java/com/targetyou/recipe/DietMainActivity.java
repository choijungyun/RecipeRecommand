package com.targetyou.recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class DietMainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    String newdate,dietTime;
    private SwipeMenuListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    String email2;
    private static final String TAG1="DietMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_main);

        Intent intent = new Intent(this.getIntent());
        email2=intent.getStringExtra("email2");

        dl = (DrawerLayout) findViewById(R.id.activity_main);
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

        Log.d(this.getClass().getName(), "성공");
        final Button writingButton = (Button) findViewById(R.id.writingButton);
        final Button recommendButton = (Button) findViewById(R.id.recommendButton);
        final TextView textView=(TextView) findViewById(R.id.textView111);
        final TextView noText=(TextView) findViewById(R.id.no);
        final ImageView noImage=(ImageView)findViewById(R.id.mainImage);
        //email값을 넘겨받고,
        TextView textView2=(TextView) findViewById(R.id.textView13);
        textView2.setVisibility(View.GONE);
        textView2.setText(email2);
        //email과 date값을 넘겨줘야함.(이거는 나중에 식단기록 버튼을 선택할 때)
        noText.setVisibility(View.GONE);
        noImage.setVisibility(View.GONE);
        noticeListView=(SwipeMenuListView)findViewById(R.id.noticeListView);
        noticeList=new ArrayList<Notice>();

        //adapter=new NoticeListAdapter(getApplicationContext(),noticeList);
        adapter=new NoticeListAdapter(getApplicationContext(), noticeList);
        //noticeList에 해당 adapter가 matching이 됨으로써, adapter에 들어있는 모든 내용들이 view 형태로 들어가서 listview로 보여지게 되는것
        noticeListView.setAdapter((adapter));
        //final Button dinnerButton = (Button) findViewById(R.id.dinnerButton);
        //해당 fragment를 눌렀을 때 바뀌는 layout을 의미
        //final LinearLayout dietMain = (LinearLayout) findViewById(R.id.dietMain);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                newdate= sdf.format(date);

                NetworkUtil.setNetworkPolicy();
                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/List.php");
                    //주의!! php로 넘겨줄때는 intent를 넘겨받은 string을 이용할 것.
                    String result = request.PhPDietMain(email2,newdate);
                    if (result != null) {
                        try {

                            //응답부분을 처리하도록 하면 된다.
                            JSONObject jsonObject = new JSONObject(result);
                            //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            //int count = 0;
                           /* if(count!=0){
                                for(int i=0;i<count;i++){
                                    adapter.remove(adapter.getItem(i));
                                }
                            }*/
                            int count=0;
                            noticeList.clear();
                            //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                            //adapter.notifyDataSetChanged();
                            adapter.notifyDataSetInvalidated();
                            if(jsonArray.length()==0){
                                noText.setVisibility(View.VISIBLE);
                                noImage.setVisibility(View.VISIBLE);
                            }
                            while (count < jsonArray.length()) {
                                noText.setVisibility(View.GONE);
                                noImage.setVisibility(View.GONE);
                                JSONObject object = jsonArray.getJSONObject(count);
                                String noticeContent = object.getString("food_name");
                                String noticeName = object.getString("amount")+"인분";
                                String noticeDate = object.getString("time");
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
                    }


                    else {

                        Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        });

//time에 따라서 달라지도록 설정.
        SimpleDateFormat time = new SimpleDateFormat( "HH", Locale.KOREA );
        Date currentTime = new Date();
        String oTime = time.format( currentTime ); //현재시간 (String)
        final int numTime = Integer.parseInt(oTime);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                        0xff)));
                // set item width
                openItem.setWidth(170);
                // set item title
                //openItem.setTitle("Open");
                openItem.setIcon(R.drawable.ic_edit);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        noticeListView.setMenuCreator(creator);

        noticeListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open

                        Notice selected_item = (Notice) noticeListView.getItemAtPosition(position);
                        String selectedFood = selected_item.getEatName();
                        String selectedFoodDietTime = selected_item.getEatDate();
                        Log.d(TAG1, "onMenuItemClick:clicked item" + index);
                        Log.d(TAG1, "11111111111111111" + selectedFood);
                        CustomDialog customDialog = new CustomDialog(DietMainActivity.this, email2, selectedFood, newdate, selectedFoodDietTime);
                        customDialog.callFunction4(textView);


                        break;
                    case 1:
                        // delete
                    {
                        Notice selected_item2 = (Notice) noticeListView.getItemAtPosition(position);
                        String selectedFood2 = selected_item2.getEatName();
                        String selectedFoodDietTime2 = selected_item2.getEatDate();

                        Log.d(TAG1, "onMenuItemClick:clicked item" + index);
                        Log.d(TAG1, "11111111111111111" + selectedFood2);
                        Log.d(TAG1, "11111111111111111" + selectedFoodDietTime2);
                        Log.d(TAG1, "11111111111111111" + newdate);
                        NetworkUtil.setNetworkPolicy();
                        try {
                            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietMainDelete.php");
                            String result = request.PhPDietMainDelete(email2,selectedFood2,newdate,selectedFoodDietTime2);

                            if (result!=null) {
                                try {
                                    Toast.makeText(getApplication(), selectedFood2+"delete", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
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
                    break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        writingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //눌린 버튼만 진하게
               // writingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                //recommendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                // dinnerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                //time도 넘겨준다.

                if((5<=numTime)&&(11>=numTime)){
                    Intent intent1 = new Intent(getApplicationContext(), DietWritingActivity.class);
                    ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                    intent1.putExtra("email2", email2);
                    intent1.putExtra("newdate", newdate);
                    intent1.putExtra("dietTime","아침");
                    startActivity(intent1);
                }
                else if((17>=numTime)&&(11<numTime)){
                    Intent intent2 = new Intent(getApplicationContext(), DietWritingActivity2.class);
                    ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                    intent2.putExtra("email2", email2);
                    intent2.putExtra("newdate", newdate);
                    intent2.putExtra("dietTime","점심");
                    startActivity(intent2);
                }
                else {
                    Intent intent3 = new Intent(getApplicationContext(), DietWritingActivity3.class);
                    ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                    intent3.putExtra("email2", email2);
                    intent3.putExtra("newdate", newdate);
                    intent3.putExtra("dietTime","저녁");
                    startActivity(intent3);
                }

            }
        });
        recommendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //dietMain.setVisibility(View.GONE);
                //눌린 버튼만 진하게
                //writingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
               // recommendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Intent intent3 = new Intent(getApplicationContext(), DietFoodRecommendActivity.class);
                ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                intent3.putExtra("email2", email2);
               // intent3.putExtra("newdate", newdate);
                startActivity(intent3);
                //dinnerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

             /*   if((5<=numTime)&&(11>=numTime)){
                    Intent intent1 = new Intent(getApplicationContext(), DietWritingActivity.class);
                    ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                    intent1.putExtra("email2", email2);
                    intent1.putExtra("newdate", newdate);
                    startActivity(intent1);
                }
                else if((17>=numTime)&&(11<numTime)){
                    Intent intent2 = new Intent(getApplicationContext(), DietWritingActivity2.class);
                    ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                    intent2.putExtra("email2", email2);
                    intent2.putExtra("newdate", newdate);
                    startActivity(intent2);
                }
                else {
                    Intent intent3 = new Intent(getApplicationContext(), DietWritingActivity3.class);
                    ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                    intent3.putExtra("email2", email2);
                    intent3.putExtra("newdate", newdate);
                    startActivity(intent3);
                }*/
                // new BackgroundTask().execute();

            }
        });
        /*
        dinnerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dietMain.setVisibility(View.GONE);
                //눌린 버튼만 진하게
                breakfastButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                lunchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                dinnerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Intent intent = new Intent(getApplicationContext(), DietWritingActivity3.class);
                startActivity(intent);
            }
        });*/


        //new DietMainActivity.BackgroundTask().execute();
        //PHPRequest를 이용하는 상황

        //상황끝

    }

    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        //target:접속할 홈페이지의 주소
        String target;

        protected void onPreExecute() {
            target="http://naancoco.cafe24.com/List.php";

        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                //input stream을 만들어서 넘어오는 결과값들을 그대로 저장하면 된다.
                InputStream inputStream = httpURLConnection.getInputStream();
                //해당 inputstream에 있는 내용들을 buffer에 담아서 읽을 수 있도록 하면된다.
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                //buffer에서 받아온 값을 한 줄 씩 읽으면서 temp에 넣고, 그것을 문자형태로 저장하면 된다.
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... Values){
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result){
            try{
                //응답부분을 처리하도록 하면 된다.
                JSONObject jsonObject=new JSONObject(result);
                //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                String noticeContent, noticeName, noticeDate;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    noticeContent=object.getString("food_name");
                    noticeName=object.getString("amount");
                    noticeDate=object.getString("time");
                    //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                    Notice notice=new Notice(noticeContent,noticeName,noticeDate);
                    //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                    noticeList.add(notice);
                    adapter.notifyDataSetChanged();
                    count++;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

/*
        noticeListView=(ListView) findViewById(R.id.noticeListView);
        noticeList=new ArrayList<Notice>();
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        noticeList.add(new Notice("공지사항입니다","안경잡이","2017-01-01"));
        adapter=new NoticeListAdapter(getApplicationContext(),noticeList);
        //noticeList에 해당 adapter가 matching이 됨으로써, adapter에 들어있는 모든 내용들이 view 형태로 들어가서 listview로 보여지게 되는것
        noticeListView.setAdapter((adapter));*/
    /*
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        //초기화하는 부분. 특정 웹사이트에 존재하는 php파일로 초기화시켜준다.
        @Override
        protected void onPreExecute() {
            target = "http://naancoco.cafe24.com/List.php";
        }

        //실제 실행이 되는 부분.
        @Override
        protected String doInBackground(Void... voids) {
            try {
                //해당 url에 접속할 수 있도록 url객체를 만들어주고, urlconnection을 만들어준다.
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //inputstream 객체를 만들어준뒤, bufferedreader를 만들어서 하나씩 읽어오도록.
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //temp는 매열마다 입력을 받을 수 있도록. 따라서 결과한줄은 temp에 담겨서 stringBuilder안에 넣어지게 될것.
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                //모든 열을 다 읽겠다는 뜻.
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                //모두 닫아주고
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //해당 문자열의 집합을 반환해준다.
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
            //오류가 발행하면 null값을 반환하도록.
            return null;
            //상속만 받고 사용하지는 않는다.
            //모든 parsing이 끝난 뒤, 다음 activity로 이동하도록. 그리고 parsing한 결과를 그대로 다음 activity로 넘겨준다.
            //버튼을 눌렀을 때, 실행될 수 있도록 해준다.
            //다음 activity에서는 textview를 만들어서 userlistTextview를 할당해주고
            //intent를 받아서 넘어온 값을 그대로 그 textview안에 넣을 수 있도록

        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute(String result){
            Intent intent=new Intent(DietMainActivity.this,DietWritingActivity2.class);
            intent.putExtra("userList",result);
            DietMainActivity.this.startActivity(intent);

        }
    }


    private long lastTImeBackPressed;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTImeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로'버튼을 한 번 더 눌러 종료합니다", Toast.LENGTH_SHORT);
        lastTImeBackPressed = System.currentTimeMillis();
    }*/
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