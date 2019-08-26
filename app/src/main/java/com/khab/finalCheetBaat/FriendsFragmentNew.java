package com.khab.finalCheetBaat;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class FriendsFragmentNew extends Fragment {
    private View mainView;

    private RecyclerView friendsList;
    private DatabaseReference mUsersDatabase;
    private LinearLayoutManager mLayoutManager;

    public FriendsFragmentNew() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_friends_fragment_new, container, false);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mLayoutManager = new LinearLayoutManager(getContext());

        friendsList = (RecyclerView)mainView.findViewById(R.id.friensList);
        friendsList.setHasFixedSize(true);
        friendsList.setLayoutManager(mLayoutManager);

        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        super.onStart();

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(mUsersDatabase, Users.class).build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Users, UsersActivity.UsersViewHolder>(options) {

            @Override
            public UsersActivity.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);
                return new UsersActivity.UsersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersActivity.UsersViewHolder usersViewHolder, int i, @NonNull final Users users) {
                final String user_name = users.getName();
                usersViewHolder.setDisplayName(users.getName());
                usersViewHolder.setUserStatus(users.getStatus());
                final String user_id = getRef(i).getKey();

                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                        chatIntent.putExtra("user_id", user_id);
                        chatIntent.putExtra("user_name", user_name);
                        startActivity(chatIntent);
                    }
                });

            }

        };
        adapter.startListening();
        friendsList.setAdapter(adapter);
    } }




