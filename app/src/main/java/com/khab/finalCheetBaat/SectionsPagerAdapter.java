package com.khab.finalCheetBaat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                ChatsFragmentNew chatsFragment = new ChatsFragmentNew();
                return chatsFragment;
            case 1:
                FriendsFragmentNew friendsFragment = new FriendsFragmentNew();
                return friendsFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    public CharSequence getPageTitle(int pos){
        switch(pos){
            case 0:
                return "CHATS";
            case 1:
                return "FRIENDS";
            default:
                return null;
        }
    }
}
