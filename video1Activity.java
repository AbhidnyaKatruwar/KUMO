package com.example.suruchi.kumo;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.TextInputLayout;

import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class video1Activity extends AppCompatActivity implements  View.OnClickListener{

    private TextInputLayout mString;


    ProgressDialog mDialog;
    VideoView videoView;
    ImageButton btnPlayPause;

    String murl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("chalaaa444","jaaa");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video1);

        videoView = (VideoView) findViewById(R.id.videoView);
        btnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);
        btnPlayPause.setOnClickListener(this);Log.i("chalaaa445","jaaa");

        mString = (TextInputLayout) findViewById(R.id.urlname);




    }



    @Override
    public void onClick(View view) {
        murl = mString.getEditText().getText().toString();

        mDialog = new ProgressDialog(video1Activity.this);
        mDialog.setMessage("Buffering...");Log.i("chalaaa446","jaaa");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();


        try{

            if(!videoView.isPlaying()) {Log.i("chalaaa447","jaaa");

                Uri uri = Uri.parse(murl);
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {Log.i("chalaaa448","jaaa");
                        btnPlayPause.setImageResource(R.drawable.ic_play);

                    }
                });
                mDialog.dismiss();Log.i("chalaaa449","jaaa");


            }
            else
            {
                videoView.pause();Log.i("chalaaa450","jaaa");
                btnPlayPause.setImageResource(R.drawable.ic_play    );
            }

        }
        catch(Exception ex  ){

        }

        videoView.requestFocus();Log.i("chalaaa451","jaaa");
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mDialog.dismiss();Log.i("chalaaa452","jaaa");
                mp.setLooping(true);
                videoView.start();
                btnPlayPause.setImageResource(R.drawable.ic_play );
            }
        });




    }
}
