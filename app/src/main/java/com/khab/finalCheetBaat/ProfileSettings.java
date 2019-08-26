package com.khab.finalCheetBaat;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileSettings extends AppCompatActivity {

    private DatabaseReference mReference;
    private FirebaseUser mUser;
    private ImageView profileImage;
    private Button changeNameButton, changeImageButton, changeStatusButton;
    private TextView profileStatus, profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        profileImage = findViewById(R.id.profileImage);
        changeImageButton = findViewById(R.id.buttonChangeImage);
        changeNameButton = findViewById(R.id.buttonChangeName);
        changeStatusButton = findViewById(R.id.buttonChangeStatus);
        profileStatus = findViewById(R.id.profileStatus);
        profileName = findViewById(R.id.profileName);
        //firebase
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = mUser.getUid();
        mReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        mReference.keepSynced(true);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                profileName.setText(name);
                String status = dataSnapshot.child("status").getValue().toString();
                profileStatus.setText(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String status_value = profileStatus.getText().toString();
                    Intent status_intent = new Intent(ProfileSettings.this, ChangeStatus.class);
                    status_intent.putExtra("status_value", status_value);
                    startActivity(status_intent);
                }catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(), "NullPointer Error!", Toast.LENGTH_LONG).show();
                }
            }

        });
        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_value = profileName.getText().toString();
                Intent name_intent = new Intent(ProfileSettings.this, ChangeNameActivity.class);
                name_intent.putExtra("name", name_value);
                startActivity(name_intent);
            }


        });
    }
}