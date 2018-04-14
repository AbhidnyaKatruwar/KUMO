package com.example.suruchi.kumo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button mRegbtn;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mRegbtn = (Button) findViewById(R.id.start_reg_btn);
        mLoginBtn = (Button) findViewById(R.id.start_login_btn);

        mRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Log.i("goooo3","awayyyy");

                Intent reg_Intent = new Intent(StartActivity.this, RegisterActivity.class);

                startActivity(reg_Intent);Log.i("goooo4","awayyyy");

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Log.i("jaaaa1","awayyyy");
                Intent log_Intent = new Intent(StartActivity.this, LoginActivity.class );

                startActivity(log_Intent);Log.i("ka2","awayyyy");
            }
        });


    }
}
