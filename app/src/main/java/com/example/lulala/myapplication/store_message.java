package com.example.lulala.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class store_message extends AppCompatActivity {
    private Button store_manager_btn,query_store_btn;
    private SQLiteDatabase dbrw;
    private Cursor c;
    private EditText name;
    private MediaPlayer mPlayer;
    final String[] information = {"地圖位置","商品目錄管理","下單管理","歷史購買紀錄","撥打電話", "發表評論"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_message);

        store_manager_btn = (Button)findViewById(R.id.button3);
        query_store_btn = (Button)findViewById(R.id.button5);
        name = (EditText)findViewById(R.id.editText4);

            mPlayer = MediaPlayer.create(store_message.this, R.raw.test);
            mPlayer.start();


        MyDBHelper dbhelper = new MyDBHelper(this);
        dbrw = dbhelper.getWritableDatabase();


        c = dbrw.query("myTable",null,null,null,null,null,null);

        display_data_and_alert_dialog();

        Toast.makeText(this,"共有"+c.getCount()+"個店家資訊",Toast.LENGTH_SHORT).show();

        store_manager_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager();
            }
        });
        query_store_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query();
            }
        });

    }

    public void manager(){
        Intent intent_go_to_add_store = new Intent();
        intent_go_to_add_store.setClass(store_message.this, add_store.class);
        startActivityForResult(intent_go_to_add_store,0);
        mPlayer.stop();
        this.finish();
    }

    public void query(){

        c = dbrw.query("myTable", null, "name=" + "'" + name.getText().toString() + "'", null, null, null, null);

        if (name.getText().toString().equals("")) {
            Toast.makeText(this, "請輸入查詢的店名", Toast.LENGTH_SHORT).show();
            c = dbrw.query("myTable", null, null, null, null, null, null);
            display_data_and_alert_dialog();
        }
        if (!name.getText().toString().equals("") && c.getCount()==0) {
            Toast.makeText(this, "無此店名", Toast.LENGTH_SHORT).show();
        }
        if (!name.getText().toString().equals("") && c.getCount()!=0) {
            display_data_and_alert_dialog();
            Toast.makeText(this, "有" + c.getCount() + "個符合的店名", Toast.LENGTH_SHORT).show();
        }
    }

    public void display_data_and_alert_dialog(){

        if (c.getCount()>0){
            c.moveToFirst();

            String[] data = new String[c.getCount()];

            for(int i = 0;i < c.getCount(); i++){

                data[i] = "店名：" + c.getString(1)+ "\n電話：" + c.getString(3)+ "\n地址：" + c.getString(2) + "\n";
                c.moveToNext();
            }

            ArrayAdapter<String> messageAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
            ListView listView = (ListView) findViewById(R.id.ListView);
            listView.setAdapter(messageAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l)
                {
                    c.moveToFirst();
                    for (int k = 0; k < i ; k++)
                        c.moveToNext();

                    AlertDialog.Builder dialog_list = new AlertDialog.Builder(store_message.this);
                    dialog_list.setTitle("請選擇");
                    dialog_list.setItems(information, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(store_message.this, "你選的是" + c.getString(1) + "的" + information[which], Toast.LENGTH_SHORT).show();
                            switch (which)
                            {
                                case 0:
                                    Uri gmmIntentUri = Uri.parse("geo:latitude,longitude?q="+ c.getString(2));
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);
                                    break;
                                case 1:
                                    Intent intent_go_to_table = new Intent();
                                    intent_go_to_table.setClass(store_message.this, table.class);
                                    intent_go_to_table.putExtra("store_name", c.getString(1));
                                    startActivity(intent_go_to_table);
                                    break;
                                case 2:
                                    Intent intent_go_to_order = new Intent();
                                    intent_go_to_order.setClass(store_message.this, order.class);
                                    intent_go_to_order.putExtra("store_name", c.getString(1));
                                    startActivity(intent_go_to_order);
                                    break;
                                case 3:
                                    Intent intent_go_to_history = new Intent();
                                    intent_go_to_history.setClass(store_message.this, history_show.class);
                                    intent_go_to_history.putExtra("store_name", c.getString(1));
                                    startActivity(intent_go_to_history);
                                    break;
                                case 4:
                                    Uri uri = Uri.parse("tel:0"+ c.getString(3));
                                    Intent call = new Intent(Intent.ACTION_DIAL, uri);
                                    startActivity(call);
                                    break;
                                case 5:
                                    Intent intent_go_to_star = new Intent();
                                    intent_go_to_star.setClass(store_message.this, star.class);
                                    intent_go_to_star.putExtra("store_name", c.getString(1));
                                    startActivity(intent_go_to_star);
                                    break;
                            }
                        }
                    });
                    dialog_list.show();
                }
            });
        }
    }


}

