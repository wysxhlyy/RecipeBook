package com.example.mario.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;
    private Button add;
    private Button searchButton;
    private ListView listView;
    private AutoCompleteTextView search;
    private String[] recipesInDB;
    private int recipesSize;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialComponent();

        String[] projection=new String[]{
                MyProviderContract._ID,
                MyProviderContract.TITLE,
        };

        String[] columnsToDisplay=new String[]{
                //MyProviderContract._ID,
                MyProviderContract.TITLE,
        };

        int[] colResIds=new int[]{
                R.id.value1,
                //R.id.value2,
        };

        cursor=getContentResolver().query(MyProviderContract.RECIPE_URI,projection,null,null,null);

        dataAdapter=new SimpleCursorAdapter(
                this,
                R.layout.db_item_layout,
                cursor,
                columnsToDisplay,
                colResIds,
                0
        );

        listView.setAdapter(dataAdapter);

        chooseRecipe();
        searchRecipe();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateRecipe.class);
                startActivity(intent);
            }
        });

    }


    public void searchRecipe(){
        recipesSize=cursor.getCount();
        Log.d("g53mdp","recipesSize:"+cursor.getCount());
        recipesInDB=new String[recipesSize];

        int count=0;
        while(cursor.moveToNext()){
            recipesInDB[count] =cursor.getString(cursor.getColumnIndex(MyProviderContract.TITLE));
            Log.d("g53mdp",recipesInDB[count]+count);
            count++;
        }

        for(int i=0;i<recipesSize;i++){
            if(recipesInDB[i]==null){
                recipesInDB[i]=" ";
            }
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recipesInDB);
        search.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DisplayRecipe.class);
                intent.putExtra("recipeTitle",search.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void initialComponent(){
        listView=(ListView)findViewById(R.id.recipes);
        add=(Button)findViewById(R.id.add);
        searchButton=(Button)findViewById(R.id.searchButton);
        search=(AutoCompleteTextView)findViewById(R.id.search);

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
