package com.targetyou.recipe;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * Created by Administrator on 2017-08-07.
 */

public class CustomDialog {

    private Context context;
    String email, food_name,food_date,food_dietTime;
    String amount;

    public CustomDialog(Context context, String email, String food_name, String food_date, String food_dietTime) {
        this.context = context;
        this.email = email;
        this.food_name = food_name;
        this.food_date = food_date;
        this.food_dietTime = food_dietTime;
    }

    /*
        public CustomDialog(Context context) {
            this.context = context;
        }*/
    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final TextView main_label) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        //커스텀 다이얼로그의 editText값을 1로 초기화시킨다. 후에, 이 값을 사용자가 자주 먹는 양으로 설정할 수 있을 것.


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                main_label.setText(message.getText().toString());
                //Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                amount=message.getText().toString();

                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietFoodAdd2.php");
                    String result = request.PhPDietFoodADD(email,food_name,amount,food_date,food_dietTime);
                    if (result != null) {
                        Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "\"입력이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
    //searchFoodActivity를 위한 function: 냉장고 음식 update상황
    public void callFunction2(final TextView main_label) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        message.setText("");
        //커스텀 다이얼로그의 editText값을 1로 초기화시킨다. 후에, 이 값을 사용자가 자주 먹는 양으로 설정할 수 있을 것.


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                main_label.setText(message.getText().toString());
                //Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                amount=message.getText().toString();

                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietRefriUpdate.php");
                    String result = request.PhPDietSearchAdd(email,food_name,amount,food_date,food_dietTime);
                    if (result != null) {
                        Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent menu = new Intent(context, DietSearchActivity.class);
                        context.startActivity(menu);
                        ((Activity) context).finish();
                    }else{
                        Toast.makeText(context, "\"입력이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
                Intent menu = new Intent(context, DietSearchActivity.class);

                menu.putExtra("email2", email);

                context.startActivity(menu);
                ((Activity) context).finish();


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
    public void callFunction3(final TextView main_label) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        message.setText("");
        //커스텀 다이얼로그의 editText값을 1로 초기화시킨다. 후에, 이 값을 사용자가 자주 먹는 양으로 설정할 수 있을 것.


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                main_label.setText(message.getText().toString());
                //Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                amount=message.getText().toString();

                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietMainEdit.php");
                    String result = request.PhPDietSearchAdd(email,food_name,amount,food_date,food_dietTime);

                    if (result != null) {
                        Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                       // ((DietMainActivity2) DietMainActivity2.mContext).onResume();



                    }else{
                        Toast.makeText(context, "\"입력이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
                Intent menu = new Intent(context, DietMainActivity2.class);
                context.startActivity(menu);
                ((Activity) context).finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
    /*public void callFunction3(final TextView main_label) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        message.setText("");
        //커스텀 다이얼로그의 editText값을 1로 초기화시킨다. 후에, 이 값을 사용자가 자주 먹는 양으로 설정할 수 있을 것.


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                main_label.setText(message.getText().toString());
                //Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                amount=message.getText().toString();

                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietMainEdit.php");
                    String result = request.PhPDietSearchAdd(email,food_name,amount,food_date,food_dietTime);

                    if (result != null) {
                        Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent menu = new Intent(context, DietMainActivity2.class);
                        context.startActivity(menu);
                        ((Activity) context).finish();
                    }else{
                        Toast.makeText(context, "\"입력이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }*/

    public void callFunction4(final TextView main_label) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        message.setText("");
        //커스텀 다이얼로그의 editText값을 1로 초기화시킨다. 후에, 이 값을 사용자가 자주 먹는 양으로 설정할 수 있을 것.


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                main_label.setText(message.getText().toString());
                //Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
                amount=message.getText().toString();

                try {
                    PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietMainEdit.php");
                    String result = request.PhPDietSearchAdd(email,food_name,amount,food_date,food_dietTime);

                    if (result != null) {
                        Toast.makeText(context, "\"" +  food_name+message.getText().toString() + "\" 인분 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context, "\"입력이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
                Intent menu = new Intent(context, DietMainActivity.class);

                menu.putExtra("email2", email);
                menu.putExtra("newdate", food_date);
                context.startActivity(menu);
                ((Activity) context).finish();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
}