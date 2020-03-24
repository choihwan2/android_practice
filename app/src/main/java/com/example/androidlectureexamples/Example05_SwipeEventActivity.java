package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Example05_SwipeEventActivity extends AppCompatActivity {
    double x1,x2;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example05_swipe_event);

        ConstraintLayout cl = findViewById(R.id.myConstraintLayout);

        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String msg = "";

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    x1 = event.getX();
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    x2 = event.getX();
                    if(x1 < x2){
                        msg = "오른쪽으로 swipe";
                    }else {
                        msg = "왼쪽으로 swipe";
                    }
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }
}
