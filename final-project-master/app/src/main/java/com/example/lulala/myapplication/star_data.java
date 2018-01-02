package com.example.lulala.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2018/1/2.
 */

public class star_data extends SQLiteOpenHelper {

    private static final String database = "mpmp.db";
    private static final int version = 1;

    public star_data(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public star_data(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE mystar(_id integer primary key autoincrement," +
                "store text no null," +
                "name text no null," +
                "star text no null," +
                "thing text no null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS mystar");
        onCreate(db);
    }
}