package com.example.lulala.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2017/12/26.
 */

public class history extends SQLiteOpenHelper {

    private static final String database = "myhistory.db";
    private static final int version = 1;

    public history(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public history(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE myhistory(_id integer primary key autoincrement," +
                "store text no null," +
                "data text no null," +
                "cost text no null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS myhistory");
        onCreate(db);
    }
}