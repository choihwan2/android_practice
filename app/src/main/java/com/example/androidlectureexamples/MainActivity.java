package com.example.androidlectureexamples;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static String pkg = "com.example.androidlectureexamples";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn = findViewById(R.id._01_linearBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼이 눌리면 이 부분이 실행되요!
                // 새로운 activity 를 찾을 수 있다
                // explicit 방식과 implicit 방식이 있어요
//                Intent intent = new Intent(getApplicationContext(),Example01_LayoutActivity.class);
//                startActivity(intent);

                //explicit 방식
                Intent intent1 = new Intent();
                ComponentName cname = new ComponentName(pkg,pkg + ".Example01_LayoutActivity");
                intent1.setComponent(cname);
                //intent1 이 정보를 들고있으니 그걸가지고 엑티비티를 시작해!
                startActivity(intent1);
            }
        });

        Button btn2 = findViewById(R.id._02_widgetBtn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example02_WidgetActivity");
                intent.setComponent(cName);
                startActivity(intent);

            }
        });

        Button btn3 = findViewById(R.id._03_eventBtn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example03_EventActivity");
                intent.setComponent(cName);
                startActivity(intent);
            }
        });

        Button btn4 = findViewById(R.id._04_activityEventBtn);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example04_TouchEventActivity");
                intent.setComponent(cName);
                startActivity(intent);
            }
        });

        Button btn5 = findViewById(R.id._05_swipeEventBtn);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example05_SwipeEventActivity");
                intent.setComponent(cName);
                startActivity(intent);
            }
        });

        Button btn6 = findViewById(R.id._06_dataSendBtn);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alert 창 (dialog) 를 이용해서 문자열 입력받고
                // 입력 받은 문자열을 다음 activity 로 전달

                // 사용자가 문자열을 입력할 수 있는 widget을 일단 하나 생성.
                Log.e("Onclick","getApplicationContext():" + getApplicationContext());
                Log.e("Onclick","MainActivity.this: " + MainActivity.this);
                final EditText editText = new EditText(getApplicationContext());
                // AlertDialog 를 하나 생성해야함.
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //화면에 대화창을 만들어주는 아이
                builder.setTitle("Activity 데이터 전달");
                builder.setMessage("다음 Activity 에 전달할 내용을 입력하세요!");
                builder.setView(editText);
                builder.setPositiveButton("전달", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 전달을 눌렀을 때 수행되는 이벤트 처리 작업을 하면된다.
                        Intent intent = new Intent();
                        ComponentName cName = new ComponentName(pkg, pkg + ".Example06_SendMessageActivity");
                        intent.setComponent(cName);
                        // 데이터를 전달해서 띄워줘야함.
                        intent.putExtra("sendMsg",editText.getText().toString());
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소를 눌렀을 때 수행되는 이벤트 처리.
                    }
                });
                builder.show();

            }
        });

        Button btn7 = findViewById(R.id._07_dataSendBtn);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example07_SendDataActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivityForResult(intent, 3000);
            }
        });

        Button btn8 = findViewById(R.id._08_ANRBtn);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example08_ANRActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivity(intent);
            }
        });
        Button btn9 = findViewById(R.id._09_countBtn);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example09_CountActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivity(intent);
            }
        });
        Button btn10 = findViewById(R.id._10_countProgressBtn);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example10_CountProgressActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivity(intent);
            }
        });
        Button btn11 = findViewById(R.id._11_countHandlerBtn);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example11_CountHandlerActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivity(intent);
            }
        });

        Button btn12 = findViewById(R.id._12_searchBookBtn);
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example12_SearchBookActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivity(intent);
            }
        });

        Button btn13 = findViewById(R.id._13_bookDetailBtn);
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example13_BookDetailActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivity(intent);
            }
        });

        Button btn14 = findViewById(R.id._14_itemBookBtn);
        btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent( 명시적 인텐트) 내가 뭘 쓸지 써놨다.
                Intent intent = new Intent();
                ComponentName cName = new ComponentName(pkg,pkg + ".Example14_ItemBookActivity");
                intent.setComponent(cName);
                // 새로 생성되는 activity 로부터 데이터를 받아오기 위한 요옫
                // 두번째 activity 가 finish 되는 순간 데이터를 받아와요!
                startActivity(intent);
            }
        });
        Button btn15 = findViewById(R.id._15_implicitIntentBtn);
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // implicit Intent (묵시적 인텐트)
                // 방금 생성한 Activity 를 호출한다!
                Intent intent = new Intent();
                intent.setAction("MY_ACTION");
                intent.addCategory("INTENT_TEST");
                startActivity(intent);
            }
        });

        /*
        App 이 실행 되었다고 해서 항상 Activity 가 보이는건 아니에요! 대표적인 경우 카톡, 멜론...
        한마디로 서비스는 화면이 없는 Activity 다!
        Activity 는 onCreate() -> onStart() -> onResume() -> onPuase() -> onStop() -> onDestroy();
        Service 는 onCreate() -> onStartCommand() -> onDestroy()
        눈에 보이지 않기 때문에 (Background) 에서 로직 처리하는데 많이 이용된다.

         */

        Button btn16 = findViewById(R.id._16_serviceBtn);
        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Example16_ServiceLifeCycleActivity.class);
                startActivity(intent);
            }
        });
        Button btn17 = findViewById(R.id._17_serviceDataBtn);
        btn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Example17_ServiceDataActivity.class);
                startActivity(intent);
            }
        });

        Button btn18 = findViewById(R.id._18_kakaoBookBtn);
        btn18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Example18_KakaoBookSearchActivity.class);
                startActivity(intent);
            }
        });

        Button btn19 = findViewById(R.id._19_broadCasterBtn);
        btn19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Example19_BroadCasterActivity.class);
                startActivity(intent);
            }
        });

        Button btn20 = findViewById(R.id._20_Btn);
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Example20_BRSMSActivity.class);
                startActivity(intent);
            }
        });

        Button btn21 = findViewById(R.id._21_Btn);
        btn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Example21_BRNotificationActivity.class);
                startActivity(intent);
            }
        });

        Button btn22 = findViewById(R.id._22_Btn);
        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Example22_SQLiteActivity.class);
                startActivity(intent);
            }
        });

        Button btn23 = findViewById(R.id._23_Btn);
        btn23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Example23_SQLiteHelperActivity.class);
                startActivity(intent);
            }
        });

        Button btn24 = findViewById(R.id._24_Btn);
        btn24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Example24_ContentProviderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3000 && resultCode == 7000){
            String msg = (String)data.getExtras().get("ResultValue");
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }
}
