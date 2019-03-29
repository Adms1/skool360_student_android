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
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;


public class PayOnlineFragment extends Fragment {
    WebView webview_report_card;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private String url;

    public PayOnlineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pay_online, container, false);
        mContext = getActivity();

        initViews();
        setListner();
        return rootView;
    }

    public void initViews() {
        url = getArguments().getString("url");

        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        webview_report_card = (WebView) rootView.findViewById(R.id.webview);
        WebSettings webSettings = webview_report_card.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_report_card.getSettings().setUseWideViewPort(true);
        webview_report_card.getSettings().setLoadWithOverviewMode(true);
        webview_report_card.getSettings().setBuiltInZoomControls(true);
        // Force links and redirects to open in the WebView instead of in a browser
        webview_report_card.setWebViewClient(new WebViewClient());
        webview_report_card.loadUrl(url);
    }

    public void setListner() {
        btnBackUnitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PaymentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PaymentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }
}
