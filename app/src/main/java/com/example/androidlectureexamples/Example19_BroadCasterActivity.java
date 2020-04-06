package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*

    Android 에서는 Broadcast 라는 signal 이 존재 이 신호은 Android System 자체에서 발생할 수도 있고
    사용자 App 에서 임의로 발생시킬 수도 있따!

    여러개의 일반적으로 정해져있는 Broadcast message 를 받 고 싶다면!
    Broadcast Receiver 를 만들어서 등록해야 한다!

    등록하면 크게 2가지 방식이 있다..
    1. AndroidManifest.xml file 에 명시
    2. 코드 상에서 Receiver 를 만들어서 등록할 수 있어요!

 */
public class Example19_BroadCasterActivity extends AppCompatActivity {

    private BroadcastReceiver bReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example19_broad_caster);

        Button register_btn = findViewById(R.id.btn_onRegister);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Broadcast Receiver 등록 버튼 Click 시 호출!
                // Broadcast Receiver 객체를 만들어서 IntentFilter 와 함께 시스템에 등록
                // IntentFilter => Broadcast Receiver 가 어떤 신호를 받을지 정해주는것.
                // 1. IntentFilter 부터 생서
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("MY_BROADCAST_SIGNAL");

                // 2. Broadcast Receiver
                bReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // 이 Receiver 가 신호를 받게 되면 이부분이 호출.
                        // 로직처리를 여기에서 하면되요!
                        if(intent.getAction().equals("MY_BROADCAST_SIGNAL")){
                            Toast.makeText(getApplicationContext(),"신호가왔어요!",Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                // Filter 와 함께 Broadcast Receiver 등록!
                // 시스템에 등록하는 함수 registerReceiver();
                registerReceiver(bReceiver,intentFilter);
            }
        });
        Button unRegister_btn = findViewById(R.id.btn_unRegister);
        unRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(bReceiver);
            }
        });
    }
}
