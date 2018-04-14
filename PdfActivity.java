package com.example.desai.kumo;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PdfActivity extends AppCompatActivity implements View.OnClickListener /*implements View.OnClickListener*/{

    public static final String STORAGE_PATH_UPLOADS_PDF = "pdfs/";
    public static final String DATABASE_PATH_UPLOADS_PDF = "pdfs";
    //this is the pic pdf code used in file chooser
    private final static int PICK_PDF_CODE = 2342;
    private static final String TAG ="1" ;
    //these are the views
    private TextView textViewStatus;
    private EditText editTextFilename;
    private ProgressBar progressBar;
    private TextView tvLink;


    //the firebase objects for storage and database
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS_PDF);

        //getting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatuspdf);
        editTextFilename = (EditText) findViewById(R.id.editTextFileNamepdf);
        progressBar = (ProgressBar) findViewById(R.id.progressbarpdf);

        //attaching listeners to views
        findViewById(R.id.btnUploadPdf).setOnClickListener(this);
        findViewById(R.id.textViewUploadspdf).setOnClickListener(this);
        tvLink = (TextView) findViewById(R.id.tvLink);
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //Check if permission was already granted or not
        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                Log.i("12", "before uploadFile");
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(STORAGE_PATH_UPLOADS_PDF + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.i("success", "Inside upload onSuccess function");
                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");
                        PdfUpload upload = new PdfUpload(editTextFilename.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                        String uploadId = mDatabaseReference.push().getKey();
                        mDatabaseReference.child(uploadId).setValue(upload);

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        tvLink.setText(downloadUrl.toString());

                        //mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.i("failure", "Inside onFailure function upload");
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUploadPdf:
                getPDF();
                break;
            case R.id.textViewUploadspdf:
                startActivity(new Intent(this, ViewUploadPdfActivity.class));
                break;
        }
    }

}

