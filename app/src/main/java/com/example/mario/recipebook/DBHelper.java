package com.example.mario.recipebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mario on 2016/12/1.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        Log.d("g54mdp", "DBHelper");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("g54mdp", "onCreate DB");

        db.execSQL("CREATE TABLE recipe("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "title VARCHAR(128) NOT NULL ,"+
                "introduction VARCHAR(128) NOT NULL"+
        ");");

        db.execSQL("INSERT INTO recipe(title,introduction) VALUES ('fanqiechaodan','Put in potato and egg')");

   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe");
        onCreate(db);
    }
}
