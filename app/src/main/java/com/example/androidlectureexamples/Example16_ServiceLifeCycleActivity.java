package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Example16_ServiceLifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example16_service_life_cycle);

        Button startServiceBtn = findViewById(R.id.start_service_btn);
        Button stopServiceBtn = findViewById(R.id.end_service_btn);

        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Service Component 를 시작!
                Intent intent = new Intent(getApplicationContext(),Example15Sub_LifeCycleService.class);
                intent.putExtra("MSG","소리 없는 아우성");
                startService(intent);
                // 만약 service 객체가 없으면 생성하고 수행 onCreate() -> onStartCommand()호출
                // 만약 있다면 onStartCommand() 실행
            }
        });

        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Example15Sub_LifeCycleService.class);
                stopService(intent);
            }
        });
    }
}
