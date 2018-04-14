package com.example.hp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    public static final String STORAGE_PATH_UPLOADS_VIDEO = "videos/";
    public static final String DATABASE_PATH_UPLOADS_VIDEO = "videos";


    private DatabaseReference mDatabaseRef;
    private List<VideoUpload> vidList;
    private ListView lv;
    private VideoListAdapter vadapter;
    private ProgressDialog progressDialog;
    private FirebaseStorage mFirebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Image is loading....");
        progressDialog.show();


        vidList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.ListViewVideo);

       // mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                //Fetch image data from firebase database
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //ImageUpload class requires default constructor
                    VideoUpload video = snapshot.getValue(VideoUpload.class);
                    vidList.add(video);
                }
                //Init adapter
                vadapter = new VideoListAdapter(VideoListActivity.this, R.layout.video_item, vidList);
                //Set adapter for listview
                lv.setAdapter(vadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

}