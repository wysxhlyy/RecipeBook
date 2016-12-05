package com.example.mario.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;
    private Button add;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] projection=new String[]{
                MyProviderContract._ID,
                MyProviderContract.TITLE,
        };

        String[] columnsToDisplay=new String[]{
                MyProviderContract._ID,
                MyProviderContract.TITLE,
        };

        int[] colResIds=new int[]{
                R.id.value1,
                R.id.value2,
        };

        Cursor cursor=getContentResolver().query(MyProviderContract.RECIPE_URI,projection,null,null,null);

        dataAdapter=new SimpleCursorAdapter(
                this,
                R.layout.db_item_layout,
                cursor,
                columnsToDisplay,
                colResIds,
                0
        );

        listView=(ListView)findViewById(R.id.recipes);
        listView.setAdapter(dataAdapter);

        chooseRecipe();

        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateRecipe.class);
                startActivity(intent);
            }
        });
    }


    public void chooseRecipe(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int myItemInt, long l) {
                Bundle bundle=new Bundle();
                bundle.putInt("chosenRecipe",myItemInt);
                Intent intent=new Intent(MainActivity.this,DisplayRecipe.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
