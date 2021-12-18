package com.wss503.recipic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Collections;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.ic_takepic);

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_profile:
                        Intent b = new Intent(CategoriesActivity.this, ProfileActivity.class);
                        startActivity(b);
                        break;
                    case R.id.ic_takepic:
                        break;
                    case R.id.ic_photos:
                        Intent a = new Intent(CategoriesActivity.this, MyPhotosActivity.class);
                        startActivity(a);
                        break;
                    case R.id.ic_upload:
                        Intent c = new Intent(CategoriesActivity.this, UploadActivity.class);
                        startActivity(c);
                        break;
                }
                return true;
            }
        });

    }
    private void updateNavigationBarState(int actionId,BottomNavigationView bottomNavigationView){
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
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