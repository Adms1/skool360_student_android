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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetReportcardAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetTermAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ReportCardModel;
import com.anandniketanbhadaj.skool360student.Models.TermModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;


public class ReportCardFragment extends Fragment {
    WebView webview_report_card;
    HashMap<Integer, String> spinnerTermIdMap;
    String FinalTermDetailIdStr = "1", FinalTermIdStr;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackUnitTest, btnshow;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private Spinner term_detail_spinner;
    private RadioGroup termrg;
    private RadioButton term1rb, term2rb;
    private ProgressDialog progressDialog = null;
    private GetReportcardAsyncTask getReportCardAsyncTask = null;
    private ArrayList<ReportCardModel> reportModels = new ArrayList<>();
    private GetTermAsyncTask getTermAsyncTask = null;
    private ArrayList<TermModel> termModels = new ArrayList<>();

    public ReportCardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_report_card, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        fillspinYear();
        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        webview_report_card = (WebView) rootView.findViewById(R.id.webview);
        term_detail_spinner = (Spinner) rootView.findViewById(R.id.term_detail_spinner);
        termrg = (RadioGroup) rootView.findViewById(R.id.termrg);
        term1rb = (RadioButton) rootView.findViewById(R.id.term1_rb);
        term2rb = (RadioButton) rootView.findViewById(R.id.term2_rb);
        btnshow = (Button) rootView.findViewById(R.id.btnshow);
        WebSettings webSettings = webview_report_card.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_report_card.getSettings().setUseWideViewPort(true);
        webview_report_card.getSettings().setLoadWithOverviewMode(true);
        webview_report_card.getSettings().setBuiltInZoomControls(true);
        // Force links and redirects to open in the WebView instead of in a browser
        webview_report_card.setWebViewClient(new WebViewClient());

    }

    public void setListners() {
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReportData();
            }
        });
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
        termrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = termrg.getCheckedRadioButtonId();
                switch (radioButtonID) {
                    case R.id.term1_rb:
                        // webview_report_card.setVisibility(View.GONE);
                        FinalTermDetailIdStr = "1";
                        break;
                    case R.id.term2_rb:
                        // webview_report_card.setVisibility(View.GONE);
                        FinalTermDetailIdStr = "2";
                        break;
                }
                getReportData();
                //getReportPermission();

            }
        });
        term_detail_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = term_detail_spinner.getSelectedItem().toString();
                String getid = spinnerTermIdMap.get(term_detail_spinner.getSelectedItemPosition());

                Log.d("TermDetailValue", name + "" + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                getReportData();
                // getReportPermission();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getReportData() {
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
                        params.put("Studentid", Utility.getPref(mContext, "studid"));
                        params.put("TermID", FinalTermIdStr);
                        params.put("TermDetailID", FinalTermDetailIdStr);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getReportCardAsyncTask = new GetReportcardAsyncTask(params);
                        reportModels = getReportCardAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (reportModels!=null){
                                if (reportModels.size() > 0) {
                                    progressDialog.dismiss();
                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    webview_report_card.loadUrl(reportModels.get(0).getURL());
                                    if (reportModels.get(0).getURL().equalsIgnoreCase("")) {
                                        webview_report_card.setVisibility(View.GONE);
                                        txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
                                    } else {
                                        webview_report_card.setVisibility(View.VISIBLE);
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
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

    public void fillspinYear() {
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
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getTermAsyncTask = new GetTermAsyncTask(params);
                        termModels = getTermAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (termModels!=null){
                                if (termModels.size() > 0) {
                                    ArrayList<String> termText = new ArrayList<String>();
                                    ArrayList<String> termId = new ArrayList<>();

                                    for (int i = 0; i < termModels.size(); i++) {
                                        termText.add(termModels.get(i).getTerm());
                                        termId.add(termModels.get(i).getTermId());
                                    }
                                    Collections.sort(termId);
                                    Collections.sort(termText);
                                    String[] spinnertermdetailIdArray = new String[termId.size()];

                                    spinnerTermIdMap = new HashMap<Integer, String>();
                                    for (int i = 0; i < termId.size(); i++) {
                                        spinnerTermIdMap.put(i, String.valueOf(termId.get(i)));
                                        spinnertermdetailIdArray[i] = termText.get(i).trim();
                                    }
                                    System.out.println("Sorted ArrayList in Java - Ascending order : " + spinnertermdetailIdArray);
                                    try {
                                        Field popup = Spinner.class.getDeclaredField("mPopup");
                                        popup.setAccessible(true);

                                        // Get private mPopup member variable and try cast to ListPopupWindow
                                        android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(term_detail_spinner);

                                        popupWindow.setHeight(spinnertermdetailIdArray.length > 1 ? 200 : spinnertermdetailIdArray.length * 100);
                                    } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
                                        // silently fail...
                                    }
                                    ArrayAdapter<String> adapterSpinYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermdetailIdArray);
                                    term_detail_spinner.setAdapter(adapterSpinYear);

                                    final Calendar calendar = Calendar.getInstance();
                                    int yy = calendar.get(Calendar.YEAR);

                                    String CurrentYear = String.valueOf(yy);
                                    for (int i = 0; i < spinnertermdetailIdArray.length; i++) {
                                        if (spinnertermdetailIdArray[i].contains(CurrentYear)) {
                                            term_detail_spinner.setSelection(i);
                                        }
                                    }

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
}
