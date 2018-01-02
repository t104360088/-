package com.example.lulala.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class add_star extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton r1, r2, r3, r4, r5;
    private Button btnok;
    private EditText edit_name, edit_thing;
    private SQLiteDatabase dbrw;
    private Cursor c;
    String str1 = "5", str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_star);



        edit_name = (EditText)findViewById(R.id.edit_name);
        edit_thing = (EditText)findViewById(R.id.edit_thing);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        r1 = (RadioButton)findViewById(R.id.r1);
        r2 = (RadioButton)findViewById(R.id.r2);
        r3 = (RadioButton)findViewById(R.id.r3);
        r4 = (RadioButton)findViewById(R.id.r4);
        r5 = (RadioButton)findViewById(R.id.r5);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (radioGroup.getCheckedRadioButtonId())
                {
                    case R.id.r1:
                        str1 = "1";
                        break;
                    case R.id.r2:
                        str1 = "2";
                        break;
                    case R.id.r3:
                        str1 = "3";
                        break;
                    case R.id.r4:
                        str1 = "4";
                        break;
                    case R.id.r5:
                        str1 = "5";
                        break;
                }
            }
        });

        btnok = (Button)findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (edit_name.getText().toString().equals("") || edit_thing.getText().toString().equals(""))
                    Toast.makeText(add_star.this, "輸入資料不完全", Toast.LENGTH_SHORT).show();
                else {

                    star_data star_datas = new star_data(add_star.this);
                    dbrw = star_datas.getWritableDatabase();

                    Intent intent = getIntent();
                    str = intent.getStringExtra("store_name");

                    ContentValues cv = new ContentValues();
                    cv.put("store", str);
                    cv.put("name", edit_name.getText().toString());
                    cv.put("star", str1);
                    cv.put("thing", edit_thing.getText().toString());

                    dbrw.insert("mystar", null, cv);

                    Toast.makeText(add_star.this, "新增評論", Toast.LENGTH_SHORT)
                            .show();

                    Intent intent_back = new Intent();
                    intent_back.setClass(add_star.this, star.class);
                    intent_back.putExtra("store_name", str);
                    startActivity(intent_back);
                    finish();
                }
            }
        });

    }
}
