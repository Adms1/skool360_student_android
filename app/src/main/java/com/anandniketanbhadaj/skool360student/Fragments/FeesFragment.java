package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.AsyncTasks.GetTermAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.TermModel;
import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.AsyncTasks.FeesAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetFeesStatusTask;
import com.anandniketanbhadaj.skool360student.Models.FeesModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;


public class FeesFragment extends Fragment {
    private FeesModel feesMainResponse;
    private LinearLayout linearBack;
    private String responseString;
    private View rootView;
    private Button btnMenu, btnBackUnitTest, more_detail_btn;
    private TextView txtNoRecordsUnitTest, payment_total_amount_txt,
            payment_total_amount_status_txt, total_fee_txt, due_fee_txt, discount_fee_txt;
    private Context mContext;
    private FragmentManager fragmentManager = null;
    private ProgressDialog progressDialog = null;
    private FeesAsyncTask getFeesAsyncTask = null;
    private GetFeesStatusTask feesStatusTask = null;
    private Spinner term_detail_spinner;
    HashMap<Integer, String> spinnerTermIdMap;
    String FinalTermDetailIdStr = "1", FinalTermIdStr;
    private LinearLayout linear_right, fees_main_linear;
    private ArrayList<TermModel> termModels = new ArrayList<>();
    private GetTermAsyncTask getTermAsyncTask = null;

