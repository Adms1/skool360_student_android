package com.anandniketanbhadaj.skool360student.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360student.Activities.Server_Error;
import com.anandniketanbhadaj.skool360student.Adapter.HomeImageAdapter;
import com.anandniketanbhadaj.skool360student.Adapter.ImageAdapter;
import com.anandniketanbhadaj.skool360student.AsyncTasks.AddDeviceDetailAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.ChangePasswordAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetUserProfileAsyncTask;
import com.anandniketanbhadaj.skool360student.Models.DeviceVersionModel;
import com.anandniketanbhadaj.skool360student.Models.StudProfileModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class HomeFragment extends Fragment {

    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; //
    int NUM_PAGES;
    int currentPage = 0;
    Timer timer;
    DeviceVersionModel deviceVersionModel;
    // Use for Rating
    Dialog ratingDialog;
    TextView rate_it_txt_view, reminde_me_txt, no_thanks_txt;
    String putData;
    //Use for dialog
    Dialog changeDialog;
    EditText edtnewpassword, edtconfirmpassword, edtcurrentpassword;
    Button changepwd_btn;
    String passWordStr, confirmpassWordStr;
    private View rootView;
    private Button btnMenu;
    private GridView grid_view;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //    Change Megha 04-09-2017
    private CircleImageView profile_image;
    private ViewPager viewPageAndroid;
    private int[] sliderImagesId = new int[]{
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3};
    private GetUserProfileAsyncTask getUserProfileAsyncTask = null;
    private ArrayList<StudProfileModel> studDetailList = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private ImageLoader imageLoader;
    private TextView student_name_txt, student_classname_txt, teacher_name_txt, teacher_name1_txt,
            vehicle_name_txt, vehicle_picktime_txt, vehicle_droptime_txt, admission_txt, attendance_txt;
    private boolean isVersionCodeUpdated = false;
    private int versionCode = 0;
    //    private DeviceVersionAsyncTask deviceVersionAsyncTask = null;
    private ChangePasswordAsyncTask changePasswordAsyncTask = null;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        mContext = getActivity().getApplicationContext();
        initViews();
        setListners();
//        if (Utility.getPref(mContext, "Loginwithother").equalsIgnoreCase("True")) {
        getUserProfile();
//        } else {
//            getVersionUpdateInfo();
//        }
        return rootView;
    }

    public void initViews() {

        PackageInfo pInfo = null;

        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        btnMenu = rootView.findViewById(R.id.btnMenu);
        grid_view = rootView.findViewById(R.id.grid_view);
        grid_view.setAdapter(new ImageAdapter(mContext));
        viewPageAndroid = rootView.findViewById(R.id.viewPageAndroid);
        student_name_txt = rootView.findViewById(R.id.student_name_txt);
        student_classname_txt = rootView.findViewById(R.id.student_classname_txt);
        teacher_name_txt = rootView.findViewById(R.id.teacher_name_txt);
        teacher_name1_txt = rootView.findViewById(R.id.teacher_name1_txt);
        vehicle_name_txt = rootView.findViewById(R.id.vehicle_name_txt);
        vehicle_picktime_txt = rootView.findViewById(R.id.vehicle_picktime_txt);
        vehicle_droptime_txt = rootView.findViewById(R.id.vehicle_droptime_txt);
        admission_txt = rootView.findViewById(R.id.admission_txt);
        attendance_txt = rootView.findViewById(R.id.attendance_txt);

        HomeImageAdapter adapterView = new HomeImageAdapter(getActivity(), sliderImagesId);
        viewPageAndroid.setAdapter(adapterView);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                viewPageAndroid.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        profile_image = rootView.findViewById(R.id.profile_image);
        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .threadPriority(Thread.MAX_PRIORITY)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                .build();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

    }

    public void getRegistrationID() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //getting old saved token
        String old_token = Utility.getPref(getActivity(), "registration_id");
        //check if called token is new
        if (refreshedToken != null && !old_token.equalsIgnoreCase(refreshedToken)) {
            Utility.setPref(getActivity(), "registration_id", refreshedToken);
            sendRegistrationToServer(refreshedToken, Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID));
        } else {
            sendRegistrationToServer(old_token, Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID));
        }
