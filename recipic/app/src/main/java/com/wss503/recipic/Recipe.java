package com.wss503.recipic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe implements Parcelable {
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

    protected Recipe(Parcel in) {
        title = in.readString();
        imgUrl = in.readString();
        recipeUrl = in.readString();
        ingredients = (HashMap<String, ArrayList<String>>) in.readSerializable();
        instructions = (ArrayList<String>) in.readSerializable();
        servings = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String toString() {
        return "Recipe: { title: " + title + ", imgUrl: " + imgUrl + ", recipeUrl: " + recipeUrl + ", ingredients: "
                + ingredients + ", instructions: " + instructions + ", servings: " + servings + " }\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(imgUrl);
        parcel.writeString(recipeUrl);
        parcel.writeSerializable(ingredients);
        parcel.writeSerializable(instructions);
        parcel.writeString(servings);
    }
}
