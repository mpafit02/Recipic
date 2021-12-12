package com.wss503.recipic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

public class MyPhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);

        LinearLayout llPhotos = (LinearLayout) findViewById(R.id.llPhotos);
        llPhotos.removeAllViews();

        ImageView ivDish1 = new ImageView(this);
        ivDish1.setImageResource(R.drawable.breakfast);

        ImageView ivDish2 = new ImageView(this);
        ivDish2.setImageResource(R.drawable.dessert);

        CardView cvDish1 = createDishCard(ivDish1);
        CardView cvDish2 = createDishCard(ivDish2);

        llPhotos.addView(cvDish1);
        llPhotos.addView(cvDish2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.ic_upload);

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_profile:
                        Intent b = new Intent(MyPhotosActivity.this, ProfileActivity.class);
                        startActivity(b);
                        break;
                    case R.id.ic_takepic:
                        Intent a = new Intent(MyPhotosActivity.this, CategoriesActivity.class);
                        startActivity(a);
                        break;
                    case R.id.ic_upload:
                        break;
                }
                return true;
            }
        });
    }

    private CardView createDishCard(ImageView ivDish) {
        CardView cardview = new CardView(MyPhotosActivity.this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(0, 10, 0, 10);

        cardview.setLayoutParams(layoutParams);
        cardview.setRadius(25);
        cardview.setCardElevation(4);
        cardview.setPadding(20, 20, 20, 20);
        cardview.addView(createRecipeImage(ivDish));
        return cardview;
    }

    private ImageView createRecipeImage(ImageView ivDish) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams.height = 500;
        ivDish.setLayoutParams(layoutParams);
        ivDish.setAdjustViewBounds(true);
        ivDish.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return ivDish;
    }

}