package com.example.mario.recipebook;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayRecipe extends AppCompatActivity implements View.OnClickListener {

    private int chosenRecipe;

    private TextView title;
    private TextView intro;

    private Button editRecipe;
    private Button deleteRecipe;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        Bundle bundle=getIntent().getExtras();
        chosenRecipe=bundle.getInt("chosenRecipe")+1;
        Log.d("g53mdp","chosen Recipte: "+chosenRecipe);

        initialComponent();

        String[] projection=new String[]{
                MyProviderContract._ID,
                MyProviderContract.TITLE,
                MyProviderContract.INSTRUCTION
        };
        Cursor cursor=getContentResolver().query(MyProviderContract.RECIPE_URI,projection,null,null,null);
        for (int i=0;i<chosenRecipe;i++){
            cursor.moveToNext();
        }
        String recipeTitle =cursor.getString(cursor.getColumnIndex("title"));
        title.setText(recipeTitle);
        String recipeIntro=cursor.getString(cursor.getColumnIndex("instruction"));
        intro.setText(recipeIntro);

        deleteRecipe.setOnClickListener(this);
        back.setOnClickListener(this);



    }

    public void initialComponent(){
        title=(TextView)findViewById(R.id.chosenRecipeTitle);
        intro=(TextView)findViewById(R.id.chosenRecipeIntro);

        editRecipe=(Button)findViewById(R.id.editRecipe);
        deleteRecipe=(Button)findViewById(R.id.deleteRecipe);
        back=(Button)findViewById(R.id.back);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editRecipe:
                editRecipe();
                break;
            case R.id.deleteRecipe:
                deleteRecipe();
                break;
            case R.id.back:
                Intent intent=new Intent(DisplayRecipe.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void editRecipe(){

    }

    public void deleteRecipe(){
        int chosenID=chosenRecipe+6;
        ContentUris uris=new ContentUris();
        Uri editedUri=uris.withAppendedId(MyProviderContract.RECIPE_URI,chosenID);
        getContentResolver().delete(editedUri,MyProviderContract._ID+"=?",null);
        Intent intent=new Intent(DisplayRecipe.this,MainActivity.class);
        startActivity(intent);
    }
}
