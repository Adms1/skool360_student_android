package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.ExpandableListAdapterInbox;
import com.anandniketanbhadaj.skool360student.AsyncTasks.SuggestionInboxAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.Suggestion.InboxFinalArray;
import com.anandniketanbhadaj.skool360student.Models.Suggestion.SuggestionInboxModel;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SuggestionSentFragment extends Fragment {
    private View rootView;
    private TextView txtNoRecordsSent;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private SuggestionInboxAsyncTask suggestionSentAsyncTask = null;
    private int lastExpandedPosition = -1;
    private LinearLayout Sent_header;

    ExpandableListAdapterInbox expandableListAdapterSent;
    ExpandableListView lvExpSent;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<InboxFinalArray>> listDataChild = new HashMap<>();
    SuggestionInboxModel response;
    String finalMessageIdArray;
    String PageType="Sent";
    public SuggestionSentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggestion_sent, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        txtNoRecordsSent = (TextView) rootView.findViewById(R.id.txtNoRecordsSent);
        lvExpSent = (ExpandableListView) rootView.findViewById(R.id.lvExpSent);
        Sent_header = (LinearLayout) rootView.findViewById(R.id.Sent_header);
        setUserVisibleHint(true);

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getSentData();
        }
        // execute your data loading logic.
    }

    public void setListners() {
        lvExpSent.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpSent.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getSentData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
//                        params.put("UserType", "student");
                        params.put("MessgaeType", "Sent");
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        suggestionSentAsyncTask = new SuggestionInboxAsyncTask(params);
                        response = suggestionSentAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (response!=null){
                                if (response.getFinalArray().size() >=0) {
                                    txtNoRecordsSent.setVisibility(View.GONE);
//                                    Sent_header.setVisibility(View.VISIBLE);
                                    setExpandableListData();
                                    expandableListAdapterSent = new ExpandableListAdapterInbox(getActivity(), listDataHeader, listDataChild, PageType);
                                    lvExpSent.setAdapter(expandableListAdapterSent);
//                                    lvExpSent.deferNotifyDataSetChanged();
//                                    expandableListAdapterSent.notifyDataSetChanged();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsSent.setVisibility(View.VISIBLE);
//                                    Sent_header.setVisibility(View.GONE);
                                }
                                } else {
                                    Intent serverintent = new Intent(mContext, Server_Error.class);
                                    startActivity(serverintent);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void setExpandableListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<InboxFinalArray>>();

        for (int j = 0; j < response.getFinalArray().size(); j++) {
            listDataHeader.add(response.getFinalArray().get(j).getDate() + "|" +
                    response.getFinalArray().get(j).getSubject());

            ArrayList<InboxFinalArray> rows = new ArrayList<InboxFinalArray>();
            rows.add(response.getFinalArray().get(j));
            listDataChild.put(listDataHeader.get(j), rows);
        }
        if(listDataChild.size()>0)
        {
//            Sent_header.setVisibility(View.VISIBLE);
            txtNoRecordsSent.setVisibility(View.GONE);
        }else{
            Sent_header.setVisibility(View.GONE);
            txtNoRecordsSent.setVisibility(View.VISIBLE);
        }
    }
}

