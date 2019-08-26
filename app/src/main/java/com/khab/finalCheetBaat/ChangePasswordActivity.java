package com.khab.finalCheetBaat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    Toolbar changePasswordBar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextInputLayout oldPassword,newPassword1,newPassword2;
    FirebaseUser user = mAuth.getCurrentUser();
    Button Button;
    String password2;
    String password1;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mProgressDialog = new ProgressDialog(ChangePasswordActivity.this);
        mProgressDialog.setTitle("Change Password");
        mProgressDialog.setMessage("Please wait while we change your password");

        changePasswordBar = findViewById(R.id.changePasswordBar);
        setSupportActionBar(changePasswordBar);
        getSupportActionBar().setTitle("Change Your Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled( true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        oldPassword=findViewById(R.id.passwordOld);
        newPassword1=findViewById(R.id.passwordNew1);
        newPassword2=findViewById(R.id.passwordNew2);
        Button=findViewById(R.id.buttonChangePassword);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(mAuth.getCurrentUser().getEmail(),oldPassword.getEditText().getText().toString().trim());
            }
        });
    }
    public void changePassword(String email1, String password){

        AuthCredential credential = EmailAuthProvider.getCredential(email1, password);
        user.reauthenticate(credential).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    password1 = newPassword1.getEditText().getText().toString().trim();
                    password2 = newPassword2.getEditText().getText().toString().trim();
                    if(password1.equals(password2)){
                        user.updatePassword(password1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Successfully changed", Toast.LENGTH_LONG).show();
                                mProgressDialog.show();
                                Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(goToMain);
                                finish();
                            }
                        });
                    }else{
                        newPassword2.setError("Password does not match");
                    }

                }else{
                    oldPassword.setError("Invalid Password");
                }
            }
        });
    }
}