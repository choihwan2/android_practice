package com.example.androidlectureexamples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

/*
     보안설정이 잘 되어 있으면
     특정 signal(Broadcast 가 전파되면) 이 발생하면
     해당 Broadcast 를 받을 수 있어요!

 */
public class Example20Sub_SMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // Broadcast 를 받으면 이 method 가 호출되요!!
        // SMS 가 도착하면 해당 method 가 호출되요!

        Log.i("SMSTest","SMS가 도착했어요!");
        // 만약 SMS signal 을 받을 수 있으면
        // 전화번호, 문자내용을 뽑아서 Activity 에게 전달하면 되요!!
        // 전달 받은 Intent 안에 보낸 사람의 전화번호, 메시지 내용, 날짜... 등 정보가 들어가있어요!

        // Intent 안에 포함되어 있는 정보를 추출해요!
        Bundle bundle = intent.getExtras();

        // Bundle 안에 key, value 형태로 데이터가 여러개 저장되어 있는데
        // SMS 의 정보는 "puds" 라는 키값으로 저장되어 있어요!
        // 거의 시간상 동시에 여러개의 SMS 가 도착할 수 있다.
        // 객체 1개가 메시지 하나라고 보면 된다.
        Object[] obj = (Object[]) bundle.get("pdus");

        // SmsMessage 라는 객체로 만들어줌
        SmsMessage[] message = new SmsMessage[obj.length];

        // 우리 예제에서는 1개의 SMS 만 전달된다고 가정하고 진행한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            // obj 에 들어가 있는 Object 클래스의 객체를 SmsMessage 객체로 변환!
            message[0] = SmsMessage.createFromPdu((byte[]) obj[0], format);
        } else{
            message[0] = SmsMessage.createFromPdu((byte[])obj[0]);
        }

        // 보낸 사람 전화번호를 SmsMessage 객체에서 추출
        String sender = message[0].getOriginatingAddress();
        // SMS 문자 내용을 추출
        String msg = message[0].getMessageBody();
        // SMS 받은 시간을 추출
        String reDate = new Date(message[0].getTimestampMillis()).toString();

        Log.i("SMSTest", "전화번호 : " + sender);
        Log.i("SMSTest", "보낸 내용 : " + msg);
        Log.i("SMSTest", "보낸 시간 : " + reDate);

        Intent i = new Intent(context,Example20_BRSMSActivity.class);

        // 기존에 이미 생성되어 있는 activity 에게 전달해야 하므로 flag 를 설정
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.putExtra("sender",sender);
        i.putExtra("msg",msg);
        i.putExtra("date",reDate);

        context.startActivity(i);

    }
}
