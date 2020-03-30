package com.example.androidlectureexamples;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

public class Example18Sub_KAKAOBookSearchService extends Service {
    public Example18Sub_KAKAOBookSearchService() {
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

        KakaoBookSearchRunnable runnable = new KakaoBookSearchRunnable(getApplicationContext(),keyword);
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

class KakaoBookSearchRunnable implements Runnable {
    String keyword;
    Context context;

    KakaoBookSearchRunnable(Context context, String keyword) {
        this.keyword = keyword;
        this.context = context;
    }

    @Override
    public void run() {
        // Network 접속을 통해 Open API를 호출
        String url = "https://dapi.kakao.com/v3/search/book?target=title";
        url += "&query=" + keyword;
        try {
            // 1. Http 접속을 하기 위해 URL 객체를 생성
            URL obj = new URL(url);
            // 2. URL Connection 을 연다.
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // 3. 접속을 하기 전에 여러가지 설정이 들어가야한다.
            // 대표적인 설정이 호출방식(GET,POST), 인증에 대한 처리
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + context.getApplicationContext().getString(R.string.kakao_key));
            // 일단 접속 성공 (정상적으로 처리가 되면)
            // 접속이 성공하면 결과 데이터를 JSON으로 보내주게 되고
            // 해당 데이터를 읽어와야 해요!
            // 데이터 연결통로(Stream) 을 열어서 데이터를 읽어와야한다.
            // 데이터 통로는 기본적인 Stream 을 먼저 얻고 이를 이용해서
            // 조금더 사용하기 편하기 통로로 변경해서 사용
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer sb = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                // 데이터가 JSON 으로 정상적으로 온걸 확인할 수 있어요!
                // documents : [ {}, {}, {} ]
                // JSON 을 처리해서 documents 라고 되어있는 key 값에 대해
                // value 값을 객체화 해서 가지고 올거에요!!
                // JACKSON library 을 이용해서 처리
                Log.i("BOOK","하하");
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(sb.toString(),
                        new TypeReference<Map<String, Object>>() {
                        });
                Object jsonObject = map.get("documents");
                // jsonObject = [ {}, {}, {} ]

                // 위에서 받은 객체를 다시 JSON 문자열로 변환
                String jsonString = mapper.writeValueAsString(jsonObject);
//                Log.i("KAKAOBOOK",jsonString);
                // jsonString => " [{책1권}, {책1권}, ...]
                // ArrayList 안에 책 객체가 들어있는 형태로 만들어야해요~!
                ArrayList<KakaoBookVO> bookList = mapper.readValue(jsonString, new TypeReference<ArrayList<KakaoBookVO>>() {});

                ArrayList<String> resultData = new ArrayList<>();

                for (KakaoBookVO book : bookList) {
                    resultData.add(book.getTitle());
                }

                Intent intent = new Intent(context.getApplicationContext(), Example18_KakaoBookSearchActivity.class);
                intent.putExtra("BOOKRESULT",resultData);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
