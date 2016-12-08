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

    static final int RECIPES=1;
    static final int RECIPE_ID=2;
    static final String TABLE_NAME="recipeTable";
    static final String DB_NAME="cwrecipeDB";

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MyProviderContract.AUTHORITY,"recipeTable",RECIPES);
        uriMatcher.addURI(MyProviderContract.AUTHORITY,"recipeTable/#",RECIPE_ID);
    }

    @Override
    public boolean onCreate() {
        Log.d("g53mdp","Contentprovider oncreate");
        this.dbHelper=new DBHelper(this.getContext(),DB_NAME,null,7);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        return db.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
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

        long id=db.insert(TABLE_NAME,null,contentValues);
        db.close();

        if(id>0){
            Uri nu= ContentUris.withAppendedId(uri,id);
            Log.d("g53mdp",nu.toString());
            getContext().getContentResolver().notifyChange(nu,null);
            return nu;
        }
        return null;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)){
           /* case RECIPES:
                db.delete(TABLE_NAME,selection,selectionArgs);
                break;*/
            case RECIPE_ID:
                String deleteId=uri.getPathSegments().get(1);
                Log.d("info",deleteId+"");
                db.delete(TABLE_NAME,MyProviderContract._ID+"=?",new String[]{deleteId});
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)){
            case RECIPE_ID:
                String updateId=uri.getPathSegments().get(1);
                db.update(TABLE_NAME,contentValues,MyProviderContract._ID+"=?",new String[]{updateId});
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }
}
