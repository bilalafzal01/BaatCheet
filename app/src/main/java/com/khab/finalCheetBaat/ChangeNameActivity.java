package com.khab.finalCheetBaat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeNameActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private TextInputLayout mNewName;
    private Button mSaveButton;
    private ProgressDialog  mProgressDialog;
    private String name2;

    //database
    private DatabaseReference mNameReference;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        //firebase
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mUser.getUid();

        mNameReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mToolBar= findViewById(R.id.changeNameBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Change Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //progress
        mNewName = (TextInputLayout)findViewById(R.id.InputName);
        name2 = getIntent().getStringExtra("name");
        mNewName.getEditText().setText(name2);

        mSaveButton = (Button)findViewById(R.id.SaveName);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progress
                mProgressDialog = new ProgressDialog(ChangeNameActivity.this);
                mProgressDialog.setTitle("Saving Changes");
                mProgressDialog.setMessage("Please Wait while Changes are being saved");
                mProgressDialog.show();
                String newName = mNewName.getEditText().getText().toString();
                mNameReference.child("name").setValue(newName).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Name Updated Successfully.",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"There was some error in validating data.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
