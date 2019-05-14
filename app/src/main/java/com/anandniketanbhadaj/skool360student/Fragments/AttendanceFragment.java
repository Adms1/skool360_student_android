package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.ListHolidayAdapter;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetAttendanceAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.AttendanceModel;
import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class AttendanceFragment extends Fragment {
    static int previousHeight;
    TextView total_present_txt, total_absent_txt, total_holiday_txt;
    String selectedmonth, selectedyear;
    LinearLayout linear_list;
    ImageView close_img;
    RecyclerView holiday_list_rcv;
    int timeDuration = 500;
    ArrayList<AttendanceModel.HolidayAtt> arrayList;
    //BottomSheet
    BottomSheetBehavior sheetBehavior;
    ListHolidayAdapter listHolidayAdapter;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnFilterAttendance, btnBackAttendance;
    private TextView txtTotalPresent, txtTotalAbsent, txtNoRecordsHomework;
    private Spinner spinMonth, spinYear;
    private RelativeLayout rlCalender;
    private Context mContext;
    private ProgressDialog progressDialog = null, progressDialog1;
    private CaldroidFragment mCaldroidFragment;
    private GetAttendanceAsyncTask getAttendanceAsyncTask = null;
    private ArrayList<AttendanceModel> attendanceModels = new ArrayList<>();
    private ArrayList<String> absentDates = new ArrayList<>();

    private boolean isclicked = true;

    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            if (attendanceModels.size() > 0) {
                for (int i = 0; i < attendanceModels.get(0).getEventsList().size(); i++) {
                    if (dateToString(date).equalsIgnoreCase(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate())) {
                        String comments = attendanceModels.get(0).getEventsList().get(i).getComment();
                        if (!comments.equalsIgnoreCase("")) {
                            AlertDialog ad = new AlertDialog.Builder(view.getContext()).create();
                            ad.setCancelable(false);
                            ad.setTitle("Comment");
                            ad.setMessage(comments);
                            ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad.show();
                        }
                    }
                }
            }
        }

        @Override
        public void onChangeMonth(int month, int year) {

            String text = "month: " + month + " year: " + year;

            progressDialog1 = new ProgressDialog(getContext());
            progressDialog1.setCanceledOnTouchOutside(true);
            progressDialog1.setMessage("Please Wait...");
            progressDialog1.show();

            if (month < 10) {
                selectedmonth = "0" + month;
            } else {
                selectedmonth = String.valueOf(month);
            }

//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {

                    if(mCaldroidFragment.isEnableSwipe()){
                getAttendance();

            }else {
                mCaldroidFragment.setEnableSwipe(true);
                progressDialog1.dismiss();
            }
//                }
//            }, 1000);
//            else {
//
//            }

//            if(isclicked) {
//                getAttendance();
//            }else {
//                isclicked = true;
//                mCaldroidFragment.swsetEnableSwipe(false);
//                progressDialog1.dismiss();
//                onCaldroidViewCreated();
//            }

            selectedyear = String.valueOf(year);
//            progressDialog1.dismiss();
        }

        @Override
        public void onLongClickDate(Date date, View view) {
        }



        @Override
        public void onCaldroidViewCreated() {

//            progressDialog1 = new ProgressDialog(mContext);
//            progressDialog1.setMessage("Please Wait...");
//            progressDialog1.setCancelable(false);
//            progressDialog1.show();

//            getAttendance();

        }
    };
    private ArrayList<String> presentDates = new ArrayList<>();
    private ArrayList<String> year1 = new ArrayList<>();

    public AttendanceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.attendance_fragment, container, false);
        mContext = getActivity();

        AppConfiguration.position = 17;

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = rootView.findViewById(R.id.btnMenu);
        btnBackAttendance = rootView.findViewById(R.id.btnBackAttendance);
        linearBack = rootView.findViewById(R.id.linearBack);
        txtNoRecordsHomework = rootView.findViewById(R.id.txtNoRecordsHomework);
        rlCalender = rootView.findViewById(R.id.rlCalender);
        total_present_txt = rootView.findViewById(R.id.total_present_txt);
        total_absent_txt = rootView.findViewById(R.id.total_absent_txt);
        total_holiday_txt = rootView.findViewById(R.id.total_holiday_txt);
        linear_list = rootView.findViewById(R.id.bottom_sheet);
        close_img = linear_list.findViewById(R.id.close_img);
        holiday_list_rcv = linear_list.findViewById(R.id.holiday_list_rcv);
        Collections.sort(year1);

        System.out.println("Sorted ArrayList in Java - Ascending order : " + year1);

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        try {
            mCaldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.SUNDAY);
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SHOW_NAVIGATION_ARROWS, true);

            mCaldroidFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.calFrameContainer, mCaldroidFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sheetBehavior = BottomSheetBehavior.from(linear_list);
        sheetBehavior.setHideable(false);
    }

    public void getAttendance() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);

            isclicked = false;
