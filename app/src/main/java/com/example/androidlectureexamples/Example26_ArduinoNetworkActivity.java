package com.example.androidlectureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

class SharedMsg {

    private final static SharedMsg instance = new SharedMsg();
    private Queue<String> msg_q = new LinkedList<String>();
    private final static Object MONITER = new Object();

    private SharedMsg() {

    }

    void addMsg(String msg) {
        synchronized (MONITER) {
            msg_q.add(msg);
            MONITER.notify();
        }
    }

    String popMsg() {
        String msg = null;
        synchronized (MONITER) {
            while (msg_q.isEmpty()) {
                try {
                    MONITER.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            msg = msg_q.poll();
        }
        return msg;
    }

    static SharedMsg getInstance() {
        return instance;
    }
}

public class Example26_ArduinoNetworkActivity extends AppCompatActivity {
    ArduinoRunnable runnable;
    SharedMsg sharedMsg;
    TextView statusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example26_arduino_network);

        sharedMsg = SharedMsg.getInstance();
        statusTv = findViewById(R.id.arduino_led_tv);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                String lightStatus = bundle.getString("data");
                StringBuilder stb = new StringBuilder();
                stb.append("LED 상태는 : ");
                assert lightStatus != null;
                if (lightStatus.equals("ON")) {
                    stb.append(lightStatus);
                } else if (lightStatus.equals("OFF")) {
                    stb.append(lightStatus);
                }
                statusTv.setText(stb.toString());
            }
        };
        Button onBtn = findViewById(R.id.btn_led_on);
        Button offBtn = findViewById(R.id.btn_led_off);
        runnable = new ArduinoRunnable(sharedMsg, handler);
        Thread t = new Thread(runnable);

        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedMsg.addMsg("ON");
                Log.d("ON", "ON버튼이 눌렸습니다.");
            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedMsg.addMsg("OFF");
            }
        });

        t.start();
    }
}

class ArduinoRunnable implements Runnable {

    private BufferedReader br;
    private SharedMsg sharedObj;
    private Handler handler;

    ArduinoRunnable(SharedMsg msg, Handler handler) {
        this.sharedObj = msg;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket("70.12.60.110", 3131);
            Log.d("Thread", "서버와 연결 성공!");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String revString = "";
                        while ((revString = br.readLine()) != null) {

                            Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", revString);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            while (true) {
                String msg = sharedObj.popMsg();
                Log.i("RunnableThread", msg);
                pw.println(msg);
                pw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
