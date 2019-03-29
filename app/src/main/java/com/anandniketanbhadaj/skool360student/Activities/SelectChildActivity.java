package com.anandniketanbhadaj.skool360student.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.Adapter.SelectChildAdapter;
import com.anandniketanbhadaj.skool360student.AsyncTasks.ParentChangePAsswordAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.VerifyLoginAsyncTask;
import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.SelectChildModel;
import com.anandniketanbhadaj.skool360student.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectChildActivity extends AppCompatActivity {

    HashMap<String, String> param = new HashMap<>();
    Dialog changeDialog;
    private ImageView ivProfilepic;
    private TextView tvName, tvEmail, tvPhone, tvChngpasswrd;
    private ParentChangePAsswordAsyncTask changePasswordAsyncTask;
    private RecyclerView rvList;
    private SelectChildAdapter selectChildAdapter;
    private Button btnBack;
    private Intent intent;
    private ArrayList<SelectChildModel.FinalArray> finalArrays;
    private ArrayList<SelectChildModel.FamilyDetail> familyDetails;
    private VerifyLoginAsyncTask verifyLoginAsyncTask = null;
    private SelectChildModel result;
    private ProgressDialog progressDialog = null;
    Boolean isChecked = false, isChecked1 = false, isChecked2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_child);

        ivProfilepic = findViewById(R.id.select_child_profile_image);
        tvName = findViewById(R.id.select_child_father_name_txt);
        tvEmail = findViewById(R.id.select_child_email_txt);
        tvPhone = findViewById(R.id.select_child_phone_txt);
        rvList = findViewById(R.id.select_child_rvChildlist);

        btnBack = findViewById(R.id.btnBack);
        tvChngpasswrd = findViewById(R.id.select_child_chngepaswrd_txt);

//        finalArrays = (ArrayList<SelectChildModel.FinalArray>) getIntent().getSerializableExtra("finalArray");
//        familyDetails = (ArrayList<SelectChildModel.FamilyDetail>) getIntent().getSerializableExtra("familyArray");

        rvList.setLayoutManager(new LinearLayoutManager(SelectChildActivity.this, LinearLayoutManager.VERTICAL, false));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvChngpasswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        getchild();
    }

    private void changePassword() {

        changeDialog = new Dialog(SelectChildActivity.this);
        changeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeDialog.setContentView(R.layout.change_password_parent);
        changeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        changeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Window window = changeDialog.getWindow();
        WindowManager.LayoutParams wlp = null;

        if (window != null) {
            wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);

        }

//        changeDialog = new Dialog(SelectChildActivity.this);
//        Window window = changeDialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        window.setAttributes(wlp);
//
////        changeDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);
//        changeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        changeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        changeDialog.setCancelable(false);
//        changeDialog.setContentView(R.layout.change_password_parent);

        final EditText oldpassword = changeDialog.findViewById(R.id.parent_password_etOldPassword);
        final EditText newpassword = changeDialog.findViewById(R.id.parent_password_etNewPassword);
        final EditText confirmpassword = changeDialog.findViewById(R.id.parent_password_etConfirmPassword);
        final Button changebtn = changeDialog.findViewById(R.id.parent_password_btnChange);
        final Button closebtn = changeDialog.findViewById(R.id.parent_password_btnClose);

        ImageView ivOldpass = changeDialog.findViewById(R.id.parent_password_ivOldpass);
        ImageView ivNewpass = changeDialog.findViewById(R.id.parent_password_ivNewpass);
        ImageView ivConfirmpass = changeDialog.findViewById(R.id.parent_password_ivConfirmpass);

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDialog.dismiss();
            }
        });

        ivOldpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    // show password
                    oldpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked = false;
                } else {
                    // hide password
                    oldpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked = true
                    ;
                }
            }
        });

        ivNewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked1) {
                    // show password
                    newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked1 = false;
                } else {
                    // hide password
                    newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked1 = true;
                }
            }
        });

        ivConfirmpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked2) {
                    // show password
                    confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked2 = false;
                } else {
                    // hide password
                    confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked2 = true;
                }
            }
        });


        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utility.getPref(SelectChildActivity.this, "upass").equalsIgnoreCase(oldpassword.getText().toString())) {
                    if (!newpassword.getText().toString().equalsIgnoreCase("")) {
                        if (newpassword.getText().toString().length() >= 4 && newpassword.getText().toString().length() <= 12) {
                            if (newpassword.getText().toString().equalsIgnoreCase(confirmpassword.getText().toString())) {
                            callChangePasswordApi(newpassword.getText().toString());
                            } else {
                                confirmpassword.setError("Confirm password does not match");
                            }
                        } else {
                            newpassword.setError("Password must be 4-12 Characters.");
                        }
                    } else {
//                    Utils.ping(mContex, "Confirm Password does not match.");
                        newpassword.setError("Please enter password");
                        newpassword.setText("");
                    }
                } else {
                    Utility.ping(SelectChildActivity.this, "Please enter correct current password");
                }

            }
        });
        changeDialog.show();

    }

    public void getchild() {

        progressDialog = new ProgressDialog(SelectChildActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        param.put("UserID", Utility.getPref(SelectChildActivity.this, "unm"));
        param.put("Password", Utility.getPref(SelectChildActivity.this, "pwd"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    verifyLoginAsyncTask = new VerifyLoginAsyncTask(param);
                    result = verifyLoginAsyncTask.execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                                if (result.size() > 0) {

                            if (result.getSuccess().equalsIgnoreCase("True")) {
//                              TODO: Store result values for future use
//
                                tvName.setText(result.getFamilyDetails().get(0).getFamilyname());
                                tvEmail.setText(result.getFamilyDetails().get(0).getEmailaddress());
                                tvPhone.setText(result.getFamilyDetails().get(0).getContactno());

                                selectChildAdapter = new SelectChildAdapter(SelectChildActivity.this, result.getFinalarray());
                                rvList.setAdapter(selectChildAdapter);
                                Log.e("bithdaywishhh", "true");

                                progressDialog.dismiss();

                            } else {
                                progressDialog.dismiss();
                            }
                        }
                    });
                } catch (Exception e) {

                    progressDialog.dismiss();

                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void callChangePasswordApi(final String newpass) {
        if (Utility.isNetworkConnected(SelectChildActivity.this)) {
            progressDialog = new ProgressDialog(SelectChildActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("FamilyID", Utility.getPref(SelectChildActivity.this, "FamilyID"));
                        params.put("NewPassword", newpass);
                        changePasswordAsyncTask = new ParentChangePAsswordAsyncTask(params);
                        final Boolean result = changePasswordAsyncTask.execute().get();

                        SelectChildActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (result == true) {
                                    changeDialog.dismiss();
                                    Utility.setPref(SelectChildActivity.this, "RegisterStatus", "true");
                                    Utility.pong(SelectChildActivity.this, "Password Updated Successfully");
                                    if (!Utility.getPref(SelectChildActivity.this, "pwd").equalsIgnoreCase("")) {
                                        Utility.setPref(SelectChildActivity.this, "pwd", newpass);
                                        //  getUserProfile();

                                        Intent intent = new Intent(SelectChildActivity.this, LoginActivity.class);
                                        startActivity(intent);

                                    }

                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Utility.ping(SelectChildActivity.this, "NEtwork not available");
        }
    }

}
