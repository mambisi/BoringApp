package com.mazeworks.boringapp.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.adapter.UserSearchAdapter;
import com.mazeworks.boringapp.entities.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUsersFragment extends Fragment {

@BindView(R.id.users_list)
    RecyclerView usersList;
    public SearchUsersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchUsersFragment newInstance() {
        SearchUsersFragment fragment = new SearchUsersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_search_users, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Query query = FirebaseDatabase.getInstance().getReference("users");
        UserSearchAdapter adapter = new UserSearchAdapter(getContext(),query,User.class);
        usersList.setAdapter(adapter);
        usersList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
