package com.example.projectuts_00000017639;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyFavorites extends AppCompatActivity {
    List<books> booksList = new ArrayList<>();
    DatabaseHelper myDBHelper;
    LinearLayout containerdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        setTitle("My Favorites");
        myDBHelper = new DatabaseHelper(getApplicationContext());
        myDBHelper.copyDatabase();
        booksList = myDBHelper.getFavoriteBooks();
        Log.e(this.getClass().toString(), "getAllBooks menuActvity");
        containerdata = findViewById(R.id.idcontainer);

        for(books counter : booksList){
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView = inflater.inflate(R.layout.datafavorites,null);
            TextView title = addView.findViewById(R.id.judul);
            TextView author =addView.findViewById(R.id.penulis);
            TextView publisher=addView.findViewById(R.id.penerbit);

            title.setText(counter.getTitle());
            author.setText(counter.getAuthor());
            publisher.setText(counter.getPublisher());

            containerdata.addView(addView);
        }

    }
    }

