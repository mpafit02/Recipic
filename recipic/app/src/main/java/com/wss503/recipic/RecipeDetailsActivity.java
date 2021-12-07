package com.wss503.recipic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra("RECIPE_EXTRA");

        LinearLayout llCard = (LinearLayout) findViewById(R.id.llRecipeDetails);
        llCard.addView(createRecipeImage(recipe));
        llCard.addView(createRecipeTitle(recipe));
        llCard.addView(createRecipeIngredients(recipe));
//        llCard.addView(createRecipeVideo(recipe));
        llCard.addView(createRecipeInstructions(recipe));
    }

    private ImageView createRecipeImage(Recipe recipe) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams.height = 700;
        ImageView imageView = new ImageView(RecipeDetailsActivity.this);
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(recipe.imgUrl).into(imageView);
        return imageView;
    }

    private TextView createRecipeTitle(Recipe recipe) {
        TextView textView = new TextView(RecipeDetailsActivity.this);
        textView.setText(recipe.title);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(25, 35, 25, 30);
        return textView;
    }

    private LinearLayout createRecipeVideo(Recipe recipe){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout llVideo = new LinearLayout(RecipeDetailsActivity.this);
        llVideo.setLayoutParams(layoutParams);

        TextView tvIngredientsTitle = new TextView(RecipeDetailsActivity.this);
        tvIngredientsTitle.setText("Video");
        tvIngredientsTitle.setTextColor(Color.BLACK);
        tvIngredientsTitle.setTextSize(22);
        tvIngredientsTitle.setTypeface(null, Typeface.BOLD);
        tvIngredientsTitle.setPadding(25, 25, 25, 20);
        llVideo.addView(tvIngredientsTitle);

        LinearLayout.LayoutParams layoutVideoParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutVideoParams.setMargins(10, 10, 10, 10);

        VideoView videoView = new VideoView(RecipeDetailsActivity.this);
        videoView.setLayoutParams(layoutVideoParams);
        videoView.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        videoView.start();

        llVideo.addView(videoView);
        return llVideo;
    }

    private LinearLayout createRecipeIngredients(Recipe recipe) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout llIngredients = new LinearLayout(RecipeDetailsActivity.this);
        llIngredients.setLayoutParams(layoutParams);
        llIngredients.setOrientation(LinearLayout.VERTICAL);

        TextView tvIngredientsTitle = new TextView(RecipeDetailsActivity.this);
        tvIngredientsTitle.setText("Ingredients");
        tvIngredientsTitle.setTextColor(Color.BLACK);
        tvIngredientsTitle.setTextSize(22);
        tvIngredientsTitle.setTypeface(null, Typeface.BOLD);
        tvIngredientsTitle.setPadding(25, 25, 25, 20);
        llIngredients.addView(tvIngredientsTitle);

        TextView tvServings = new TextView(RecipeDetailsActivity.this);
        tvServings.setText(recipe.servings.toString());
        tvServings.setTextColor(Color.BLACK);
        tvServings.setTextSize(16);
        tvServings.setPadding(25, 10, 25, 40);
        llIngredients.addView(tvServings);

        for(String category : recipe.ingredients.keySet()){

            TextView tvIngredientsCategoryTitle = new TextView(RecipeDetailsActivity.this);
            tvIngredientsCategoryTitle.setText(category);
            tvIngredientsCategoryTitle.setTextColor(Color.BLACK);
            tvIngredientsCategoryTitle.setTextSize(16);
            tvIngredientsCategoryTitle.setTypeface(null, Typeface.BOLD);
            tvIngredientsCategoryTitle.setPadding(25, 25, 25, 20);
            llIngredients.addView(tvIngredientsCategoryTitle);

            for(String ingredient: recipe.ingredients.get(category)){

                TextView tvIngredient = new TextView(RecipeDetailsActivity.this);
                tvIngredient.setText(ingredient);
                tvIngredient.setTextColor(Color.BLACK);
                tvIngredient.setTextSize(14);
                tvIngredient.setPadding(85, 20, 25, 20);
                llIngredients.addView(tvIngredient);
            }
        }
        return llIngredients;
    }


    private LinearLayout createRecipeInstructions(Recipe recipe) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout llInstructions = new LinearLayout(RecipeDetailsActivity.this);
        llInstructions.setLayoutParams(layoutParams);
        llInstructions.setOrientation(LinearLayout.VERTICAL);

        TextView tvIngredientsTitle = new TextView(RecipeDetailsActivity.this);
        tvIngredientsTitle.setText("Instructions");
        tvIngredientsTitle.setTextColor(Color.BLACK);
        tvIngredientsTitle.setTextSize(22);
        tvIngredientsTitle.setTypeface(null, Typeface.BOLD);
        tvIngredientsTitle.setPadding(25, 85, 25, 20);
        llInstructions.addView(tvIngredientsTitle);

        int count = 1;
        for(String instruction : recipe.instructions){
            TextView tvIngredient = new TextView(RecipeDetailsActivity.this);
            tvIngredient.setText(count + " " + instruction);
            tvIngredient.setTextColor(Color.BLACK);
            tvIngredient.setTextSize(14);
            tvIngredient.setPadding(85, 20, 25, 20);
            llInstructions.addView(tvIngredient);
            count+=1;
        }
        return llInstructions;
    }
}