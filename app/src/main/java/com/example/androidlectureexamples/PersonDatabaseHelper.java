package com.example.androidlectureexamples;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class PersonDatabaseHelper extends SQLiteOpenHelper{

    public PersonDatabaseHelper(@Nullable Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 초기에 만들어지는 시점에 Table 도 같이 만들꺼에요!
        String sql = "CREATE TABLE IF NOT EXISTS " + "person" + "( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "age INTEGER," +
                "mobile TEXT)";
        db.execSQL(sql);
        Log.i("DBTest","테이블 생성되었어요!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
