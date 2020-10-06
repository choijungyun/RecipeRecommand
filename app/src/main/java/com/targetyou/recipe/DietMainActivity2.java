package com.targetyou.recipe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class DietMainActivity2 extends AppCompatActivity {
    private static final String TAG="DietMainActivity2";
    public static Context mContext;
    private SwipeMenuListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    String email2,newdate,dietTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_main2);
        noticeListView=(SwipeMenuListView)findViewById(R.id.noticeListView);
        Intent intent = new Intent(this.getIntent());
        email2="naancoco";
        newdate="2018-09-04";
        dietTime="저녁";
        final TextView textView111=(TextView) findViewById(R.id.textView111);
        noticeList=new ArrayList<Notice>();
        mContext=this;


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

                    int count=0;
                   // noticeList.clear();
                    //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                    //adapter.notifyDataSetChanged();
                   // adapter.notifyDataSetInvalidated();

                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        String noticeContent = object.getString("food_name");
                        String noticeName = object.getString("amount")+"인분";
                        String noticeDate = object.getString("time");
                        //하나의 공지사항에 대한 객체를 생성해둔다. 하나의 생성자를 이용
                        Notice notice = new Notice(noticeContent, noticeName,noticeDate);
                        //모든 공지사항 리스트가 noticeList에 추가되게 된다.
                        noticeList.add(notice);
//                        adapter.notifyDataSetChanged();
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
        adapter=new NoticeListAdapter(getApplicationContext(),noticeList);
        //noticeList에 해당 adapter가 matching이 됨으로써, adapter에 들어있는 모든 내용들이 view 형태로 들어가서 listview로 보여지게 되는것
        noticeListView.setAdapter((adapter));


       // ArrayAdapter adapter=new ArrayAdapter(DietMainActivity2.this, android.R.layout.simple_list_item_1,list);
       // noticeListView.setAdapter(adapter);
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
                        Log.d(TAG, "onMenuItemClick:clicked item" + index);
                        Log.d(TAG, "11111111111111111" + selectedFood);
                        CustomDialog customDialog = new CustomDialog(DietMainActivity2.this, email2, selectedFood, newdate, selectedFoodDietTime);
                        customDialog.callFunction3(textView111);


                        break;
                    case 1:
                        // delete
                    {
                        Notice selected_item2 = (Notice) noticeListView.getItemAtPosition(position);
                        String selectedFood2 = selected_item2.getEatName();
                        String selectedFoodDietTime2 = selected_item2.getEatDate();

                        Log.d(TAG, "onMenuItemClick:clicked item" + index);
                        Log.d(TAG, "11111111111111111" + selectedFood2);
                        Log.d(TAG, "11111111111111111" + selectedFoodDietTime2);
                        Log.d(TAG, "11111111111111111" + newdate);
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
    }
/*
public void onResume(){
        super.onResume();
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
                int count=0;
                noticeList.clear();
                //밑에 있는 2가지 메소드 다 가능하다. 왜냐하면 위에꺼는  dataset이 바뀌었을 때 동작하고, 아래꺼는 dataset이 없을 때 동작하기 때문이다.
                adapter.notifyDataSetChanged();
                // adapter.notifyDataSetInvalidated();

                while (count < jsonArray.length()) {
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
        } else {
            Toast.makeText(getApplication(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }

}*/


}
