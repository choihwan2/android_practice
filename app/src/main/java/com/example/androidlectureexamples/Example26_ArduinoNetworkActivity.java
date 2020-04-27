package com.example.androidlectureexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    private SharedMsg(){

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example26_arduino_network);

        sharedMsg = SharedMsg.getInstance();
        Button onBtn = findViewById(R.id.btn_led_on);
        Button offBtn = findViewById(R.id.btn_led_off);
        runnable = new ArduinoRunnable(sharedMsg);
        Thread t = new Thread(runnable);

        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedMsg.addMsg("ON");
                Log.d("ON","ON버튼이 눌렸습니다.");
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

class ArduinoRunnable implements Runnable{

    private BufferedReader br;
    private PrintWriter pw;
    private SharedMsg sharedObj;

    ArduinoRunnable(SharedMsg msg) {
        this.sharedObj = msg;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket("70.12.60.110", 3131);
            Log.d("Thread" ,"서버와 연결 성공!");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());
            while(true){
                String msg = sharedObj.popMsg();
                pw.println(msg);
                pw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
