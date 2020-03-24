package com.example.androidlectureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example12_SearchBookActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example12_search_book);
        listView = findViewById(R.id.list_search_book);
        Button button = findViewById(R.id.btn_search_book);
        editText = findViewById(R.id.edit_search_book);

        //Network 연결(Web Application 호출 )을 해야 하기떄문에
        // UI Thread 에서 이 작업을 하면 안된다! Thread 로 해결해야한다.
        //==> 결국 Thread 와 데이터를 주고받기 위해 Hander 가 필요하다.

        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // Thread 가 보내준 데이터로 ListView를 채우는 ㅗ드
                // Thread 가 보내준 Message에서 Bundle 을 꺼내요!
                Bundle bundle = msg.getData();
                // Bundle에서 key 값을 이용해 데이터를 추출해요!!
//                 String[] bookList = (String[]) bundle.get("BOOKLIST");
                final String[] bookList = bundle.getStringArray("BOOKLIST");

                // ListView 사용은 Spinner 사용과 비슷.
                // ListView 에 데이터를 설정하려면 Adapter 가 있어야 해요!
                // Adapter 에 데이터를 설정해서 이 Adpater 를 ListView에 붙여요!
                ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,bookList);
                // ListView 에 위에서 생성한 adapter 를 붙인다
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.putExtra("bookTitle",bookList[position]);
                        ComponentName cName = new ComponentName(MainActivity.pkg, MainActivity.pkg + ".BookDetailActivity");
                        intent.setComponent(cName);
                        startActivity(intent);
                    }
                });

            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookSearchRunnable bookSearchRunnable = new BookSearchRunnable(handler,editText.getText().toString());
                Thread thread = new Thread(bookSearchRunnable);
                thread.start();
            }
        });
    }
}

// Thread 를 생성하기 위한 Runnable interface 를 구현한 class 정의

class BookSearchRunnable implements Runnable{
    private Handler handler;
    private String keyword;

    public BookSearchRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    public BookSearchRunnable() { }

    @Override
    public void run() {
        // Web Aplication을 호출
        // 결과를 받아와서 그결과를 예쁘게 만든 후 handler 를 통해서 activity dprp wjsekf
        // 1. 접속할 URL 을 준비
        String url = "http://70.12.60.110:8080/bookSearch/searchTitle?keyword=" + keyword;

        // 2. Java Network 기능은 무조건 Exception 처리를 해야한다.
        try{
            // 3. URL 객체를 생성해야 해요! ( Java의 URL class 를 이용해서)
            URL obj = new URL(url); // URL class 안에은 해당 객체에 접속할수 있는 기능을 갖고있다.
            // 4. URL 객체를 이용해서 접속을 시도해요!
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // 5. Web Apllication의 호출방식을 설정해요!! (GET, POST)
            con.setRequestMethod("GET");
            // 6. 정상적으로 접속이 되었는지를 확인!!
            //  HTTP protocol 로 접속할 때 결과에 대한 상태값.
            // 200 : 접속성공~, 404: Not found , 500 : internal server error, 403 : forbidden,
            int responseCode = con.getResponseCode();
            Log.i("BookSearch", "서버로부터 전달된 code : " + responseCode);
            // Android app 은 특정 기능을 사용하기 위해서 보안 설정을 해줘야한다.
            // android 9(pie) 버전부터는 보안이 강화되었다!
            // 웹 프로토콜에 대한 기본 protocol 이 HTTP 에서 HTTPS 로 변경.
            // 따라서 http protocol 을 이용하는 경우 특수한 설정이 AndroidManifest.xml 파일에 있어야 해요!

            // 8. 서버와의 연결객체를 이용해서 서버와의 데이터 통로를 하나 열어야 해요!
            // Java Stream 이 연결통로를 이용해서 데이터를 읽어들일 수 있어요!
            // 이 통로를 하나 생성해야 해요!!
            // 기본적인 연결 통로를 이용해서 조금 더 효율적인 연결 통로로 다시 만든다
            // InputStreamReader => BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            //서버가 보내주는 데이터를 읽어서 하나의 문자열로 만어요!
            String readLine = "";
            StringBuffer responseText = new StringBuffer();
            while ((readLine = br.readLine()) != null){
                responseText.append(readLine);
            }
            br.close(); // 통로 사용이 끝났으니 해당 resource 자원을 해제

            // 서버로부터 얻어온 문자열을 Log로 출력해보아요!

            Log.i("BookSearch", "얻어온 내용은 : " + responseText.toString());
            // ["Head First Java: 뇌 회로를 자극하는 자바 학습법(개정판)","뇌를 자극..."]
            // 가져온 데이터(문자열)을 자료구조화 시켜서 안드로이드 앱에 적용하는 방법을 생각해 보아요!
            // 우리가 받은 데이터도 Json 형식이에요!
            // 받은 json 을 java 의 자료구조로 변경해야 해요!!
            // JSON parsing library 를 가져다가 좀 편하고 쉽게 JSON 을 handling
            // 가장 대표적인 JSON 처리 library 중 하나인 JACKSON 을 이용

            ObjectMapper mapper = new ObjectMapper();
            String[] resultArr = mapper.readValue(responseText.toString(),String[].class); // String[] 형태로 responseText 를 변환한다!
            // 얻어온 Json 문자열 데이터를 Java 의 String array 로 반환했어요!

            // 최종 결과 데이터를 Activity 에게 전달해야 해요! (UI thread 에게 전달해여 화면제어!

            // 데이터를 Activity 에게 전달하기 위해서
            // 1. Bundle 에 전달할 데이터를 붙이기!
            Bundle bundle = new Bundle();
            bundle.putStringArray("BOOKLIST", resultArr);
            // 2. Message 를 만들어서 Bundle 을 Message 에 부착
            Message message = new Message();
            message.setData(bundle);
            // 3. Handler 를 이용해서 Message 를 Activity 에게 전달.
            handler.sendMessage(message);

        }catch (Exception e){
            Log.e("Booksearch",e.toString());
        }

    }
}



