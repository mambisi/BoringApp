package com.mazeworks.boringapp.layout;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazeworks.boringapp.adapter.MessagesFragmentPager;
import com.mazeworks.boringapp.R;

public class MessagesContainerFragment extends Fragment {

    private TabLayout mTabLayout;

    public MessagesContainerFragment() {
        // Required empty public constructor
    }


    public static MessagesContainerFragment newInstance() {

        Bundle args = new Bundle();

        MessagesContainerFragment fragment = new MessagesContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messages_container, container, false);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabs);
        FragmentManager manager = getChildFragmentManager();
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setAdapter(new MessagesFragmentPager(manager));
        mTabLayout.setupWithViewPager(viewPager);
        return v;
    }

}
