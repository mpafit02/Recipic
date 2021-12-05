package com.wss503.recipic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class RecipesViewActivity extends AppCompatActivity {
    Scraper scraper;
    ArrayList<Recipe> recipesArrayList;
    Set<String> labels;
    String category;
    LinearLayout llRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_view);

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.labels =  sharedPref.getStringSet(getString(R.string.saved_labels), new HashSet<String>());
//        this.category =  sharedPref.getString(getString(R.string.saved_category), "breakfast");

        this.category = "breakfast";
//        System.out.println(this.labels);

//        Button bChoosePicture = findViewById(R.id.bChoosePicture);
//        Button bFindRecipes = findViewById(R.id.bFindRecipes);
//        bChoosePicture.setEnabled(true);
//        bFindRecipes.setEnabled(true);

        // Initiate the scrapper
        this.scraper = new Scraper();
        try {
            this.recipesArrayList = this.scraper.execute(this.labels, this.category).get();
            llRecipes = (LinearLayout) findViewById(R.id.llRecipes);
            for(Recipe recipe : recipesArrayList){
                CardView cardview = createRecipeCard(recipe);
                llRecipes.addView(cardview);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        this.recipesArrayList = this.scraper.findRecipes(this.labels, this.category);
//        System.out.println(this.recipesArrayList);
    }

    private CardView createRecipeCard(Recipe recipe) {
        CardView cardview = new CardView(RecipesViewActivity.this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(0, 20, 0, 20);

        cardview.setLayoutParams(layoutParams);
        cardview.setRadius(25);
        cardview.setCardElevation(4);
        cardview.setPadding(0, 25, 0, 25);
        cardview.setCardBackgroundColor(Color.WHITE);
        LinearLayout llCard = new LinearLayout(RecipesViewActivity.this);
        llCard.setOrientation(LinearLayout.VERTICAL);
        llCard.addView(createRecipeImage(recipe));
        llCard.addView(createRecipeTitle(recipe));
        llCard.addView(createRecipeSubtitle(recipe));
        cardview.addView(llCard);
        return cardview;
    }

    private ImageView createRecipeImage(Recipe recipe) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams.height = 500;
        ImageView imageView = new ImageView(RecipesViewActivity.this);
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(recipe.imgUrl).into(imageView);
        return imageView;
    }

    private TextView createRecipeTitle(Recipe recipe) {
        TextView textView = new TextView(RecipesViewActivity.this);
        textView.setText(recipe.title);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        textView.setPadding(25, 25, 25, 20);
        return textView;
    }

    private TextView createRecipeSubtitle(Recipe recipe) {
        TextView textView = new TextView(RecipesViewActivity.this);
        String ingredientsStr = "";
        int count = 0;
        for(String label : labels) {
            boolean isAdded = false;
            for(ArrayList<String> ingredientCategory : recipe.ingredients.values()) {
                for (String ingredient : ingredientCategory) {
                    if (ingredient.toLowerCase(Locale.ROOT).contains(label.toLowerCase(Locale.ROOT))) {
                        if (!ingredientsStr.isEmpty()) {
                            ingredientsStr += ", ";
                        }
                        ingredientsStr += label;
                        isAdded = true;
                        count += 1;
                    }
                    if (count > 5 || isAdded) {
                        break;
                    }
                }
                if (count > 5 || isAdded) {
                    break;
                }
            }
        }
        textView.setText(ingredientsStr);
        textView.setTextSize(14);
        textView.setPadding(25, 0, 25, 25);
        return textView;
    }

}