package com.wss503.recipic;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class Scraper extends AsyncTask<Object, Void, ArrayList<Recipe>> {
    private ArrayList<Recipe> recipes;

    public Scraper() {
        this.recipes = new ArrayList<Recipe>();
    }

    @Override
    protected ArrayList<Recipe> doInBackground(Object... parameters) {
        Scraper scraper = new Scraper();
        return scraper.findRecipes((Set<String>)parameters[0], (String)parameters[1]);
    }

//    protected void onPostExecute( ArrayList<Recipe> recipes) {
//    }

    private Recipe getDetails(String recipeUrl, String title, String imgUrl) {
        try {
            Document document = Jsoup.connect(recipeUrl).get();
            Elements ingredientsPreparations = document.getElementsByClass("ingredients-prep");
            for (Element ingredientsPreparation : ingredientsPreparations) {
                String servings = ingredientsPreparation.getElementsByClass("servings-display").text();

                // Fetch the ingredients
                Elements ingredientsElements = ingredientsPreparation.getElementsByClass("ingredients__section");
                HashMap<String, ArrayList<String>> ingredients = new HashMap<String, ArrayList<String>>();
                for (Element ingredientsElement : ingredientsElements) {
                    String listName = ingredientsElement.getElementsByClass("ingredient-section-name").text();
                    if (listName.isEmpty()) {
                        listName = "General";
                    }
                    ArrayList<String> products = new ArrayList<String>();
                    Elements productElements = ingredientsElement.getElementsByClass("ingredient");
                    for (Element productElement : productElements) {
                        products.add(productElement.text());
                    }
                    ingredients.put(listName,products);
                }

                // Fetch the instructions
                Elements instructionsElements = ingredientsPreparation.getElementsByClass("preparation");
                ArrayList<String> instructions = new ArrayList<String>();
                for (Element instructionsElement : instructionsElements) {
                    Elements steps = instructionsElement.getElementsByClass("prep-steps");
                    for (Element step : steps) {
                        Elements stepLIs = step.getElementsByTag("li");
                        for (Element stepLi : stepLIs) {
                            instructions.add(stepLi.text());
                        }
                    }
                }
                return new Recipe(title, imgUrl, recipeUrl, ingredients, instructions, servings);
            }
            System.out.println("Returned null");
            return null;
        } catch (IOException e) {
            System.err.println("For '" + recipeUrl + "': " + e.getMessage());
        }
        System.out.println("Returned null");
        return null;
    }

    private void getRecipes(String URL) {
        try {
            // 1. Fetch the HTML code
            Document document = Jsoup.connect(URL).get();
            Elements feedItems = document.getElementsByClass("feed-item");
            for (Element feedItem : feedItems) {
                String recipeUrl = feedItem.attr("href");
                String imgUrl = feedItem.select("img").attr("src");
                String title = feedItem.getElementsByClass("item-title").text();
                if (imgUrl.isEmpty()) {
                    continue;
                }
                Recipe recipe = getDetails(recipeUrl, title, imgUrl);
                if (recipe != null) {
                    this.recipes.add(recipe);
                }
            }
            System.out.println(this.recipes);

        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }
    }

    public ArrayList<Recipe> findRecipes(Set<String> labels, String category) {
        String src = "https://tasty.co/topic/";
        String link = src + category;
        this.getRecipes(link);

        // Filter with the labels
        int limit = 8;
        int count = 0;
        ArrayList<Recipe> filteredRecipes = new ArrayList<Recipe>();
        for (Recipe recipe : this.recipes) {
            boolean isAdded = false;
            for (String label : labels) {
                for(ArrayList<String> ingredientCategory : recipe.ingredients.values()) {
                    for(String ingredient: ingredientCategory){
                        if(ingredient.toLowerCase(Locale.ROOT).contains(label.toLowerCase(Locale.ROOT))){
                            filteredRecipes.add(recipe);
                            isAdded = true;
                            count += 1;
                            break;
                        }
                    }
                    if(isAdded){
                        break;
                    }
                }
                if(isAdded){
                    break;
                }
            }
            if(count >= limit){
                break;
            }
        }
        return filteredRecipes;
    }

}
