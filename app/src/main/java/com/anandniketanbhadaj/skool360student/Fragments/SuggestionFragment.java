package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.AsyncTasks.CreateSuggestionAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.CreateLeaveModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class SuggestionFragment extends Fragment {
    CreateLeaveModel suggestionResponse;
    String purpose, description, selectedType;
    Fragment fragment;
    FragmentManager fragmentManager;
    Dialog thankyouDialog;
    ArrayList<String> selectedArray;
    private View rootView;
    private Context mContext;
    private EditText edtSubject, edtSuggestion;
    private Button btnSave, btnCancel;
    private Spinner selectdetailspinner;
    private ProgressDialog progressDialog = null;
    private CreateSuggestionAsyncTask createSuggestionAsyncTask = null;

    public SuggestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggestion, container, false);
        mContext = getActivity();

        AppConfiguration.position = 17;

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        edtSubject = (EditText) rootView.findViewById(R.id.edtSubject);
        edtSuggestion = (EditText) rootView.findViewById(R.id.edtSuggestion);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        selectdetailspinner = (Spinner) rootView.findViewById(R.id.select_detail_spinner);
        fillSpinner();

    }

    public void setListners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsendSuggestionData();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSubject.setText("");
                edtSuggestion.setText("");
            }
        });
        selectdetailspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = selectdetailspinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void fillSpinner() {
        selectedArray = new ArrayList<>();
        selectedArray.add("Please Select");
        selectedArray.add("Academic");
        selectedArray.add("Admin");
        selectedArray.add("Other");


        //Collections.sort(selectedArray);
        System.out.println("Sorted ArrayList in Java - Ascending order : " + selectedArray);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(selectdetailspinner);

            popupWindow.setHeight(selectedArray.size() > 1 ? 200 : selectedArray.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterSpinYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, selectedArray);
        selectdetailspinner.setAdapter(adapterSpinYear);

        selectdetailspinner.setSelection(0);
    }

    public void getsendSuggestionData() {
        purpose = edtSubject.getText().toString();
        description = edtSuggestion.getText().toString();

        if (Utility.isNetworkConnected(mContext)) {
            if (!purpose.equalsIgnoreCase("") && !description.equalsIgnoreCase("")) {
                if (!selectedType.equalsIgnoreCase("Please Select")) {
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HashMap<String, String> params = new HashMap<String, String>();
                                params.put("StudentId", Utility.getPref(mContext, "studid"));
                                params.put("Subject", purpose);
                                params.put("Comment", description);
                                params.put("Type", selectedType);
                                params.put("LocationID", Utility.getPref(mContext, "locationId"));
                                createSuggestionAsyncTask = new CreateSuggestionAsyncTask(params);
                                suggestionResponse = createSuggestionAsyncTask.execute().get();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        if (suggestionResponse != null) {
                                            if (suggestionResponse.getSuccess().equalsIgnoreCase("True")) {
                                                edtSubject.setText("");
                                                edtSuggestion.setText("");
                                                Utility.ping(mContext, suggestionResponse.getFinalArray().get(0).getMessage());
                                                //ThankyouDialog();
                                            } else {
                                                edtSubject.setText("");
                                                edtSuggestion.setText("");
                                                Utility.ping(mContext, suggestionResponse.getFinalArray().get(0).getMessage());
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
                    Utility.ping(mContext, "Please select to");
                }
            } else {
                Utility.ping(mContext, "Blank field not allowed.");
            }
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }
}
