package com.example.mario.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] projection=new String[]{
                MyProviderContract._ID,
                MyProviderContract.TITLE,
                MyProviderContract.INTRODUCTION
        };

        String[] colsToDisplay=new String[]{
                MyProviderContract._ID,
                MyProviderContract.TITLE,
                MyProviderContract.INTRODUCTION
        };

        int[] colResIds=new int[]{
                R.id.value1,
                R.id.value2,
                R.id.value3
        };

        Cursor cursor=getContentResolver().query(MyProviderContract.RECIPE_URI,projection,null,null,null);

        dataAdapter=new SimpleCursorAdapter(
                this,
                R.layout.db_item_layout,
                cursor,
                colsToDisplay,
                colResIds,
                0
        );

        ListView listView=(ListView)findViewById(R.id.recipes);
        listView.setAdapter(dataAdapter);

        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateRecipe.class);
                startActivity(intent);
            }
        });
    }
}
