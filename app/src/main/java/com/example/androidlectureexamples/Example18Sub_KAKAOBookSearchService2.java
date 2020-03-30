package com.example.androidlectureexamples;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Example18Sub_KAKAOBookSearchService2 extends Service {
    public Example18Sub_KAKAOBookSearchService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Activity 로 부터 전달된 intent 를 이용해서 keyword 를 얻어내요!
        String keyword = intent.getExtras().getString("KEYWORD");

        KakaoBookSearchRunnable2 runnable = new KakaoBookSearchRunnable2(getApplicationContext(), keyword);
        Thread t = new Thread(runnable);
        t.start();

        // Network 연결을 통해 Open API 를 호출하는 시간이 걸리는 작업을 수행
        // Thread 를 이용해서 처리!
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

class KakaoBookSearchRunnable2 implements Runnable {
    String keyword;
    Context context;

    KakaoBookSearchRunnable2(Context context, String keyword) {
        this.keyword = keyword;
        this.context = context;
    }

    @Override
    public void run() {
        // Network 접속을 통해 Open API를 호출
        String url = "https://dapi.kakao.com/v3/search/book?target=title";
        url += "&query=" + keyword;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + context.getApplicationContext().getString(R.string.kakao_key));

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer sb = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                Map<String, Object> mapValue = mapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>() {
                });
//                Log.i("Service", mapValue.get("documents").toString());
//                [{authors=[Paul Deitel, Harvey Deitel], contents=, datetime=2018-03-28T00:00:00.000+09:00, isbn=1292...
                String jsonString = mapper.writeValueAsString(mapValue.get("documents"));
                KakaoBookVO[] bookList = mapper.readValue(jsonString, KakaoBookVO[].class);
                for (KakaoBookVO book : bookList) {
                    Log.i("Service", book.getTitle());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
