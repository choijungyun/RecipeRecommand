package com.targetyou.recipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DietSSTStep1Activity extends AppCompatActivity {
    String email2, newdate, dietTime,selectedFood;
    private ListView foodListView;
    //static ListView foodListView;
    private FoodListAdapter adapter;
    //static FoodListAdapter adapter;
    private List<Food> foodList;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_sststep1);
        Intent intent = new Intent(this.getIntent());
        email2=intent.getStringExtra("email2");
        newdate=intent.getStringExtra("newdate");
        dietTime=intent.getStringExtra("dietTime");
        selectedFood=intent.getStringExtra("selectedFood");
        //forlist
        listview=(ListView)findViewById(R.id.listview);
        List<String>list=new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);
        String selectedarr[] = selectedFood.split(" ");
       // ListView listView = getListView();
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        for(int i=0;i<selectedarr.length;i++) {
            list.add(selectedarr[i]);
        }
        Button result_button = (Button)findViewById(R.id.result_button);

       /* result_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final List<String> selectedItems = new ArrayList<>();

                //리스트뷰에서 선택된 아이템의 목록을 가져온다.
                SparseBooleanArray checkedItemPositions = listview.getCheckedItemPositions();
                for( int i=0; i<checkedItemPositions.size(); i++){
                    int pos = checkedItemPositions.keyAt(i);

                    if (checkedItemPositions.valueAt(i))
                    {
                        selectedItems.add(listview.getItemAtPosition(pos).toString());
                    }
                }

                final CharSequence[] items = selectedItems.toArray(new String[selectedItems.size()]);


                //다이얼 로그에 가져온 목록을 보여준다.
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("선택한 아이템 목록");
                dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        String selectedText = items[pos].toString();
                        Toast.makeText(MainActivity.this, selectedText, Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialogObject = dialogBuilder.create();
                alertDialogObject.show();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                String selected_item = (String)adapterView.getItemAtPosition(position);

                //텍스트뷰에 출력
                ;
            }
        });*/
    }
}