//            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("Month", selectedmonth);//String.valueOf(spinMonth.getSelectedItemPosition() + 1)
                        params.put("Year", selectedyear);//spinYear.getSelectedItem().toString()
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getAttendanceAsyncTask = new GetAttendanceAsyncTask(params);
                        attendanceModels = getAttendanceAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                progressDialog1.dismiss();
                                absentDates.clear();
                                HashMap hm = new HashMap();
                                if (attendanceModels!=null) {
                                    if (attendanceModels.size() > 0) {
                                        rlCalender.setVisibility(View.VISIBLE);
                                        total_absent_txt.setText(attendanceModels.get(0).getTotalAbsent());
                                        total_present_txt.setText(attendanceModels.get(0).getTotalPresent());
                                        total_holiday_txt.setText(attendanceModels.get(0).getTotalHolidayCount());
                                        if (attendanceModels.get(0).getEventsList().size() > 0) {
                                            mCaldroidFragment.moveToDate(stringToDate(attendanceModels.get(0).getEventsList().get(0).getAttendanceDate()));
                                        }
                                        for (int i = 0; i < attendanceModels.get(0).getEventsList().size(); i++) {
                                            if (attendanceModels.get(0).getEventsList().get(i).getAttendenceStatus().equalsIgnoreCase("Absent")) {
                                                hm.put(stringToDate(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate()), new ColorDrawable(getResources().getColor(R.color.absent_bg)));
                                            } else if (attendanceModels.get(0).getEventsList().get(i).getAttendenceStatus().equalsIgnoreCase("Present")) {
                                                hm.put(stringToDate(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate()), new ColorDrawable(getResources().getColor(R.color.present_header)));
                                            } else if (attendanceModels.get(0).getEventsList().get(i).getAttendenceStatus().equalsIgnoreCase("Holiday")) {
                                                hm.put(stringToDate(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate()), new ColorDrawable(getResources().getColor(R.color.schedule_active)));
                                            }
                                        }

                                        if (hm.size() > 0) {
                                            mCaldroidFragment.setBackgroundDrawableForDates(hm);
                                        }
                                        mCaldroidFragment.refreshView();

                                        if (attendanceModels.get(1).getHolidayAtt().size() > 0) {
                                            linear_list.setVisibility(View.VISIBLE);
                                            listHolidayAdapter = new ListHolidayAdapter(mContext, attendanceModels);
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                                            holiday_list_rcv.setLayoutManager(mLayoutManager);
                                            holiday_list_rcv.setItemAnimator(new DefaultItemAnimator());
                                            holiday_list_rcv.setAdapter(listHolidayAdapter);
                                        } else {
                                            linear_list.setVisibility(View.GONE);
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsHomework.setVisibility(View.GONE);
                                        rlCalender.setVisibility(View.VISIBLE);

                                        total_absent_txt.setText("0");
                                        total_present_txt.setText("0");
                                        total_holiday_txt.setText("0");
                                    }

                                    mCaldroidFragment.setEnableSwipe(true);


                                }else{
                                    Intent serverintent=new Intent(mContext,Server_Error.class);
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

    public void setListners() {
        mCaldroidFragment.setCaldroidListener(listener);

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
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    close_img.setImageResource(R.drawable.down_add_family);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    close_img.setImageResource(R.drawable.up_add_family);
                }
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public Date stringToDate(String stirngDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(stirngDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public String dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String stirngDate = "";
        stirngDate = df.format(date);

        return stirngDate;
    }


}
