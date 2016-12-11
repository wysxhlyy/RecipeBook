package com.example.mario.recipebook;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRecipe extends AppCompatActivity {

    private EditText titleField;
    private EditText introField;

    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        titleField=(EditText) findViewById(R.id.title);
        introField=(EditText)findViewById(R.id.intro);
        create=(Button)findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=titleField.getText().toString();
                String intro=introField.getText().toString();

                ContentValues newRecipe=new ContentValues();
                newRecipe.put(MyProviderContract.TITLE,title);
                newRecipe.put(MyProviderContract.INSTRUCTION,intro);

                //Insert the title and introduction got from the EditText field.
                getContentResolver().insert(MyProviderContract.RECIPE_URI,newRecipe);

                Intent intent=new Intent(CreateRecipe.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
