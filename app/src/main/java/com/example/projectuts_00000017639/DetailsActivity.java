package com.example.projectuts_00000017639;

import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    List<books> booksList = new ArrayList<>();
    DatabaseHelper myDBHelper;
    LinearLayout containerdata;
    books Book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setTitle("Details View");
        Log.e(this.getClass().toString(), "onCreate menuActvity");
        myDBHelper = new DatabaseHelper(getApplicationContext());
        myDBHelper.copyDatabase();
        containerdata = findViewById(R.id.idcontainer);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addView = inflater.inflate(R.layout.datadetails, null);

        TextView title = addView.findViewById(R.id.judul);
        TextView author = addView.findViewById(R.id.penulis);
        TextView publisher = addView.findViewById(R.id.penerbit);
        TextView booktype = addView.findViewById(R.id.tipebuku);
        TextView group = addView.findViewById(R.id.grup);
        TextView asin = addView.findViewById(R.id.Asin);
        final FloatingActionButton floatbutton = findViewById(R.id.floatingfavorite);
        final int Favorite;

        Bundle bundle = getIntent().getExtras();
        title.setText(bundle.getString("title"));
        author.setText(bundle.getString("author"));
        publisher.setText(bundle.getString("publisher"));
        booktype.setText(bundle.getString("format"));
        group.setText(bundle.getString("group"));
        asin.setText(bundle.getString("asin"));
        Favorite = bundle.getInt("favorite");
        Log.e("asdas", "tes4: "+bundle.getString("asin"));
        Book = myDBHelper.retrieveSingleBook(bundle.getString("asin"));
        Log.e("asdas", "tes3: "+Book.getAsin());
        if (Book.getFavorites() == 0) {
            floatbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favoritebeforeclick));
        } else {
            floatbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
        }

        containerdata.addView(addView);

        floatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues conva = new ContentValues();
                if (Book.getFavorites() == 0) {
                    floatbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
                    Log.e("asdas", "tes1: "+Book.getFavorites());
                    Book.setFavorites(1);
                    conva.put("FAVORITE", 1);
                    Log.e("asdas", "tes2: "+Book.getFavorites());
                    Toast.makeText(DetailsActivity.this, "This book has been added to my Favorites.", Toast.LENGTH_SHORT).show();
                } else {
                    floatbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favoritebeforeclick));
                    Book.setFavorites(0);
                    conva.put("FAVORITE", 0);
                    Toast.makeText(DetailsActivity.this, "This book has been removed from my Favorites.", Toast.LENGTH_SHORT).show();
                }
                myDBHelper.updateCurrentDB(conva, Book.getAsin());
            }
        });

    }
}
