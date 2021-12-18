package com.wss503.recipic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashSet;

public class ProfileActivity extends AppCompatActivity {

    String email;
    String firstname;
    String lastname;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.firstname =  sharedPref.getString(getString(R.string.saved_firstname), "none");
        this.lastname =  sharedPref.getString(getString(R.string.saved_lastname), "none");
        this.gender =  sharedPref.getString(getString(R.string.saved_gender), "none");
        this.email =  sharedPref.getString(getString(R.string.saved_email), "none");

        TextView tvHello = (TextView) findViewById(R.id.tvHello);
        TextView tvFirstNameUser = (TextView) findViewById(R.id.tvFirstNameUser);
        TextView tvLastNameUser = (TextView) findViewById(R.id.tvLastNameUser);
        TextView tvGenderUser = (TextView) findViewById(R.id.tvGenderUser);
        TextView tvEmailUser = (TextView) findViewById(R.id.tvEmailUser);

        tvHello.setText("Hello " + firstname + "!");
        tvFirstNameUser.setText(firstname);
        tvLastNameUser.setText(lastname);
        tvGenderUser.setText(gender);
        tvEmailUser.setText(email);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.ic_profile);

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_profile:
                        break;
                    case R.id.ic_takepic:
                        Intent a = new Intent(ProfileActivity.this, CategoriesActivity.class);
                        startActivity(a);
                        break;
                    case R.id.ic_photos:
                        Intent b = new Intent(ProfileActivity.this, MyPhotosActivity.class);
                        startActivity(b);
                        break;
                    case R.id.ic_upload:
                        Intent c = new Intent(ProfileActivity.this, UploadActivity.class);
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

    public void onClickLogout(View view) {
        Intent switchActivityIntent = new Intent(ProfileActivity.this, LoginActivity.class);
        switchActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switchActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(switchActivityIntent);
    }
}