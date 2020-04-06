package com.example.androidlectureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Example20_BRSMSActivity extends AppCompatActivity {
    TextView senderTv;
    TextView msgTv;
    TextView dateTv;
    /*
    1. 우리 App 이 휴대단말로 온 문자메시지를 처리하려고 한다.
    문자 메시지 처리는 상당히 개인적 -> 보안처리
    - AndroidManifest.xml 파이렝 기본보안에 대한 설정 필요
    <uses-permission> 등록

    2. Broadcast Receiver Component 를 하나 생성
    코드상에서 생성하는게 아니라 AndroidManifest.xml 에 등록해서 생성
    외부 Component 로 바로 생성 되기 때문에 AndroidManifest 에 자동등록!
    => 생성 된 후 intent-filter 를 이용해서 어떤 broadcast signal 을 수신할지를 설정해야 한다.
    SMS 문자가 오면 이 문자를 받아서 Activity 에게 전달할꺼에요.

    Broadcast Receiver 에서 Activity 로 데이터가 전달되고..
    Intent를 통해서 데이터가 전달되요!!
    -> activity 는 method 를 이용해서 Intent를 받아요!
    -> onNewIntent() 라는 메소드로 받게된다.

    3. Activity 가 실행되면 보안설정부터 해야해요!
    마쉬멜로우 버전이후 부턴즌 강화된 보안정책을 따라야 해요!
    보안 처리코드가 나와야한다.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example20__brssms);

        senderTv = findViewById(R.id.sender_tv);
        msgTv = findViewById(R.id.phone_tv);
        dateTv = findViewById(R.id.date_tv);


        // 1. 사용자의 단말기 OS(안드로이드 버전) 이 마쉬멜로우 버전 이전이냐 이후 인지를 check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 만약 우리가 사용하는 기기가 M 이상이면
            // 사용자 권한 중 SMS 받기 권한이 설정되어 있는지를 check!!
            int permissionResult = ActivityCompat.checkSelfPermission(
                    getApplicationContext(),
                    Manifest.permission.RECEIVE_SMS
            );

            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                // 권한이 없으면
                // 왜 권한이 없을까?
                // 1. 앱을 처음 실행해서 아예 물어본적이 없는 경우.
                // 2. 권한 허용에 대해서 사용자에게 물어는 봤지만 사용자가 거절을 선택한 경우
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.RECEIVE_SMS
                )) {
                    // true => 권한을 거부한적이 없는 경우.
                    // 일반적으로 dialog 같은걸 이용해서 다시 물어보기
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Example20_BRSMSActivity.this);
                    dialog.setTitle("권한이 필요해요");
                    dialog.setMessage("SMS 수신기능이 필요합니다. 수락할까요?");
                    dialog.setPositiveButton("네!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 100);
                        }
                    });
                    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 권한 설정을 하지 않는다는 의미
                            // 아무런 작업도 할 필요가 없다.
                        }
                    });

                    dialog.create().show();

                } else {
                    // false => 한번도 물어본적이 없는 경우.
                    // 사용자한테 허용할까요 묻는 함수 => requestPermissions()
                    // 여러 권한을 동시에 사용자에게 요청할 수 있끼 때문에 인자가 String 배열로 들어가요!
                    requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 100);
                }
            } else {
                // 권한이 있으면
                Log.i("SMSTest", "보안설정 통과!");
            }

        } else {
            // 만약 우리가 사용하는 기가 M 미만이면
            Log.i("SMSTest", "보안설정 통과!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 사용자가 권한을 설정하게 되면 이부분이 마지막으로 호출되게 되요!!
        // 사용자가 권한 설정을 하거나 그렇지 않거나 두가지 경우 모두 이 callBack 함수가 호출되요!
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 권한 허용을 눌렀을 경우.
                Log.i("SMSTest", "보안설정 통과!!");
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Broadcast receiver 가 보내준 intent 를 이놈이 받아요!
        // intent 안에 들어있는 정보를 꺼내서 화면에 출력!
        String sender = intent.getExtras().getString("sender");
        String msg = intent.getExtras().getString("msg");
        String reDate = intent.getExtras().getString("date");

        senderTv.setText(sender);
        msgTv.setText(msg);
        dateTv.setText(reDate);
    }
}
