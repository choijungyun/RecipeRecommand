package com.targetyou.recipe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import android.net.ConnectivityManager;
import android.speech.RecognizerIntent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.layout.simple_list_item_1;

public class DietWritingActivity extends AppCompatActivity implements View.OnClickListener  {
    // private ListView noticeListView;
    //  private NoticeListAdapter adapter;
    //  private List<Notice> noticeList;
    private ImageView profile_img;

    private Button searchButton;
    private Button cookButton;
    private Button completeButton;
    private LinearLayout notice;

    private ImageView camera;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    //라디오 그룹설정
    private RadioGroup radioGroup;

    String email2, newdate, dietTime;
    //sst관련
    private static final int REQUEST_CODE = 1234;
    ImageButton  Start;
    TextView Speech;
    Dialog match_text_dialog;
    ListView textlist;
    ArrayList<String> matches_text;
    //sst->insert user_eat을 위한 변수
    String selectedFood;
    String[] selectedarr;
    ListView listview;


    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_writing);
        setUI();
        setUITEXT();
        searchButton = (Button) findViewById(R.id.searchButton);
        cookButton = (Button) findViewById(R.id.cookButton);
        completeButton=(Button) findViewById(R.id.completeButton);
        //해당 fragment를 눌렀을 때 바뀌는 layout을 의미
        notice = (LinearLayout) findViewById(R.id.notice);
        searchButton.setOnClickListener(this);
        cookButton.setOnClickListener(this);
        completeButton.setOnClickListener(this);

        //email값을 넘겨받고,
        Intent intent = new Intent(this.getIntent());
        email2 = intent.getStringExtra("email2");
        newdate = intent.getStringExtra("newdate");
        dietTime = intent.getStringExtra("dietTime");
        //TextView textView2 = (TextView) findViewById(R.id.textView14);
        //textView2.setText(email2 + newdate + dietTime);
        Log.d(this.getClass().getName(), "아침페이지");
        radioGroup = (RadioGroup) findViewById(R.id.courseUnitversityGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        //sst 관련
        Start =(ImageButton)findViewById(R.id.imageView2);
        Speech = (TextView)findViewById(R.id.speech);


        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });

        dl = (DrawerLayout) findViewById(R.id.activity_writing);
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

/*
        noticeListView=(ListView) findViewById(R.id.noticeListView);
        noticeList=new ArrayList<Notice>();

        adapter=new NoticeListAdapter(getApplicationContext(),noticeList);
        adapter=new NoticeListAdapter(getApplicationContext(), noticeList);
        //noticeList에 해당 adapter가 matching이 됨으로써, adapter에 들어있는 모든 내용들이 view 형태로 들어가서 listview로 보여지게 되는것
        noticeListView.setAdapter((adapter));

        new BackgroundTask().execute();*/
/*
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView lv;
    TextView tv;
    ArrayAdapter<String> adapter;
    String[] list = {"Apple", "Orange", "Melon", "Lemon"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listview);
        tv = (TextView) findViewById(R.id.textview);
        adapter = new ArrayAdapter&lt;String&gt;(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fruit = list[position];
        tv.setText(fruit);
    }
}
    String 배열을 하나 만들*/


    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //gallery
            if (requestCode == SELECT_FILE)

                onSelectFromGalleryResult(data);
            //camera
            else if (requestCode == REQUEST_CAMERA)

                onCaptureImageResult(data);
            //sst
            else if (requestCode==REQUEST_CODE){
                match_text_dialog = new Dialog(DietWritingActivity.this);
                match_text_dialog.setContentView(R.layout.dialog_matches_frag);
                match_text_dialog.setTitle("Select Matching Text");
                textlist = (ListView)match_text_dialog.findViewById(R.id.list);
                matches_text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        simple_list_item_1, matches_text);
                textlist.setAdapter(adapter); //Listview textlist
                textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        //user_eat으로의 기록을 위해서
                        selectedFood=matches_text.get(position);
                        Speech.setText("You have said " +selectedFood);
                       // String selectedarr[] = selectedFood.split(" ");




                        match_text_dialog.hide();
                       // Intent intent2=new Intent(DietWritingActivity.this,DietFoodActivity.class);

                    }
                });
                match_text_dialog.show();

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