    public FeesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fees, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        fillspinYear();
        getFeesData();
        return rootView;
    }

    public void initViews() {
        btnMenu = rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = rootView.findViewById(R.id.btnBackUnitTest);
        linearBack = rootView.findViewById(R.id.linearBack);
        payment_total_amount_txt = rootView.findViewById(R.id.payment_total_amount_txt);
        payment_total_amount_status_txt = rootView.findViewById(R.id.payment_total_amount_status_txt);
        total_fee_txt = rootView.findViewById(R.id.total_fee_txt);
        due_fee_txt = rootView.findViewById(R.id.due_fee_txt);
        discount_fee_txt = rootView.findViewById(R.id.discount_fee_txt);
        more_detail_btn = rootView.findViewById(R.id.more_detail_btn);
        linear_right = rootView.findViewById(R.id.linear_right);
        fees_main_linear = rootView.findViewById(R.id.fees_main_linear);
        term_detail_spinner = rootView.findViewById(R.id.fees_term_detail_spinner);
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
        more_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PaymentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.frame_container, fragment).commit();
            }
        });

        term_detail_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = term_detail_spinner.getSelectedItem().toString();
                String getid = spinnerTermIdMap.get(term_detail_spinner.getSelectedItemPosition());

                Log.d("TermDetailValue", name + "" + getid);
                FinalTermIdStr = getid;
                Log.d("FinalTermIdStr", FinalTermIdStr);

                Utility.setPref(mContext, "TermID", getid);

                getFeesData();
                // getReportPermission();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getFeesData() {
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
//                        params.put("Term", Utility.getPref(mContext, "TermID"));
//                        params.put("StandardID", Utility.getPref(mContext, "standardID"));
                        params.put("Term", FinalTermIdStr);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));

                        feesStatusTask = new GetFeesStatusTask(params);

                        responseString = feesStatusTask.execute().get();



                        if(responseString != null){

                            try {
                                JSONObject jsonObject = new JSONObject(responseString);

                                String successMsg = jsonObject.getString("Success");


                                if(successMsg.equalsIgnoreCase("True") || successMsg.equalsIgnoreCase("true")){

                                    getFeesAsyncTask = new FeesAsyncTask(params);
                                    feesMainResponse = getFeesAsyncTask.execute().get();


                                    JSONArray dataArray = jsonObject.getJSONArray("FinalArray");



//                                    for(int count = 0;count<dataArray.length();count++){
//
//                                        JSONObject dataObject = dataArray.getJSONObject(count);
//
//                                        String ledgerName  = dataObject.optString("LedgerName") == null ? "" :dataObject.optString("LedgerName");
//
//                                        String term1Amount  = dataObject.optString("Term1Amt") == null ? "" :dataObject.optString("Term1Amt");
//
//                                        String term2Amount  = dataObject.optString("Term2Amt") == null ? "" :dataObject.optString("Term2Amt");
//
//
//                                    }



                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (feesMainResponse != null) {
                                                if (feesMainResponse.getSuccess().equalsIgnoreCase("True")) {
                                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                                    fees_main_linear.setVisibility(View.VISIBLE);
                                                    progressDialog.dismiss();
                                                    payment_total_amount_txt.setText("₹" + " " + feesMainResponse.getTermPaid());
                                                    total_fee_txt.setText("Total" + "\n" + "₹" + " " + Html.fromHtml(feesMainResponse.getTermTotal()));
                                                    due_fee_txt.setText("Due" + "\n" + "₹" + " " + Html.fromHtml(feesMainResponse.getTermDuePay()));
                                                    discount_fee_txt.setText("Discount" + "\n" + "₹" + " " + Html.fromHtml(feesMainResponse.getTermDiscount()));
                                                    linear_right.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.right2));

                                                } else {
                                                    progressDialog.dismiss();
                                                    payment_total_amount_txt.setText("₹" + " " + "0");
                                                    total_fee_txt.setText("Total" + "\n" + "₹" + " " + "0");
                                                    due_fee_txt.setText("Due" + "\n" + "₹" + " " + "0");
                                                    discount_fee_txt.setText("Discount" + "\n" + "₹" + " " + "0");
                                                    linear_right.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.right2));
                                                    Utility.ping(mContext, "Payment Detail are not available.");
                                                    more_detail_btn.setEnabled(false);
                                                    more_detail_btn.setAlpha(1);
                                                }
                                            } else {
                                                Intent serverintent = new Intent(mContext,Server_Error.class);
                                                startActivity(serverintent);

//                                    progressDialog.dismiss();
//                                    payment_total_amount_txt.setText("₹" + " " + "0");
//                                    total_fee_txt.setText("Total" + "\n" + "₹" + " " + "0");
//                                    due_fee_txt.setText("Due" + "\n" + "₹" + " " + "0");
//                                    discount_fee_txt.setText("Discount" + "\n" + "₹" + " " + "0");
//                                    linear_right.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.right2));
//                                    Utility.ping(mContext, "Payment Detail are not available.");
//                                    more_detail_btn.setEnabled(false);
//                                    more_detail_btn.setAlpha(1);
                                            }
                                        }
                                    });


                                }else{


                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            payment_total_amount_txt.setText("₹" + " " + "0");
                                            total_fee_txt.setText("Total" + "\n" + "₹" + " " + "0");
                                            due_fee_txt.setText("Due" + "\n" + "₹" + " " + "0");
                                            discount_fee_txt.setText("Discount" + "\n" + "₹" + " " + "0");
                                            linear_right.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.right2));
                                            Utility.ping(mContext, "Payment Detail are not available.");
                                            more_detail_btn.setEnabled(false);
                                            more_detail_btn.setAlpha(1);
                                        }
                                    });

                                }
                            }catch (Exception ex){

                            Log.d("exception",ex.getLocalizedMessage());
                            }

                        }else{
                            Intent serverintent = new Intent(mContext,Server_Error.class);
                            startActivity(serverintent);

                        }

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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<>();
                        getTermAsyncTask = new GetTermAsyncTask(params);
                        termModels = getTermAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (termModels != null) {
                                    if (termModels.size() > 0) {
                                        ArrayList<String> termText = new ArrayList<>();
                                        ArrayList<String> termId = new ArrayList<>();

                                        for (int i = 0; i < termModels.size(); i++) {
                                            termText.add(termModels.get(i).getTerm());
                                            termId.add(termModels.get(i).getTermId());
                                        }
                                        Collections.sort(termId);
                                        Collections.sort(termText);
                                        String[] spinnertermdetailIdArray = new String[termId.size()];

                                        spinnerTermIdMap = new HashMap<>();
                                        for (int i = 0; i < termId.size(); i++) {
                                            spinnerTermIdMap.put(i, String.valueOf(termId.get(i)));
                                            spinnertermdetailIdArray[i] = termText.get(i).trim();
                                        }
                                        System.out.println("Sorted ArrayList in Java - Ascending order : " + spinnertermdetailIdArray);
//                                        try {
//                                            Field popup = Spinner.class.getDeclaredField("mPopup");
//                                            popup.setAccessible(true);
//
//                                            // Get private mPopup member variable and try cast to ListPopupWindow
//                                            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(term_detail_spinner);
//
//                                            popupWindow.setHeight(spinnertermdetailIdArray.length > 1 ? 200 : spinnertermdetailIdArray.length * 100);
//                                        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//                                            // silently fail...
//                                        }
                                        ArrayAdapter<String> adapterSpinYear = new ArrayAdapter<>(mContext, R.layout.spinner_layout, spinnertermdetailIdArray);
                                        term_detail_spinner.setAdapter(adapterSpinYear);

                                        final Calendar calendar = Calendar.getInstance();
                                        int yy = calendar.get(Calendar.YEAR);

                                        String CurrentYear = String.valueOf(yy);
                                        for (int i = 0; i < spinnertermdetailIdArray.length; i++) {
                                            if (spinnertermdetailIdArray[i].contains(CurrentYear)) {
                                                term_detail_spinner.setSelection(i - 1);
                                            }
                                        }

                                    } else {
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