//        Log.d("reg id from activity: ", refreshedToken);
    }

    /*public synchronized String uniqueID() {
        String uniqueID = Utility.getPref(getActivity(), "uniqueID");

        if (uniqueID == null || uniqueID.equalsIgnoreCase("")) {
            uniqueID = UUID.randomUUID().toString();
            Utility.setPref(getActivity(), "uniqueID", uniqueID);
        }
        Log.d("Unique id : ", uniqueID);
        return uniqueID;
    }*/


    private void sendRegistrationToServer(String token, String uniqueID) {
        Utility.setPref(mContext, "deviceId", uniqueID);

        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("StudentID", Utility.getPref(getActivity(), "studid"));
            hashMap.put("DeviceId", uniqueID); // uniqueID
            hashMap.put("TokenId", token); //token
            hashMap.put("DeviceType", "A");
            hashMap.put("LocationID", Utility.getPref(mContext, "locationId"));
            AddDeviceDetailAsyncTask addDeviceDetailAsyncTask = new AddDeviceDetailAsyncTask(hashMap);
            boolean result = addDeviceDetailAsyncTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //change Megha 04-09-2017

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new AnnouncmentFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 1;
                } else if (position == 1) {
                    fragment = new AttendanceFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 2;
                } else if (position == 2) {
                    putData = "test";
                    fragment = new HomeworkFragment();
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 3;
                } else if (position == 3) {
                    putData = "test";
                    fragment = new ClassworkFragment();
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 4;
                } else if (position == 4) {
                    fragment = new TimeTableFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;
                } else if (position == 5) {
                    fragment = new ExamSyllabusFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 6;
                } else if (position == 6) {
                    fragment = new ResultFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 7;
                } else if (position == 7) {
                    fragment = new ReportCardFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 8;
                } else if (position == 8) {
                    fragment = new FeesFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 9;
                }
