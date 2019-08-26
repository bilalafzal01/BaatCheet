package com.khab.finalCheetBaat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolBarRegister;
    TextInputLayout Email,Password,Name,Phoneno;
    Button Confirm;
    private FirebaseAuth mAuth;
    private ProgressDialog  mProgressDialog;
    private ImageView profileImage;

    String email, password, nameString, phoneNo;
    String TAG;

    DatabaseReference userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        Name=findViewById(R.id.namer);
        Email=findViewById(R.id.emailr);
        Password=findViewById(R.id.passwordr);
        Phoneno=findViewById(R.id.phonenor);
        Confirm=findViewById(R.id.Create);
        mProgressDialog = new ProgressDialog(RegisterActivity.this);
        mProgressDialog.setTitle("Register");
        mProgressDialog.setMessage("Please wait while we register you into your account");

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getEditText().getText().toString();
                password = Password.getEditText().getText().toString();
                nameString = Name.getEditText().getText().toString();
                phoneNo = Phoneno.getEditText().getText().toString();
                confirmInput(email, password, nameString, phoneNo);
            }
        });


        toolBarRegister=findViewById(R.id.toolBarRegister);
        setSupportActionBar(toolBarRegister);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create A New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private Boolean validatename()
    {
        String nameinput = Name.getEditText().getText().toString().trim();
        if (nameinput.isEmpty()){
            Name.setError("Field Can't be empty");
            return false;
        }
        else
        {
            Name.setError(null);
            return true;
        }
    }
    private Boolean validateemail()
    {
        String emailinput = Email.getEditText().getText().toString().trim();
        if (emailinput.isEmpty()){
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
        String Passwordinput = Password.getEditText().getText().toString().trim();
        if (Passwordinput.isEmpty()){
            Password.setError("Field Can't be empty");
            return false;
        }
        else
        {
            Password.setError(null);
            return true;
        }
    }
    private Boolean validatePhoneno()
    {
        phoneNo = Phoneno.getEditText().getText().toString().trim();
        if (phoneNo.isEmpty()){
            Phoneno.setError("Field Can't be empty");
            return false;
        }
        else
        {
            Phoneno.setError(null);
            return true;
        }
    }

    public  void confirmInput(String email, String password, String Name, String PhoneNo)
    {
        if (validateemail()&&validatename()&&validatePassword()&&validatePhoneno())
        {
            mProgressDialog.show();
            createAccount(email, password, Name, PhoneNo);
        }
        else
        {
            Toast.makeText(this, "There was an error in registration", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void createAccount(String email, String password, final String Name, String PhoneNo){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uID = currentUser.getUid();
                    userDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uID);

                    HashMap<String, String> userDataMap = new HashMap<>();
                    userDataMap.put("name", Name);
                    userDataMap.put("phoneNo", phoneNo);
                    userDataMap.put("status", "Hi there, I'm using BaatCheet!");
                    userDataMap.put("online", "false");
                    //userDataMap.put("timestamp", ServerValue.TIMESTAMP.toString());
//                    userDataMap.put("image", );
//                    userDataMap.put("thumbnailImage, ");
                    userDatabase.setValue(userDataMap);
                    Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                    goToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToMain);
                    finish();
                } else {
                    Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }

            }
        });
    }
}
