package com.mazeworks.boringapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mazeworks.boringapp.layout.MessagesFragment;


/**
 * Created by mambisiz on 7/31/17.
 */

public class MessagesFragmentPager extends FragmentPagerAdapter {



    public MessagesFragmentPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return MessagesFragment.newInstance("REC");
            case 1:
                return MessagesFragment.newInstance("SEN");

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Recieved";
            case 1:
                return "Sent";

        }

        return null;
    }
}
