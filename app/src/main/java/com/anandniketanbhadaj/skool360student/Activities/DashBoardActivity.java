package com.anandniketanbhadaj.skool360student.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360student.Adapter.menuoptionItemAdapter;
import com.anandniketanbhadaj.skool360student.AsyncTasks.DeleteDeviceDetailAsyncTask;
import com.anandniketanbhadaj.skool360student.AsyncTasks.GetUserProfileAsyncTask;
import com.anandniketanbhadaj.skool360student.Fragments.AnnouncmentFragment;
import com.anandniketanbhadaj.skool360student.Fragments.AttendanceFragment;
import com.anandniketanbhadaj.skool360student.Fragments.CircularFragment;
import com.anandniketanbhadaj.skool360student.Fragments.ClassworkFragment;
import com.anandniketanbhadaj.skool360student.Fragments.ExamSyllabusFragment;
import com.anandniketanbhadaj.skool360student.Fragments.FeesFragment;
import com.anandniketanbhadaj.skool360student.Fragments.GalleryFragment;
import com.anandniketanbhadaj.skool360student.Fragments.HolidayFragment;
import com.anandniketanbhadaj.skool360student.Fragments.HomeFragment;
import com.anandniketanbhadaj.skool360student.Fragments.HomeworkFragment;
import com.anandniketanbhadaj.skool360student.Fragments.PaymentFragment;
import com.anandniketanbhadaj.skool360student.Fragments.ProfileFragment;
import com.anandniketanbhadaj.skool360student.Fragments.ReportCardFragment;
import com.anandniketanbhadaj.skool360student.Fragments.ResultFragment;
import com.anandniketanbhadaj.skool360student.Fragments.ShowLeaveFragment;
import com.anandniketanbhadaj.skool360student.Fragments.SuggestionMainFragment;
import com.anandniketanbhadaj.skool360student.Fragments.TimeTableFragment;
import com.anandniketanbhadaj.skool360student.Models.ExamSyllabus.CreateLeaveModel;
import com.anandniketanbhadaj.skool360student.Models.ShowBirthdayWish;
import com.anandniketanbhadaj.skool360student.Models.StudProfileModel;
import com.anandniketanbhadaj.skool360student.Models.menuoptionItem;
import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.DialogUtils;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class DashBoardActivity extends FragmentActivity {
    public static String filename = "Valustoringfile";
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    static RelativeLayout leftRl;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    ImageView viewprofile_img;
    static Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    String MenuName[];
    String token;
    int dispPOS = 0;
    SharedPreferences SP;
    Fragment fragment = null;
    int myid;
    boolean first_time_trans = true;
    CreateLeaveModel logoutResponse;
    private ArrayList<menuoptionItem> navDrawerItems_main;
    private menuoptionItemAdapter adapter_menu_item;
    private String putData = "0";
    private DeleteDeviceDetailAsyncTask deleteDeviceDetailAsyncTask = null;
    private ProgressDialog progressDialog = null;
    private static GetUserProfileAsyncTask getUserProfileAsyncTask = null;
    private static ArrayList<StudProfileModel> studDetailList = new ArrayList<>();
    private static ImageLoader imageLoader;
    private static CircleImageView profile_image;
    private static TextView studName;
    private static TextView grade;
    private TextView selectChild;
    private String name;

    private ArrayList<ShowBirthdayWish> birthdayWishes;

    public static void onLeft() {
        // TODO Auto-generated method stub
         new DashBoardActivity().getUserProfile();
        mDrawerList.setSelectionAfterHeaderView();
        mDrawerLayout.openDrawer(leftRl);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        // FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        mContext = this;
        Initialize();
        dispPOS = getIntent().getIntExtra("POS", 0);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        AppConfiguration.firsttimeback = true;

        Log.d("Dashboard : notificationDefault", "gegeg");
//        Log.d("Data123", Utility.getPref(mContext, "data"));
//        Log.d("messagekill", Utility.getPref(mContext, "message"));


//        displayView(dispPOS);
//        String fromWhere = getIntent().getStringExtra("fromNotification");

        /*if(fromWhere.equalsIgnoreCase("android.intent.action.MAIN")){
            displayView(0);

        }else {
            displayView(Integer.parseInt(fromWhere));
        }*/

//        Log.d("Dashboard : fromNotification", fromWhere);
//        onNewIntent(getIntent());

//        Log.d("Data",Utility.getPref(mContext,"data"));
//        Log.d("message",Utility.getPref(mContext,"message"));

//        birthdayWishes = Utility.loadArray(DashBoardActivity.this, "showbirthday");

        try {
            name = getIntent().getStringExtra("Name");
            Log.d("Dashboard : notificationname", name);
        }catch (Exception ex){
            ex.printStackTrace();
            Log.d("Dashboard : notificationex", "jjtgnjhgt");
        }

        if (getIntent().getStringExtra("message") != null) {
            putData = getIntent().getStringExtra("message");
            Log.d("Dashboard : notificationData", putData);
        }
        if (getIntent().getStringExtra("fromNotification") != null) {
            AppConfiguration.Notification = "1";
            AppConfiguration.messageNotification = putData;
            String key = getIntent().getStringExtra("fromNotification");
            Log.d("key", key);
            if (key.equalsIgnoreCase("HW")) {
                displayView(4);
            } else if (key.equalsIgnoreCase("CW")) {
                displayView(5);
            } else if (key.equalsIgnoreCase("Attendance")) {
                displayView(3);
            } else if (key.equalsIgnoreCase("Announcement")) {
                displayView(2);
            } else if (key.equalsIgnoreCase("Circular")) {
                displayView(13);
            }else if (key.equalsIgnoreCase("Leave")){
                displayView(12);
            }else if(key.equalsIgnoreCase("Suggestion")){
                displayView(15);
            }else if(key.equalsIgnoreCase("Birthday")){
                if (name != null) {
                    if (!TextUtils.isEmpty(name)) {

//                        for (int i = 0; i < birthdayWishes.size(); i++) {
//                            if(Utility.getPref(DashBoardActivity.this, "studid").equalsIgnoreCase(birthdayWishes.get(i).getId())){
//                                birthdayWishes.get(i).setShow(true);
//                            }
//
//                            if(birthdayWishes.get(i).isShow()) {
                        String fullname = name.replace("|", " ");
                        displayView(0);
                        AppConfiguration.Notification = "0";
                        DialogUtils.showGIFDialog(DashBoardActivity.this, fullname);
//                                birthdayWishes.get(i).setShow(false);
//                            }
//                        }
                    }
                }

            }
        } else {
            AppConfiguration.Notification = "0";
            displayView(0);
        }
//        if (!AppConfiguration.dataNOtification.equalsIgnoreCase("")) {
//            Log.d("dataNotification", AppConfiguration.dataNOtification);
//            Log.d("dataMessage", AppConfiguration.messageNotification);
//        }

//        if (Utility.getPref(mContext, "data") != null) {
//            AppConfiguration.Notification = "1";
//            if (!Utility.getPref(mContext, "data").equalsIgnoreCase("")) {
//                //   String[] split = Utility.getPref(mContext, "data").split("\\=");
//                String key = Utility.getPref(mContext, "data");//.substring(0, split[1].length() - 1);
//                Log.d("key", key);
//                if (key.equalsIgnoreCase("Homework")) {
//                    key = "HW";
//                }
//                if (key.equalsIgnoreCase("Classwork")) {
//                    key = "CW";
//                }
//                if (key.equalsIgnoreCase("HW")) {
//                    displayView(4);
//                } else if (key.equalsIgnoreCase("CW")) {
//                    displayView(5);
//                } else if (key.equalsIgnoreCase("Attendance")) {
//                    displayView(3);
//                } else if (key.equalsIgnoreCase("Announcement")) {
//                    displayView(2);
//                } else if (key.equalsIgnoreCase("Circular")) {
//                    displayView(13);
//                }
//                Utility.setPref(mContext, "data", "");
//                Utility.setPref(mContext, "message", "");
//                Log.d("Data123", Utility.getPref(mContext, "data"));
//                Log.d("message123", Utility.getPref(mContext, "message"));
//            } else {
//                AppConfiguration.Notification = "0";
//                displayView(0);
//            }

//        } else {
//            AppConfiguration.Notification = "0";
//            displayView(0);
//        }


        System.out.println("Dashboard Message :" + getIntent().getStringExtra("message"));
        System.out.println("Dashboard Extra : " + getIntent().getStringExtra("fromNotification"));

    }

    private void Initialize() {
        // TODO Auto-generated method stub
        MenuName = getResources().getStringArray(R.array.menuoption1);
        viewprofile_img = findViewById(R.id.viewprofile_img);
        studName = findViewById(R.id.studName);
        profile_image = findViewById(R.id.profile_image);
        grade = findViewById(R.id.grade);
        selectChild = findViewById(R.id.select_child);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        leftRl = findViewById(R.id.whatYouWantInLeftDrawer);
        mDrawerList = findViewById(R.id.list_slidermenu);
        navDrawerItems_main = new ArrayList<>();
        adapter_menu_item = new menuoptionItemAdapter(DashBoardActivity.this, navDrawerItems_main);
        for (int i = 0; i < MenuName.length; i++) {
            navDrawerItems_main.add(new menuoptionItem(MenuName[i]));
        }
        mDrawerList.setAdapter(adapter_menu_item);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        viewprofile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(1);
            }
        });

        if(Utility.getIntPref(DashBoardActivity.this, "childcount") > 1){
            selectChild.setVisibility(View.VISIBLE);
        }else {
            selectChild.setVisibility(View.GONE );
        }

        selectChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, SelectChildActivity.class);
                startActivity(intent);
            }
        });

        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
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

        getUserProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void displayView(int position) {
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                if (getIntent().getStringExtra("message") != null) {
                    putData = getIntent().getStringExtra("message");
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                } else {
                    putData = "test";
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                }
                AppConfiguration.position = 0;
                break;
            case 1:
                fragment = new ProfileFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 1;
                break;
            case 2:
                fragment = new AnnouncmentFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 2;
                break;
            case 3:
                fragment = new AttendanceFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 3;
                break;
            case 4:
                fragment = new HomeworkFragment();
                if (getIntent().getStringExtra("message") != null) {
                    putData = getIntent().getStringExtra("message");
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                } else {
                    putData = "test";
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                }
                AppConfiguration.position = 4;
                break;
            case 5:
                fragment = new ClassworkFragment();
                if (getIntent().getStringExtra("message") != null) {
                    putData = getIntent().getStringExtra("message");
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                } else {
                    putData = "test";
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;
                }
                break;
            case 6:
                fragment = new TimeTableFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 6;
                break;
            case 7:
                fragment = new ExamSyllabusFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 7;
                break;
            case 8:
                fragment = new ResultFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 8;
                break;
            case 9:
                fragment = new ReportCardFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 9;
                break;
            case 10:
                fragment = new FeesFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 10;
                break;
//            case 11:
//                fragment = new ImprestFragment();
//                myid = fragment.getId();
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                AppConfiguration.firsttimeback = true;
//                break;
            case 11:
                fragment = new HolidayFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;
                break;
            case 12:
                fragment = new ShowLeaveFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 12;
                break;
            case 13:
                fragment = new CircularFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 13;
                break;
            case 14:
                fragment = new GalleryFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 14;
                break;
            case 15:
                fragment = new SuggestionMainFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 15;
                break;
            case 16:
                AppConfiguration.position = 16;
                new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AppTheme))
                        .setCancelable(false)
                        .setTitle("Logout")
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getDeleteDeviceData();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing

                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();
                break;

            case 18:
                fragment = new PaymentFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 18;
                break;
        }

        if (AppConfiguration.position != 16) {
            if (fragment != null) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                if (fragment instanceof HomeFragment) {
                    if (first_time_trans) {
                        first_time_trans = false;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();

                    } else {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                }

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                mDrawerLayout.closeDrawers();
            } else {
                // error in creating fragment
                Log.e("Dashboard", "Error in creating fragment");
            }
        }else{
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (AppConfiguration.firsttimeback) {
            if (AppConfiguration.position != 0) {
                displayView(AppConfiguration.position);
            }
//            else {
//                Utility.ping(mContext, "Press again to exit");
//            }
            AppConfiguration.firsttimeback = false;
        } else {
            finish();
            System.exit(0);
        }
    }

    public void getDeleteDeviceData() {
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
                        params.put("DeviceID", Utility.getPref(mContext, "deviceId"));
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));
                        deleteDeviceDetailAsyncTask = new DeleteDeviceDetailAsyncTask(params);
                        logoutResponse = deleteDeviceDetailAsyncTask.execute().get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (logoutResponse!=null) {
                                    if (logoutResponse.getSuccess().equalsIgnoreCase("True")) {
                                        Utility.setPref(mContext, "unm", "");
                                        Utility.setPref(mContext, "pwd", "");
                                        Utility.setPref(mContext, "studid", "");
                                        Utility.setPref(mContext, "FamilyID", "");
                                        Utility.setPref(mContext, "standardID", "");
                                        Utility.setPref(mContext, "ClassID", "");
                                        Utility.setPref(mContext, "TermID", "");
                                        Utility.setPref(mContext, "deviceId", "");
                                        Utility.setPref(mContext, "image", "");
                                        Utility.setPref(mContext,"user_birthday","");
                                        Utility.setPref(mContext, "user_birthday_wish", "0");
                                        Utility.birthday.clear();
                                        Utility.setArray(mContext, "birthdayarr", Utility.birthday);
                                        AppConfiguration.UserImage = "";
                                        AppConfiguration.UserName = "";
                                        AppConfiguration.UserGrade = "";
                                        AppConfiguration.UserGrNo = "";
                                        AppConfiguration.UserAttendance = "";
                                        AppConfiguration.UserTeacherName = "";
                                        AppConfiguration.UserDropTime = "";
                                        AppConfiguration.UserPickTime = "";
                                        Intent intentLogin = new Intent(DashBoardActivity.this, LoginActivity.class);
                                        startActivity(intentLogin);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();

                                    }
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

    public void getUserProfile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("StudentID", Utility.getPref(mContext, "studid"));
                    params.put("LocationID", Utility.getPref(mContext, "locationId"));
                    getUserProfileAsyncTask = new GetUserProfileAsyncTask(params);
                    studDetailList = getUserProfileAsyncTask.execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (studDetailList!=null) {
                                if (studDetailList.size() > 0) {
                                    imageLoader.displayImage(studDetailList.get(0).getStudentImage(), profile_image);
                                    studName.setText(studDetailList.get(0).getStudentName() + " (" + studDetailList.get(0).getGRNO() + ")");
                                    grade.setText(studDetailList.get(0).getStandard() + "-" + studDetailList.get(0).getStudClass());
                                    // grno.setText("GRNo :" + " " + studDetailList.get(0).getGRNO());

                                }
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

    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

}
