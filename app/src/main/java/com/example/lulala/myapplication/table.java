package com.example.lulala.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class table extends AppCompatActivity
{
    private ListView listView;
    private Button btnnew, btndelete,btnback;
    private EditText editText_name, editText_price;
    private SQLiteDatabase dbrw;
    private Cursor c;
    private String str;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        editText_name = (EditText) findViewById(R.id.editText6);
        editText_price = (EditText) findViewById(R.id.editText7);


        box boxs = new box(this);
        dbrw = boxs.getWritableDatabase();

        Intent intent = getIntent();
        str = intent.getStringExtra("store_name");

        c = dbrw.query("mybox", null, "store=" + "'" + str + "'", null, null, null, null);
        String[] data = new String[c.getCount()];
        if (c.getCount() > 0)
        {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++)
            {

                data[i] = "商品：" + c.getString(2) + "  ,    價格： " + c.getString(3);
                c.moveToNext();
            }
        }

        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView = (ListView) findViewById(R.id.listview_table);
        listView.setAdapter(messageAdapter);



        btnnew = (Button) findViewById(R.id.button7);
        btndelete = (Button) findViewById(R.id.button6);
        btnback = (Button) findViewById(R.id.button8);

        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                newpackage();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deletepackage();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                backstore();
            }
        });

    }



    public void newpackage()
    {
        if (editText_name.getText().toString().equals("") || editText_price.getText().toString().equals(""))
        {
            Toast.makeText(this, "輸入資料不完全", Toast.LENGTH_SHORT)
                    .show();
        }
        else
        {
            ContentValues cv = new ContentValues();
            cv.put("store", str);
            cv.put("name", editText_name.getText().toString());
            cv.put("price", editText_price.getText().toString());
            dbrw.insert("mybox", null, cv);

            Toast.makeText(this,"新增成功",Toast.LENGTH_SHORT).show();

            editText_name.setText("");
            editText_price.setText("");

            repackage();
        }
    }

    public void deletepackage()
    {
        if (editText_name.getText().toString().equals(""))
            Toast.makeText(this, "請輸入要刪除的商品", Toast.LENGTH_SHORT).show();
        else
        {
            dbrw.delete("mybox","name="+"'"+editText_name.getText().toString()+"'",null);

            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();

            editText_name.setText("");
            editText_price.setText("");

            repackage();
        }
    }

    public void repackage()
    {
        box boxs = new box(this);
        dbrw = boxs.getWritableDatabase();

        c = dbrw.query("mybox", null, "store=" + "'" + str + "'", null, null, null, null);
        String[] data = new String[c.getCount()];
        if (c.getCount() > 0)
        {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++)
            {

                data[i] = "商品：" + c.getString(2) + "  ,    價格： " + c.getString(3);
                c.moveToNext();
            }
        }

        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(messageAdapter);
    }

    public void backstore()
    {
        this.finish();
    }
}
