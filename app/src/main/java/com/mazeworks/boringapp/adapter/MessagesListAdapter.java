package com.mazeworks.boringapp.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.Query;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.entities.Post;

/**
 * Created by mambisiz on 7/31/17.
 */

public class MessagesListAdapter extends FirebaseRecyclerAdapter<MessagesListAdapter.ProfileViewHolder, Post> {
    private Context mContext;

    public MessagesListAdapter(Context context, Query query, Class<Post> itemClass) {
        super(query, itemClass);
        this.mContext = context;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_message,parent,false);

        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        final Post p = getItem(position);

        holder.messageText.setText(p.getMessage());
    }

    @Override
    protected void itemAdded(Post item, String key, int position) {

    }

    @Override
    protected void itemChanged(Post oldItem, Post newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Post item, String key, int position) {

    }

    @Override
    protected void itemMoved(Post item, String key, int oldPosition, int newPosition) {

    }

    class ProfileViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView messageText;
        public ProfileViewHolder(View itemView) {
            super(itemView);
            messageText = (AppCompatTextView) itemView.findViewById(R.id.message_text);
        }
    }
}
