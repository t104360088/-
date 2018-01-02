package com.example.lulala.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2017/12/27.
 */

public class buyy extends SQLiteOpenHelper {

    private static final String database = "mydata.db";
    private static final int version = 1;

    public buyy(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public buyy(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE myTable(_id integer primary key autoincrement," +
                "store text no null," +
                "name text no null," +
                "cost text no null");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS myTable");
        onCreate(db);
    }
}
