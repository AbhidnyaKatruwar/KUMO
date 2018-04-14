package com.example.desai.kumo;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AppActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String STORAGE_PATH_UPLOADS_APP = "apps/";
    public static final String DATABASE_PATH_UPLOADS_APP = "apps";
    //this is the pic pdf code used in file chooser
    private final static int PICK_DOCX_CODE = 2342;
    private static final String TAG ="1" ;
    //these are the views
    private TextView textViewStatus;
    private EditText editTextFilename;
    private ProgressBar progressBar;
    private Uri docUri;

    //the firebase objects for storage and database
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private TextView tvLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS_APP);

        //getting the views
        textViewStatus = (TextView) findViewById(R.id.textViewStatusapp);
        editTextFilename = (EditText) findViewById(R.id.editTextFileNameapp);
        progressBar = (ProgressBar) findViewById(R.id.progressbarapp);

        //attaching listeners to views
        findViewById(R.id.btnUploadApp).setOnClickListener(this);
        findViewById(R.id.textViewUploadsapp).setOnClickListener(this);
        tvLink = (TextView) findViewById(R.id.tvLink);
    }
    public void getApp(){
        Intent intent = new Intent();
        intent.setType("docx/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select APP"), PICK_DOCX_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_DOCX_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            docUri = data.getData();      //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(STORAGE_PATH_UPLOADS_APP + System.currentTimeMillis() + "." + getImageExt(docUri));
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

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUploadApp:
                getApp();
                break;
            case R.id.textViewUploadsapp:
                startActivity(new Intent(this, ViewUploadAppActivity.class));
                break;
        }
    }
}