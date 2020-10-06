package com.targetyou.recipe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private List<Food> foodList;


    public FoodListAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;

    }



    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //하나의 뷰로 만들어줄 수 있도록 해준다.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(context, R.layout.food, null);
        TextView foodnameText=(TextView)v.findViewById(R.id.foodnameText);

        TextView gramText=(TextView)v.findViewById(R.id.gramText);

        foodnameText.setText(foodList.get(i).getFoodName());

        gramText.setText(foodList.get(i).getGram());

        v.setTag(foodList.get(i).getFoodName());
        return v;

//나중에 saveList.remove(i)도 onClick에 추가해줘야한다.
    }
    /*
    @Override
    public void onClick(View view){
        Response.Listener<String> responseListener =new Response.Listener<String>(){
            @Override
            public void onReponse(String response){
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success=jsonResponse.getBoolean("success");
                    if(success){
                        foodList.remove(i);
                        for(int i=0;i<saveList.size();i++){
                            if(saveList.get(i).getUserID.equals(userID.getText().toString())){
                                saveList.remove(i);
                                break;
                            }
                        }
                        notifyDataSetchanged();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        DeleteRequest deleteRequest=new DeleteRequest(userID.getText().toString(),responseListener);
        RequestQueue queue=Volley.newRequestQueue(parentActivity);
        queue.add(deleteRequest);
    }*/
}

