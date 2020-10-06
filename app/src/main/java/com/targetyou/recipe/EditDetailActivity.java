package com.targetyou.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EditDetailActivity extends FontActivity {

    String email, sex, age, high, weight;
    private Spinner spinner1, spinner2, spinner3, spinner4;
    private Button button;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);

        Intent getIntent = getIntent();
        email = getIntent.getStringExtra("email");

        intent = new Intent(EditDetailActivity.this, EditDetailActivity2.class);
        intent.putExtra("email", email);


        spinner1 = findViewById(R.id.sex_spinner);
        spinner2 = findViewById(R.id.weight_spinner);
        spinner3 = findViewById(R.id.age_spinner);
        spinner4 = findViewById(R.id.height_spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.age, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.height, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = (String) spinner1.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weight = (String) spinner2.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = (String) spinner3.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                high = (String) spinner4.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        NetworkUtil.setNetworkPolicy();
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("age", age);
                intent.putExtra("sex", sex);
                intent.putExtra("high", high);
                intent.putExtra("weight", weight);
                startActivity(intent);
            }
        });
    }
}
