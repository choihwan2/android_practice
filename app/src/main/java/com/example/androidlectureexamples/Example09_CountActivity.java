package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Example09_CountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example09_count);

        Button startBtn = findViewById(R.id.btn_count_start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRunnable runnable = new MyRunnable();
                Thread t = new Thread(runnable);
                t.start();
            }
        });

        Button secondBtn = findViewById(R.id.btn_count_second);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"끄아아",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            long sum = 0;
            for(long i = 0; i < 10000000000L; i++){
                sum += i;
            }
            Log.d("SumTest", "연산된 결과는? " + sum);
        }
    }
}
