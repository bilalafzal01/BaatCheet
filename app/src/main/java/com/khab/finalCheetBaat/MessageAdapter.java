package com.khab.finalCheetBaat;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;

import java.util.List;



public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mCurrentUser = mAuth.getCurrentUser();
    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;

    public MessageAdapter(List<Messages> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout ,parent, false);
        return new MessageViewHolder(v);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        //public ImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;

        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            //profileImage = (ImageView) view.findViewById(R.id.user_single_image);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_profile_layout);
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);

        String from_user = c.getFrom();
        String message_type = c.getType();

        if(from_user.equals(mCurrentUser.getUid())){
            viewHolder.messageText.setBackgroundResource(R.drawable.message_user_background);
            viewHolder.messageText.setTextColor(Color.BLACK);
            viewHolder.messageImage.setBackgroundResource(R.drawable.default_avatar);
        }else{
            viewHolder.messageText.setBackgroundResource(R.drawable.message_main_background);
            viewHolder.messageText.setTextColor(Color.WHITE);
            viewHolder.messageImage.setBackgroundResource(R.drawable.other_user_avatar);
        }

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                //String image = dataSnapshot.child("thumb_image").getValue().toString();

                viewHolder.displayName.setText(name);

//                Picasso.with(viewHolder.profileImage.getContext()).load(image).placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(message_type.equals("text")) {

            viewHolder.messageText.setText(c.getMessage());
            //viewHolder.messageImage.setVisibility(View.INVISIBLE);


        } else {

            viewHolder.messageText.setVisibility(View.INVISIBLE);
            //Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage()).placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);

        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}

