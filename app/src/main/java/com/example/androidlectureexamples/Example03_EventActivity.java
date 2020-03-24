package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Example03_EventActivity extends AppCompatActivity {
    private ImageView img; //heap
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example03_event);

        Button btn = findViewById(R.id.btn_change);
        img = findViewById(R.id.img_alpaca);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isClick)
                    img.setImageResource(R.drawable.alpaca3);
                else
                    img.setImageResource(R.drawable.alpaca2);

                isClick = !isClick;
            }
        });
    }
}
