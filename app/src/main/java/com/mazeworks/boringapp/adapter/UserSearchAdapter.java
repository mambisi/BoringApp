package com.mazeworks.boringapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.Query;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.activity.SendMessageActitvity;
import com.mazeworks.boringapp.entities.MessageNavigationClassParser;
import com.mazeworks.boringapp.entities.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mambisiz on 7/31/17.
 */

public class UserSearchAdapter extends FirebaseRecyclerAdapter<UserSearchAdapter.ProfileViewHolder, User> {
    private Context mContext;

    public UserSearchAdapter(Context context,Query query, Class<User> itemClass) {
        super(query, itemClass);
        this.mContext = context;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_user_layout,parent,false);

        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        final User user = getItem(position);
        Glide.with(mContext).load(user.getProfileUrl()).into(holder.profileImage);
        holder.profileName.setText(user.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageNavigationClassParser.recpient = user;
                mContext.startActivity(new Intent(mContext, SendMessageActitvity.class));
            }
        });
    }

    @Override
    protected void itemAdded(User item, String key, int position) {

    }

    @Override
    protected void itemChanged(User oldItem, User newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(User item, String key, int position) {

    }

    @Override
    protected void itemMoved(User item, String key, int oldPosition, int newPosition) {

    }

    class ProfileViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profileImage;
        AppCompatTextView profileName;
        public ProfileViewHolder(View itemView) {
            super(itemView);
            profileImage = (CircleImageView) itemView.findViewById(R.id.user_profile);
            profileName = (AppCompatTextView) itemView.findViewById(R.id.name);
        }
    }
}
