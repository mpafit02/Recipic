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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
//import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.util.List;

public class ObjectDetectorSimpleActivity extends AppCompatActivity {

//        private static final String TAG = "MyTag";
//
//        private final static int CAMERA_PERMISSION_CODE = 223;
//        private final static int READ_STORAGE_PERMISSION_CODE = 144;
//        private final static int WRITE_STORAGE_PERMISSION_CODE = 113;
//
//        ImageView ivUploadedPicture;
//        TextView tvMLResult;
//        Button bChoosePicture;
//
//        ActivityResultLauncher<Intent> cameraLauncher;
//        ActivityResultLauncher<Intent> galleryLauncher;
//
//        InputImage inputImage;
//
//        ObjectDetectorOptions objectOptions;
//        ObjectDetector objectDetector;
////    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
////    static {
////        ORIENTATIONS.append(Surface.ROTATION_0, 0);
////        ORIENTATIONS.append(Surface.ROTATION_90, 90);
////        ORIENTATIONS.append(Surface.ROTATION_180, 180);
////        ORIENTATIONS.append(Surface.ROTATION_270, 270);
////    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_object_detector_simple);

//            ivUploadedPicture = findViewById(R.id.ivUploadedPicture);
//            tvMLResult = findViewById(R.id.tvMLResult);
//            bChoosePicture = findViewById(R.id.bChoosePicture);
//
//            // Multiple object detection in static images
//            objectOptions = new ObjectDetectorOptions.Builder()
//                    .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
//                    .enableMultipleObjects()
//                    .enableClassification()  // Optional
//                    .build();
//
//            objectDetector = ObjectDetection.getClient(objectOptions);
//
//            cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Intent data = result.getData();
//                    try{
//                        Bitmap photo = (Bitmap) data.getExtras().get("data");
//                        ivUploadedPicture.setImageBitmap(photo);
//                        inputImage=InputImage.fromBitmap(photo,0);
//
//                        processImage();
//                    }catch(Exception e){
//                        Log.d(TAG, "onActivityResult:" + e.getMessage());
//                    }
//                }
//            });
//
//            galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Intent data = result.getData();
//                    try{
//                        inputImage=InputImage.fromFilePath(ObjectDetectorSimpleActivity.this, data.getData());
//                        ivUploadedPicture.setImageURI(data.getData());
//                        processImage();
//                    }catch(Exception e){
//                        Log.d(TAG, "onActivityResult:" + e.getMessage());
//                    }
//                }
//            });
//
//            bChoosePicture.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // Check permissions
//                    String[] options={"camera","gallery"};
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ObjectDetectorSimpleActivity.this);
//                    builder.setTitle("Pick an option");
//                    System.out.println("Pick Option");
//                    builder.setItems(options, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int which) {
//                            System.out.println(which);
//                            if(which==0){
//                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                cameraLauncher.launch(cameraIntent);
//                            }else{
//                                Intent storageIntent = new Intent();
//                                storageIntent.setType("image/*");
//                                storageIntent.setAction(Intent.ACTION_GET_CONTENT);
//                                galleryLauncher.launch(storageIntent);
//                            }
//                        }
//                    });
//                    builder.show();
//                }
//            });

        }

//        private void processImage() {
//            objectDetector.process(inputImage).addOnSuccessListener(new OnSuccessListener<List<DetectedObject>>() {
//                @Override
//                public void onSuccess(@NonNull List<DetectedObject> detectedObjects) {
//                    String result = "";
//                    for (DetectedObject object :detectedObjects){
//                        for(DetectedObject.Label label : object.getLabels()){
//                            result = result + "\n" + label.getText();
//                        }
//                    }
//                    tvMLResult.setText(result);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(TAG, "onFailure: " + e.getMessage());
//                }
//            });
//        }
//
//        @Override
//        protected void onResume(){
//            super.onResume();
//            checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
//        }
//
//        public void checkPermission(String permission, int requestCode){
//            // Check if permission granted or not
//            if(ContextCompat.checkSelfPermission(ObjectDetectorSimpleActivity.this, permission) == PackageManager.PERMISSION_DENIED){
//                // Take permission
//                ActivityCompat.requestPermissions(ObjectDetectorSimpleActivity.this, new String[]{permission}, requestCode);
//            }else {
//                Toast.makeText(ObjectDetectorSimpleActivity.this, "Permission already Granted", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//            if(requestCode==CAMERA_PERMISSION_CODE){
//                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(ObjectDetectorSimpleActivity.this, "Camera permission Granted", Toast.LENGTH_SHORT).show();
//                }else{
//                    checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
////                Toast.makeText(ObjectDetectorSimpleActivity.this, "Camera permission Denied", Toast.LENGTH_SHORT).show();
//                }
//            }else if(requestCode==READ_STORAGE_PERMISSION_CODE){
//                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(ObjectDetectorSimpleActivity.this, "Read Storage permission Granted", Toast.LENGTH_SHORT).show();
//                }else{
//                    checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_STORAGE_PERMISSION_CODE);
////                Toast.makeText(ObjectDetectorSimpleActivity.this, "Read Storage permission Denied", Toast.LENGTH_SHORT).show();
//                }
//            }else if(requestCode==WRITE_STORAGE_PERMISSION_CODE){
//                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(ObjectDetectorSimpleActivity.this, "Write Storage permission Granted", Toast.LENGTH_SHORT).show();
//                }else{
//                    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_STORAGE_PERMISSION_CODE);
////                Toast.makeText(ObjectDetectorSimpleActivity.this, "Write Storage permission Denied", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//        //    /**
////     * Get the angle by which an image must be rotated given the device's current
////     * orientation.
////     */
////    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
////    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
////            throws CameraAccessException {
////        // Get the device's current rotation relative to its "native" orientation.
////        // Then, from the ORIENTATIONS table, look up the angle the image must be
////        // rotated to compensate for the device's rotation.
////        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
////        int rotationCompensation = ORIENTATIONS.get(deviceRotation);
////
////        // Get the device's sensor orientation.
////        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
////        int sensorOrientation = cameraManager
////                .getCameraCharacteristics(cameraId)
////                .get(CameraCharacteristics.SENSOR_ORIENTATION);
////
////        if (isFrontFacing) {
////            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
////        } else { // back-facing
////            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
////        }
////        return rotationCompensation;
////    }
////
////    private class StillImageAnalyzer implements ImageAnalysis.Analyzer {
////        @Override
////        public void analyze(ImageProxy imageProxy) {
////            Image mediaImage = imageProxy.getImage();
////            if (mediaImage != null) {
////                InputImage image =
////                        InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
////            }
////        }
////    }
    }