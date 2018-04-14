package com.example.suruchi.kumo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UploadMenu extends AppCompatActivity {

    private  Button  uMusic;
    private  Button  uVideo;
    private  Button  uPpt;
    private  Button  uApp;
    private  Button  uDoxc;
    private  Button  uCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_menu);

        uMusic = (Button) findViewById(R.id.music);
        uCamera = (Button) findViewById(R.id.camera);
        uApp = (Button) findViewById(R.id.app);
        uPpt = (Button) findViewById(R.id.ppt);
        uDoxc = (Button) findViewById(R.id.docx);
        uVideo = (Button) findViewById(R.id.video);






        uMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent musicIntent = new Intent(UploadMenu.this, MusicActivity.class);
                startActivity(musicIntent);
            }
        });


        uVideo.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {Log.i("chala00","ja");
                Intent videoIntent = new Intent(UploadMenu.this, VideoActivity.class);Log.i("chala02","ja");
                startActivity(videoIntent);Log.i("chala01","ja");
            }
        });


        uPpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pptIntent = new Intent(UploadMenu.this, PptActivity.class);
                startActivity(pptIntent);
            }
        });


        uCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(UploadMenu.this, ImgActivity.class);
                startActivity(cameraIntent);
            }
        });


        uApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(UploadMenu.this, PdfActivity.class);
                startActivity(appIntent);
            }
        });


        uDoxc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent docxIntent = new Intent(UploadMenu.this, DocActivity.class);
                startActivity(docxIntent);
            }
        });







    }







}
