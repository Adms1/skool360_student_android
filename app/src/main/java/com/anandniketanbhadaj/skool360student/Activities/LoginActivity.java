package com.anandniketanbhadaj.skool360student.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.AsyncTasks.ForgotpasswordAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetStandardSectionAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetStudentListAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.VerifyLoginAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360student.SelectChildModel;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends Activity {


    //Use for dialog
    Dialog forgotDialog, idpasswordDialog;
    String communicationnoStr;
    ExamModel forgotModelResponse, standardsectionResponse, studentListResponse;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSectionMap;
    HashMap<Integer, String> studentMap;
    private ForgotpasswordAsyncTask forgotpasswordAsyncTask = null;
    private GetStandardSectionAsyncTask getStandardSectionAsyncTask = null;
    private GetStudentListAsyncTask getStudentListAsyncTask = null;
    private EditText edtUserName, edtPassword, edtmobileno;
    private TextView forgot_title_txt;
    private Button btnLogin, cancel_btn, submit_btn;
    private Spinner standard_spinner, section_spinner, student_spinner;
    private CheckBox chkPassword;
    private VerifyLoginAsyncTask verifyLoginAsyncTask = null;
    private Context mContext;
    private ProgressDialog progressDialog;
    private SelectChildModel result;
    private HashMap<String, String> param = new HashMap<String, String>();
    private String putExtras = "0";
    private String putExtrasData = "0", Name;
    private String FinalStandardIdStr, FinalStandardStr, FinalSectionIdStr, FinalSectionStr, FinalStudentIdStr, FinalStudentNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        putExtrasData = getIntent().getStringExtra("message");
        putExtras = getIntent().getStringExtra("fromNotification");
        try {
            Name = getIntent().getStringExtra("Name");//getAction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Login Extra : " + putExtrasData);
        Log.d("Data", Utility.getPref(mContext, "data"));
        Log.d("message", Utility.getPref(mContext, "message"));
        checkUnmPwd();
        initViews();
        setListners();
    }

    public void initViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        chkPassword = findViewById(R.id.chPass);
        forgot_title_txt = findViewById(R.id.forgot_title_txt);
    }

    public void setListners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkConnected(mContext)) {
                    if (!edtUserName.getText().toString().equalsIgnoreCase("")) {
                        if (!edtPassword.getText().toString().equalsIgnoreCase("")) {
                            login();
                        } else {
                            Utility.pong(mContext, "Please Enter Password");
                            edtPassword.requestFocus();
                        }
                    } else {
                        Utility.pong(mContext, "Please Enter User Name");
                        edtUserName.requestFocus();
                    }
                } else {
                    Utility.ping(mContext, "Network not available");
                }
            }
        });
        forgot_title_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordDialog();
            }
        });
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (Utility.isNetworkConnected(mContext)) {
                        if (!edtUserName.getText().toString().equalsIgnoreCase("")) {
                            if (!edtPassword.getText().toString().equalsIgnoreCase("")) {
                                login();
                            } else {
                                Utility.pong(mContext, "Please Enter Password");
                                edtPassword.requestFocus();
                            }
                        } else {
                            Utility.pong(mContext, "Please Enter User Name");
                            edtUserName.requestFocus();
                        }
                    } else {
                        Utility.ping(mContext, "Network not available");
                    }
                }
                return false;
            }
        });

        chkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chkPassword.setBackground(getResources().getDrawable(R.drawable.icon_showpass));
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    chkPassword.setBackground(getResources().getDrawable(R.drawable.icon_showpass_h));
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    public void login() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        param.put("UserID", edtUserName.getText().toString().trim());
        param.put("Password", edtPassword.getText().toString().trim());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    verifyLoginAsyncTask = new VerifyLoginAsyncTask(param);
                    result = verifyLoginAsyncTask.execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (result != null) {
//                                if (result.size() > 0) {
                                    if (result.getSuccess().equalsIgnoreCase("True")) {
//                              TODO: Store result values for future use
//                                if (chkRemember.isChecked()) {
                                        saveUserNamePwd(edtUserName.getText().toString(), edtPassword.getText().toString());
//                                }
                                        Utility.pong(mContext, "Login Successful");
//                                        Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);//SplashScreenActivity
//                                        Utility.setPref(mContext, "Loginwithother", "false");
//                                        intentDashboard.putExtra("message", putExtrasData);
//                                        intentDashboard.putExtra("fromNotification", putExtras);
//                                        if(Name != null) {
//                                            intentDashboard.putExtra("Name",Name);
//                                        }
//                                        System.out.println("messageLogin: " + putExtrasData);
//                                        startActivity(intentDashboard);
//                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                                        finish();

                                        Utility.setIntPref(mContext, "childcount", result.getStudentcount());
                                        Utility.setPref(mContext, "upass", edtPassword.getText().toString());
                                        Utility.setPref(mContext, "FamilyID", result.getFinalarray().get(0).getFamilyid());

                                        if(result.getStudentcount() > 1) {

                                            Intent intent = new Intent(LoginActivity.this, SelectChildActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Utility.setPref(mContext, "studname", result.getFinalarray().get(0).getStudentname());
                                            Utility.setPref(mContext, "studid", result.getFinalarray().get(0).getStudentid());
                                            Utility.setPref(mContext, "locationId", result.getFinalarray().get(0).getLocationid());
                                            Utility.setPref(mContext, "standardID", result.getFinalarray().get(0).getStandardid());
                                            Utility.setPref(mContext, "ClassID", result.getFinalarray().get(0).getClassid());
                                            Utility.setPref(mContext, "TermID", result.getFinalarray().get(0).getTermid());//result.getFinalarray().get(0).get("TermID"));
                                            Utility.setPref(mContext, "RegisterStatus", result.getFinalarray().get(0).getRegisterstatus());

                                            Intent intent = new Intent(mContext, DashBoardActivity.class);
                                            mContext.startActivity(intent);
                                        }

                                        edtUserName.setText("");
                                        edtPassword.setText("");

//                                        finish();

                                    } else {
                                        if (result.getStatus().equalsIgnoreCase("1")) {
                                            IdPasswordDialog();
                                        } else {
                                            Utility.pong(mContext, "Invalid Username/ password");
                                        }
                                    }
//                                } else {
//                                    Utility.pong(mContext, "Invalid Username/ password");
//                                }
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
    }

    public void checkUnmPwd() {
        if (!Utility.getPref(mContext, "unm").equalsIgnoreCase("")) {

            Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
            Utility.setPref(mContext, "Loginwithother", "false");
            intentDashboard.putExtra("message", putExtrasData);
            intentDashboard.putExtra("fromNotification", putExtras);
            if (Name != null) {
                intentDashboard.putExtra("Name", Name);
            }
            startActivity(intentDashboard);
            finish();
        }
    }

    public void saveUserNamePwd(String unm, String pwd) {
        Utility.setPref(mContext, "unm", unm);
        Utility.setPref(mContext, "pwd", pwd);
    }

    public void forgotPasswordDialog() {
        forgotDialog = new Dialog(mContext, R.style.Theme_Dialog);
        Window window = forgotDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        forgotDialog.getWindow().getAttributes().verticalMargin = 0.0f;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

//        forgotDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);
        forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        forgotDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgotDialog.setCancelable(false);
        forgotDialog.setContentView(R.layout.forgot_password);

        cancel_btn = forgotDialog.findViewById(R.id.cancel_btn);
        submit_btn = forgotDialog.findViewById(R.id.submit_btn);
        edtmobileno = forgotDialog.findViewById(R.id.edtmobileno);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotDialog.dismiss();
            }
        });

        edtmobileno.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    communicationnoStr = edtmobileno.getText().toString();
                    if (!communicationnoStr.equalsIgnoreCase("")) {
                        if (communicationnoStr.length() >= 10) {
                            getForgotData();
                        } else {
                            edtmobileno.setError("Please enter valid no");
                        }
                    } else {
                        edtmobileno.setError("Please enter communication no");
                    }
                }
                return false;
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicationnoStr = edtmobileno.getText().toString();
                if (!communicationnoStr.equalsIgnoreCase("")) {
                    if (communicationnoStr.length() >= 10) {
                        getForgotData();
                    } else {
                        edtmobileno.setError("Please enter valid no");
                    }
                } else {
                    edtmobileno.setError("Please enter communication no");
                }
            }
        });

        forgotDialog.show();

    }

    public void getForgotData() {
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
                        params.put("MobileNo", communicationnoStr);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        forgotpasswordAsyncTask = new ForgotpasswordAsyncTask(params);
                        forgotModelResponse = forgotpasswordAsyncTask.execute().get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (forgotModelResponse != null) {
                                    if (forgotModelResponse.getSuccess().equalsIgnoreCase("True")) {
                                        Utility.ping(mContext, "Please check your inbox for id or password");
                                        forgotDialog.dismiss();
                                    } else {
                                        progressDialog.dismiss();
                                        Utility.ping(mContext, forgotModelResponse.getMessage());
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


    public void IdPasswordDialog() {
        idpasswordDialog = new Dialog(mContext, R.style.Theme_Dialog);
        Window window = idpasswordDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        idpasswordDialog.getWindow().getAttributes().verticalMargin = 0.0f;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

//        forgotDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);
        idpasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        idpasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        idpasswordDialog.setCancelable(false);
        idpasswordDialog.setContentView(R.layout.idpassworddialog);
        getStandardSectionData();
        cancel_btn = idpasswordDialog.findViewById(R.id.cancel_btn);
        submit_btn = idpasswordDialog.findViewById(R.id.submit_btn);
        standard_spinner = idpasswordDialog.findViewById(R.id.standard_spinner);
        section_spinner = idpasswordDialog.findViewById(R.id.section_spinner);
        student_spinner = idpasswordDialog.findViewById(R.id.student_spinner);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idpasswordDialog.dismiss();
            }
        });


        standard_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = standard_spinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(standard_spinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                FinalStandardStr = name;
                Log.d("StandardName", FinalStandardStr);
                fillSectionSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = section_spinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(section_spinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSectionIdStr = getid;
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                FinalSectionStr = name;
                Log.d("SectionName", FinalSectionStr);
                getStudentListData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        student_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = student_spinner.getSelectedItem().toString();
                String getid = studentMap.get(student_spinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStudentIdStr = getid;
                Log.d("FinalStudentIdStr", FinalStudentIdStr);
                FinalStudentNameStr = name;
                Log.d("StudentName", FinalStudentNameStr);
                for (int j = 0; j < studentListResponse.getFinalArray().size(); j++) {
                    if (FinalStudentNameStr.equalsIgnoreCase(studentListResponse.getFinalArray().get(j).getStudentName())) {
                        Utility.setPref(mContext, "studid", studentListResponse.getFinalArray().get(j).getStudentID());
                        Utility.setPref(mContext, "FamilyID", studentListResponse.getFinalArray().get(j).getFamilyID());
                        Utility.setPref(mContext, "standardID", String.valueOf(studentListResponse.getFinalArray().get(j).getStandardID()));
                        Utility.setPref(mContext, "ClassID", studentListResponse.getFinalArray().get(j).getClassID());
                        Utility.setPref(mContext, "TermID", String.valueOf(studentListResponse.getFinalArray().get(j).getTermID()));//result.get("TermID"));
                        Utility.setPref(mContext, "RegisterStatus", String.valueOf(studentListResponse.getFinalArray().get(j).getRegisterStatus()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.setPref(mContext, "studid", FinalStudentIdStr);//
                Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
                Utility.setPref(mContext, "Loginwithother", "True");
                intentDashboard.putExtra("message", putExtrasData);
                intentDashboard.putExtra("fromNotification", putExtras);
                startActivity(intentDashboard);
                finish();
            }
        });

        idpasswordDialog.show();
    }

    public void getStandardSectionData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            //    progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        getStandardSectionAsyncTask = new GetStandardSectionAsyncTask(params);
                        standardsectionResponse = getStandardSectionAsyncTask.execute().get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (standardsectionResponse != null) {
                                    if (standardsectionResponse.getSuccess().equalsIgnoreCase("True")) {
                                        fillStandardSpinner();
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

    public void fillStandardSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");

        ArrayList<String> standardname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            standardname.add(firstValue.get(z));
            for (int i = 0; i < standardsectionResponse.getFinalArray().size(); i++) {
                standardname.add(standardsectionResponse.getFinalArray().get(i).getStandard());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            standardId.add(firstValueId.get(m));
            for (int j = 0; j < standardsectionResponse.getFinalArray().size(); j++) {
                standardId.add(standardsectionResponse.getFinalArray().get(j).getStandardID());
            }
        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(standard_spinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 300 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        standard_spinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    public void fillSectionSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");

        ArrayList<String> sectionName = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            sectionName.add(firstValue.get(z));
            for (int i = 0; i < standardsectionResponse.getFinalArray().size(); i++) {
                if (FinalStandardStr.equalsIgnoreCase(standardsectionResponse.getFinalArray().get(i).getStandard())) {
                    for (int j = 0; j < standardsectionResponse.getFinalArray().get(i).getSectionDetail().size(); j++) {
                        sectionName.add(standardsectionResponse.getFinalArray().get(i).getSectionDetail().get(j).getSection());
                    }
                }
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> sectionId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            sectionId.add(firstValueId.get(m));
            for (int j = 0; j < standardsectionResponse.getFinalArray().size(); j++) {
                if (FinalStandardStr.equalsIgnoreCase(standardsectionResponse.getFinalArray().get(j).getStandard())) {
                    for (int i = 0; i < standardsectionResponse.getFinalArray().get(j).getSectionDetail().size(); i++) {
                        sectionId.add(standardsectionResponse.getFinalArray().get(j).getSectionDetail().get(i).getSectionID());
                    }
                }
            }
        }
        String[] spinnerstandardIdArray = new String[sectionId.size()];

        spinnerSectionMap = new HashMap<Integer, String>();
        for (int i = 0; i < sectionId.size(); i++) {
            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
            spinnerstandardIdArray[i] = sectionName.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(section_spinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 300 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        section_spinner.setAdapter(adapterstandard);

        FinalSectionIdStr = spinnerSectionMap.get(0);
    }

    public void getStudentListData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            //    progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();

                        getStudentListAsyncTask = new GetStudentListAsyncTask(params);
                        params.put("StandrdID", FinalStandardIdStr);
                        params.put("ClassID", FinalSectionIdStr);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        studentListResponse = getStudentListAsyncTask.execute().get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (studentListResponse != null) {
                                    if (studentListResponse.getSuccess().equalsIgnoreCase("True")) {
                                        fillStudentSpinner();
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

    public void fillStudentSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");

        ArrayList<String> studentName = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            studentName.add(firstValue.get(z));
            for (int i = 0; i < studentListResponse.getFinalArray().size(); i++) {
                studentName.add(studentListResponse.getFinalArray().get(i).getStudentName());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> studentId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            studentId.add(firstValueId.get(m));
            for (int j = 0; j < studentListResponse.getFinalArray().size(); j++) {
                studentId.add(Integer.valueOf(studentListResponse.getFinalArray().get(j).getStudentID()));

            }
        }
        String[] spinnerstandardIdArray = new String[studentId.size()];

        studentMap = new HashMap<Integer, String>();
        for (int i = 0; i < studentId.size(); i++) {
            studentMap.put(i, String.valueOf(studentId.get(i)));
            spinnerstandardIdArray[i] = studentName.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(student_spinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 300 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        student_spinner.setAdapter(adapterstandard);

        FinalStudentIdStr = studentMap.get(0);
    }

}
