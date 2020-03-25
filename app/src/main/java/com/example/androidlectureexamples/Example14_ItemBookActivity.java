package com.example.androidlectureexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Example14_ItemBookActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example14_item_book);
        button = findViewById(R.id.btn_item_book);
        editText = findViewById(R.id.edit_item_book);
        list = findViewById(R.id.list_item_book);

        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                ArrayList<BookVO> bookList = (ArrayList<BookVO>) bundle.getSerializable("BOOKLIST");
                BookListAdapter adapter = new BookListAdapter();
                adapter.setBookList(bookList);
                list.setAdapter(adapter);
            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemHandlerRunnable itemRunnable = new ItemHandlerRunnable(handler,editText.getText().toString());
                Thread t = new Thread(itemRunnable);
                t.start();
            }
        });
    }
}

class ItemHandlerRunnable implements Runnable {
    Handler handler;
    String keyword;

    public ItemHandlerRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        String url = "http://70.12.60.110:8080/bookSearch/searchDetail?keyword=" + keyword;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                BookVO[] books = mapper.readValue(obj, BookVO[].class);
                ArrayList<BookVO> bookList = new ArrayList<>();
                bookList.addAll(Arrays.asList(books));
                Bundle bundle = new Bundle();
                bundle.putSerializable("BOOKLIST", bookList);

                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
