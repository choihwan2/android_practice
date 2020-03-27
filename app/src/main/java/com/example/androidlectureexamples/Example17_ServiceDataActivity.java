package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Example17_ServiceDataActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example17_service_data);

        textView = findViewById(R.id.dataFromServiceTv);
        editText = findViewById(R.id.dataToService);
        button = findViewById(R.id.dataToServiceBtn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Example17_Sub_Service.class);
                //Service 에게 데이터를 전달하려면 intent를 이용해서 데이터를 전달해야해요!
                // key, value 의 형식으로 데이터를 intent 에 붙여요!
                intent.putExtra("DATA",editText.getText().toString());
                startService(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 여기서 Service 가 보내준 결과 데이터를 받아서 화면 처리!
        String result = intent.getExtras().getString("RESULT");
        // 추출한 결과를 textView 에 셋팅
        textView.setText(result);
        super.onNewIntent(intent);
    }
}
