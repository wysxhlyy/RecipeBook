package com.example.mario.recipebook;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;
    private ListView listView;
    private AutoCompleteTextView search;
    private String[] recipesInDB;
    private int recipesSize;
    private Cursor cursor;

    private Button add;
    private Button searchButton;
    private Button deleteAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialComponent();

        String[] projection=new String[]{
                MyProviderContract._ID,
                MyProviderContract.TITLE,
        };

        //Only show the title here
        String[] columnsToDisplay=new String[]{
                MyProviderContract.TITLE,
        };

        int[] colResIds=new int[]{
                R.id.value1,
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

        //Click the button to create a new recipe.
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateRecipe.class);
                startActivity(intent);
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAll();
            }
        });

    }


        /*
        Search the recipe from recipes already created. This function is designed in case that
        there exists too much recipes.
        The edittext will generate the matched exsiting recipe when user input.
        */
    public void searchRecipe(){
        recipesSize=cursor.getCount();
        recipesInDB=new String[recipesSize];

        int count=0;
        while(cursor.moveToNext()){
            recipesInDB[count] =cursor.getString(cursor.getColumnIndex(MyProviderContract.TITLE));
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
                String chosenRecipe=search.getText().toString();
                int judge=0;            //First check whether the inputed recipe title is exsiting ot.
                cursor.moveToFirst();
                try {
                    do {
                        if (chosenRecipe.equals(cursor.getString(cursor.getColumnIndex(MyProviderContract.TITLE)))) {
                            judge = 1;
                        }
                    } while (cursor.moveToNext());
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(judge==1){
                    Intent intent=new Intent(MainActivity.this,DisplayRecipe.class);
                    intent.putExtra("recipeTitle",search.getText().toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"Cannot find this recipe,try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void initialComponent(){
        listView=(ListView)findViewById(R.id.recipes);
        add=(Button)findViewById(R.id.add);
        searchButton=(Button)findViewById(R.id.searchButton);
        search=(AutoCompleteTextView)findViewById(R.id.search);
        deleteAll=(Button)findViewById(R.id.deleteAll);

    }


    //Click the title to view the specific information of the recipe.
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

    /*
    * Delete All the existing recipes.
    * */
    public void deleteAll(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete all these existing recipe?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getContentResolver().delete(MyProviderContract.RECIPE_URI,null,null);
                recreate();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"Nothing Changed",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
