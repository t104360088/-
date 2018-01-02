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
import android.widget.ListView;
import android.widget.Toast;

public class history_show extends AppCompatActivity {

    private SQLiteDatabase dbrw;
    private Cursor c;
    private ContentValues cv = new ContentValues();
    private String str;
    private ListView listView;
    private Button back,clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_show);

        Intent intent = getIntent();
        str = intent.getStringExtra("store_name");

        history h = new history(this);
        dbrw = h.getWritableDatabase();

        a();

        back = (Button)findViewById(R.id.button13);
        clean =(Button)findViewById(R.id.button14);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        clean.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dbrw.delete("myhistory","store="+"'"+str+"'",null);
                a();
            }
        });
    }
    public void a(){
        c = dbrw.query("myhistory", null, "store=" + "'" + str + "'", null, null, null, null);
        final String[] data = new String[c.getCount()];
        if (c.getCount() > 0)
        {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++)
            {
                data[i] = "內容：\n" + c.getString(2) + " ，一共 " + c.getString(3)+"元";
                c.moveToNext();
            }
        }

        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView = (ListView) findViewById(R.id.list_history);
        listView.setAdapter(messageAdapter);
    }
}
