package com.example.lulala.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class box extends SQLiteOpenHelper
{
    private static final String database = "momomo.db";
    private static final int version = 1;

    public box(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
    }

    public box(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE mybox(_id integer primary key autoincrement," +
                "store text no null," +
                "name text no null," +
                "price text no null)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS mybox");
        onCreate(db);
    }
}
