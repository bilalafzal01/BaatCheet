package com.khab.finalCheetBaat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //for tab layout(chats, contacts etc)
    private SectionsPagerAdapter mSectionPagerAdapter;
    private TabLayout mainTabLayout;

    //for left navigation bar
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle navigationToggle;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        //main toolbar with search button etc
        Toolbar mainAppBar = (Toolbar) findViewById(R.id.mainAppBar);
        setSupportActionBar(mainAppBar);
        getSupportActionBar().setTitle("BaatCheet");

        //viewpager for fragments
        ViewPager mainViewPager = findViewById(R.id.TabPager);
        mSectionPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(mSectionPagerAdapter);

        //tab layout for chats, groups and contacts
        mainTabLayout = findViewById(R.id.main_tabLayout);
        mainTabLayout.setupWithViewPager(mainViewPager);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //for left navigation panel
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutMain);
        navigationToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        navigationToggle.syncState();
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationToggle.setDrawerIndicatorEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.navigationDarkMode:
                        Toast.makeText(getApplicationContext(), "Dark mode enabled", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.navigationNotifications:
                        Toast.makeText(getApplicationContext(), "Notifications!",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.navigationAddaFriend:
                        Toast.makeText(getApplicationContext(), "Add a Friend!",Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return true;
                }
                //return true;
            }
        });
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
           Intent goToStart = new Intent(getApplicationContext(), StartActivity.class);
           startActivity(goToStart);
           finish();
        }
        else{
            mUserRef.child("online").setValue("true");
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseApp.initializeApp(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            mUserRef.child("online").setValue("true");
        }
        else{
            Intent goToStart = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(goToStart);
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                Intent goToStart = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(goToStart);
                finish();
                return true;
            case R.id.main_app_bar_search:
                Toast.makeText(getApplicationContext(), "Search called!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteAccount:
                startActivity(new Intent(MainActivity.this, DeleteAccountActivity.class));
                return true;
            case R.id.changePassword:
                startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
                return true;
            case R.id.profileSettings:
                startActivity( new Intent(getApplicationContext(), ProfileSettings.class));
                return true;
            case R.id.allusers:
                startActivity( new Intent(getApplicationContext(), UsersActivity.class));
                return true;
            default:
                return false;

        }
    }
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigationToggle.syncState();
    }

}
