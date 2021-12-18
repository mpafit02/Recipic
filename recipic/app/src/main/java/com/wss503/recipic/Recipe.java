package com.wss503.recipic;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe implements Parcelable {
    int id;
    String title;
//    Bitmap imgBit;
    String videoUrl;
    String recipeUrl;
    HashMap<String, ArrayList<String>> ingredients;
    ArrayList<String> instructions;
    String servings;

//    public Recipe(String title, Bitmap imgBit, String videoUrl, String recipeUrl, HashMap<String, ArrayList<String>> ingredients,
//                  ArrayList<String> instructions, String servings) {
    public Recipe( String title, String videoUrl, String recipeUrl, HashMap<String, ArrayList<String>> ingredients,
                  ArrayList<String> instructions, String servings) {

        this.id = 0;
        this.title = title;
//        this.imgBit = imgBit;
        this.videoUrl = videoUrl;
        this.recipeUrl = recipeUrl;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servings = servings;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        title = in.readString();
//        imgBit = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
        videoUrl = in.readString();
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
        return "Recipe: { title: " + title + ", videoUrl: " + videoUrl + ", recipeUrl: " + recipeUrl + ", ingredients: "
                + ingredients + ", instructions: " + instructions + ", servings: " + servings + " }\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
//        parcel.writeValue(imgBit);
        parcel.writeString(videoUrl);
        parcel.writeString(recipeUrl);
        parcel.writeSerializable(ingredients);
        parcel.writeSerializable(instructions);
        parcel.writeString(servings);
    }
}
