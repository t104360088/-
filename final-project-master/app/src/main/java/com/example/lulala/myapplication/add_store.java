package com.example.lulala.myapplication;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_store extends AppCompatActivity {

    Button allstore,addstore,delstore,restore;
    EditText editname,editaddress,editphone;
    SQLiteDatabase dbrw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        editname = (EditText)findViewById(R.id.editText);
        editaddress = (EditText)findViewById(R.id.editText2);
        editphone = (EditText)findViewById(R.id.editText3);
        addstore = (Button) findViewById(R.id.button);
        delstore = (Button) findViewById(R.id.button2);
        restore = (Button) findViewById(R.id.button3);
        allstore = (Button)findViewById(R.id.button4);

        MyDBHelper dbhelper = new MyDBHelper(this);
        dbrw = dbhelper.getWritableDatabase();



        addstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a();
                }
            });
        delstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b();
            }
        });
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c();
            }
        });
        allstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d();
            }
        });
    }

    public void a(){

        if (editname.getText().toString().equals("")
                || editaddress.getText().toString().equals("")
                || editphone.getText().toString().equals(""))
            Toast.makeText(this, "輸入資料不完全", Toast.LENGTH_SHORT).show();
        else{
            int phone = Integer.parseInt(editphone.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("name",editname.getText().toString());
            cv.put("address",editaddress.getText().toString());
            cv.put("phone",phone);

            dbrw.insert("myTable",null,cv);

            Toast.makeText(this,"新增店家："+editname.getText().toString(),Toast.LENGTH_SHORT).show();

            editname.setText("");
            editaddress.setText("");
            editphone.setText("");
        }
    }
    public void b(){
        if (editname.getText().toString().equals(""))
            Toast.makeText(this, "請輸入要刪除的店名", Toast.LENGTH_SHORT).show();
        else{
            dbrw.delete("myTable","name="+"'"+editname.getText().toString()+"'",null);

            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();

            editname.setText("");
            editaddress.setText("");
            editphone.setText("");
        }
    }
    public void c(){
        if (editname.getText().toString().equals("")
                || editaddress.getText().toString().equals("")
                || editphone.getText().toString().equals(""))
            Toast.makeText(this, "輸入資料不完全", Toast.LENGTH_SHORT).show();
        else{
            int phone = Integer.parseInt(editphone.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put("name",editname.getText().toString());
            cv.put("address",editaddress.getText().toString());
            cv.put("phone",phone);

            dbrw.update("myTable",cv,"name="+"'"+editname.getText().toString()+"'",null);

            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();

            editname.setText("");
            editaddress.setText("");
            editphone.setText("");
        }
    }
    public void d(){
        Intent intent_go_to_store_message = new Intent();
        intent_go_to_store_message.setClass(add_store.this, store_message.class);
        startActivityForResult(intent_go_to_store_message,1);
        this.finish();
    }
}
