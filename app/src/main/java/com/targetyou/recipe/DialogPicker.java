package com.targetyou.recipe;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;


import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DialogPicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int year, month, day;
    private EditText target;
    final Calendar c = Calendar.getInstance();


    public DialogPicker(EditText target) {
        // TODO Auto-generated constructor stub
        this.target = target;
    }


    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

// TODO Auto-generated method stub

        if (getTag() == "DatePicker") {

            String getDate = target.getText().toString();
            if (!StringUtils.equals(getDate, "")) {

                System.out.println("2");

                String y = getDate.substring(0, 4);

                String m = getDate.substring(5, 7);

                String d = getDate.substring(8, 10);

                int YEAR = Integer.parseInt(y);

                int MONTH = Integer.parseInt(m);

                int DAY = Integer.parseInt(d);


                year = YEAR;

                month = MONTH - 1;

                day = DAY;


            } else {


                System.out.println("3");

                year = c.get(Calendar.YEAR);

                month = c.get(Calendar.MONTH);

                day = c.get(Calendar.DAY_OF_MONTH);

            }


            return new DatePickerDialog(getActivity(), this, year, month, day);

        }


        return null;

    }


//날짜를 넣어주는 함수

    @Override

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

// TODO Auto-generated method stub


        this.year = year;

        month = monthOfYear + 1; // 월은 1을 더해줌.

        day = dayOfMonth;


//예를 들어 받아온 날짜가 2013년 7월 7일 일경우 201377 이되므로 앞자리에 0을 붙여주기 위해

//leftpad 사용.. 여러번 사용하기 때문에 함수를 만들었다.

        String s_year = ObjectUtils.toString(year).trim();

        String s_month = leftPad(month);

        String s_day = leftPad(day);
        target.setText(s_year + "/" + s_month + "/" + s_day);


    }



    String leftPad(int num) {

        String val = null;

        if (num < 10) {

            val = ObjectUtils.toString(num);

            val = StringUtils.leftPad(val, 2, '0').trim();

        } else {

            val = ObjectUtils.toString(num).trim();

        }


        return val;

    }
}