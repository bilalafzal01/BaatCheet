package com.khab.finalCheetBaat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    //android.support.v7.widget.Toolbar a;
    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //mAuth = FirebaseAuth.getInstance();
        Button btnLogin =(Button) findViewById(R.id.buttonLogin);
        Button btnRegister =(Button) findViewById(R.id.buttonRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(new Intent(StartActivity.this,LoginActivity.class));
                startActivity(it1);
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
