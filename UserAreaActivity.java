package com.example.suruchi.kumo;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.ActionBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

public class UserAreaActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;


    public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
        Log.i("chalaaa30","jaaa");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.i("chalaaa31","jaaa");
        if(currentUser == null){
            sendToStart();
        }
        else{

            mUserRef.child("online").setValue("true");

        }

    }


/*
    protected void onStop() {
        super.onStop();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }

    }*/


    private void sendToStart(){
        Intent startIntent = new Intent(UserAreaActivity.this, StartActivity.class);
        startActivity(startIntent);Log.i("chalaaa31","jaaa");
        finish();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);Log.i("chalaaa35","jaaa");

        mAuth = FirebaseAuth.getInstance();
        Log.i("chalaaa37","jaaa");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();Log.i("chalaaa36","jaaa");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i("chalaaa38","jaaa");


        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_menu);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case(R.id.nav_viewImage):
                        Intent viewImgActivity = new Intent(getApplicationContext(), ImageActivity.class);
                        startActivity(viewImgActivity);
                        break;

                    case(R.id.nav_share):
                        Intent chatActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(chatActivity);
                        break;

                    case(R.id.nav_logout):
                        Intent logActivity = new Intent(getApplicationContext(), StartActivity.class);
                        startActivity(logActivity);
                        break;

                    case(R.id.nav_upload):
                        Intent upActivity = new Intent(getApplicationContext(), UploadMenu.class);
                        startActivity(upActivity);
                        break;


                    case(R.id.nav_mv):
                        Intent videoActivity = new Intent(getApplicationContext(), OptionActivity.class);
                        startActivity(videoActivity);
                        break;

                    case(R.id.nav_music):
                        Intent mvActivity = new Intent(getApplicationContext(), ListActivity.class);
                        startActivity(mvActivity);
                        break;

                    case(R.id.nav_video):
                        Intent mv1Activity = new Intent(getApplicationContext(), ListVideoPlayerActivity.class);
                        startActivity(mv1Activity);
                        break;




                }
                return true;
            }
        });


        final EditText etUsername = (EditText) findViewById(R.id.login_email);
       // final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);



       // String message = " Welcome to KUMO :)";
       // welcomeMessage.setText(message);






    }











    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
