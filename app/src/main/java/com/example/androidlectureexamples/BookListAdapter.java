package com.example.androidlectureexamples;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookListAdapter extends BaseAdapter {
    private ArrayList<BookVO> bookList = new ArrayList<>();

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public BookVO getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 view 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_search, parent, false);
        }
        final BookVO book = getItem(position);
        TextView bTitle = view.findViewById(R.id.item_title);
        TextView bAuthor = view.findViewById(R.id.item_author);
        TextView bPublisher = view.findViewById(R.id.item_publisher);
        ImageView bImage = view.findViewById(R.id.img_item_search);

        bTitle.setText(book.getBtitle());
        bAuthor.setText(book.getBauthor());
        bPublisher.setText(book.getBpublisher());
        Picasso.get().load(book.getBimgurl()).into(bImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("BISBN",book.getBisbn());
                ComponentName cName = new ComponentName(MainActivity.pkg, MainActivity.pkg + ".BookDetailActivity");
                intent.setComponent(cName);
                context.startActivity(intent);
            }
        });


        return view;
    }

    public ArrayList<BookVO> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<BookVO> bookList) {
        this.bookList = bookList;
    }
}