//                else if (position == 9) {
//                    fragment = new ImprestFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
//                            .replace(R.id.frame_container, fragment).commit();
//                    AppConfiguration.firsttimeback = true;
//                }
                else if (position == 9) {
                    fragment = new HolidayFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 10;
                } else if (position == 10) {
                    fragment = new ShowLeaveFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;
                } else if (position == 11) {
                    fragment = new CircularFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 12;
                } else if (position == 12) {
                    fragment = new GalleryFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 13;
                } else if (position == 13) {
                    fragment = new SuggestionMainFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 14;
                }
            }
        });
    }

    public void getUserProfile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("StudentID", Utility.getPref(mContext, "studid"));
                    params.put("LocationID", Utility.getPref(mContext, "locationId"));
                    getUserProfileAsyncTask = new GetUserProfileAsyncTask(params);
                    studDetailList = getUserProfileAsyncTask.execute().get();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (studDetailList != null) {
                                if (studDetailList.size() > 0) {
//                            student_name_txt.setText("Bhadresh Jadav");

                                    String bDate = studDetailList.get(0).getStudentDOB();

                                    Utility.showUserBirthdayWish(getActivity(), bDate);

                                    student_name_txt.setText(studDetailList.get(0).getStudentName());
                                    vehicle_picktime_txt.setText("Pick Up : " + studDetailList.get(0).getTransport_PicupTime());
                                    vehicle_droptime_txt.setText("Drop Off : " + studDetailList.get(0).getTransport_DropTime());
                                    student_classname_txt.setText("Grade : " + " " + studDetailList.get(0).getStandard() + "  " + "Section :" + " " + studDetailList.get(0).getStudClass());
                                    teacher_name1_txt.setText(studDetailList.get(0).getTeacherName());
                                    // teacher_name1_txt.setText("Sourabh Pachouri");
                                    imageLoader.displayImage(studDetailList.get(0).getStudentImage(), profile_image);
                                    // Utility.setPref(mContext, "image", studDetailList.get(0).getStudentImage());
                                    admission_txt.setText("GRNo :" + " " + studDetailList.get(0).getGRNO());

                                    if (studDetailList.get(0).getTodayAttendance().equalsIgnoreCase("")) {
                                        attendance_txt.setText("Attendance :" + " " + "N/A Today");
                                    } else {
                                        attendance_txt.setText("Attendance :" + " " + studDetailList.get(0).getTodayAttendance());
                                    }

                                    if (!Utility.getPref(mContext, "Loginwithother").equalsIgnoreCase("True")) {
                                        if (Utility.getPref(mContext, "RegisterStatus").equalsIgnoreCase("false")) {

                                            changePasswordDialog();

                                        } else {

                                            getRegistrationID();

                                            if (Utility.getPref(mContext, "LAST_LAUNCH_DATE").equalsIgnoreCase(Utility.getTodaysDate())) {
                                                // Date matches. User has already Launched the app once today. So do nothing.
                                            } else {
                                                RatingDialog();
                                                Utility.setPref(mContext, "LAST_LAUNCH_DATE", Utility.getTodaysDate());
                                            }
                                        }
                                    }

                                    AppConfiguration.UserImage = studDetailList.get(0).getStudentImage();
                                    AppConfiguration.UserName = studDetailList.get(0).getStudentName();
                                    AppConfiguration.UserGrade = "Grade : " + " " + studDetailList.get(0).getStandard() + "  " + "Section :" + " " + studDetailList.get(0).getStudClass();
                                    AppConfiguration.UserGrNo = "GRNo :" + " " + studDetailList.get(0).getGRNO();
                                    AppConfiguration.UserTeacherName = studDetailList.get(0).getTeacherName();
                                    AppConfiguration.UserDropTime = "Drop Off : " + studDetailList.get(0).getTransport_DropTime();
                                    AppConfiguration.UserPickTime = "Pick Up : " + studDetailList.get(0).getTransport_PicupTime();
                                    AppConfiguration.UserAttendance = attendance_txt.getText().toString();

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

    }

//    public void getVersionUpdateInfo() {
//        if (Utility.isNetworkConnected(mContext)) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//
//                        HashMap<String, String> params = new HashMap<>();
////                        params.put("UserID", Utility.getPref(mContext, "studid"));
////                        params.put("VersionID", String.valueOf(versionCode));//String.valueOf(versionCode)
////                        params.put("UserType", "Student");
//                        //=========new ========
//                        params.put("VersionID",String.valueOf(versionCode));//String.valueOf(versionCode)
//                        params.put("DeviceType","Android");
//                        deviceVersionAsyncTask = new DeviceVersionAsyncTask(params);
//                        deviceVersionModel = deviceVersionAsyncTask.execute().get();
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (deviceVersionModel!=null) {
//                                    getUserProfile();
//                                    if (deviceVersionModel.getSuccess().equalsIgnoreCase("True")) {
//                                        isVersionCodeUpdated = true;
//                                        Log.d("hellotrue", "" + isVersionCodeUpdated);
//                                        // getRegistrationID();
//                                        if (Utility.isAppIsInBackground(mContext)) {
//
//                                        } else {
//                                            if (!AppConfiguration.UserImage.equalsIgnoreCase("")) {
//                                                imageLoader.displayImage(AppConfiguration.UserImage, profile_image);
//                                                student_name_txt.setText(AppConfiguration.UserName);
//                                                student_classname_txt.setText(AppConfiguration.UserGrade);
//                                                admission_txt.setText(AppConfiguration.UserGrNo);
//                                                attendance_txt.setText(AppConfiguration.UserAttendance);
//                                                teacher_name1_txt.setText(AppConfiguration.UserTeacherName);
//                                                vehicle_picktime_txt.setText(AppConfiguration.UserPickTime);
//                                                vehicle_droptime_txt.setText(AppConfiguration.UserDropTime);
//                                            } else {
//
//                                            }
//                                        }
//
//                                    } else {
//                                        isVersionCodeUpdated = false;
//                                        Log.d("hellofalse", "" + isVersionCodeUpdated);
//                                        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppTheme))
//                                                .setCancelable(false)
//                                                .setTitle("Skool360 Bhadaj Update")
//                                                .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
//                                                .setMessage("Please update to a new version of the app.")
//                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.anandniketanbhadaj.skool360"));//"market://details?id=com.anandniketanbhadaj.skool360"));
//                                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                        mContext.startActivity(i);
//                                                    }
//                                                })
//                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        // do nothing
//                                                        dialog.dismiss();
//                                                        Utility.pong(mContext, "You wont be able to use other funcationality without updating to a newer version");
//                                                        //getActivity().finish();
//                                                    }
//                                                })
//                                                .setIcon(R.drawable.ic_launcher)
//                                                .show();
//
//                                    }
//                                }else{
////                                   Intent serverintent=new Intent(mContext, Server_Error.class);
////                                   startActivity(serverintent);
//                                }
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        } else {
//            Utility.ping(mContext, "Network not available");
//
//        }
//    }

    public void RatingDialog() {
        ratingDialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        Window window = ratingDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        ratingDialog.getWindow().getAttributes().verticalMargin = 0.10f;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        ratingDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);

        ratingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ratingDialog.setCancelable(false);
        ratingDialog.setContentView(R.layout.rating_dialog);

        rate_it_txt_view = ratingDialog.findViewById(R.id.rate_it_txt_view);
        reminde_me_txt = ratingDialog.findViewById(R.id.reminde_me_txt);
        no_thanks_txt = ratingDialog.findViewById(R.id.no_thanks_txt);

        rate_it_txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.anandniketanbhadaj.skool360"));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                ratingDialog.dismiss();
            }
        });
        reminde_me_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingDialog.dismiss();
            }
        });
        no_thanks_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingDialog.dismiss();
            }
        });
        ratingDialog.show();

    }

    public void changePasswordDialog() {
        changeDialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        Window window = changeDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        changeDialog.getWindow().getAttributes().verticalMargin = 0.0f;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        changeDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);
