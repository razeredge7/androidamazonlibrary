package com.example.projectuts_00000017639;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    List<books> booksList = new ArrayList<>();
    DatabaseHelper myDBHelper;
    LinearLayout containerdata;
    ImageButton btnFavorite;
    EditText searchbar;
    Button searchbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("KetanMatcha Library");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Log.e(this.getClass().toString(), "onCreate menuActvity");
        myDBHelper = new DatabaseHelper(getApplicationContext());
        myDBHelper.copyDatabase();
        //Log.e(this.getClass().toString(), "copy database menuActivity");
        booksList = myDBHelper.getAllBooks();
        //Log.e(this.getClass().toString(), "getAllBooks menuActvity");
        containerdata = findViewById(R.id.idcontainer);

        searchbar = findViewById(R.id.searchquery);
        searchbutton = findViewById(R.id.searchbook);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempquery = searchbar.getText().toString();
                booksList = myDBHelper.searchForBooks(tempquery);
                showData();

            }
        });
        showData();
    }

    @Override
    protected void onResume() {
        booksList = myDBHelper.getAllBooks();
        showData();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.authorasc:
                booksList = myDBHelper.sortAuthor();
                showData();
            return true;

            case R.id.authordesc:
                booksList = myDBHelper.sortAuthorDesc();
                showData();
                return true;

            case R.id.titleasc:
                booksList = myDBHelper.sortTitle();
                showData();
                return true;

            case R.id.titledesc:
                booksList = myDBHelper.sortTitleDesc();
                showData();
                return true;

            case R.id.publisherasc:
                booksList = myDBHelper.sortPublisher();
                showData();
                return true;

            case R.id.publisherdesc:
                booksList= myDBHelper.sortPublisherDesc();
                showData();
                return true;

            case R.id.aboutme:
            Toast.makeText(this, "Redirecting...", Toast.LENGTH_SHORT).show();
            openAboutMeActivity();
            return true;

            case R.id.myfavorites:
            Toast.makeText(this, "Redirecting...", Toast.LENGTH_SHORT).show();
            openmyfavoriteActivity();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void openAboutMeActivity(){
        Intent intent = new Intent(this, AboutMe.class);
        startActivity(intent);
    }

    public void openmyfavoriteActivity(){
        Intent intent = new Intent(this, MyFavorites.class);
        startActivity(intent);
    }

    public void opendetailsActivity(){
        Intent intent =new Intent(MenuActivity.this, DetailsActivity.class);
        startActivity(intent);
    }

    public void showData(){
        if(containerdata.getChildCount()>0){
            containerdata.removeAllViews();
        }
        for(final books counter : booksList){
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView = inflater.inflate(R.layout.data,null);
            TextView title = addView.findViewById(R.id.judul);
            TextView author =addView.findViewById(R.id.penulis);
            TextView publisher=addView.findViewById(R.id.penerbit);
            Button details = addView.findViewById(R.id.detailsbuton);

            title.setText(counter.getTitle());
            author.setText(counter.getAuthor());
            publisher.setText(counter.getPublisher());

            final books object = counter;
            final ContentValues conva = new ContentValues();
            btnFavorite = addView.findViewById(R.id.buttonfavorite);

            final ImageButton buttonFavoriteclick = btnFavorite;
            buttonFavoriteclick.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(object.getFavorites()==0){
                        buttonFavoriteclick.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
                        object.setFavorites(1);
                        conva.put("FAVORITE", 1);
                        Toast.makeText(MenuActivity.this, "This book has been added to my Favorites.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        buttonFavoriteclick.setImageDrawable(getResources().getDrawable(R.drawable.ic_favoritebeforeclick));
                        object.setFavorites(0);
                        conva.put("FAVORITE", 0);
                        Toast.makeText(MenuActivity.this, "This book has been removed from my Favorites.", Toast.LENGTH_SHORT).show();

                    }
                    myDBHelper.updateCurrentDB(conva, object.getAsin());
                }


            });

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this ,DetailsActivity.class);
                    intent.putExtra("title", counter.getTitle());
                    intent.putExtra("asin", counter.getAsin());
                    intent.putExtra("group", counter.getGroup());
                    intent.putExtra("format", counter.getFormat());
                    intent.putExtra("author", counter.getAuthor());
                    intent.putExtra("publisher", counter.getPublisher());
                    intent.putExtra("favorite", counter.getFavorites());
                    startActivity(intent);

                }
            });


            if(counter.getFavorites()==0){
                buttonFavoriteclick.setImageDrawable(getResources().getDrawable(R.drawable.ic_favoritebeforeclick));
            }else {
                buttonFavoriteclick.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
            }


            containerdata.addView(addView);
        }


    }
}
