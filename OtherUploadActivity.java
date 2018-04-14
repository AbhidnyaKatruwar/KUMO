package com.example.desai.kumo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtherUploadActivity extends AppCompatActivity {

    private Button bpdf, bdoc, bmusic, bvideo, bppt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_upload);

        bpdf = (Button) findViewById(R.id.btnPdf);
        bmusic = (Button) findViewById(R.id.btnMusic);
        bvideo = (Button) findViewById(R.id.btnVideo);
        bdoc = (Button) findViewById(R.id.btnDoc);
        bppt = (Button) findViewById(R.id.btnPpt);

        bpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUploadActivity.this, PdfActivity.class);
                startActivity(intent);
            }
        });

        bppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUploadActivity.this, PptActivity.class);
                startActivity(intent);
            }
        });

        bmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUploadActivity.this, AppActivity.class);
                startActivity(intent);
            }
        });
/*
        bvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUploadActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
*/
        bdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUploadActivity.this, DocActivity.class);
                startActivity(intent);
            }
        });
    }
}
