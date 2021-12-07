package com.wss503.recipic;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObjectDetectorCustomActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";

    private final static int CAMERA_PERMISSION_CODE = 223;
    private final static int READ_STORAGE_PERMISSION_CODE = 144;
    private final static int WRITE_STORAGE_PERMISSION_CODE = 113;

    ImageView ivUploadedPicture;
    ChipGroup cgLabels;
    Button bChoosePicture;
    Button bFindRecipes;
//    ProgressBar pbScraper;

    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> galleryLauncher;

    InputImage inputImage;

    CustomObjectDetectorOptions objectOptions;
    ObjectDetector objectDetector;

    Set<String> labelsSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detector_custom);

        ivUploadedPicture = findViewById(R.id.ivUploadedPicture);
        cgLabels = findViewById(R.id.cgLabels);
        bChoosePicture = findViewById(R.id.bChoosePicture);
        bFindRecipes = findViewById(R.id.bFindRecipes);
        bFindRecipes.setEnabled(false);

        labelsSet = new HashSet<String>();

//        pbScraper = (ProgressBar) findViewById(R.id.pbScraper);
//        pbScraper.bringToFront();
//        pbScraper.setVisibility(View.INVISIBLE);

        LocalModel localModel = new LocalModel.Builder()
                        .setAssetFilePath("model.tflite")
                        // or .setAbsoluteFilePath(absolute file path to model file)
                        // or .setUri(URI to model file)
                        .build();

        // Multiple object detection in static images
        objectOptions =  new CustomObjectDetectorOptions.Builder(localModel)
                        .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
                        .enableMultipleObjects()
                        .enableClassification()
                        .setClassificationConfidenceThreshold(0.5f)
                        .setMaxPerObjectLabelCount(3)
                        .build();

        objectDetector =
                ObjectDetection.getClient(objectOptions);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                Intent data = result.getData();
                try{
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    ivUploadedPicture.setImageBitmap(photo);
                    ivUploadedPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    inputImage=InputImage.fromBitmap(photo,0);
                    cgLabels.removeAllViews();
                    processImage();
                }catch(Exception e){
                    Log.d(TAG, "onActivityResult:" + e.getMessage());
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent data = result.getData();
                try{
                    inputImage=InputImage.fromFilePath(ObjectDetectorCustomActivity.this, data.getData());
                    ivUploadedPicture.setImageURI(data.getData());
                    ivUploadedPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    cgLabels.removeAllViews();
                    processImage();
                }catch(Exception e){
                    Log.d(TAG, "onActivityResult:" + e.getMessage());
                }
            }
        });

        bChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check permissions
                String[] options={"Camera","Gallery"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ObjectDetectorCustomActivity.this);
                builder.setTitle("Pick an option");
                System.out.println("Pick Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        System.out.println(which);
                        if(which==0){
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraLauncher.launch(cameraIntent);
                        }else{
                            Intent storageIntent = new Intent();
                            storageIntent.setType("image/*");
                            storageIntent.setAction(Intent.ACTION_GET_CONTENT);
                            galleryLauncher.launch(storageIntent);
                        }
                    }
                });
                builder.show();
            }
        });

            bFindRecipes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ObjectDetectorCustomActivity.this, "Searching for recipes ...", Toast.LENGTH_LONG).show();

//                    pbScraper.setVisibility(View.VISIBLE);
//                    bChoosePicture.setEnabled(false);
                    bFindRecipes.setEnabled(false);

                    Context context = ObjectDetectorCustomActivity.this;
                    SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putStringSet(getString(R.string.saved_labels), labelsSet);
                    editor.commit();

                    // Navigate to the next page to display the recipes
                    Intent switchActivityIntent = new Intent(ObjectDetectorCustomActivity.this, RecipesViewActivity.class);
                    startActivity(switchActivityIntent);

//                    bFindRecipes.setEnabled(true);
//                    pbScraper.setVisibility(View.GONE);
                }
            });

    }
    private void processImage() {
        objectDetector.process(inputImage).addOnSuccessListener(new OnSuccessListener<List<DetectedObject>>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(@NonNull List<DetectedObject> detectedObjects) {
                for (DetectedObject object :detectedObjects){
                    for(DetectedObject.Label label : object.getLabels()){
                        String labelTxt = label.getText();
                        if(labelTxt.equals("Food") || labelTxt.equals("Tableware") || labelTxt.equals("Spoon") || labelTxt.equals("Bowl") || labelTxt.equals("Dairy")){
                            continue;
                        }else {
                            labelsSet.add(labelTxt);
                        }
                    }
                }

                for (String labelStr : labelsSet) {
                    Chip chip = new Chip(cgLabels.getContext());
                    chip.setText(labelStr);
                    chip.setCloseIconVisible(true);
                    chip.setTextAppearanceResource(R.style.ChipTextStyle);
                    chip.setChipBackgroundColorResource(R.color.red_700);
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            labelsSet.remove(labelStr);
                            cgLabels.removeView(chip);
                            checkFindRecipesBtn();
                        }
                    });

                    cgLabels.addView(chip);
                }
                checkFindRecipesBtn();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    private void checkFindRecipesBtn(){
        bChoosePicture.setEnabled(true);
        if(labelsSet.isEmpty()){
            bFindRecipes.setEnabled(false);
        }else{
            bFindRecipes.setEnabled(true);
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
    }

    public void checkPermission(String permission, int requestCode){
        // Check if permission granted or not
        if(ContextCompat.checkSelfPermission(ObjectDetectorCustomActivity.this, permission) == PackageManager.PERMISSION_DENIED){
            // Take permission
            ActivityCompat.requestPermissions(ObjectDetectorCustomActivity.this, new String[]{permission}, requestCode);
        }else {
//            Toast.makeText(ObjectDetectorCustomActivity.this, "Permission already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==CAMERA_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ObjectDetectorCustomActivity.this, "Camera permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
//                Toast.makeText(ObjectDetectorSimpleActivity.this, "Camera permission Denied", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==READ_STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ObjectDetectorCustomActivity.this, "Read Storage permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_STORAGE_PERMISSION_CODE);
//                Toast.makeText(ObjectDetectorSimpleActivity.this, "Read Storage permission Denied", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==WRITE_STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ObjectDetectorCustomActivity.this, "Write Storage permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_STORAGE_PERMISSION_CODE);
//                Toast.makeText(ObjectDetectorSimpleActivity.this, "Write Storage permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}