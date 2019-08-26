package com.khab.finalCheetBaat;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class DeleteAccountActivity extends AppCompatActivity {

    Button btndelete;
    TextInputLayout Password;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private Toolbar mainDeleteBar;
    String password1;
    String email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        Password = findViewById(R.id.deleteAccountPassword);
        btndelete = findViewById(R.id.buttondeleteaccount);

        mainDeleteBar = findViewById(R.id.deleteAccountBar);
        setSupportActionBar(mainDeleteBar);
        getSupportActionBar().setTitle("Delete Account");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount(mAuth.getCurrentUser().getEmail(),Password.getEditText().getText().toString().trim());
            }
        });
    }
    public void deleteAccount(String email1, String password1){
        AuthCredential credential = EmailAuthProvider.getCredential(email1, password1);
        user.reauthenticate(credential).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "User successfully deleted!", Toast.LENGTH_LONG).show();
                                Intent goToStart = new Intent(DeleteAccountActivity.this, StartActivity.class);
                                startActivity(goToStart);
                                goToStart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Error deleting user!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
