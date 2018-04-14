package com.example.hp.app;

import android.media.MediaPlayer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.URL;


public class MusicStreamActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer mediaPlayer;
    private TextInputLayout mString;
    String murl;
    private Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_stream);

        mediaPlayer = new MediaPlayer();
        play = (Button)findViewById(R.id.play);
        play.setOnClickListener(this);
        mString = (TextInputLayout)findViewById(R.id.urlName);
    }

    public void onClick(View view){
        murl = mString.getEditText().getText().toString();
        try {

            if (play.getText().toString().equals("stop")) {
                play.setText("play");
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;

            } else {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(murl);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();


                    }

                });
                play.setText("stop");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
