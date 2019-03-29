package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;


import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;

import com.anandniketanbhadaj.skool360student.Activities.ParallaxRecyclerView;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.HolidayListAdapter;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetHolidayAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamDatum;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class HolidayFragment extends Fragment implements View.OnClickListener {
    Fragment fragment;
    FragmentManager fragmentManager;
    ParallaxRecyclerView holiday_list;
    ExamModel holidayDataResponse;
    HolidayListAdapter holidayListAdapter;
    List<String> montharrayList;
    List<ExamDatum> monthwisedata;
    String month;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackCanteen;
    private TextView txtNoRecordsClasswork;
    private FloatingActionButton add_leave_fab_btn;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetHolidayAsyncTask holidayAsyncTask = null;

    public HolidayFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_holiday, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        Calendar cal = Calendar.getInstance();
        // month = cal.get(Calendar.MONTH) + 1;

        SimpleDateFormat input = new SimpleDateFormat("MM");
        SimpleDateFormat output = new SimpleDateFormat("MMMM");

        Date date = null;
        String str = null;
        try {
            date = input.parse(String.valueOf(cal.get(Calendar.MONTH) + 1));
            str = output.format(date);
            month = str;
            Log.i("mini", "Month:" + str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(month);
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        btnBackCanteen = (Button) rootView.findViewById(R.id.btnBackCanteen);
        holiday_list = (ParallaxRecyclerView) rootView.findViewById(R.id.holiday_list);
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        getLeaveData();
    }

    public void setListners() {
        btnMenu.setOnClickListener(this);
        btnBackCanteen.setOnClickListener(this);
        linearBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMenu:
                DashBoardActivity.onLeft();
                break;
            case R.id.btnBackCanteen:
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.linearBack:
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
                break;
        }
    }

    public void getLeaveData() {
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
                        holidayAsyncTask = new GetHolidayAsyncTask(params);
                        holidayDataResponse = holidayAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (holidayDataResponse!=null) {
                                    if (holidayDataResponse.getSuccess().equalsIgnoreCase("True")) {
                                        setLeaveDataList();
                                    } else {
                                        progressDialog.dismiss();

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

    public void setLeaveDataList() {
        montharrayList = new ArrayList<>();
        monthwisedata = new ArrayList<ExamDatum>();

        ArrayList<String> image = new ArrayList<>();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            image.add(String.valueOf(R.drawable.april_t6));
            image.add(String.valueOf(R.drawable.may_t6));
            image.add(String.valueOf(R.drawable.june_t6));
            image.add(String.valueOf(R.drawable.july_t6));
            image.add(String.valueOf(R.drawable.aug_t6));
            image.add(String.valueOf(R.drawable.sep_t6));
            image.add(String.valueOf(R.drawable.oct_t6));
            image.add(String.valueOf(R.drawable.nov_t6));
            image.add(String.valueOf(R.drawable.dec_t6));
            image.add(String.valueOf(R.drawable.jan_t6));
            image.add(String.valueOf(R.drawable.feb_t6));
            image.add(String.valueOf(R.drawable.march_t6));
        } else {
            // smaller device
            image.add(String.valueOf(R.drawable.april_mobile));
            image.add(String.valueOf(R.drawable.may_mobile));
            image.add(String.valueOf(R.drawable.june_mobile));
            image.add(String.valueOf(R.drawable.july_mobile));
            image.add(String.valueOf(R.drawable.aug_mobile));
            image.add(String.valueOf(R.drawable.sep_mobile));
            image.add(String.valueOf(R.drawable.oct_mobile));
            image.add(String.valueOf(R.drawable.nov_mobile));
            image.add(String.valueOf(R.drawable.dec_mobile));
            image.add(String.valueOf(R.drawable.jan_mobile));
            image.add(String.valueOf(R.drawable.feb_mobile));
            image.add(String.valueOf(R.drawable.march_mobile));
        }
//        if (diagonalInches >= 6.5) {
//            image.add(AppConfiguration.IMAGE_LIVE + "april_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "may_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "june_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "july_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "aug_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "sep_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "oct_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "nov_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "dec_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "jan_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "feb_t6.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "march_t6.png");
//        } else {
//            // smaller device
//            image.add(AppConfiguration.IMAGE_LIVE + "april_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "may_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "june_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "july_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "aug_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "sep_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "oct_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "nov_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "dec_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "jan_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "feb_mobile.png");
//            image.add(AppConfiguration.IMAGE_LIVE + "march_mobile.png");
//        }

        for (int i = 0; i < holidayDataResponse.getFinalArray().size(); i++) {
            holidayDataResponse.getFinalArray().get(i).setMonthImage(image.get(i));

        }
        Log.d("monthwise", "" + monthwisedata);


        holidayListAdapter = new HolidayListAdapter(mContext, holidayDataResponse, monthwisedata, holiday_list.getHeight());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holiday_list.setLayoutManager(mLayoutManager);
        holiday_list.setItemAnimator(new DefaultItemAnimator());
        holiday_list.setAdapter(holidayListAdapter);
        holiday_list.setupParallax(mContext);
        for (int i = 0; i < holidayDataResponse.getFinalArray().size(); i++) {
            if (holidayDataResponse.getFinalArray().get(i).getMonthName().equalsIgnoreCase(month)) {
                holiday_list.getLayoutManager().scrollToPosition(i);
            }
        }
    }
}
