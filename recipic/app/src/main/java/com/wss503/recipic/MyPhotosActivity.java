package com.wss503.recipic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyPhotosActivity extends AppCompatActivity {

//    FloatingActionButton fabTakePic;
    LinearLayout llPhotos;

    private int userID;
    private boolean isAssigned  = false;
    private String GET_IMAGE_URL="https://fooderloo.com/recipic/get_all_images.php?user_id=";
    private GetAllImages getAllImages;
//    public static final String BITMAP_ID = "BITMAP_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        userID =  sharedPref.getInt(getString(R.string.saved_id), 0);

        // Image Upload - End
        if(!isAssigned) {
            GET_IMAGE_URL = GET_IMAGE_URL + userID;
            isAssigned = true;
        }

        // Image Upload - Start

//        fabTakePic = (FloatingActionButton) findViewById(R.id.fabTakePic);
//        fabTakePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent b = new Intent(MyPhotosActivity.this, UploadActivity.class);
//                startActivity(b);
//            }
//        });

        llPhotos = (LinearLayout) findViewById(R.id.llPhotos);
        llPhotos.removeAllViews();
        getURLs();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.ic_photos);

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
                    case R.id.ic_photos:
                        break;
                    case R.id.ic_upload:
                        Intent c = new Intent(MyPhotosActivity.this, UploadActivity.class);
                        startActivity(c);
                        break;
                }
                return true;
            }
        });
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MyPhotosActivity.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                String[] urls =GetAllImages.imageURLs;
                Bitmap[] bitmaps= GetAllImages.bitmaps;

                for(int i=0; i < bitmaps.length; i++) {
                    ImageView ivDish = new ImageView(MyPhotosActivity.this);
                    ivDish.setImageBitmap(bitmaps[i]);
                    CardView cvDish = createDishCard(ivDish);
                    llPhotos.addView(cvDish);
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAllImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MyPhotosActivity.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAllImages = new GetAllImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
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