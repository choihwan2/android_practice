package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Example10_CountProgressActivity extends AppCompatActivity {

    private TextView tv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example10_count_progress);

        tv = findViewById(R.id.sumTextView2);
        pb = findViewById(R.id.progress_count);

        Button btn = findViewById(R.id.btn_count_progress_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {

                    // android UI component(Widget) 들은 Thread safe 하지 않다. (먼소리징?)
                    // => 쓰레드를 안전하게 처리하지 않는다.
                    // UI Component 는 오직 UI Thread 에 의해서만 제어되어야한다.
                    // 화면 제어(Ui component) 제어는 Activity 에서만 하자! 여러곳에서 접근하면 화면이 이상해 질 수 있다.
                    // 아래 코드는 실행되지만 올바르지는 않은 코드이다.
                    // 외부 Thread 에서 UI Component 를 제어할 수 없다!
                    // Handler 를 이용해서 이 문제를 해결해야 한다!

                    @Override
                    public void run() {
                        //숫자를 더해가면서 progressbar 를 진행시켜요!
                        long sum = 0;
                        for (long i = 0; i<10000000000L; i++){
                            sum += i;
                            if (i % 100000000 == 0) {
                                long loop = i / 100000000;
                                pb.setProgress((int) loop);
                            }
                        }
                    }
                });
                thread.start();
            }
        });

        Button secondBtn = findViewById(R.id.btn_count_progress_second);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
