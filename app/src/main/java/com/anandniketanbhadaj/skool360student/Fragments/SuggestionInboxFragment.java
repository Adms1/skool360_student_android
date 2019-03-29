package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class SuggestionInboxFragment extends Fragment {
    private View rootView;
    private TextView txtNoRecordsinbox;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private SuggestionInboxAsyncTask suggestionInboxAsyncTask = null;
    private int lastExpandedPosition = -1;
    private LinearLayout inbox_header;
    InboxFinalArray mainInboxFinalArray;

    ExpandableListAdapterInbox expandableListAdapterInbox;
    ExpandableListView lvExpinbox;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<InboxFinalArray>> listDataChild = new HashMap<>();
    SuggestionInboxModel response;
    String PageType="Inbox";

    public SuggestionInboxFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggestion_inbox, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        txtNoRecordsinbox = (TextView) rootView.findViewById(R.id.txtNoRecordsinbox);
        lvExpinbox = (ExpandableListView) rootView.findViewById(R.id.lvExpinbox);
        inbox_header = (LinearLayout) rootView.findViewById(R.id.inbox_header);
        setUserVisibleHint(true);

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getInboxData();
        }
        // execute your data loading logic.
    }

    public void setListners() {
        lvExpinbox.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpinbox.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getInboxData() {
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
//                        params.put("UserType", "Student");
                        params.put("MessgaeType", "Inbox");
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        suggestionInboxAsyncTask = new SuggestionInboxAsyncTask(params);
                        response = suggestionInboxAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (response!=null){
                                if (response.getFinalArray().size() > 0) {
                                    txtNoRecordsinbox.setVisibility(View.GONE);
                                    setExpandableListData();

                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsinbox.setVisibility(View.VISIBLE);
                                    inbox_header.setVisibility(View.GONE);
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
            listDataHeader.add(response.getFinalArray().get(j).getReplyDate() + "|" +
                    response.getFinalArray().get(j).getSubject()+"|"+response.getFinalArray().get(j).getDate());


            ArrayList<InboxFinalArray> rows = new ArrayList<InboxFinalArray>();
            rows.add(response.getFinalArray().get(j));
            listDataChild.put(listDataHeader.get(j), rows);
            expandableListAdapterInbox = new ExpandableListAdapterInbox(getActivity(), listDataHeader, listDataChild,PageType);
            lvExpinbox.setAdapter(expandableListAdapterInbox);
            expandableListAdapterInbox.notifyDataSetChanged();
            lvExpinbox.deferNotifyDataSetChanged();

            ArrayList<String> dateArray=new ArrayList<>();
            if (AppConfiguration.Notification.equalsIgnoreCase("1")) {
                if (AppConfiguration.messageNotification.contains("-")) {
                    String[] strsplit = AppConfiguration.messageNotification.split("\\-");
                  //  strsplit[2] = strsplit[2].substring(0, strsplit[2].length() - 1);
                    for (int i = 0; i < response.getFinalArray().size(); i++) {
                        String inputPattern = "yyyy-MM-dd";
                        String outputPattern = "dd/MM/yyyy";


                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);


                        Date startdateTime = null;
                        String str = null;

                        try {
                            startdateTime = inputFormat.parse(response.getFinalArray().get(i).getDate());
                            str = outputFormat.format(startdateTime);
                            Log.d("str", str);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateArray.add(str);
                            if (str.trim().equalsIgnoreCase(strsplit[2].trim())) {
                                lvExpinbox.expandGroup(i);
                            }
                    }
                }
//            listannouncment.expandGroup(0);
            }
        }
    }
}
