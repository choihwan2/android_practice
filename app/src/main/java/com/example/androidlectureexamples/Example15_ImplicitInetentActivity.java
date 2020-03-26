package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Example15_ImplicitInetentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example15__mplicit_inetent);
        Button btn16 = findViewById(R.id._16_dialBtn);
        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 전화걸기 Activity 를 호출하려면 2가지 중 하나를 사용해야한다.
                // 1. 클래스명을 알면 호출할 수 있ㄷ.(Explicit intent)
                // 2. 묵시적 Intent 를 통해서 알려져있는 Action 을 통해 이용
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01027532175"));
                startActivity(intent);
            }
        });

    }
}
