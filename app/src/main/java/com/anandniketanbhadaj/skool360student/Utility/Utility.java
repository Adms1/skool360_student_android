package com.anandniketanbhadaj.skool360student.Utility;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.Toast;

import com.anandniketanbhadaj.skool360student.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class Utility {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static SharedPreferences sharedpreferences;
    private static final int  MEGABYTE = 1024 * 1024;
    public static String parentFolderName = "Skool 360 Bhadaj";
    public static String childAnnouncementFolderName = "Announcement";
    public static String childCircularFolderName = "Circular";
    public static Dialog dialog;
    public static AVLoadingIndicatorView avi;

    public static boolean isNetworkConnected(Context ctxt) {
        ConnectivityManager cm = (ConnectivityManager) ctxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void ping(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void pong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setPref(Context context, String key, String value){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
        editor.apply();
    }

    public static void setIntPref(Context context, String key, int value){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        editor.apply();
    }

    public static int getIntPref(Context context, String key){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int value = sharedpreferences.getInt(key, 0);
        return value;
    }

    public static String getPref(Context context, String key){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key, "");
        return value;
    }

    public static boolean isFileExists(String fileName, String moduleName){
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        if(moduleName.equalsIgnoreCase("announcement"))
            return new File(extStorageDirectory, parentFolderName+"/"+ childAnnouncementFolderName +"/"+fileName).isFile();
        else
            return new File(extStorageDirectory, parentFolderName+"/"+ childCircularFolderName +"/"+fileName).isFile();
    }

    public static File createFile(String fileName, String moduleName){
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = null;

        if(moduleName.equalsIgnoreCase("announcement"))
            folder = new File(extStorageDirectory, parentFolderName+"/"+childAnnouncementFolderName);
        else
            folder = new File(extStorageDirectory, parentFolderName+"/"+childCircularFolderName);

        folder.mkdirs();

        File pdfFile = new File(folder, fileName);

        try{
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        return pdfFile;
    }

    public static void downloadFile(String fileUrl, String fileName, String moduleName){
        try {

            File directoryPath = createFile(fileName, moduleName);

            fileUrl = fileUrl.replace(" ", "%20");
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directoryPath);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer)) > 0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getTodaysDate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);


        String mDAY, mMONTH, mYEAR;

        mDAY = Integer.toString(dd);
        mMONTH = Integer.toString(mm);
        mYEAR = Integer.toString(yy);

        if (dd < 10) {
            mDAY = "0" + mDAY;
        }
        if (mm < 10) {
            mMONTH = "0" + mMONTH;
        }

        return mDAY + "/" + mMONTH + "/" + mYEAR;
    }

    public static void showDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressbar_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        avi=(AVLoadingIndicatorView)dialog.findViewById(R.id.avi) ;
      avi.show();
        dialog.show();
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing())
            try {
            avi.hide();
                dialog.dismiss();
            } catch (final IllegalArgumentException e) {
                // Do nothing.
            } catch (final Exception e) {
                // Do nothing.
            } finally {
                dialog = null;
            }
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public static boolean checkAndRequestPermissions(final Context context) {
        int readExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);



        List<String> listPermissionsNeeded = new ArrayList<>();

        if (readExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    static SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");

    public static boolean CheckDates(String startdate,String enddate)   {
        boolean b = true;
        try {
            if(dfDate.parse(enddate).before(dfDate.parse(startdate)))
            {
                b = false;//If start date is before end date
            }
            else if(dfDate.parse(enddate).equals(dfDate.parse(startdate)))
            {
                b = true;//If two dates are equal
            }
            else
            {
                b = true; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static boolean checkBirthdayOfUser(int month,int  date){

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH) + 1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        if((month == monthOfYear) && (date == dayOfMonth)){
            return true;
        }
        return false;
    }

    public static void showUserBirthdayWish(Context context,String Bday){

        try {
            if (Utility.getPref(context, "user_birthday_wish").equalsIgnoreCase("0")) {
                if (Utility.getPref(context, "user_birthday") != null) {
                    if (Utility.getPref(context, "user_birthday").equals("")) {

                        Utility.setPref(context, "user_birthday", Bday);

                        String Bday_local = Utility.getPref(context, "user_birthday");

                        String[] BdayArray = Bday_local.split("/");
                        String date = BdayArray[0];
                        String month = BdayArray[1];

                        if (Utility.checkBirthdayOfUser(Integer.parseInt(month), Integer.parseInt(date))) {

                            String flag = Utility.getPref(context, "user_birthday_wish");
                            if (flag != null && !flag.equalsIgnoreCase("")) {
                                if (flag.equalsIgnoreCase("0")) {
                                    Utility.setPref(context, "user_birthday_wish", "1");
                                    Utility.setPref(context, "user_birthday", "N/A");
                                    DialogUtils.showGIFDialog(context, "Anand Niketan Bhadaj");

                                }
                            }
                        }
                    }else {

//                            Utility.setPref(context, "user_birthday", Bday);
//
//                            String Bday_local = Utility.getPref(context, "user_birthday");
//
//                            String[] BdayArray = Bday_local.split("/");
//                            String date = BdayArray[0];
//                            String month = BdayArray[1];
//
//                            if (Utility.checkBirthdayOfUser(Integer.parseInt(month), Integer.parseInt(date))) {
//                                String flag = Utility.getPref(context, "user_birthday_wish");
//                                if (flag != null && !flag.equalsIgnoreCase("")) {
//                                    if (flag.equalsIgnoreCase("0")) {
//                                        Utility.setPref(context, "user_birthday_wish", "1");
//
//                                        Utility.setPref(context, "user_birthday", "N/A");
//                                        DialogUtils.showGIFDialog(context, "Anand Niketan Bhadaj");
//
//                                    }
//                                }
//                            }
                    }
                }
            }
            else {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
