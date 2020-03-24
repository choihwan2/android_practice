package com.example.androidlectureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookDetailActivity extends AppCompatActivity {
    TextView title_tv;
    TextView isbn_tv;
    TextView price_tv;
    TextView publish_tv;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        title_tv = findViewById(R.id.book_title);
        isbn_tv = findViewById(R.id.book_isbn);
        price_tv = findViewById(R.id.book_price);
        publish_tv = findViewById(R.id.book_publish);
        image = findViewById(R.id.image_detail_book);
        Intent i = getIntent();
        String bIsbn = i.getStringExtra("BISBN");


        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                BookVO[] bookVO = (BookVO[]) bundle.getSerializable("book");
                title_tv.setText(bookVO[0].getBtitle());
                isbn_tv.setText(bookVO[0].getBisbn());
                price_tv.setText(String.valueOf(bookVO[0].getBprice()));
                publish_tv.setText(bookVO[0].getBpublisher());
                Picasso.get().load(bookVO[0].getBimgurl()).into(image);
            }
        };


        DetailBookSearchRunnable detailBookSearchRunnable = new DetailBookSearchRunnable(handler,bIsbn);
        Thread thread = new Thread(detailBookSearchRunnable);
        thread.start();
    }
}

class DetailBookSearchRunnable implements  Runnable{
    private Handler handler;
    private String keyword;

    public DetailBookSearchRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;

    }

    @Override
    public void run() {
        String url = "http://70.12.60.110:8080/bookSearch/searchBisbn?keyword=" + keyword;
        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if(responseCode == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String readLine = "";
                StringBuilder responseText = new StringBuilder();
                if((readLine = br.readLine()) != null){
                    responseText.append(readLine);
                }
                br.close();
                ObjectMapper mapper = new ObjectMapper();
                BookVO[] book = mapper.readValue(obj,BookVO[].class);
                Log.i("responseCode",book.toString());
//                String[] bookDetail = mapper.readValue(responseText.toString(),String[].class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);

                Message message = new Message();
                message.setData(bundle);

                handler.sendMessage(message);

            }
        }catch (Exception e){
            Log.e("SearchDetailBook", e.toString());
        }
    }
}
