package com.targetyou.recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class DietFoodRecommendActivity extends AppCompatActivity {
    String email2;
    EditText mEditTextSearchKeyword;

    String result;
    String disease;
    String energy;
    //추천받는 음식이름
    String food_name;
    Double std_cal, std_carbo, std_fat, std_sugar, std_natrium;
    Double gram, cal, carbo, protein, fat, sugar, natrium;
    String gramText, calText, carboText, proteinText, fatText, sugarText, natriumText;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    String result_reco;
    String[] selectedarr;

    List<String> list = new ArrayList<>();
    int size;
    private ListView recoomendListView;
    //static ListView foodListView;
    private RecommendListAdapter adapter;
    //static FoodListAdapter adapter;
    private List<Recommend> recommendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_food_recommend);

        dl = (DrawerLayout) findViewById(R.id.activity_dietFoodRecommend);
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

        Intent intent = new Intent(this.getIntent());
        email2 = intent.getStringExtra("email2");
        Log.d(email2, "입니다");
        mEditTextSearchKeyword = (EditText) findViewById(R.id.foodRecoText);
        Button button_search = (Button) findViewById(R.id.searchRecoButton);

        recoomendListView = (ListView) findViewById(R.id.recommendListView);
        //List초기화과정
        recommendList = new ArrayList<Recommend>();
        //초기화실행
        //std_cal= Double.valueOf(100);
        adapter = new RecommendListAdapter(getApplicationContext(), recommendList);
        recoomendListView.setAdapter(adapter);
        std_carbo = Double.valueOf(0);
        std_fat = Double.valueOf(0);
        std_natrium = Double.valueOf(0);
        //초기화과정
        calText = " ";
        carboText = " ";
        proteinText = " ";
        fatText = " ";
        sugarText = " ";
        natriumText = " ";
        result_reco = "";


        NetworkUtil.setNetworkPolicy();

        //먼저 사용자의 건강상태를 체크해본다
        try {
            PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietFoodReco.php");
            //String result = request.PhPDietFood(mEditTextSearchKeyword.getText().toString());
            result = request.PhPDietSearch(email2);
            if (result != null) {
                try {
                    //응답부분을 처리하도록 하면 된다.
                    JSONObject jsonObject = new JSONObject(result);
                    //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        disease = object.getString("disease");
                        energy = object.getString("energy");
                        Log.d(energy, "나는 에너지입니다");
                        Log.d(disease,"질병입니다");


                        //Log.d(selectedarr[1], "입니다");
                            /*for(int i=0;i<selectedarr.length;i++) {

                                try {
                                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietSSTAdd2.php");
                                    String result = request.PhPDietSST(email2, selectedarr[i], amount_sst, newdate, dietTime);

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }*/
                        // String gramText = object.getString("food_gram")+"g";

                        // String dateText = object.getString("purchase_date");
                        //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                        // Food food = new Food(foodNameText, gramText);
                        //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                        //foodList.add(food);

                        //ter.notifyDataSetChanged();
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
        if (disease.contains(",")) {
            selectedarr = disease.split(",");
            for(int i=0;i<selectedarr.length;i++){
                list.add(selectedarr[i]);
            }
        } else {
            String disease2=null;
            //if (TextUtils.isEmpty(disease)){
            //if(disease.isEmpty()){
            if(disease.equals("null")){
                list.add("평범");
                 Log.d(disease2, "입니다1111111111111.");
            }
            else
                list.add(disease);
        }
        size=list.size();
        Log.d(String.valueOf(size),"사이즈 입니다");
        // Log.d(selectedarr[1], "입니다2222222222222222222.");

//std값 갱신 완료

        button_search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietFoodReco2.php");
                    // String result = request.PhPDietRecoFood(mEditTextSearchKeyword.getText().toString());
                    result = request.PhPDietFoodReco(mEditTextSearchKeyword.getText().toString());
                    if (result != null) {
                        try {
                            //응답부분을 처리하도록 하면 된다.
                            JSONObject jsonObject = new JSONObject(result);
                            //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            int count = 0;
                            while (count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);
                                food_name = object.getString("food_name");
                                Log.d(food_name, "입니다!!!!!!!!!!!!!");
                                gram = Double.parseDouble(object.getString("gram"));
                                cal = Double.parseDouble(object.getString("cal"));
                                carbo = Double.parseDouble(object.getString("carbo"));
                                protein = Double.parseDouble(object.getString("protein"));
                                fat = Double.parseDouble(object.getString("fat"));
                                natrium = Double.parseDouble(object.getString("natrium"));
                                sugar = Double.parseDouble(object.getString("sugar"));
                                //초기화과정
                                gramText=gram+"g";
                                calText = cal + "kcal";
                                carboText = carbo + "g ";
                                proteinText = protein + "g ";
                                fatText = fat + "g ";
                                sugarText = sugar + "g ";
                                natriumText = natrium + "mg ";
                                result_reco = "";
                                std_sugar = Double.valueOf(Integer.parseInt(energy) / 10);
                                std_cal = Double.valueOf(Integer.parseInt(energy) / 3);
                                std_natrium = Double.valueOf(3700 / 3);
                                std_carbo = Double.valueOf(Integer.parseInt(energy) * 0.7 / 4 / 3);
                                std_fat = Double.valueOf(Integer.parseInt(energy) * 0.07 / 9 / 3);
                                for (int i = 0; i < size; i++){

                                    if ((list.get(i)).equals("고혈압")) {
                                        //std_natrium = Double.valueOf(3700 / 3);
                                        Log.d(String.valueOf(std_natrium), "나트륨입니다");
                                        if (natrium > std_natrium) {
                                            if (natrium * 1.25 > std_natrium) {
                                                Log.d(food_name, "나트륨과다");
                                                natriumText = natriumText + "(" + String.format("%.2f", (natrium / std_natrium * 100)) + "%초과)";
                                                result_reco = result_reco + "섭취불가:나트륨 과다입니다\n";
                                            } else {
                                                natriumText = natriumText + "(" + String.format("%.2f", (natrium / std_natrium * 100)) + "%초과)";
                                                result_reco = result_reco + "소량섭취:나트륨 과다입니다\n";
                                            }
                                        }// else {
                                        // natriumText = "(추천:기준치의" + (std_carbo - carbo) / 100 + "%미달";
                                        //result_reco = result_reco  + "좋은 음식입니다\n";

                                        // }
                                    } else if ((list.get(i)).equals("당뇨")) {
                                        //std_carbo = Double.valueOf(Integer.parseInt(energy) * 0.7 / 4 / 3);
                                        Log.d(String.valueOf(std_carbo), "탄수화물입니다");
                                        if (carbo > std_carbo) {
                                            if (carbo * 1.25 > std_carbo) {
                                                Log.d(food_name, "carbo과다");
                                                carboText = carboText + "(" + String.format("%.2f", (carbo / std_carbo * 100)) + "%초과)";
                                                result_reco = result_reco + "섭취불가:탄수화물 과다입니다\n";

                                            } else {
                                                carboText = carboText + "(소량권장:기준치의" + String.format("%.2f", (carbo / std_carbo * 100)) + "%초과";
                                                result_reco = result_reco + "소량섭취:탄수화물 과다입니다\n";
                                            }
                                        }
                                        // else {
                                        //carboText = carboText + "(추천:기준치의" + String.format("%.2f", (std_natrium - natrium) / 100) + "%미달)";
                                        //result_reco = result_reco + "탄수화물 \n";
                                        // }
                                    } else if ((list.get(i)).equals("고지혈증")) {
                                        // std_fat = Double.valueOf(Integer.parseInt(energy) * 0.07 / 9 / 3);
                                        Log.d(String.valueOf(std_fat), "고지혈증입니다");
                                        if (cal > std_cal) {
                                            if (fat * 1.25 > std_fat) {
                                                Log.d(food_name, "fat과다");
                                                fatText = fatText + "(" + String.format("%.2f", (fat / std_fat * 100)) + "%초과)";
                                                result_reco = result_reco + "섭취불가:지방 과다입니다\n";

                                            } else {
                                                fatText = fatText + "(" + String.format("%.2f", (fat / std_fat * 100)) + "%초과)";
                                                result_reco = result_reco + "소량섭취:지방 과다입니다\n";
                                            }
                                        } //else
                                        // fatText = fat + "(" + (std_fat - fat) / 100 + "%미달)";
                                        // result_reco = result_reco + "소량섭취:지방 과다입니다\n";

                                    }
                                }
                                    //std_sugar = Double.valueOf(Integer.parseInt(energy) / 10);
                                    Log.d(String.valueOf(std_sugar), "평범");
                                    if (sugar > std_sugar) {
                                        if (sugar * 1.25 > std_sugar) {
                                            Log.d(food_name, "sugar과다");
                                            sugarText = sugarText + "(" + String.format("%.2f", (sugar / std_sugar * 100)) + "%초과)";
                                            result_reco = result_reco + "섭취불가:당류과다\n";

                                        } else {
                                            sugarText = sugarText + "(" + String.format("%.2f", (sugar / std_sugar * 100)) + "%초과)";
                                            result_reco = result_reco + "소량섭취:당류과다\n";

                                        }

                                    }// else
                                    // sugarText = sugar + sugarText + "(" +  String.format("%.2f",(std_sugar - sugar) / 100 )+ "%미달)";

                                    //std_cal = Double.valueOf(Integer.parseInt(energy) / 3);
                                    Log.d(String.valueOf(std_cal), "평범");
                                    if (cal > std_cal) {
                                        if (cal * 1.25 > std_cal) {
                                            Log.d(food_name, "cal과다");
                                            calText = calText + "(" + String.format("%.2f", (cal  / std_cal * 100)) + "%초과)";
                                            result_reco = result_reco + "섭취불가:칼로리과다\n";

                                        } else {
                                            calText = calText + "(" + String.format("%.2f", (cal/ std_cal * 100)) + "%초과";
                                            result_reco = result_reco + "소량섭취:칼로리과다\n";

                                        }
                                    }
                                    if (result_reco.equals("")) {
                                        result_reco = food_name + "추천합니다";
                                    }
                                Recommend recommend = new Recommend(food_name, gramText, carboText
                                        , proteinText, fatText, sugarText, natriumText, calText, result_reco);
                                recommendList.add(recommend);

                                adapter.notifyDataSetChanged();
                                count++;

                                }
                               /* else {
                                    for (int i = 0; i < selectedarr.length; i++) {

                                        if (selectedarr[i].equals("고혈압")) {
                                            //std_natrium = Double.valueOf(3700 / 3);
                                            Log.d(String.valueOf(std_natrium), "나트륨입니다");
                                            if (natrium > std_natrium) {
                                                if (natrium * 1.25 > std_natrium) {
                                                    Log.d(food_name, "나트륨과다");
                                                    natriumText = natriumText + "(" + String.format("%.2f", ((natrium - std_natrium) / 100 + 100)) + "%초과)";
                                                    result_reco = result_reco + "섭취불가:나트륨 과다입니다\n";
                                                } else {
                                                    natriumText = natriumText + "(" + String.format("%.2f", (natrium - std_natrium) / 100 + 100) + "%초과)";
                                                    result_reco = result_reco + "소량섭취:나트륨 과다입니다\n";
                                                }
                                            }*/// else {
                                            // natriumText = "(추천:기준치의" + (std_carbo - carbo) / 100 + "%미달";
                                            //result_reco = result_reco  + "좋은 음식입니다\n";

                                            // }
                                        //} //else if (selectedarr[i].equals("당뇨")) {
                                            //std_carbo = Double.valueOf(Integer.parseInt(energy) * 0.7 / 4 / 3);
                                           // Log.d(String.valueOf(std_carbo), "탄수화물입니다");
                                          /*  if (carbo > std_carbo) {
                                                if (carbo * 1.25 > std_carbo) {
                                                    Log.d(food_name, "carbo과다");
                                                    carboText = carboText + "(" + String.format("%.2f", ((carbo - std_carbo) / 100 + 100)) + "%초과)";
                                                    result_reco = result_reco + "섭취불가:탄수화물 과다입니다\n";

                                                } else {
                                                    carboText = carboText + "(소량권장:기준치의" + String.format("%.2f", ((carbo - std_carbo) / 100 + 100)) + "%초과";
                                                    result_reco = result_reco + "소량섭취:탄수화물 과다입니다\n";
                                                }
                                            }*/
                                            // else {
                                            //carboText = carboText + "(추천:기준치의" + String.format("%.2f", (std_natrium - natrium) / 100) + "%미달)";
                                            //result_reco = result_reco + "탄수화물 \n";
                                            // }
                                       /* } else if (selectedarr[i].equals("고지혈증")) {
                                            // std_fat = Double.valueOf(Integer.parseInt(energy) * 0.07 / 9 / 3);
                                            Log.d(String.valueOf(std_fat), "고지혈증입니다");
                                            if (fat > std_fat) {
                                                if (fat * 1.25 > std_fat) {
                                                    Log.d(food_name, "fat과다");
                                                    fatText = fatText + "(" + String.format("%.2f", ((fat - std_fat) / 100 + 100)) + "%초과)";
                                                    result_reco = result_reco + "섭취불가:지방 과다입니다\n";

                                                } else {
                                                    fatText = fatText + "(" + String.format("%.2f", ((fat - std_fat) / 100 + 100)) + "%초과)";
                                                    result_reco = result_reco + "소량섭취:지방 과다입니다\n";*/
                                            //    }
                                          //  } //else
                                            // fatText = fat + "(" + (std_fat - fat) / 100 + "%미달)";
                                            // result_reco = result_reco + "소량섭취:지방 과다입니다\n";

                                        //}
                                  /*  else if(selectedarr[i].equals("비만")){
                                        std_cal= Double.valueOf(Integer.parseInt(energy)/3);
                                        Log.d(String.valueOf(std_cal),"비만입니다");
                                        if(cal>std_cal){
                                            if(cal*1.25>std_cal){
                                                Log.d(food_name,"cal과다");
                                            }
                                            else Log.d(food_name,"cal조금만");
                                        }
                                    }*/


                                        //else
                                        //calText = calText + calText + "(" + (std_cal - cal) / 100 + "%미달";


/*
                                    }
                                    if (sugar > std_sugar) {
                                        if (sugar * 1.25 > std_sugar) {
                                            Log.d(food_name, "sugar과다");
                                            sugarText = sugarText + "(" + String.format("%.2f", ((sugar - std_sugar) / 100 + 100)) + "%초과)";
                                            result_reco = result_reco + "섭취불가:당류과다\n";

                                        } else {
                                            sugarText = sugarText + "(" + String.format("%.2f", ((sugar - std_sugar) / 100 + 100)) + "%초과)";
                                            result_reco = result_reco + "소량섭취:당류과다\n";

                                        }

                                    }// else
                                    // sugarText = sugar + sugarText + "(" +  String.format("%.2f",(std_sugar - sugar) / 100 )+ "%미달)";

                                    //std_cal = Double.valueOf(Integer.parseInt(energy) / 3);
                                    Log.d(String.valueOf(std_cal), "평범");
                                    if (cal > std_cal) {
                                        if (cal * 1.25 > std_cal) {
                                            Log.d(food_name, "cal과다");
                                            calText = calText + "(" + String.format("%.2f", ((cal - std_cal) / 100 + 100)) + "%초과)";
                                            result_reco = result_reco + "섭취불가:칼로리과다\n";

                                        } else {
                                            calText = calText + "(" + String.format("%.2f", ((cal - std_cal) / 100 + 100)) + "%초과";
                                            result_reco = result_reco + "소량섭취:칼로리과다\n";

                                        }
                                    }
                                    if (result_reco.equals("")) {
                                        result_reco = food_name + "추천합니다";
                                    }

                                    Recommend recommend = new Recommend(food_name, gramText, carboText
                                            , proteinText, fatText, sugarText, natriumText, calText, result_reco);
                                    recommendList.add(recommend);

                                    adapter.notifyDataSetChanged();
                                }*/

                           // }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (
                        MalformedURLException e)

                {
                    e.printStackTrace();
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
