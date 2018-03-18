package com.example.sese.myrecipes2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditRecipe extends AppCompatActivity{
    DBHandler db;
    List<Food> foodList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editrecipe);

        //Database
        db = new DBHandler(this);

        //Bundle for getting key
        Bundle bundle = getIntent().getExtras();
        int keyFromBundle = Integer.parseInt(bundle.getString("key"));

        //ArrayList for food entries
        foodList = new ArrayList<>();
        foodList = db.getAllFoodList();

        //EditText
        EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
        EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        //Setting EditText title & descriptions
        titleEditText.setText(foodList.get(keyFromBundle-1).getTitle());
        descriptionEditText.setText(foodList.get(keyFromBundle-1).getDescription());

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Edit Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Bundle bundle = getIntent().getExtras();
        int keyFromBundle = Integer.parseInt(bundle.getString("key"));
        EditText titleText = (EditText) findViewById(R.id.titleEditText);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionEditText);
        switch (item.getItemId()){
            case R.id.item1:
                //Adding food
                db.updateFoodRecipe(foodList.get(keyFromBundle-1).getKey(), titleText.getText().toString(),
                        descriptionText.getText().toString());
                Intent intentNewRecipe = new Intent(this, MainActivity.class);
                startActivity(intentNewRecipe);

        }
        return super.onOptionsItemSelected(item);
    }
}
