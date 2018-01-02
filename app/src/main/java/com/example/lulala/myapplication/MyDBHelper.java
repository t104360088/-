package com.example.lulala.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lulala on 2017/12/12.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String database = "mydata.db";
    private static final int version = 1;

    public MyDBHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    public MyDBHelper(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE myTable(_id integer primary key autoincrement," +
                "name text no null," +
                "address text no null,"+
                "phone int no null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS myTable");
        onCreate(db);
    }
}