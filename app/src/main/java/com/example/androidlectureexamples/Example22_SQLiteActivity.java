package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Example22_SQLiteActivity extends AppCompatActivity {

    EditText _22_dbNameEt;
    EditText _22_tableNameEt;
    EditText _22_empNameEt;
    EditText _22_empAgeEt;
    EditText _22_empPhoneEt;
    SQLiteDatabase database;
    TextView _22_empTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example22_sqlite);

        _22_dbNameEt = findViewById(R.id._22_dbNameEt);
        _22_tableNameEt = findViewById(R.id._22_tableNameEt);
        _22_empNameEt = findViewById(R.id._22_empNameEt);
        _22_empAgeEt = findViewById(R.id._22_empAgeEt);
        _22_empPhoneEt = findViewById(R.id._22_empPhoneEt);
        Button dbCreateBtn = findViewById(R.id._22_dbCreateBtn);
        _22_empTv = findViewById(R.id._22_resultTv);
        dbCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Database 생성!
                // 파일로 만들어진다 "DB Name.db"라는 파일로 생성
                // /data/data/App Name/ 여기에 생성
                String dbName = _22_dbNameEt.getText().toString();
                // Mode_PRIVATE 는
                database = openOrCreateDatabase(dbName, MODE_PRIVATE, null);

                Log.i("DBTEst", "Database가 생성되었어요!");

            }
        });

        Button tableCreateBtn = findViewById(R.id._22_tableCreateBtn);
        tableCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = _22_tableNameEt.getText().toString();
                if (database == null) {
                    Toast.makeText(getApplicationContext(), "DB부터 만들어주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 현재 database 에 대한 reference 가 존재
                // SQL 을 이용해서 Database 안에 Table 을 생성하면 된다.
                String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "age INTEGER," +
                        "mobile TEXT)";

                // 완성된 SQL 을 어떤 database 에서 실행시킨건지를 결정해서 실행~!
                database.execSQL(sql);
                Log.i("DBTest", "Table 이 생성 되었습니다!");
            }
        });
        final Button _22_empBtn = findViewById(R.id._22_empCreateBtn);
        _22_empBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _22_empNameEt.getText().toString();
                int age = Integer.parseInt(_22_empAgeEt.getText().toString());
                String mobile = _22_empPhoneEt.getText().toString();

                if(database == null){
                    Toast.makeText(Example22_SQLiteActivity.this, "Database를 오픈해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sql = "INSERT INTO emp(name,age,mobile) VALUES ('" + name + "'," + age + ",'" + mobile + "')";
                database.execSQL(sql);
                Toast.makeText(Example22_SQLiteActivity.this, "데이터 입력 성공!", Toast.LENGTH_SHORT).show();
                _22_empNameEt.setText("");
                _22_empAgeEt.setText("");
                _22_empPhoneEt.setText("");
            }
        });

        Button _22_empSelectBtn = findViewById(R.id.empSelectBtn);
        _22_empSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "SELECT * FROM emp";
                // JDBC 와 상당히 유사하게 실행된다.
                if (database == null){
                    Toast.makeText(Example22_SQLiteActivity.this, "DB를 생성해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // execSQL() :select 계열이 아닌 SQL 문장을 실행할때 사용.
                // rawQuery() : select 계열의 SQL 문장을 실행할때 사용.
                // TODO: rawQuery() 에 대해 알아보기
                Cursor cursor = database.rawQuery(sql,null);
                StringBuilder stb = new StringBuilder();
                while(cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int age = cursor.getInt(2);
                    String mobile = cursor.getString(3);

                    String result =  "레코드 : " + id + ", " + name + ", " + age + ", " + mobile + "\n";
                    stb.append(result);
                }
                _22_empTv.setText(stb.toString());

            }
        });
    }
}
