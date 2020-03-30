package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Example18_KakaoBookSearchActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example18_kakao_book_serach);

        final EditText editText = findViewById(R.id.edit_kakao);
        Button button = findViewById(R.id.btn_kakao);
        listView = findViewById(R.id.list_kakao);

        // Button 에 대한 이벤트 처리
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Example18Sub_KAKAOBookSearchService2.class);
                intent.putExtra("KEYWORD",editText.getText().toString());
                startService(intent);
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<String> booksTitle = (ArrayList<String>) intent.getExtras().get("BOOKRESULT");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,booksTitle);
        listView.setAdapter(adapter);
    }
}
