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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.ExpandableListAdapterResult;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetStudentResultAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ResultModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ResultFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetStudentResultAsyncTask getStudentResultAsyncTask = null;
    private ArrayList<ResultModel> resultModels = new ArrayList<>();
    private int lastExpandedPosition = -1;
    private RadioGroup termrg;
    private RadioButton term1rb, term2rb;
    ExpandableListAdapterResult expandableListAdapterResult;
    ExpandableListView lvExpResult;
    List<String> listDataHeader;
    HashMap<String, ArrayList<ResultModel.Data>> listDataChild;
    String FinalTermDetailIdStr="1";
    LinearLayout linearBack;

    public ResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        lvExpResult = (ExpandableListView) rootView.findViewById(R.id.lvExpResult);
        termrg = (RadioGroup) rootView.findViewById(R.id.termrg);
        term1rb = (RadioButton) rootView.findViewById(R.id.term1_rb);
        term2rb = (RadioButton) rootView.findViewById(R.id.term2_rb);
        getUnitTestData();
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackUnitTest.setOnClickListener(new View.OnClickListener() {
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
        lvExpResult.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpResult.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        termrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = termrg.getCheckedRadioButtonId();
                switch (radioButtonID) {
                    case R.id.term1_rb:
                        FinalTermDetailIdStr = "1";
                        break;
                    case R.id.term2_rb:
                        FinalTermDetailIdStr = "2";
                        break;
                }
                getUnitTestData();

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
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("TermID", Utility.getPref(mContext, "TermID")); //4
                        params.put("TermDetailId",FinalTermDetailIdStr);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getStudentResultAsyncTask = new GetStudentResultAsyncTask(params);
                        resultModels = getStudentResultAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (resultModels!=null){
                                if (resultModels.size() > 0) {
                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    lvExpResult.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();
                                    prepaareList();
                                    expandableListAdapterResult = new ExpandableListAdapterResult(getActivity(), listDataHeader, listDataChild);
                                    lvExpResult.setAdapter(expandableListAdapterResult);
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
                                    lvExpResult.setVisibility(View.GONE);
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
        }else{
            Utility.ping(mContext,"Network not available");
        }
    }

    public void prepaareList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<ResultModel.Data>>();

        for (int i = 0; i < resultModels.size(); i++) {
            listDataHeader.add(resultModels.get(i).getTestName() + "|" + resultModels.get(i).getTotalMarksGained() + "|" +
                    resultModels.get(i).getTotal_Marks() + "|" + resultModels.get(i).getTotalPercentage());

            ArrayList<ResultModel.Data> rows = new ArrayList<ResultModel.Data>();
            for (int j = 0; j < resultModels.get(i).getDataArrayList().size(); j++) {
                rows.add(resultModels.get(i).getDataArrayList().get(j));

            }
            listDataChild.put(listDataHeader.get(i), rows);
        }
    }
}
