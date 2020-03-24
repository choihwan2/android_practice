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

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Example13_BookDetailActivity extends AppCompatActivity {
    EditText edit_search;
    Button btn_search;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example13_book_detail);

        edit_search = findViewById(R.id.edit_detail_book);
        btn_search = findViewById(R.id.btn_detail_book);
        listView = findViewById(R.id.list_detail_book);

        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                final BookVO[] books = (BookVO[])bundle.getSerializable("BOOKLIST");
                ArrayList<String> book_title = bundle.getStringArrayList("BOOKTITLE");

                ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,book_title);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.putExtra("BISBN",books[position].getBisbn());
                        ComponentName cName = new ComponentName(MainActivity.pkg, MainActivity.pkg + ".BookDetailActivity");
                        intent.setComponent(cName);
                        startActivity(intent);
                    }
                });

            }
        };

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailSearchHandler dbsRun = new DetailSearchHandler(handler,edit_search.getText().toString());
                Thread t = new Thread(dbsRun);
                t.start();
            }
        });
    }
}
class DetailSearchHandler implements Runnable{
    Handler handler;
    String searchText;

    public DetailSearchHandler(Handler handler, String searchText) {
        this.handler = handler;
        this.searchText = searchText;
    }

    @Override
    public void run() {
        String url = "http://70.12.60.110:8080/bookSearch/searchDetail?keyword=" + searchText;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == 200){
                ObjectMapper mapper = new ObjectMapper();
                BookVO[] books = mapper.readValue(obj,BookVO[].class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BOOKLIST",books);

                ArrayList<String> book_title = new ArrayList<>();
                for (BookVO book: books) {
                    book_title.add(book.getBtitle());
                }
                bundle.putStringArrayList("BOOKTITLE",book_title);

                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            }

        }catch (Exception e){
            Log.e("Example13_bookdetail",e.toString());
        }
    }
}
