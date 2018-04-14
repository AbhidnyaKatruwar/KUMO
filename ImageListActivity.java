package com.example.desai.kumo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class  ImageListActivity extends AppCompatActivity{

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private FirebaseStorage mFirebaseStorage;
    private Button btnDeleteimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.ListViewImage);
        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Image is loading....");
        progressDialog.show();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                //Fetch image data from firebase database
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //ImageUpload class requires default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }
                //Init adapter
                adapter = new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imgList);
                //Set adapter for listview
                lv.setAdapter(adapter);

                            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
/*
        btnDeleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ImageListActivity.this, "toooooooooooo", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

}
