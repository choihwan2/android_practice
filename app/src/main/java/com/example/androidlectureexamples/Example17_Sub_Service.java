package com.example.androidlectureexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Example17_Sub_Service extends Service {
    public Example17_Sub_Service() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getExtras().getString("DATA");

        String resultData = data + "를 받았어요!";
        Intent resultIntent = new Intent(getApplicationContext(),
                Example17_ServiceDataActivity.class);

        // 결과값을 intent 에 부착!
        resultIntent.putExtra("RESULT",resultData);

        // Service 에서 Activity 를 호출하려고 해요!
        // 화면이 없는 Service 에서 화면을 가지고 있는 Activity 를 호출하려고 한다.
        // 그렇다면 Task 라는게 필요 => resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Activity 를 새로 생성하는게 아니라 메모리에 존재하는 이전 Activity 를 찾아서 실행 => 플래그 2개 추가
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(resultIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
