package com.khab.finalCheetBaat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar loginToolBar;
    TextInputLayout Email,Password;
    Button Login;
    private ProgressDialog  mProgressDialog;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Email=findViewById(R.id.emailL);
        Password=findViewById(R.id.passwordL);

        loginToolBar=findViewById(R.id.loginToolBar);
        setSupportActionBar(loginToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setTitle("Login");
        mProgressDialog.setMessage("Please wait while we log you into your account");

        Login=findViewById(R.id.btnL);
        Login.setOnClickListener(new View.OnClickListener() {
            String email, password;
            @Override
            public void onClick(View v) {
                email = Email.getEditText().getText().toString().trim();
                password = Password.getEditText().getText().toString().trim();
                confirmInput(email, password);



            }
        });
    }

    private Boolean validateemail()
    {
        String emailInput = Email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            Email.setError("Field Can't be empty");
            return false;
        }
        else
        {
            Email.setError(null);
            return true;
        }
    }
    private Boolean validatePassword()
    {
        String passwordInput = Password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()){
            Password.setError("Field Can't be empty");
            return false;
        }
        else
        {
            Password.setError(null);
            return true;
        }
    }
    public  void confirmInput(String email1, String password1)
    {

        if (!validateemail()||!validatePassword())
        {

            Toast.makeText(getApplicationContext(), "Invalid details", Toast.LENGTH_LONG).show();

        }
        else
        {
            mProgressDialog.show();
            logIn(email1, password1);
        }
    }
    public void logIn(String email1, String password1){
        mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                    goToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToMain);
                    finish();
                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Invalid details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}

