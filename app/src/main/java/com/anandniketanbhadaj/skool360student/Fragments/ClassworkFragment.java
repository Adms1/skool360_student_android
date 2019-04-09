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
import com.anandniketanbhadaj.skool360student.Adapter.ExpandableListAdapter;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetStudClassworkAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ClassWorkModel;
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
public class ClassworkFragment extends Fragment {
    private static TextView fromDate, toDate;
    private static String dateFinal;
    private static boolean isFromDate = false;
    String putData, formatedate;
    String[] spiltdata;
    ExpandableListAdapter listAdapter;
    ExpandableListView lvExpClassWork;
    List<String> listDataHeader;
    HashMap<String, ArrayList<ClassWorkModel.ClassWorkData>> listDataChild;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnFilterClasswork, btnBackClasswork;
    private TextView txtNoRecordsClasswork;
    private Context mContext;
    private GetStudClassworkAsyncTask getStudClassworkAsyncTask = null;
    private ProgressDialog progressDialog = null;
    private ArrayList<ClassWorkModel> classWorkModels = new ArrayList<>();
    private int lastExpandedPosition = -1;

    public ClassworkFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.classwork_fragment, container, false);
        mContext = getActivity();

        AppConfiguration.position = 17;

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        fromDate = (TextView) rootView.findViewById(R.id.fromDate);
        toDate = (TextView) rootView.findViewById(R.id.toDate);
        btnFilterClasswork = (Button) rootView.findViewById(R.id.btnFilterClasswork);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        btnBackClasswork = (Button) rootView.findViewById(R.id.btnBackClasswork);
        lvExpClassWork = (ExpandableListView) rootView.findViewById(R.id.lvExpClassWork);


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
        getClassworkData(fromDate.getText().toString(), toDate.getText().toString());
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

        btnFilterClasswork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromDate.getText().toString().equalsIgnoreCase("")) {
                    if (!toDate.getText().toString().equalsIgnoreCase("")) {
                        if (Utility.CheckDates(fromDate.getText().toString(), toDate.getText().toString()) == true) {
                            getClassworkData(fromDate.getText().toString(), toDate.getText().toString());
                        } else {
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

        btnBackClasswork.setOnClickListener(new View.OnClickListener() {
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
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpClassWork.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpClassWork.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getClassworkData(final String fromDate, final String toDate) {
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
                        params.put("ClassWorkFromDate", fromDate);
                        params.put("ClassWorkToDate", toDate);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        classWorkModels.clear();
                        getStudClassworkAsyncTask = new GetStudClassworkAsyncTask(params);
                        classWorkModels = getStudClassworkAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (classWorkModels != null) {
                                    if (classWorkModels.size() > 0) {
                                        txtNoRecordsClasswork.setVisibility(View.GONE);
                                        prepaareList();
                                        lvExpClassWork.setVisibility(View.VISIBLE);
                                        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild, true);
                                        lvExpClassWork.setAdapter(listAdapter);
                                        if (AppConfiguration.Notification.equalsIgnoreCase("1")) {
//                                        String[] strsplit = AppConfiguration.messageNotification.split("\\-");
//                                        strsplit[2]=strsplit[2].substring(0, strsplit[2].length() - 1);
//                                        for (int i = 0; i < announcmentmodelReponse.getFinalArray().size(); i++) {
//                                            if (announcmentmodelReponse.getFinalArray().get(i).getSubject().toLowerCase().trim().contains(strsplit[2].trim().toLowerCase())){
//                                                listannouncment.expandGroup(i);
//                                            }
//                                        }
                                            lvExpClassWork.expandGroup(0);
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsClasswork.setVisibility(View.VISIBLE);
                                        lvExpClassWork.setVisibility(View.GONE);
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
        listDataChild = new HashMap<String, ArrayList<ClassWorkModel.ClassWorkData>>();
        if (!getArguments().getString("message").equalsIgnoreCase("test")) {
            //spiltdata = putData.split("\\-");
            for (int i = 0; i < classWorkModels.size(); i++) {
                if (classWorkModels.get(i).getClassWorkDate().equalsIgnoreCase(formatedate)) {
                    listDataHeader.add(classWorkModels.get(i).getClassWorkDate());

                    ArrayList<ClassWorkModel.ClassWorkData> rows = new ArrayList<ClassWorkModel.ClassWorkData>();
                    for (int j = 0; j < classWorkModels.get(i).getClassWorkDatas().size(); j++) {
                        // if (classWorkModels.get(i).getClassWorkDatas().get(j).getSubject().equalsIgnoreCase(spiltdata[2].trim())) {
                        rows.add(classWorkModels.get(i).getClassWorkDatas().get(j));
                        // }
                    }

                    listDataChild.put(listDataHeader.get(i), rows);
                }
            }
        } else {
            for (int i = 0; i < classWorkModels.size(); i++) {
                listDataHeader.add(classWorkModels.get(i).getClassWorkDate());
                ArrayList<ClassWorkModel.ClassWorkData> rows = new ArrayList<ClassWorkModel.ClassWorkData>();
                for (int j = 0; j < classWorkModels.get(i).getClassWorkDatas().size(); j++) {
                    rows.add(classWorkModels.get(i).getClassWorkDatas().get(j));
                    listDataChild.put(listDataHeader.get(i), rows);
                }
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
