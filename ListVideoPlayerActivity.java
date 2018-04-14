package com.example.hp.app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import static com.example.hp.app.MusicActivity.REQUEST_CODE;

public class ListVideoPlayerActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1234;
    private final static int PICK_AUDIO_CODE = 2342;

    private Button choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video_player);

        choose = (Button)findViewById(R.id.choose_video);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Audio"),REQUEST_CODE);

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("success", "on activity result");
        // TextView uriTxt = (TextView)findViewById(R.id.editTextFileNameaudio);

        //when the user choses the file
        //requestCode == PICK_AUDIO_CODE &&
        if ( resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                Log.i("success", "data.getdata");
               //play video


               // String fileName = "video";
               // String filePlace =  "android.resource://" + getPackageName() + "/raw/" + fileName;
                VideoView videoView = (VideoView)findViewById(R.id.videoView);
                videoView.setVideoURI(Uri.parse(String.valueOf(data.getData())));

                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.setLooping(true);
                    }
                });

                videoView.setMediaController(new MediaController(this));

                videoView.start();

            }else{
                Log.i("success", "else");
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.i("Failure","req code");
        }

        super.onActivityResult(requestCode, resultCode, data);
        // super.onActivityResult(requestCode, resultCode, data);
    }
}
