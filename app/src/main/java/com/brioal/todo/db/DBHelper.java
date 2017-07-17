package com.brioal.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private String todo_sql = "create table todo ( id long primary key  , parent string , content string , isdone int );CREATE UNIQUE INDEX todo_I ON todo(parent, content);";
    private String classify_sql = "create table classify ( id primary key ,name string UNIQUE) ";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(todo_sql);
        sqLiteDatabase.execSQL(classify_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
