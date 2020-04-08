package com.example.androidlectureexamples;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/*
    Content Provider(내용제공자) App 에서 관리하는 데이터(SQLite Database)를
    다른 App 이 접근할 수 있도록 해주는 기능을 해요!
    CP는 Android 구성요소 중의 하나로 안드로이드 시스템에 의해서 관리되요!
    AndroidManifest.xml 파일에 등록해서 사용하게되요!
    CP 가 필요한 이유는 보안규정때문!! 안드로이드 App 은 오직 자신의 App 안에서 생성한 데이터만 사용할 수 있다.
    => 다른 App 이 가지고 있는 데이터의 접근권한이 없어요!
    CP 를 이용해서 다른 App 의 데이터에 접근하는데 일반적으로 Database 에 접근하는 방식을 이용합니다.
    그 이유는 CP가 CRUD 를 기반으로 하고 있기 때문에! (CP를 통해서 DB의 일부분만 다른 쪽에 공유를 한다.)

    ContentProvider 는 어디선가 생성하고 사용하는것이 아니다. AndroidManifest 에 등록하고 앱을 실행하면 자동적으로
    생성이 되어서 사용할 수 있는 객체이다.

 */

public class Example24_ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example24_content_provider);
        EditText _24_empNameEt = findViewById(R.id._24_empNameEt);
        EditText _24_empAgeEt = findViewById(R.id._24_empAgeEt);
        EditText _24_empMobileEt = findViewById(R.id._24_empPhoneEt);
        Button btn = findViewById(R.id._24_empCreateBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CP 를 찾아보자
                String uriString = "content://com.exam.person.provider/person";
                Uri uri = new Uri.Builder().build().parse(uriString);
                ContentValues values = new ContentValues(); //HashMap 형태의 데이터베이스에 입력할 데이터를 저장.
                values.put("name", "홍길동");
                values.put("age", 20);
                values.put("mobile", "010-2715-1400");

                // getContentResolver() => ContentProvider 를 찾아서 그안에있는 insert() 메소드를 호출함.
                getContentResolver().insert(uri, values);
                Log.i("DBTest", "데이터가 입력되었습니다.");
            }
        });
        final TextView resultTv = findViewById(R.id._24_resultTv);
        Button _24_selectBtn = findViewById(R.id._24_empSelectBtn);
        _24_selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DBTest", "Select 버튼 클릭!");
                //1. DB 처리 기능을 제공하는 Content Provider 를 찾아야 해요!
                // Content Provider 를 찾기 위한 URI 가 있어야해요!
                String uriString = "content://com.exam.person.provider/person";
                Uri uri = new Uri.Builder().build().parse(uriString);

                // 2. Uri 를 이용해서 Content Provider 를 찾아서 특징 method 를 호출
                // column 을 표현하기 위한 String[] 을 생성해요!
                // "select name, age, mobile from person where ~~"
                String[] columns = new String[]{"name", "age", "mobile"};
//                getContentResolver().query(uri, "어떻게 select 를 할건지..","조건","조건에 대한 argument","정렬방향");
                Cursor cursor = getContentResolver().query(uri,columns,null,null,"name ASC");
                // 성공 하면 Database table 에서 결과 record 의 집합을 가져와요!
                while (cursor.moveToNext()){
                    String name = cursor.getString(0);
                    int age = cursor.getInt(1);
                    String mobile = cursor.getString(2);

                    String result = "record => " + name + ", " + age + ", " + mobile;
                    resultTv.append(result + "\n");
                }
            }
        });
    }
}


