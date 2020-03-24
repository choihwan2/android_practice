package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Example06_SendMessageActivity extends AppCompatActivity {

    TextView tv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example06_send_message);
        tv = findViewById(R.id.tv_sendMsg);
        btn = findViewById(R.id.btn_sendMsg);

        Intent i = getIntent();
        String msg = (String) i.getExtras().get("sendMsg");
        tv.setText(msg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Example06_SendMessageActivity.this.finish();
            }
        });

    }
}