//        changeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeDialog.setCancelable(false);
        changeDialog.setContentView(R.layout.change_password_dialog);

        changepwd_btn = changeDialog.findViewById(R.id.changepwd_btn);
        edtconfirmpassword = changeDialog.findViewById(R.id.edtconfirmpassword);
        edtnewpassword = changeDialog.findViewById(R.id.edtnewpassword);


        changepwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmpassWordStr = edtconfirmpassword.getText().toString();
                passWordStr = edtnewpassword.getText().toString();
                if (!passWordStr.equalsIgnoreCase("")) {
                    if (passWordStr.length() >= 4 && passWordStr.length() <= 12) {
                        if (passWordStr.equalsIgnoreCase(confirmpassWordStr)) {
                            callChangePasswordApi();
                        } else {
                            edtconfirmpassword.setError("Confirm password does not match");
                        }
                    } else {
                        edtnewpassword.setError("Password must be 4-12 Characters.");
                    }
                } else {
//                    Utils.ping(mContex, "Confirm Password does not match.");
                    edtnewpassword.setError("Please enter password");
                    edtnewpassword.setText("");
                    edtnewpassword.setText("");
                }

            }
        });
        changeDialog.show();

    }

    public void callChangePasswordApi() {
        if (Utility.isNetworkConnected(getActivity())) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("OldPassword", Utility.getPref(mContext, "pwd"));
                        params.put("NewPassword", edtnewpassword.getText().toString());
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        changePasswordAsyncTask = new ChangePasswordAsyncTask(params);
                        final Boolean result = changePasswordAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (result == true) {
                                    changeDialog.dismiss();
                                    Utility.setPref(mContext, "RegisterStatus", "true");
                                    Utility.pong(mContext, "Password Updated Successfully");
                                    if (!Utility.getPref(mContext, "pwd").equalsIgnoreCase("")) {
                                        Utility.setPref(mContext, "pwd", edtnewpassword.getText().toString());
                                        //  getUserProfile();
                                    }
                                    getRegistrationID();
                                    if (Utility.getPref(mContext, "LAST_LAUNCH_DATE").equalsIgnoreCase(Utility.getTodaysDate())) {
                                        // Date matches. User has already Launched the app once today. So do nothing.
                                    } else {
                                        RatingDialog();
                                        Utility.setPref(mContext, "LAST_LAUNCH_DATE", Utility.getTodaysDate());
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
            Utility.ping(mContext, "NEtwork not available");
        }
    }

}
