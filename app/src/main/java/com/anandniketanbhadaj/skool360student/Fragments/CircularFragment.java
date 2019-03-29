package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.ExpandableListAdapterCircular;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetCircularAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.CircularModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class CircularFragment extends Fragment {
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<CircularModel>> listDataChildCircular;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackCircular;
    private ExpandableListView listCircular;
    private TextView txtNoRecordsCircular;
    private Context mContext;
    private GetCircularAsyncTask getCircularAsyncTask = null;
    private ExpandableListAdapterCircular circularListAdapter = null;
    private ProgressDialog progressDialog = null;
    private ArrayList<CircularModel> circularModels = new ArrayList<>();
    private int lastExpandedPosition = -1;

    public CircularFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.circular_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        getCircularData();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsCircular = (TextView) rootView.findViewById(R.id.txtNoRecordsCircular);
        btnBackCircular = (Button) rootView.findViewById(R.id.btnBackCircular);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        listCircular = (ExpandableListView) rootView.findViewById(R.id.listCircular);
        if (Utility.checkAndRequestPermissions(mContext)) {
        }
    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.Notification = "0";
                DashBoardActivity.onLeft();
            }
        });

        listCircular.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    listCircular.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        btnBackCircular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                AppConfiguration.Notification = "0";
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
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
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    public void getCircularData() {
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
                        params.put("StandardID", Utility.getPref(mContext, "standardID"));
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getCircularAsyncTask = new GetCircularAsyncTask(params);
                        circularModels = getCircularAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (circularModels != null) {
                                    if (circularModels.size() > 0) {
                                        txtNoRecordsCircular.setVisibility(View.GONE);
                                        prepaareList();
                                        if (AppConfiguration.Notification.equalsIgnoreCase("1")) {
                                            String[] strsplit = AppConfiguration.messageNotification.split("\\-");
                                            strsplit[2] = strsplit[2].substring(0, strsplit[2].length() - 1);
                                            for (int i = 0; i < circularModels.size(); i++) {
                                                if (circularModels.get(i).getSubject().toLowerCase().trim().contains(strsplit[2].trim().toLowerCase())) {
                                                    listCircular.expandGroup(i);
                                                    listCircular.smoothScrollToPosition(i);
                                                }
                                            }

                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsCircular.setVisibility(View.VISIBLE);
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
            Utility.ping(mContext, "Network not avialable");
        }
    }

    public void prepaareList() {
        listDataHeader = new ArrayList<>();
        listDataChildCircular = new HashMap<String, ArrayList<CircularModel>>();

        String pdf;
        for (int i = 0; i < circularModels.size(); i++) {
//            Circulardemo cdemo = new Circulardemo();
//            cdemo.Date = circularModels.get(i).getDate().toString();
//            cdemo.Subject = circularModels.get(i).getSubject().toString();
            if (circularModels.get(i).getCircularPDF().equalsIgnoreCase("")) {
                listDataHeader.add(circularModels.get(i).getSubject().toString() + "|" + circularModels.get(i).getDate() + "|" + "1");
            } else {
                listDataHeader.add(circularModels.get(i).getSubject().toString() + "|" + circularModels.get(i).getDate() + "|" + circularModels.get(i).getCircularPDF());
            }
            Log.d("displaypositiondata", listDataHeader.get(0));

            ArrayList<CircularModel> rows = new ArrayList<CircularModel>();
            rows.add(circularModels.get(i));
            listDataChildCircular.put(listDataHeader.get(i), rows);
            circularListAdapter = new ExpandableListAdapterCircular(getActivity(), listDataHeader, listDataChildCircular);
            listCircular.setAdapter(circularListAdapter);
            for (int k = 0; k < circularModels.size(); k++) {
                if (!circularModels.get(k).getCircularPDF().equalsIgnoreCase("")) {
                    listCircular.setGroupIndicator(null);
                }
            }

        }
    }

}
