package com.anandniketanbhadaj.skool360student.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class HomeImageAdapter extends PagerAdapter {
    Context context;
    FragmentActivity activity;
    int imageArray[];

    public HomeImageAdapter(FragmentActivity act, int[] imgArra) {
        imageArray = imgArra;
        activity = act;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    private int pos = 0;

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mwebView = new ImageView(activity);
        ((ViewPager) container).addView(mwebView, 0);
        mwebView.setScaleType(ImageView.ScaleType.FIT_XY);
        mwebView.setImageResource(imageArray[pos]);

        if (pos >= imageArray.length - 1)
            pos = 0;
        else
            ++pos;
        return mwebView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}

