package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Example08_ANRActivity extends AppCompatActivity {
    TextView sumTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example08_anr);

        sumTv = findViewById(R.id.sumTextView);

        // 첫번째 버튼에 대한 reference 획득 & 이벤트 처리
        // 버튼을 누르면 상당히 오랜 시간 연산이 수행된다.
        // 결과적으로 사용자와의 interaction 이 중지 된다.
        // Activity 가 block 된 것처럼 보인다(ANR : Application Not Responding!)
        // 당연히 ANR 현상은 피해줘야 한다.
        // Activity 의 최우선 작업은 사용자와의 interaction
        // Activity 에서는 시간이 오래 걸리는 작업은 하면 안돼! (ex: DB처리 네트워크을 이용한 연산 처리.)
        // Activity 는 Thread 로 동작한다! (UI Thread)
        // 로직처리는 background Thread 를 이용해서 처리해야 한다.
        Button startBtn = findViewById(R.id.btn_anr_start);
        Button secondBtn = findViewById(R.id.btn_anr_second);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long sum = 0;
                for(long i = 0; i < 10000000000L; i++){
                    sum += i;
                }
                Log.d("SumTest", "연산된 결과는? " + sum);
            }
        });

        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Example08_ANRActivity.this,"뿅!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
