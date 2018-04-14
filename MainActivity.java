package com.example.desai.kumo;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private ImageView imageView;
    private EditText txtImageName;
    private Uri imgUri;
    private ProgressDialog mProgressDialog;
    private TextView tvLink;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;
    private static final int PERMISSION_READ_WRITE_EXTERNAL_STORAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        imageView = (ImageView)findViewById(R.id.imageView);
        txtImageName = (EditText)findViewById(R.id.txtImageName);
        tvLink = (TextView) findViewById(R.id.tvLink);
     }

    public void btnBrowse_Click(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data !=null && data.getData() != null){
            imgUri = data.getData();
            try{
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void btnUpload_Click(View v){

        if(imgUri != null){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Please Wait while the image is loading :)");
            dialog.show();
            //Getting storage reference
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));
            //Adding file to reference
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Dismiss dialog when sucess
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();
                    ImageUpload imageUpload = new ImageUpload(txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                    //Save image info in to firebase database
                    String uploadId = mDatabaseRef.push().getKey();
                    Log.i("2", "uploadId:" + uploadId);
                    mDatabaseRef.child(uploadId).setValue(imageUpload);

                    /*  Getting Links*/

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    tvLink.setText(downloadUrl.toString());
                    Log.d("MainActivity", downloadUrl.toString());
                    Glide.with(MainActivity.this)
                            .load(downloadUrl)
                            .into(imageView);
                    /*  */
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Dismiss dialog when sucess
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //Show upload progress
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    dialog.setMessage("Uploading... " + (int)progress + "%");
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Please select an image", Toast.LENGTH_SHORT).show();
        }
    }
    public void btnShowListImage_Click(View v){
        Intent i = new Intent(MainActivity.this, ImageListActivity.class);
        startActivity(i);
    }

    public void btnUpload_Other(View v){
     Intent i = new Intent(this,OtherUploadActivity.class);
        startActivity(i);
    }

    
}




