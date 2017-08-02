package com.mazeworks.boringapp.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mazeworks.boringapp.adapter.MessagesListAdapter;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.activity.MainActivity;
import com.mazeworks.boringapp.entities.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mambisiz on 7/31/17.
 */

public class MessagesFragment extends Fragment {
    private String boxType;

    @BindView(R.id.messages_list)
    RecyclerView mMessageList;

    public static MessagesFragment newInstance(String boxType) {

        Bundle args = new Bundle();
        args.putString("BOX_TYPE", boxType);
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
           this.boxType = getArguments().getString("BOX_TYPE");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_messages,container,false);
        ButterKnife.bind(this,v);



        Query query =  null;

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(getActivity(), MainActivity.class));
        }

        if(boxType.equals("REC")){
            query = FirebaseDatabase.getInstance().getReference().child("messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("inbox");
            query.orderByChild("timeStamp");
        }
        else if (boxType.equals("SEN")){
           query = FirebaseDatabase.getInstance().getReference().child("messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("outbox");
            query.orderByChild("timeStamp");
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        MessagesListAdapter adapter = new MessagesListAdapter(getContext(),query,Post.class);
        mMessageList.setAdapter(adapter);
        mMessageList.setLayoutManager(mLayoutManager);

        return v;
    }
}
