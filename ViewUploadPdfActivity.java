package com.example.desai.kumo;

import android.content.Intent;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewUploadPdfActivity extends AppCompatActivity {
    //the listview
    ListView listView;

    public static final String STORAGE_PATH_UPLOADS_PDF = "pdfs/";
    public static final String DATABASE_PATH_UPLOADS_PDF = "pdfs";


    //database reference to get uploads data
    DatabaseReference mDatabaseReference;

    //list to store uploads data
    List<PdfUpload> uploadList;
    private PdfListAdapter pdfAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_upload_pdf);


        uploadList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);


        //adding a clicklistener on listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the upload
                PdfUpload upload = uploadList.get(i);

                //Opening the upload file in browser using the upload url
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        });


        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS_PDF);

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PdfUpload upload = postSnapshot.getValue(PdfUpload.class);
                    uploadList.add(upload);
                }

                pdfAdapter = new PdfListAdapter(ViewUploadPdfActivity.this, R.layout.upload_item,uploadList);
                 listView.setAdapter(pdfAdapter);

                String[] uploads = new String[uploadList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadList.get(i).getName();
                 }
                        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

