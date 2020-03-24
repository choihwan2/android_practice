package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Example07_SendDataActivity extends AppCompatActivity {

    private String result;
    private ArrayList<String> list;
    private Button closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example07_send_data);

        // Spinner 안에 표현될 데이터를 만들어봅시다.
        // 예제에에서는 Spinner 안에 표현될 데이터가 문자열로 가정.
        list = new ArrayList<>();
        list.add("포도");
        list.add("딸기");
        list.add("바나나");
        list.add("사과");

        // spinner 의 레퍼런스를 들고오자

        Spinner spinner = findViewById(R.id.mySpinner);
        closeBtn = findViewById(R.id.btn_sendData);

        // Adapter 를 하나 만들어야 해요! (Adapter 의 종류가 다양하다!)
        // is a 관계 ?
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list);

        // adapter 를 spinner 에게 부착
        spinner.setAdapter(adapter);

        // spinner 의 event 처리
        // 많은 이벤트가 있다. 그중 어떤걸 사용할지 잘 정해야함.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ResultValue",result);
                setResult(7000, intent);
                Example07_SendDataActivity.this.finish();
            }
        });



    }
}
