package com.anandniketanbhadaj.skool360student.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anandniketanbhadaj.skool360student.Fragments.SuggestionFragment;
import com.anandniketanbhadaj.skool360student.Fragments.SuggestionInboxFragment;
import com.anandniketanbhadaj.skool360student.Fragments.SuggestionSentFragment;

public class SuggestionPageAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public SuggestionPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                SuggestionInboxFragment tab1 = new SuggestionInboxFragment();
                return tab1;
            case 1:
                SuggestionSentFragment tab2 = new SuggestionSentFragment();
                return tab2;
            case 2:
                SuggestionFragment tab3 = new SuggestionFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}





