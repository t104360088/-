package com.example.lulala.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class star extends AppCompatActivity {
    private Button btnnew, btnclear, btnback;
    private ListView listview;
    private SQLiteDatabase dbrw;
    private Cursor c;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        Intent intent = getIntent();
        str = intent.getStringExtra("store_name");

        star_data star_datas = new star_data(this);
        dbrw = star_datas.getWritableDatabase();

        btnnew = (Button)findViewById(R.id.btnnew);
        btnnew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent_add = new Intent();
                intent_add.setClass(star.this, add_star.class);
                intent_add.putExtra("store_name", str);
                startActivity(intent_add);
                finish();
            }
        });

        btnclear = (Button)findViewById(R.id.btnclear);
        btnclear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dbrw.delete("mystar","store="+"'"+str+"'",null);

                Toast.makeText(star.this,"刪除成功",Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        btnback = (Button)findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });






        c = dbrw.query("mystar",null,"store=" + "'" + str + "'",null,null,null,null);


        if (c.getCount()>0) {
            c.moveToFirst();

            String[] data = new String[c.getCount()];
            for (int i = 0; i < c.getCount(); i++) {

                data[i] = "暱稱：" + c.getString(2) + "\n星星：" + c.getString(3) + "顆星\n評論：" + c.getString(4);
                c.moveToNext();
            }


            ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
            listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(messageAdapter);


            Toast.makeText(this, "共有" + c.getCount() + "筆紀錄", Toast.LENGTH_SHORT).show();
        }


    }
}
