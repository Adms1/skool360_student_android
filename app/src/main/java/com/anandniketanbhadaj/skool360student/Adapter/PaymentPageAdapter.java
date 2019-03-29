package com.anandniketanbhadaj.skool360student.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.anandniketanbhadaj.skool360student.Fragments.OnlineTranscationFragment;
import com.anandniketanbhadaj.skool360student.Fragments.PaymentReceiptFragment;

public class PaymentPageAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PaymentPageAdapter(FragmentManager fm, int tabCount) {
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
                PaymentReceiptFragment tab1 = new PaymentReceiptFragment();
                return tab1;
            case 1:
                OnlineTranscationFragment tab2 = new OnlineTranscationFragment();
                return tab2;
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





