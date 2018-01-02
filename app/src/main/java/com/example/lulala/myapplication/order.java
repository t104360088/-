package com.example.lulala.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class order extends AppCompatActivity
{
    private ListView listView;
    private Button btnadd, btnok, btncancel;
    private EditText editText_name, editText_num;
    private TextView detail, total;
    private SQLiteDatabase dbrw;
    private Cursor c;



    private String str;
    private int money = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        editText_name = (EditText) findViewById(R.id.editText5);
        editText_num = (EditText) findViewById(R.id.editText8);
        detail = (TextView)findViewById(R.id.textView5);
        total = (TextView)findViewById(R.id.textView4);

        Intent intent = getIntent();
        str = intent.getStringExtra("store_name");


        box boxs = new box(this);
        dbrw = boxs.getWritableDatabase();

        c = dbrw.query("mybox", null, "store=" + "'" + str + "'", null, null, null, null);
        String[] data = new String[c.getCount()];
        if (c.getCount() > 0)
        {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++)
            {

                data[i] = "商品：" + c.getString(2) + "  ,價格： " + c.getString(3);
                c.moveToNext();
            }
        }

        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView = (ListView) findViewById(R.id.listoder);
        listView.setAdapter(messageAdapter);

        btnadd = (Button) findViewById(R.id.button9);
        btnok = (Button) findViewById(R.id.button10);
        btncancel = (Button) findViewById(R.id.button11);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addpackage();
            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                okpackage();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                backstore();
            }
        });

    }

    public void addpackage()
    {
        if (editText_name.getText().toString().equals("") || editText_num.getText().toString().equals(""))
        {
            Toast.makeText(this, "輸入資料不完全", Toast.LENGTH_SHORT)
                    .show();
        }
        else
        {
            int num = Integer.parseInt(editText_num.getText().toString());
            String string = detail.getText().toString() + "\n" + editText_name.getText().toString() + "購買" + num + "個";
            detail.setText(string);

            c = dbrw.query("mybox", null, "name=" + "'" + editText_name.getText().toString() + "'", null, null, null, null);
            c.moveToFirst();
            int price = Integer.valueOf(c.getString(3));

            money = (price * num) +  money;
            total.setText("總額：" + money);



            editText_name.setText("");
            editText_num.setText("");
        }
    }
    public void okpackage()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(order.this);
        dialog.setTitle("確認");
        dialog.setMessage("是否下訂?");
        dialog.setNegativeButton("否",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
            }

        });
        dialog.setPositiveButton("是",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                ContentValues cv_ok = new ContentValues();
                SQLiteDatabase dbrw_ok;
                history h = new history(order.this);
                dbrw_ok = h.getWritableDatabase();
                cv_ok.put("store", str);
                cv_ok.put("data", detail.getText().toString());
                cv_ok.put("cost", Integer.toString(money));
                dbrw_ok.insert("myhistory", null, cv_ok);
                Toast.makeText(order.this, "感謝下訂",Toast.LENGTH_SHORT).show();
                finish();
            }

        });
        dialog.show();

    }
    public void backstore()
    {
        this.finish();
    }
}
