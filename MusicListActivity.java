package com.example.hp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MusicListActivity extends AppCompatActivity {

    public static final String STORAGE_PATH_UPLOADS_AUDIO = "audios/";
    public static final String DATABASE_PATH_UPLOADS_AUDIO = "audios";


    private DatabaseReference mDatabaseRef;
    private List<AudioUpload> audList;
    private ListView lv;
    private AudioListAdapter adapter;
    private ProgressDialog progressDialog;
    private FirebaseStorage mFirebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);



        audList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewAudio);

        //adding a clicklistener on listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the upload
                AudioUpload upload = audList.get(i);

                //Opening the upload file in browser using the upload url
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        });
        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Audio is loading....");
        progressDialog.show();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MusicActivity.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Success","inside mdatabaseref");
                progressDialog.dismiss();
                //Fetch image data from firebase database
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //ImageUpload class requires default constructor
                    AudioUpload audio = snapshot.getValue(AudioUpload.class);
                    audList.add(audio);
                }
                //Init adapter
                adapter = new AudioListAdapter(MusicListActivity.this, R.layout.audio_item, audList);
                //Set adapter for listview
                lv.setAdapter(adapter);

                String[] uploads = new String[audList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = audList.get(i).getName();
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}
