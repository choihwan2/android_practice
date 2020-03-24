package com.example.androidlectureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Example11_CountHandlerActivity extends AppCompatActivity {
    ProgressBar pb;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example11_count_handler);
        Button btn = findViewById(R.id.btn_count_handler_start);
        tv = findViewById(R.id.sumTextView3);
        pb = findViewById(R.id.progress_handler_count);

        // 데이터를 주고받는 역활을 수행하는 Handler 객체를 하나 생성.
        // Handler 객체는 메시지를 전달하는 method 와 메시지를 전달받아 로직 처리를 하는
        // method 2개를 주로 사용.

        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            // 메시지를 받게 되면 handleMessage 메소드가 호출된다!
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //  받은 메시지를 이용해서 화면처리!!
                Bundle data = msg.getData();
                pb.setProgress(Integer.parseInt(data.getString("count")));;
            }
        };

        // 연산시작이라는 버튼을 클릭했을때 로직처리하는 Thread 를 생성해서 실행!
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread 를 생성하자!
                // Thread 에게 Activity 와 데이터 통신을 할 수 있는 Handler 객체를 전달한다.
                MySumThread runnable = new MySumThread(handler);
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }
}

//연산을 담당하는 Thread class 를 위한 Runnable interface 를 구현한 클래스
class MySumThread implements Runnable {
    private Handler handler;

    public MySumThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        long sum = 0;
        for (long i = 0; i<10000000000L; i++){
            sum += i;
            if (i % 100000000 == 0) {
                long loop = i / 100000000;
                // message 를 만들어서 handler 를 이용해 Activity 에게 메시지를 전달해줘야한다.
                // Bundle 객체를 하나 만들어요!
                Bundle bundle = new Bundle();
                bundle.putString("count", String.valueOf(loop));
                // 이 번들을 가질수 있는 Message 객체를 생성
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }
    }
}
