package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Example21_BRNotificationActivity extends AppCompatActivity {

    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example21_brnoti);

        Button register_btn = findViewById(R.id._21_registerNotiBtn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Broadcast Receiver 를 등록하는 작업을 진행
                // 1. IntentFilter 를 먼저 만들어요 (Action 을 설정)
                IntentFilter filter = new IntentFilter();
                filter.addAction("MY_NOTI_SIGNAL");

                // 2. Broadcast 를 받는 Receiver 를 생성
                br = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        //Broadcast 를 받으면 이 method가 호출!!
                        //Notification 을 띄워보아요!1

                        // 1. Notification(알림)을 띄울려면
                        // Notification Manager 가 있어야 해요!
                        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                        // 2. Android Oreo(8버전) 이후에 따라서 약간의 코드차이가 발생한다.
                        // Oreo 버전 이후에는 Channel 이라는걸 이용해야 해요!
                        // Channel 이 나온 이유는.. 알림의 종류가 많아요!1
                        // 별로 중요하지 않은 알림인 경우 단순 소리만 나게끔 처리
                        // 중요한 알림인 경우 진동까지 같이 나게끔 처리한다.
                        // Channel 은 어떠한 형식으로 알림을 가게해줄껀지 설정을 해주는 것이다.

                        String channelId = "MY_CHANNEL";
                        String channelName = "MY_CHANNEL_NAME";
                        int importance = NotificationManager.IMPORTANCE_HIGH;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            // 현재 사용하는 Android 버전이 Oreo 이상일때
                            // 반드시 Channel 을 만들어서 사용!!
                            NotificationChannel nChannel = new NotificationChannel(channelId,
                                    channelName, importance);

                            // 이렇게 알림 채널을 만든 후에 설정을 붙여요!
                            nChannel.enableVibration(true);
                            nChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
                            nChannel.enableLights(true);
                            // 잠긴 화면에서도 Notification 이 동작하도록 설정
                            nChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

                            nManager.createNotificationChannel(nChannel);
                        }

                        // Notification 을 만들어요! 만들기 위해서 Builder 가 필요~
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), channelId);

                        // Notification 을 띄우기 위해서 Intent 가 필요하다.
                        Intent nIntent = new Intent(getApplicationContext(), Example21_BRNotificationActivity.class);

                        // Notification 이 Activity 위에 띄워야 하기 때문에 설정 필요
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // TODO: 중복되지 않는 상수값을 얻기위해서 사용
                        int requestId = (int) System.currentTimeMillis();

                        // TODO: PendingIntent 를 생성해서 사용해야 해요!
                        // 위에서 만든 Intent 를 가지고 PendingIntent 를 생성
                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),
                                requestId, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        // builder 를 이용해서 최종적으로 Notification 을 생성
                        builder.setContentTitle("Noti제목");
                        builder.setContentText("Noti내용");

                        // 알림의 기본 사운드, 기본 진동 설정
                        builder.setDefaults(Notification.DEFAULT_ALL);
                        builder.setAutoCancel(true); // 알림 터치시 반응 후 삭제
                        // 알림의 기본음으로 설정
                        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                        builder.setSmallIcon(android.R.drawable.btn_star);
                        builder.setContentIntent(pIntent);

                        // 실세조 Notification 을 띄우는 코드
                        nManager.notify(0,builder.build());

                    }
                };

                //3. 이렇게 만든 Receiver 를 Filter 와 함께 등록!

                registerReceiver(br, filter);
            }
        });

        Button sendSignalBtn = findViewById(R.id._21_sendSignalBtn);
        sendSignalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("MY_NOTI_SIGNAL");
                sendBroadcast(i);
            }
        });
    }
}
