package com.targetyou.recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class DietFoodActivity extends AppCompatActivity {
    //private로 해당 클래스에 멤버 변수를 만들어준 상태
    private ListView foodListView;
    //static ListView foodListView;
    private FoodListAdapter adapter;
    //static FoodListAdapter adapter;
    private List<Food> foodList;
   //static List<Food> foodList;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;




    EditText mEditTextSearchKeyword;
   // Intent intent;
   String email2,newdate,dietTime,result;
   String selectedFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_diet_food);
        Intent intent = new Intent(this.getIntent());
        email2=intent.getStringExtra("email2");
        newdate=intent.getStringExtra("newdate");
        dietTime=intent.getStringExtra("dietTime");
        selectedFood=intent.getStringExtra("selectedFood");

        final TextView textView=(TextView) findViewById(R.id.textView11);
        //.setVisibility(View.GONE);
        textView.setText(email2+newdate+dietTime+selectedFood);
        textView.setVisibility(View.GONE);

        foodListView = (ListView) findViewById(R.id.foodListView);
        //List초기화과정
        foodList = new ArrayList<Food>();

        /*foodList.add(new Food("오이","1회","1인분","100g"));
        foodList.add(new Food("오이","1회","1인분","100g"));
        foodList.add(new Food("오이","1회","1인분","100g"));
        foodList.add(new Food("오이","1회","1인분","100g"));
        foodList.add(new Food("오이","1회","1인분","100g"));
        foodList.add(new Food("오이","1회","1인분","100g"));
        foodList.add(new Food("오이","1회","1인분","100g"));*/
        adapter = new FoodListAdapter(getApplicationContext(), foodList);
        foodListView.setAdapter(adapter);
        mEditTextSearchKeyword = (EditText) findViewById(R.id.foodText);
        Button button_search = (Button) findViewById(R.id.searchFoodButton);
        Button button_complete=(Button)findViewById(R.id.completeButton_Food);
        NetworkUtil.setNetworkPolicy();
        try {
            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietFavorite.php");
            //String result = request.PhPDietFood(mEditTextSearchKeyword.getText().toString());
            result = request.PhPDietFavorite(email2);
            if (result != null) {
                try {
                    Log.i("result", result);
                    //응답부분을 처리하도록 하면 된다.
                    JSONObject jsonObject = new JSONObject(result);
                    //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    Log.i("jsonArray", jsonArray.length()+"");
                    //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                    //adapter.notifyDataSetChanged();

                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        String foodNameText = object.getString("food_name");
                        String gramText = object.getString("amount");

                        // String dateText = object.getString("purchase_date");
                        //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                        Food food = new Food(foodNameText, gramText);
                        //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                        foodList.add(food);

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
        button_search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietFood2.php");
                    //String result = request.PhPDietFood(mEditTextSearchKeyword.getText().toString());
                    result = request.PhPDietFood(mEditTextSearchKeyword.getText().toString());
                    if (result != null) {
                        try {
                            //응답부분을 처리하도록 하면 된다.
                            JSONObject jsonObject = new JSONObject(result);
                            //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            int count = 0;
                            foodList.clear();
                            while (count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);
                                String foodNameText = object.getString("food_name");
                                String gramText = object.getString("food_gram")+"g";

                                // String dateText = object.getString("purchase_date");
                                //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                                Food food = new Food(foodNameText, gramText);
                                //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                                foodList.add(food);

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
//커스텀 리스트뷰를 클릭하면, 리스트뷰의 position값을 이용하여, 음식의 이름값을 가져오게 된다.
        button_complete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), DietMainActivity.class);

                ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                intent3.putExtra("email2", email2);
                intent3.putExtra("newdate", newdate);
                startActivity(intent3);
            }

        });

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                Food selected_item = (Food)adapterView.getItemAtPosition(position);
                String selectedFood=selected_item.getFoodName();

                //텍스트뷰에 출력(확인작업)
                textView.setText("");

                textView.setText(selected_item.getFoodName());
                CustomDialog customDialog = new CustomDialog(DietFoodActivity.this,email2,selectedFood,newdate,dietTime);
                customDialog.callFunction(textView);
                ///food 이제 진짜 insert하는 상황.
                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietFood2.php");
                    String result = request.PhPDietFood(mEditTextSearchKeyword.getText().toString());
                    if (result != null) {
                        try {


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


        dl = (DrawerLayout) findViewById(R.id.activity_food);
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

