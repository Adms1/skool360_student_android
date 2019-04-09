package com.anandniketanbhadaj.skool360student.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Adapter.SuggestionPageAdapter;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;


public class SuggestionMainFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBack;
    View view;
    private TabLayout tablayout_ptm_main;
    private ViewPager viewPager;
    private Context mContext;
    LinearLayout linearBack;
    SuggestionPageAdapter adapter;

    public SuggestionMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggestion_main, container, false);
        mContext = getActivity();

        AppConfiguration.position = 17;

        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout

        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBack= (Button) rootView.findViewById(R.id.btnBack);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        view = (View) rootView.findViewById(R.id.view);

        tablayout_ptm_main = (TabLayout) rootView.findViewById(R.id.tablayout_ptm_main);
        tablayout_ptm_main.addTab(tablayout_ptm_main.newTab().setText("Inbox"),true);
        tablayout_ptm_main.addTab(tablayout_ptm_main.newTab().setText("Sent"));
        tablayout_ptm_main.addTab(tablayout_ptm_main.newTab().setText("Create"));
        tablayout_ptm_main.setTabMode(TabLayout.MODE_FIXED);
        tablayout_ptm_main.setTabGravity(TabLayout.GRAVITY_FILL);


        adapter = new SuggestionPageAdapter(getFragmentManager(), tablayout_ptm_main.getTabCount());
//Adding adapter to pager
        viewPager.setAdapter(adapter);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setListner() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                tablayout_ptm_main));
        tablayout_ptm_main.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
}
