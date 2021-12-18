package com.wss503.recipic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class RecipeDetailsActivity extends AppCompatActivity {

    Recipe recipe;
    String category;
    Set<String> labels;

    int breakfasts[] = {R.drawable.hcr, R.drawable.whipped_coffee, R.drawable.cinnamon_rolls, R.drawable.fluffy_pancakes};
    int lunches[] = {R.drawable.cheeseburger_pasta, R.drawable.mushroom_stroganoff, R.drawable.chicken_sandwich, R.drawable.chicken_fajita};
    int lunches2[] = {R.drawable.broccoll_soup, R.drawable.pesto_chicken, R.drawable.mushroom_stroganoff, R.drawable.quinoa_salad};
    int dinners[] = {R.drawable.tomato_pasta, R.drawable.ingredient_pasta, R.drawable.garlic_pasta, R.drawable.chicken_fajita};
    int desserts[] = {R.drawable.chip_cookies, R.drawable.butter_cookies, R.drawable.cinnamon_rolls, R.drawable.fudgy_brownies};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.labels =  sharedPref.getStringSet(getString(R.string.saved_labels), new HashSet<String>());
        this.category =  sharedPref.getString(getString(R.string.saved_category), "breakfast");

        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra("RECIPE_EXTRA");

        LinearLayout llRecipeDetails = (LinearLayout) findViewById(R.id.llRecipeDetails);
        llRecipeDetails.addView(createRecipeImage(recipe));
        llRecipeDetails.addView(createRecipeTitle(recipe));
        llRecipeDetails.addView(createRecipeIngredients(recipe));
        llRecipeDetails.addView(createRecipeVideo(recipe));
        llRecipeDetails.addView(createRecipeInstructions(recipe));
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
        int did = 0;
        if(category.equals("breakfast")){
            did = breakfasts[recipe.id];
        }else if(category.equals("lunch")){
            if(labels.contains("tomato") || labels.contains("Tomato")){
                did = lunches2[recipe.id];
            }else{
                did = lunches[recipe.id];
            }
        }else if(category.equals("dinner")){
            did = dinners[recipe.id];
        }else if(category.equals("desserts")){
            did = desserts[recipe.id];
        }
        imageView.setImageDrawable(getResources().getDrawable(did));
//        imageView.setImageBitmap(recipe.imgBit);
//        Picasso.get().load(recipe.imgUrl).into(imageView);
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
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout llVideo = new LinearLayout(RecipeDetailsActivity.this);
        llVideo.setLayoutParams(layoutParams);
        llVideo.setOrientation(LinearLayout.VERTICAL);

        TextView tvIngredientsTitle = new TextView(RecipeDetailsActivity.this);
        tvIngredientsTitle.setText("Video");
        tvIngredientsTitle.setTextColor(Color.BLACK);
        tvIngredientsTitle.setTextSize(22);
        tvIngredientsTitle.setTypeface(null, Typeface.BOLD);
        tvIngredientsTitle.setPadding(25, 25, 25, 20);
        llVideo.addView(tvIngredientsTitle);

//        String URL = recipe.videoUrl;
        String URL = "https://vid.tasty.co/output/65088/landscape_480/1512083102";

        if(URL.isEmpty()){

            TextView tvServings = new TextView(RecipeDetailsActivity.this);
            tvServings.setText("No video available");
            tvServings.setTextColor(Color.BLACK);
            tvServings.setTextSize(16);
            tvServings.setPadding(25, 10, 25, 40);
            llVideo.addView(tvServings);

        }else{
            LinearLayout llVideoButton = new LinearLayout(RecipeDetailsActivity.this);
            llVideoButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llVideoButton.setGravity(Gravity.CENTER);
            llVideoButton.setOrientation(LinearLayout.HORIZONTAL);

            Button bVideo = new Button(this);
            bVideo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            bVideo.setText("Recipe Video");
            bVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW );
                    intent.setDataAndType(Uri.parse(URL), "video/*");
                    startActivity(intent);

                }
            });
            llVideoButton.addView(bVideo);
            llVideo.addView(llVideoButton);


//            LinearLayout.LayoutParams layoutVideoParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutVideoParams.setMargins(10, 10, 10, 10);
//
//            VideoView videoView = findViewById(R.id.videoView);
//            videoView.setLayoutParams(layoutVideoParams);
//            videoView.setVideoURI(Uri.parse(URL));
//
//            MediaController mediaController = new MediaController(this);
//            mediaController.setAnchorView(videoView);
//            mediaController.setMediaPlayer(videoView);
//            videoView.setMediaController(mediaController);
//            videoView.start();
//
//            llVideo.addView(videoView);
        }

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