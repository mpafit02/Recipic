package com.wss503.recipic;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {
    String title;
    String imgUrl;
    String recipeUrl;
    HashMap<String, ArrayList<String>> ingredients;
    ArrayList<String> instructions;
    String servings;

    public Recipe(String title, String imgUrl, String recipeUrl, HashMap<String, ArrayList<String>> ingredients,
                  ArrayList<String> instructions, String servings) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.recipeUrl = recipeUrl;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servings = servings;
    }

    public String toString() {
        return "Recipe: { title: " + title + ", imgUrl: " + imgUrl + ", recipeUrl: " + recipeUrl + ", ingredients: "
                + ingredients + ", instructions: " + instructions + ", servings: " + servings + " }\n";
    }
}
