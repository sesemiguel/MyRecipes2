package com.example.sese.myrecipes2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewRecipe extends AppCompatActivity {
    DBHandler db;
    List<Food> foodList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrecipe);

        //Database
        db = new DBHandler(this);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //FoodArrayList
        foodList = new ArrayList<>();
        foodList = db.getAllFoodList();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
        EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        switch (item.getItemId()){
            case R.id.item1:
            if(titleEditText.getText().length()==0 && descriptionEditText.getText().length()==0){
                Toast.makeText(getApplicationContext(), "Title and description is blank!", Toast.LENGTH_SHORT).show();
            }
            else {
                if (titleEditText.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Title is blank!", Toast.LENGTH_SHORT).show();
                }
                else if (descriptionEditText.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Description is blank!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!foodList.isEmpty()){
                        db.addNewFood(new Food(foodList.get(foodList.size()-1).getKey()+1, titleEditText.getText().toString(), descriptionEditText.getText().toString()));
                    }
                    else{
                        db.addNewFood(new Food(1, titleEditText.getText().toString(), descriptionEditText.getText().toString()));
                    }
                    Toast.makeText(NewRecipe.this, "Added recipe!", Toast.LENGTH_SHORT).show();
                    Intent intentNewRecipe = new Intent(NewRecipe.this, MainActivity.class);
                    startActivity(intentNewRecipe);
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
