package com.example.androidlectureexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class Example15Sub_LifeCycleService extends Service {
    Thread thread;
    ArrayList<Thread> list = new ArrayList<>();
    public Example15Sub_LifeCycleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Service 객체가 생성될 때 호출
        Log.i("ServiceExam", "onCreate() 호출");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 실제 서비스 동작을 수행하는 부분
        // onCreate() -> onStartCommand()

        // Service 가 하는 일은 1초 간격으로 Log 찍기
        Log.i("ServiceExam", "onStartCommand() 호출");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<100; i++){
                    try{
                        Thread.sleep(1000);
                        // sleep 을 하려고 할때 만약 interrupt 가 걸려있음녀 Exception 이 발생!
                        Log.i("Thread","i: " + i);
                    }catch (Exception e){
                        Log.e("Thread", e.toString());
                        break;
                    }
                }
            }
        });
        list.add(thread);
        thread.start();
        // Thread 가 가지고 있는 run() 이라는 메소드가 호출된다.
        // 언젠가는 run() 이라는 메소드의 실행이 끝난다.
        // Thread 의 상태가 DEAD상태가 된다 => 이 DEAD 상태에서 다시 실행을 시킬수 있는 방법이 없다.
        // 유일하게 다시 실행시키는 방법은 Thread를 다시 생성해서 실행시키는거다.
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // stopService() 가 호출되면 onDestroy() 가 호출되요!
        // 현재 Service 에 의해서 동작하고 있는 모든 Thread 를 종료! 그걸 우리가 해야한다. 코드를 짜보자
//        if(thread != null && thread.isAlive()){
//            // thread 가 존재하고 현재 thread 가 실행 중이면, thread 를 중지시키자.
////            thread.stop(); // 과거 코드
//            thread.interrupt(); // 상황이 되면 종료될 체크해준다고 생각하면됨.
//        }
        if(!list.isEmpty()){
            for (Thread thread : list){
                thread.interrupt();
            }
        }


        // Service 객체가 종료될때.
        Log.i("ServiceExam", "OnDestroy() 호출");
        super.onDestroy();
    }
}
