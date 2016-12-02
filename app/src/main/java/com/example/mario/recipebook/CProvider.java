package com.example.mario.recipebook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mario on 2016/12/1.
 */

public class CProvider extends ContentProvider {

    private DBHelper dbHelper;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    }

    @Override
    public boolean onCreate() {
        Log.d("g53mdp","Contentprovider oncreate");
        this.dbHelper=new DBHelper(this.getContext(),"recipeDB",null,7);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        if(uri.getLastPathSegment()==null){
            return "vnd.android.cursor.dir/MyProvider.data.text";
        }else{
            return "vnd.android.cursor.item/MyProvider.data.text";
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String tableName;
        switch (uriMatcher.match(uri)){
            case 1:
                tableName="recipe";
                break;
            default:
                tableName="recipe";
        }

        long id=db.insert(tableName,null,contentValues);
        db.close();
        Uri nu= ContentUris.withAppendedId(uri,id);

        Log.d("g53mdp",nu.toString());
        getContext().getContentResolver().notifyChange(nu,null);

        return nu;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
