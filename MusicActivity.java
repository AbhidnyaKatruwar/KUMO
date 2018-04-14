package com.example.hp.app;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class MusicActivity extends AppCompatActivity {

    public static final String STORAGE_PATH_UPLOADS_AUDIO = "Audios/";
    public static final String DATABASE_PATH_UPLOADS_AUDIO = "audios";
    public static final String FB_STORAGE_PATH = "Music/";
    public static final String FB_DATABASE_PATH = "Music";
    public static final int REQUEST_CODE = 1234;
    private final static int PICK_AUDIO_CODE = 2342;
    private Button  Browse;
    private TextView View;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private TextView textViewStatus;
    private EditText editTextFilename;
    private ProgressBar progressBar;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Browse = (Button)findViewById(R.id.btnUploadAud);
        View = (TextView) findViewById(R.id.textViewUploadsAudio);

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        textViewStatus = (TextView) findViewById(R.id.textViewStatusaud);
        editTextFilename = (EditText) findViewById(R.id.editTextFileNameaudio);
        progressBar = (ProgressBar) findViewById(R.id.progressbaraudio);

        Browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                //startActivity(intent);

                //home.listFiles(new FileExtensionFilter());
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Audio"),REQUEST_CODE);
            }
        });

        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Success","Here");
                Intent i = new Intent(MusicActivity.this, MusicListActivity.class);
                startActivity(i);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("success", "on activity result");
       // TextView uriTxt = (TextView)findViewById(R.id.editTextFileNameaudio);

        //when the user choses the file
        //requestCode == PICK_AUDIO_CODE &&
        if ( resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                Log.i("success", "data.getdata");
                //uploading the file
               // uri = data.getData();
                //uriTxt.setText(uri.toString());
                uploadFile(data.getData());
            }else{
                Log.i("success", "else");
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.i("Failure","req code");
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    private void uploadFile(Uri data) {

        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageRef.child(STORAGE_PATH_UPLOADS_AUDIO + System.currentTimeMillis() + ".mp3");
       // final ProgressDialog progressDialog = new ProgressDialog(this);
       // progressDialog.setTitle("Uploading...");
       // progressDialog.show();
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // progressDialog.dismiss();
                        Log.i("success", "Inside upload onSuccess function");
                        progressBar.setVisibility(View.GONE);
                       //

                       // Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                        textViewStatus.setText("File uploaded successfully");

                        AudioUpload upload = new AudioUpload(editTextFilename.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(upload);

                        mDatabaseRef.child(mDatabaseRef.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    //    progressDialog.dismiss();
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


}
