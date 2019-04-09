package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.anandniketanbhadaj.skool360student.Adapter.ExpandableListAdapterAnnouncementnew;
import com.anandniketanbhadaj.skool360student.AsyncTasks.AnnouncmentAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamFinalArray;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AnnouncmentFragment extends Fragment {
    ExamModel announcmentmodelReponse;
    List<String> listDataHeader;
    HashMap<String, ArrayList<ExamFinalArray>> listDataChild;
    ExpandableListAdapterAnnouncementnew expandableListAdapterAnnouncement;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBack;
    private ExpandableListView listannouncment;
    private TextView txtNoRecords;
    private Context mContext;
    private int lastExpandedPosition = -1;
    private AnnouncmentAsyncTask announcmentAsyncTask = null;
    private ProgressDialog progressDialog = null;

    public AnnouncmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_announcment, container, false);
        mContext = getActivity();

        AppConfiguration.position = 17;


        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecords = (TextView) rootView.findViewById(R.id.txtNoRecords);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        listannouncment = (ExpandableListView) rootView.findViewById(R.id.listannouncment);
        if (Utility.checkAndRequestPermissions(mContext)) {
        }
        getUnitTestData();

    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.Notification = "0";
                DashBoardActivity.onLeft();
            }
        });

        listannouncment.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    listannouncment.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.Notification = "0";
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.Notification = "0";
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

    public void getUnitTestData() {
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
                        announcmentAsyncTask = new AnnouncmentAsyncTask(params);
                        announcmentmodelReponse = announcmentAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (announcmentmodelReponse != null) {
                                    if (announcmentmodelReponse.getSuccess().equalsIgnoreCase("True")) {
                                        if (announcmentmodelReponse.getFinalArray().size() > 0) {
                                            txtNoRecords.setVisibility(View.GONE);
                                            progressDialog.dismiss();
                                            prepaareList();
                                        } else {
                                            progressDialog.dismiss();
                                            txtNoRecords.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecords.setVisibility(View.VISIBLE);

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

    public void prepaareList() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<ExamFinalArray>>();

        for (int i = 0; i < announcmentmodelReponse.getFinalArray().size(); i++) {
            listDataHeader.add(announcmentmodelReponse.getFinalArray().get(i).getCreateDate() + "|" +
                    announcmentmodelReponse.getFinalArray().get(i).getSubject() + "|" +
                    announcmentmodelReponse.getFinalArray().get(i).getAnnoucementDescription() + "|" +
                    announcmentmodelReponse.getFinalArray().get(i).getAnnoucementPDF());

            ArrayList<ExamFinalArray> rows = new ArrayList<ExamFinalArray>();
            rows.add(announcmentmodelReponse.getFinalArray().get(i));
            listDataChild.put(listDataHeader.get(i), rows);
        }

        expandableListAdapterAnnouncement = new ExpandableListAdapterAnnouncementnew(getActivity(), listDataHeader, listDataChild);
        listannouncment.setAdapter(expandableListAdapterAnnouncement);
        if (AppConfiguration.Notification.equalsIgnoreCase("1")) {
            if (AppConfiguration.messageNotification.contains("-")) {
                String[] strsplit = AppConfiguration.messageNotification.split("\\-");
                strsplit[2] = strsplit[2].substring(0, strsplit[2].length() - 1);
                for (int i = 0; i < announcmentmodelReponse.getFinalArray().size(); i++) {
                    if (announcmentmodelReponse.getFinalArray().get(i).getSubject().toLowerCase().trim().contains(strsplit[2].trim().toLowerCase())) {
                        listannouncment.expandGroup(i);
                        listannouncment.smoothScrollToPosition(i);
                    }
                }
            }
//            listannouncment.expandGroup(0);
        }
    }
}
