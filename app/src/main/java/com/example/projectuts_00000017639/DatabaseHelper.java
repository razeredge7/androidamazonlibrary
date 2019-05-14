package com.example.projectuts_00000017639;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME="books.db";

    private static String DB_PATH;

    private static final int DB_VERSION = 1;

    private SQLiteDatabase myDatabase;

    private final Context myContext;

    private boolean myNeedUpdate = false;


    public DatabaseHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(DB_NAME).toString();

        openDatabase();



    }

    public void updateDatabase() throws IOException {
        if (myNeedUpdate) {
            File dbFile = new File(DB_PATH);
            if (dbFile.exists())
                dbFile.delete();

            copyDatabase();

            myNeedUpdate = false;
        }
    }

    private boolean checkDatabase() {
        return myContext.getDatabasePath(DB_NAME).exists();
    }

    public void copyDatabase() {
        if (!checkDatabase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException myIOException) {
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        byte[] myBuffer = new byte[myInput.available()];
        myInput.read(myBuffer);
        myOutput.write(myBuffer);
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //private void readDatabase()

    public void openDatabase() throws SQLException {
        if(myDatabase==null){
            copyDatabase();
            myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

   /* public void createDatabase(){
        boolean isDBExist = checkDatabase();
        if(!isDBExist){
            this.getReadableDatabase();
            copyDatabase();
        }

    }*/

   public void setFavorites(){

   }


    @Override
    public synchronized void close() {
        if (myDatabase != null)
            myDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            myNeedUpdate = true;
    }


    public List<books> getFavoriteBooks(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + "books" + " WHERE " + "FAVORITE" + " = 1";
        Cursor c;
        try{
            c = db.rawQuery(select,new String[]{});
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public List<books> getAllBooks(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,null,null,null,null,null, "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                       c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public void updateCurrentDB(ContentValues conva, String asin){
        SQLiteDatabase tempupdate = this.getWritableDatabase();
        tempupdate.update("books",conva,"ASIN = ?", new String[] {asin});
    }


    public List<books> sortAuthor(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,null,null,null,null,"AUTHOR", "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public List<books> sortAuthorDesc(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,null,null,null,null,"AUTHOR DESC" , "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public List<books> sortTitle(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,null,null,null,null,"TITLE", "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public List<books> sortTitleDesc(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,null,null,null,null,"TITLE DESC", "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public List<books> sortPublisher(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,null,null,null,null,"PUBLISHER", "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public List<books> sortPublisherDesc(){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,null,null,null,null,"PUBLISHER DESC", "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public List<books> searchForBooks(String tempsearch){
        List<books> temp = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books",null,"TITLE LIKE ?",new String[]{"%"+tempsearch+"%"},null,null,null, "50");
            if(c==null) return null;

            c.moveToFirst();
            do{
                books book = new books(
                        c.getString(c.getColumnIndex("ASIN")),
                        c.getString(c.getColumnIndex("GROUP")),
                        c.getString(c.getColumnIndex("FORMAT")),
                        c.getString(c.getColumnIndex("TITLE")),
                        c.getString(c.getColumnIndex("AUTHOR")),
                        c.getString(c.getColumnIndex("PUBLISHER")),
                        c.getInt(c.getColumnIndex("FAVORITE"))
                );
                temp.add(book);
            }while(c.moveToNext());
            c.close();
        }
        catch(Exception e){
            Log.d(TAG, "Failed to get data");
        }
        return temp;
    }

    public books retrieveSingleBook(String asin){
        books retrieveBook = new books();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try{
            c = db.query("books", null,"ASIN=?", new String[]{asin},null,null,null
            ,null);
            if(c==null){
                return null;
            }
            c.moveToFirst();
            Log.e("asdas", "tes6: "+asin);
            books book = new books(
                    c.getString(c.getColumnIndex("ASIN")),
                    c.getString(c.getColumnIndex("GROUP")),
                    c.getString(c.getColumnIndex("FORMAT")),
                    c.getString(c.getColumnIndex("TITLE")),
                    c.getString(c.getColumnIndex("AUTHOR")),
                    c.getString(c.getColumnIndex("PUBLISHER")),
                    c.getInt(c.getColumnIndex("FAVORITE"))
            );
            retrieveBook = book;
            Log.e("asdas", "tes7: "+asin);
            Log.e("asdas", "tes5: "+retrieveBook.getAsin()+" "+retrieveBook.getFavorites());
            c.close();
        }
        catch (Exception e){
            Log.d(TAG, "Failed to get data");
        }

        return retrieveBook;
    }


}