//radio group 클릭시, 아침,점심, 저녁이 구분되도록
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.breakfast) {
                Toast.makeText(DietWritingActivity.this, "아침", Toast.LENGTH_SHORT).show();
                ;

            } else if (checkedId == R.id.lunch) {
                Toast.makeText(DietWritingActivity.this, "점심", Toast.LENGTH_SHORT).show();
                dietTime = "점심";

            } else if (checkedId == R.id.dinner) {
                Toast.makeText(DietWritingActivity.this, "저녁", Toast.LENGTH_SHORT).show();
                dietTime = "저녁";
            }

        }

    };

    //camera 및 image 기능
    private void setUITEXT() {

    }

    private void setUI() {

        profile_img = (ImageView) findViewById(R.id.imageView);
        camera = (ImageView) findViewById(R.id.imageView1);
        camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView1:
                selectImage();
                break;
            case R.id.searchButton:
                //눌린 버튼만 진하게
                //searchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
               // cookButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Intent intent = new Intent(getApplicationContext(), DietSearchActivity.class);
                ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                intent.putExtra("email2", email2);
                intent.putExtra("newdate", newdate);
                intent.putExtra("dietTime", dietTime);
                startActivity(intent);
                break;
            case R.id.cookButton:
                //눌린 버튼만 진하게
               // searchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
               // cookButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Intent intent2 = new Intent(getApplicationContext(), DietFoodActivity.class);

                ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                intent2.putExtra("email2", email2);
                intent2.putExtra("newdate", newdate);
                intent2.putExtra("dietTime", dietTime);
                startActivity(intent2);
                break;
            case R.id.completeButton:

               String amount_sst="1";

                if(selectedFood.length()!=0){
                    selectedarr = selectedFood.split(" ");
                }
                else{
                    selectedarr[0]=selectedFood;
                }

                for(int i=0;i<selectedarr.length;i++) {

                    try {
                        PHPRequest request = new PHPRequest("http://naancoco.cafe24.com/DietSSTAdd2.php");
                        String result = request.PhPDietSST(email2, selectedarr[i], amount_sst, newdate, dietTime);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent3 = new Intent(getApplicationContext(), DietMainActivity.class);

                ////이때, email과 date값을 넘겨줘야함-앞에 애들의 이름으로(초록), 보라애들의 값을 전달하게 되는 것.
                intent3.putExtra("email2", email2);
                intent3.putExtra("newdate", newdate);
                intent3.putExtra("dietTime", dietTime);
                intent3.putExtra("selectedFood", selectedFood);
                startActivity(intent3);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void selectImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Library",

                "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(DietWritingActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                boolean result = Utility.checkPermission(DietWritingActivity.this);


                if (items[item].equals("Take Photo")) {

                    userChoosenTask = "Take Photo";

                    if (result)

                        cameraIntent();


                } else if (items[item].equals("Choose from Library")) {

                    userChoosenTask = "Choose from Library";

                    if (result)

                        galleryIntent();


                } else if (items[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();


    }


    private void galleryIntent() {


        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);//

        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);


    }


    private void cameraIntent() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_CAMERA);


    }


    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (userChoosenTask.equals("Take Photo"))

                        cameraIntent();

                    else if (userChoosenTask.equals("Choose from Library"))

                        galleryIntent();

                } else {

                    //code for deny

                }

                break;

        }

    }




    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


        File destination = new File(Environment.getExternalStorageDirectory(),

                System.currentTimeMillis() + ".jpg");


        FileOutputStream fo;

        try {

            destination.createNewFile();

            fo = new FileOutputStream(destination);

            fo.write(bytes.toByteArray());

            fo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile_img.setImageBitmap(thumbnail);

    }


    @SuppressWarnings("deprecation")

    private void onSelectFromGalleryResult(Intent data) {


        Bitmap bm = null;

        if (data != null) {

            try {

                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bm);
                Glide.with(this).load(data.getData()).override(400, 400).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }


        //  profile_img.setImageBitmap(bm);

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

