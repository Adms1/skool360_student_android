package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.ExpandableListAdapterHomework;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetStudHomeworkAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.HomeWorkModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class HomeworkFragment extends Fragment {
    private static TextView fromDate, toDate;
    private static String dateFinal;
    private static boolean isFromDate = false;
    ExpandableListAdapterHomework listAdapter;
    ExpandableListView lvExpHomework;
    List<String> listDataHeader;
    HashMap<String, ArrayList<HomeWorkModel.HomeWorkData>> listDataChild;
    String putData, formatedate;
    String[] spiltdata;
    private View rootView;
    private Button btnMenu, btnFilterHomework, btnBackHomework;
    private TextView txtNoRecordsHomework;
    private Context mContext;
    private GetStudHomeworkAsyncTask getStudHomeworkAsyncTask = null;
    private ArrayList<HomeWorkModel> homeWorkModels = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private int lastExpandedPosition = -1;
    LinearLayout linearBack;

    public HomeworkFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.homework_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {

        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        fromDate = (TextView) rootView.findViewById(R.id.fromDate);
        toDate = (TextView) rootView.findViewById(R.id.toDate);
        btnFilterHomework = (Button) rootView.findViewById(R.id.btnFilterHomework);
        txtNoRecordsHomework = (TextView) rootView.findViewById(R.id.txtNoRecordsHomework);
        btnBackHomework = (Button) rootView.findViewById(R.id.btnBackHomework);
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        lvExpHomework = (ExpandableListView) rootView.findViewById(R.id.lvExpHomework);

        if (!getArguments().getString("message").equalsIgnoreCase("test")) {
            putData = getArguments().getString("message");
            spiltdata = putData.split("\\-");
            fromDate.setText(spiltdata[1]);
            toDate.setText(spiltdata[1]);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat output = new SimpleDateFormat("dd/MMM EEEE");
            Date d = null;
            try {
                d = sdf.parse(spiltdata[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatedate = output.format(d);
            Log.d("date", formatedate);
        } else {
            putData = getArguments().getString("message");
            fromDate.setText(Utility.getTodaysDate());
            toDate.setText(Utility.getTodaysDate());
        }
        getHomeworkData(fromDate.getText().toString(), toDate.getText().toString());
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        btnFilterHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromDate.getText().toString().equalsIgnoreCase("")) {
                    if (!toDate.getText().toString().equalsIgnoreCase("")) {
                        if (Utility.CheckDates(fromDate.getText().toString(), toDate.getText().toString()) == true) {
                            getHomeworkData(fromDate.getText().toString(), toDate.getText().toString());
                        }else{
                            Utility.pong(mContext, "Please select proper date.");
                        }
                    } else {
                        Utility.pong(mContext, "You need to select a to date");
                    }
                } else {
                    Utility.pong(mContext, "You need to select a from date");
                }
            }
        });

        btnBackHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                Fragment fragment = new HomeFragment();
                Bundle args = new Bundle();
                args.putString("message", putData);
                fragment.setArguments(args);
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
                Bundle args = new Bundle();
                args.putString("message", putData);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpHomework.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpHomework.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getHomeworkData(final String fromDate, final String toDate) {

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
                        params.put("HomeWorkFromDate", fromDate);
                        params.put("HomeWorkToDate", toDate);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        homeWorkModels.clear();
                        getStudHomeworkAsyncTask = new GetStudHomeworkAsyncTask(params);
                        homeWorkModels = getStudHomeworkAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (homeWorkModels!=null){
                                if (homeWorkModels.size() > 0) {
                                    txtNoRecordsHomework.setVisibility(View.GONE);
                                    lvExpHomework.setVisibility(View.VISIBLE);
                                    prepaareList();
                                    listAdapter = new ExpandableListAdapterHomework(getActivity(), listDataHeader, listDataChild);
                                    lvExpHomework.setAdapter(listAdapter);
                                    if (AppConfiguration.Notification.equalsIgnoreCase("1")) {
                                        lvExpHomework.expandGroup(0);
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsHomework.setVisibility(View.VISIBLE);
                                    lvExpHomework.setVisibility(View.GONE);
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
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<HomeWorkModel.HomeWorkData>>();

        if (!getArguments().getString("message").equalsIgnoreCase("test")) {
            spiltdata = putData.split("\\-");
            for (int i = 0; i < homeWorkModels.size(); i++) {
                if (homeWorkModels.get(i).getHomeWorkDate().equalsIgnoreCase(formatedate)) {
                    listDataHeader.add(homeWorkModels.get(i).getHomeWorkDate());

                    ArrayList<HomeWorkModel.HomeWorkData> rows = new ArrayList<HomeWorkModel.HomeWorkData>();
                    for (int j = 0; j < homeWorkModels.get(i).getHomeWorkDatas().size(); j++) {
                        // if (homeWorkModels.get(i).getHomeWorkDatas().get(j).getSubject().equalsIgnoreCase(spiltdata[2].trim())) {
                        rows.add(homeWorkModels.get(i).getHomeWorkDatas().get(j));
                        // }
                    }
                    listDataChild.put(listDataHeader.get(i), rows);
                }
            }
        } else {
            for (int i = 0; i < homeWorkModels.size(); i++) {
                listDataHeader.add(homeWorkModels.get(i).getHomeWorkDate());

                ArrayList<HomeWorkModel.HomeWorkData> rows = new ArrayList<HomeWorkModel.HomeWorkData>();
                for (int j = 0; j < homeWorkModels.get(i).getHomeWorkDatas().size(); j++) {
                    rows.add(homeWorkModels.get(i).getHomeWorkDatas().get(j));
                }
                listDataChild.put(listDataHeader.get(i), rows);
            }
        }
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String d, m, y;
            d = Integer.toString(day);
            m = Integer.toString(month);
            y = Integer.toString(year);

            if (day < 10) {
                d = "0" + d;
            }
            if (month < 10) {
                m = "0" + m;
            }
            dateFinal = d + "/" + m + "/" + y;


            if (isFromDate) {
                fromDate.setText(dateFinal);
            } else {
                toDate.setText(dateFinal);
            }
        }
    }


}
