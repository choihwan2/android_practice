package com.example.androidlectureexamples;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Example23_SQLiteHelperActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example23_sqlite_helper);
        Button _22_dbCreateBtn = findViewById(R.id._23_dbCreateBtn);
        et = findViewById(R.id._23_dbNameEt);
        _22_dbCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbName = et.getText().toString();
                MyDatabaseHelper helper = new MyDatabaseHelper(Example23_SQLiteHelperActivity.this,dbName,1);
                // helper 를 통해서 database reference 를 획득
                database = helper.getWritableDatabase();
                // 1. 새로운 데이터베이스를 만들어 보아요!
                // helper 가 생성되면서 데이터베이스가 만들어지고
                // onCreate() -> onOpen()

            }
        });
    }
}

// DatabaseOpenHelper 를 이용하려면 별도의 클래스를 정의해줘야한다.
class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(@Nullable Context context, @Nullable String dbName, int version) {

        // SQLiteOpenHelper 는 default 생성자가 없다.
        super(context, dbName, null, version);
        // 만약 dbName 으로 만든 데이터베이스가 없다면 해당 데이터베이스를 생성할때 version 정보를 같이 명시.
        // onCreate() 를 callback -> onOpen() 도 바로 호출.
        // 만약 dbName 으로 만든 데이터베이스가 이미 존재하면
        // onOpen() 호출 근데 이미 존재하는데 만약 version 값이 다르다?? => onUpgrade() 호출!
        // 기존 데이터베이스의 스키마를 변경하는 작업을 진행!
        // 초창기에 앱을 만들어서 배포할때 DB 스키마를 생성. 앱을 업데이트해서 다시 베포할때
        // DB가 변경된 사항이 있다면?? 다시 배포할때 DB 스키마를 다시 생성하기 위해서 사용이 되요!
        // 이전 DB를 drop 시키고 새로운 DB를 만드는 작업을 진행!
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // 데이터베이스가 Open할때 자동으로 호출!!
        Log.i("DBTest", "onOpen() 호출");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 존재하지 않아서 생성할때 호출!!
        Log.i("DBTest", "onCreate()호출!");
        String sql = "CREATE TABLE IF NOT EXISTS " + "person" + "( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "age INTEGER," +
                "mobile TEXT)";
        db.execSQL(sql);
        Log.i("DBTest","Table 생성 성공!");
        // 데이터베이스 생성하는 코드가 나온다.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 버전이 바뀌어서 데이터베이스를 수정할때 호출!!
        Log.i("DBTest","onUpgrade()호출!!");
    }
}
