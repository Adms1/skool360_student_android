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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.ExpandableListAdapterUnitTest;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetTestDetailAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamDatum;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamFinalArray;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360student.Models.UnitTestModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExamSyllabusFragment extends Fragment {
    ExpandableListAdapterUnitTest expandableListAdapterUnitTest;
    ExpandableListView lvExpUnitTest;
    List<String> listDataHeader;
    HashMap<String, ArrayList<ExamDatum>> listDataChild;
    HashMap<String, ArrayList<UnitTestModel>> listtestname;
    ExamModel responseExam;
    String spinnerSelectedValue, value;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Spinner exam_spinner;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetTestDetailAsyncTask getTestDetailAsyncTask = null;
    private int lastExpandedPosition = -1;

    public ExamSyllabusFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exam_syllabus, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        lvExpUnitTest = (ExpandableListView) rootView.findViewById(R.id.lvExpUnitTest);
        exam_spinner = (Spinner) rootView.findViewById(R.id.exam_spinner);
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
        lvExpUnitTest.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpUnitTest.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        exam_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerSelectedValue = adapterView.getItemAtPosition(i).toString();
                Log.d("spinner", spinnerSelectedValue);
                List<ExamFinalArray> filterFinalArray = new ArrayList<ExamFinalArray>();
                for (ExamFinalArray arrayObj : responseExam.getFinalArray()) {
                    if (arrayObj.getTestName().equalsIgnoreCase(spinnerSelectedValue.trim())) {
                        filterFinalArray.add(arrayObj);
                    }
                }
                setExpandableListView(filterFinalArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                        params.put("TermID", Utility.getPref(mContext, "TermID"));
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getTestDetailAsyncTask = new GetTestDetailAsyncTask(params);
                        responseExam = getTestDetailAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (responseExam != null) {
                                    if (responseExam.getSuccess().equalsIgnoreCase("True")) {
                                        if (responseExam.getFinalArray().size() > 0) {
                                            txtNoRecordsUnitTest.setVisibility(View.GONE);
                                            progressDialog.dismiss();
                                            fillspinner();
                                        } else {
                                            progressDialog.dismiss();
                                            txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
                                        exam_spinner.setVisibility(View.GONE);
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

    private void setExpandableListView(List<ExamFinalArray> array) {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<ExamDatum>>();

        for (int i = 0; i < array.size(); i++) {
            if (array.size() > 0) {
                lvExpUnitTest.setVisibility(View.VISIBLE);
                txtNoRecordsUnitTest.setVisibility(View.GONE);
                listDataHeader.add(array.get(i).getTestDate());
                ArrayList<ExamDatum> rows = new ArrayList<ExamDatum>();
                for (int j = 0; j < responseExam.getFinalArray().size(); j++) {
                    if (array.get(i).getTestDate().equalsIgnoreCase(responseExam.getFinalArray().get(j).getTestDate()) &&
                            array.get(i).getTestName().equalsIgnoreCase(responseExam.getFinalArray().get(j).getTestName())) {
                        for (int k = 0; k < responseExam.getFinalArray().get(j).getData().size(); k++) {
                            rows.add(responseExam.getFinalArray().get(j).getData().get(k));
                        }
                    }
                }
                listDataChild.put(listDataHeader.get(i), rows);
            } else {
                txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
                lvExpUnitTest.setVisibility(View.GONE);
            }
        }
        expandableListAdapterUnitTest = new ExpandableListAdapterUnitTest(getActivity(), listDataHeader, listDataChild);
        lvExpUnitTest.setAdapter(expandableListAdapterUnitTest);
    }

    public void fillspinner() {
        ArrayList<String> row = new ArrayList<String>();

        for (int z = 0; z < responseExam.getFinalArray().size(); z++) {
            row.add(responseExam.getFinalArray().get(z).getTestName());
        }

        ArrayList<String> characters = new ArrayList<String>();

        for(int i = 0; i < row.size(); i++) {
            if (!characters.contains(row.get(i))) {
                characters.add(row.get(i));
            }
        }
Log.d("Array",""+characters);
//        HashSet hs = new HashSet();
//        hs.addAll(row);
//        row.clear();
//        row.addAll(hs);
//        Log.d("marks", "" + row);
//        Collections.sort(row);
//        System.out.println("Sorted ArrayList in Java - Ascending order : " + row);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(exam_spinner);

            popupWindow.setHeight(characters.size() > 5 ? 500 : characters.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, characters);
        exam_spinner.setAdapter(adapterYear);
    }
}
