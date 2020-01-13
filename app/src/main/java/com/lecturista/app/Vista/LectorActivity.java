package com.lecturista.app.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import com.lecturista.app.Interface.LecturaInterface;
import com.lecturista.app.POJO.Cliente;
import com.lecturista.app.Presentador.LecturaPresentador;
import com.lecturista.app.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LectorActivity extends AppCompatActivity implements LecturaInterface.LecturaVista {

    @BindView(R.id.cropimage)  ImageView imageView;
    @BindView(R.id.texto) EditText texto;
    @BindView(R.id.numafiliado) TextView numafiliado;
    @BindView(R.id.nomafiliado) TextView nomafiliado;
    @BindView(R.id.dirafiliado) TextView dirafiliado;
    @BindView(R.id.grabar)  MaterialButton grabarboton;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    public LecturaInterface.LecturaPresentador lPresentador;

    Uri imageURI;
    Bitmap image;
    String targetPath = "";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.lector_activity);
         ButterKnife.bind(this);
         Bundle bundle = getIntent().getExtras();
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setDisplayShowHomeEnabled(true);
         if (bundle != null) {
             Cliente  cliente = (Cliente) bundle.getSerializable("cliente");
             numafiliado.setText(cliente.getOriginal_id());
             nomafiliado.setText(cliente.getName());
             dirafiliado.setText(cliente.getAddress());
         }
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
             checkPermission();
         }
         lPresentador = new LecturaPresentador(this);
    }

    @OnClick(R.id.grabar)
    public void grabar(){
         String textoReconocido = texto.getText().toString();
         lPresentador.enviarDatos(image, textoReconocido);
    }

    @OnClick(R.id.capturar)
    public void capturar(){
        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "captura.jpg");
        imageURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
        m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageURI);
        startActivityForResult(m_intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            CropImage.activity(imageURI).start(this);
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //Habilito el boton de enviar grabacio//
                grabarboton.setEnabled(true);
                Uri resultUri = result.getUri();
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                image = BitmapFactory.decodeFile(resultUri.getPath(),bmOptions);
                targetPath = resultUri.getPath();
                imageView.setImageBitmap(image);
                detextTextFromImage();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void detextTextFromImage(){
        FirebaseVisionImage imagev = FirebaseVisionImage.fromBitmap(image);
        FirebaseVisionTextRecognizer textRecognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        textRecognizer.processImage(imagev).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                textFromImage(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
     }

     private void textFromImage(FirebaseVisionText firebaseVisionText){
         List<FirebaseVisionText.TextBlock> blocklist = firebaseVisionText.getTextBlocks();
         if(blocklist.size()>0){
             for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
                 String blockText = block.getText();
                 Float blockConfidence = block.getConfidence();
                 List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
                 Point[] blockCornerPoints = block.getCornerPoints();
                 Rect blockFrame = block.getBoundingBox();
                 for (FirebaseVisionText.Line line: block.getLines()) {
                     String lineText = line.getText();
                     lineText = lineText.replace("B","8");
                     lineText = lineText.replace(" ","");
                     texto.setText(lineText);

                 }
         }
        }

     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void checkPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Camera, Read Contacts and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                LectorActivity.this,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(getApplicationContext(),"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    // Permissions are granted
                    Toast.makeText(getApplicationContext(),"Permissions granted.",Toast.LENGTH_SHORT).show();
                }else {
                    // Permissions are denied
                    Toast.makeText(getApplicationContext(),"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
