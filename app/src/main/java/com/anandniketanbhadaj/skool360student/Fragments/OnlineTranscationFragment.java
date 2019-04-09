package com.anandniketanbhadaj.skool360student.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.Adapter.OnlinePaymentAdapter;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetPaymentLedgerAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.Suggestion.SuggestionInboxModel;
import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.util.HashMap;

public class OnlineTranscationFragment extends Fragment {
    Fragment fragment;
    OnlinePaymentAdapter onlinePaymentAdapter;
    LinearLayout linearBack;
    TextView txtNoRecordsUnitTest;
    SuggestionInboxModel paymentdetailsModel;
    private View rootView;
    private Context mContext;
    private GetPaymentLedgerAsyncTask getPaymentLedgerAsyncTask = null;
    private RecyclerView payment_online_report_list;
    private LinearLayout lv_header;


    public OnlineTranscationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_online_transcation, container, false);
        mContext = getActivity();

        initViews();
        setListners();


        return rootView;
    }

    public void initViews() {
        payment_online_report_list = rootView.findViewById(R.id.payment_online_report_list);
        lv_header = rootView.findViewById(R.id.lv_header);
        txtNoRecordsUnitTest = rootView.findViewById(R.id.txtNoRecordsUnitTest);
        setUserVisibleHint(true);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getPaymentLedger();
        }
        // execute your data loading logic.
    }

    public void setListners() {

    }


    public void getPaymentLedger() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Utility.isNetworkConnected(mContext)) {
                    try {

                        HashMap<String, String> params = new HashMap<>();
                        params.put("studentid", Utility.getPref(mContext, "studid"));
                        params.put("TermID", Utility.getPref(mContext, "TermID"));
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getPaymentLedgerAsyncTask = new GetPaymentLedgerAsyncTask(params);
                        paymentdetailsModel = getPaymentLedgerAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (paymentdetailsModel != null) {
                                    if (paymentdetailsModel.getOnlineTransaction().size() > 0) {
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                        lv_header.setVisibility(View.VISIBLE);
                                        payment_online_report_list.setVisibility(View.VISIBLE);
                                        onlinePaymentAdapter = new OnlinePaymentAdapter(mContext, paymentdetailsModel);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                        payment_online_report_list.setLayoutManager(mLayoutManager);
                                        payment_online_report_list.setItemAnimator(new DefaultItemAnimator());
                                        payment_online_report_list.setAdapter(onlinePaymentAdapter);
                                    } else {
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                        lv_header.setVisibility(View.GONE);
                                        payment_online_report_list.setVisibility(View.GONE);
                                    }
                                }
//                                else {
//                                    Intent serverintent = new Intent(mContext, Server_Error.class);
//                                    startActivity(serverintent);
//                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Utility.ping(mContext, "Network not available");
                }
            }
        }).start();
    }
}
