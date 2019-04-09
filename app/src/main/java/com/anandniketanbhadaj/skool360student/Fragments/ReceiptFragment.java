package com.anandniketanbhadaj.skool360student.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;


public class ReceiptFragment extends Fragment {

    Fragment fragment;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private Context mContext;
    private FragmentManager fragmentManager = null;
    private WebView receipt_view;
    private String recieptUrl;

    public ReceiptFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_receipt, container, false);
        mContext = getActivity();
        recieptUrl = getArguments().getString("url");

        AppConfiguration.position = 18;
        AppConfiguration.firsttimeback = true;

        recieptUrl = (recieptUrl.substring(1, recieptUrl.length() - 1));
        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        receipt_view = (WebView) rootView.findViewById(R.id.receipt_view);
        WebSettings webSettings = receipt_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        receipt_view.getSettings().setUseWideViewPort(true);
        receipt_view.getSettings().setLoadWithOverviewMode(true);
        receipt_view.setInitialScale(10);
        receipt_view.getSettings().setBuiltInZoomControls(true);
        // Force links and redirects to open in the WebView instead of in a browser
        receipt_view.setWebViewClient(new WebViewClient());
        receipt_view.loadUrl(recieptUrl);
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackUnitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new PaymentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new PaymentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }


}
