package com.targetyou.recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.List;

public class DietSSTActivity extends AppCompatActivity {

    String email2, newdate, dietTime,selectedFood;
    private ListView foodListView;
    //static ListView foodListView;
    private FoodListAdapter adapter;
    //static FoodListAdapter adapter;
    private List<Food> foodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_sst);

        Intent intent = new Intent(this.getIntent());
        email2=intent.getStringExtra("email2");
        newdate=intent.getStringExtra("newdate");
        dietTime=intent.getStringExtra("dietTime");
        selectedFood=intent.getStringExtra("selectedFood");

        NetworkUtil.setNetworkPolicy();
                // String[] selectedFoodArray=selectedFood.split(" ");
                       //  for(int i=0;i<=selectedFoodArray.length;i++){
                            try {
                                PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietFood2.php");
                                String result = request.PhPDietFood(selectedFood);
                                //result = request.PhPDietFood("김치");
                                //Speech.setText("김치");
                                if (result != null) {
                                    try {
                                        //응답부분을 처리하도록 하면 된다.
                                        JSONObject jsonObject = new JSONObject(result);
                                        //아까 php에서 response에 각각의 공지사항리스트가 담기게 되는것
                                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                                        int count = 0;
                                        while (count < jsonArray.length()) {
                                            JSONObject object = jsonArray.getJSONObject(count);
                                            String foodNameText = object.getString("food_name");
                                            String gramText = object.getString("food_gram")+"g";

                                            // String dateText = object.getString("purchase_date");
                                            //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                                            Food food = new Food(foodNameText, gramText);
                                            //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                                            foodList.add(food);
                                            adapter = new FoodListAdapter(getApplicationContext(), foodList);
                                            foodListView.setAdapter(adapter);
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
                        //}

    }
}
