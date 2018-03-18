package com.example.sese.myrecipes2;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewRecipe extends AppCompatActivity{
    DBHandler db;
    List<Food> foodList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecipe);
        db = new DBHandler(this);

        //Put db into a list
        foodList = new ArrayList<>();
        foodList = db.getAllFoodList();

        //Get key from Main Activity
        final Bundle bundle = getIntent().getExtras();
        final int keyFromBundle = Integer.parseInt(bundle.getString("key"));

        //Updating textViews
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        titleTextView.setText(foodList.get(keyFromBundle-1).getTitle());
        descriptionTextView.setText(foodList.get(keyFromBundle-1).getDescription());
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        //Custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(foodList.get(keyFromBundle-1).getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //OnClick for deleteButton
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlert(foodList.get(keyFromBundle-1).getKey());
            }
        });

        //OnClick for editButton
        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewRecipe = new Intent(ViewRecipe.this, EditRecipe.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", String.valueOf(keyFromBundle));
                intentNewRecipe.putExtras(bundle);
                startActivity(intentNewRecipe);
            }
        });

    }
    public void createAlert(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deleting recipe");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete
                        db.deleteFood(i);
                        Toast.makeText(ViewRecipe.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                        Intent intentDelete = new Intent(ViewRecipe.this, MainActivity.class);
                        startActivity(intentDelete);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
