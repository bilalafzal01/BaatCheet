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

public class ChangeStatus extends AppCompatActivity {
    private Toolbar mToolBar;
    private TextInputLayout mnewStatus;
    private Button mSaveButton;
    private ProgressDialog  mProgressDialog;


    //database
    private DatabaseReference mStatusReference;
    private FirebaseUser mUser;

    private String status2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        //firebase
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mUser.getUid();

        mStatusReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mToolBar= (Toolbar)findViewById(R.id.toolBarRegister2);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Change Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //progress
        mnewStatus = (TextInputLayout)findViewById(R.id.InputStatus);
        status2 = getIntent().getStringExtra("status_value");
        mnewStatus.getEditText().setText(status2);

        mSaveButton = (Button)findViewById(R.id.SaveStatus);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progress
                mProgressDialog = new ProgressDialog(ChangeStatus.this);
                mProgressDialog.setTitle("Saving Changes");
                mProgressDialog.setMessage("Please Wait while Changes are being saved");
                mProgressDialog.show();
                String newStatus = mnewStatus.getEditText().getText().toString();
                mStatusReference.child("status").setValue(newStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Status Updated Successfully.",Toast.LENGTH_LONG).show();
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
