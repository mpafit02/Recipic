package com.wss503.recipic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.Collections;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


    }

    public void onClickCategory(View view) {
        int buttonID = view.getId();
        String selectedCategory = "breakfast";

        if(buttonID == R.id.cvLunch){
            selectedCategory = "lunch";
        } else if(buttonID == R.id.cvDinner){
            selectedCategory = "dinner";
        } else if(buttonID == R.id.cvBreakfast) {
            selectedCategory = "breakfast";
        } else if(buttonID == R.id.cvDessert) {
            selectedCategory = "desserts";
        }

        Context context = CategoriesActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_category), selectedCategory);
        editor.commit();

        // Navigate to the next page to display the recipes
        Intent switchActivityIntent = new Intent(CategoriesActivity.this, ObjectDetectorCustomActivity.class);
        startActivity(switchActivityIntent);

    }
}