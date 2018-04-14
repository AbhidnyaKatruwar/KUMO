package com.example.hp.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button music,video,videoPlayer,musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        music = (Button)findViewById(R.id.music);
        video = (Button)findViewById(R.id.video);
        videoPlayer = (Button)findViewById(R.id.videoPlayer);
        musicPlayer = (Button)findViewById(R.id.musicPlayer);

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MusicActivity.class);
                startActivity(i);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,VideoActivity.class);
                startActivity(i);
            }
        });
        videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ListVideoPlayerActivity.class);
                startActivity(i);
            }
        });
        musicPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MusicStreamActivity.class);
                startActivity(i);
            }
        });
    }


}
