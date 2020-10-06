package com.targetyou.recipe;

public class ListItem {

    private String[] mData;

    public ListItem(String[] data ){


        mData = data;
    }

    public ListItem(String image){

        mData = new String[0];
        mData[0] = image;
//        mData[1] = image2;
//        mData[2] = image3;




    }

    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        return mData[index];
    }

    public void setData(String[] data){
        mData = data;
    }



}
