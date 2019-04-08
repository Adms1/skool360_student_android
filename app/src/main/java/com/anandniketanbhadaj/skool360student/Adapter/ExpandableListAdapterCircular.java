package com.anandniketanbhadaj.skool360student.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.anandniketanbhadaj.skool360student.R;
import com.anandniketanbhadaj.skool360student.Models.CircularModel;
import com.anandniketanbhadaj.skool360student.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360student.Utility.Utility;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admsandroid on tha/1/2017.
 */

public class ExpandableListAdapterCircular extends BaseExpandableListAdapter {

    boolean visible = true;
    TextView txtCircularSubject, txtCircularDate;
    LinearLayout llHeaderRow;
    ImageView imgBulletCircular;
    File filepdf;
    String file1;
    private Context _context;
    private ProgressDialog progressDialog = null;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<CircularModel>> _listDataChildcircular;

    public ExpandableListAdapterCircular(Context context, List<String> listDataHeader,
                                         HashMap<String, ArrayList<CircularModel>> listDataChildcircular) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChildcircular = listDataChildcircular;
    }

    @Override
    public ArrayList<CircularModel> getChild(int groupPosition, int childPosititon) {
        return this._listDataChildcircular.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<CircularModel> childData = getChild(groupPosition, 0);
        WebView circular_description_webview;
        TextView showfile;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_circular_row, null);
        }
        circular_description_webview = (WebView) convertView.findViewById(R.id.circular_description_webview);
        showfile = (TextView) convertView.findViewById(R.id.show_file);

        if (childData.get(childPosition).getCircularPDF().equalsIgnoreCase("")) {
            circular_description_webview.setVisibility(View.VISIBLE);
            showfile.setVisibility(View.GONE);
            circular_description_webview.loadData(childData.get(childPosition).getDiscription(), "text/html", "UTF-8");
            WebSettings webSettings = circular_description_webview.getSettings();
            webSettings.setTextSize(WebSettings.TextSize.SMALLER);
            Log.d("webview", childData.get(childPosition).getDiscription());
        } else {
            showfile.setVisibility(View.GONE);
            circular_description_webview.setVisibility(View.GONE);
            showfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String extStorageDirectory = "";
                    String saveFilePath = null;
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    Log.d("date", "" + currentTime);
                    Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                    Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
                    final String fileName = childData.get(childPosition).getCircularPDF().substring(childData.get(childPosition).getCircularPDF().lastIndexOf('/') + 1);
                    if (isSDSupportedDevice && isSDPresent) {
                        // yes SD-card is present
                        Utility.ping(_context, "present");
                        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                        saveFilePath = String.valueOf(new File(extStorageDirectory, Utility.parentFolderName + "/" + Utility.childAnnouncementFolderName + "/" + fileName).getPath());

                    } else {
                        // Sorry
//                            Utility.ping(mContext, "notpresent");
//
                        File cDir = _context.getExternalFilesDir(null);
                        saveFilePath = String.valueOf(new File(cDir.getPath() + "/" + fileName));
                        Log.d("path", saveFilePath);

                    }
//
                    Log.d("path", extStorageDirectory);

                    String fileURL = childData.get(childPosition).getCircularPDF();
                    Log.d("URL", fileURL);
                    if (Utility.isNetworkConnected(_context)) {

                        Ion.with(_context)
                                .load(fileURL)  // download url
                                .write(new File(saveFilePath))  // File no path
                                .setCallback(new FutureCallback<File>() {
                                    //                                    @Override
                                    public void onCompleted(Exception e, File file) {
                                        if (file != null)
                                        {
                                            if (file.length() > 0)
                                            {
                                                //Utility.ping(_context, "Download complete.");
                                                file1 = file.getPath();
                                                filepdf = file.getAbsoluteFile();

                                                Log.d("file11", "" + filepdf);

                                                if (file1 != null) {
                                                    File local_file = new File(file1);
                                                    Log.d("DownloadfilePath", "File to download = " + String.valueOf(local_file));
                                                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                                                    String ext = local_file.getName().substring(local_file.getName().indexOf(".") + 1);
                                                    String type = mime.getMimeTypeFromExtension(ext);
                                                    Log.d("type", type);
                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setDataAndType(Uri.fromFile(file), type);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                    _context.startActivity(intent);
                                                }
                                            } else {
                                                Utility.ping(_context, "Something error");
                                            }
                                        }
                                    }

                                });
                    }
                    else
                        {
                        Utility.ping(_context, "Network not available");
                    }

                }
            });
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChildcircular.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTemp = getGroup(groupPosition).toString().split("\\|");
        String headerTitle = headerTemp[0];
        String headerTitle1 = headerTemp[1];
        final String headerTitle2 = headerTemp[2];
        Log.d("positon", "" + headerTitle + "" + headerTitle1);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_item, null);
        }
        txtCircularSubject = (TextView) convertView.findViewById(R.id.txtCircularSubject);
        txtCircularDate = (TextView) convertView.findViewById(R.id.txtCircularDate);
        llHeaderRow = (LinearLayout) convertView.findViewById(R.id.llHeaderRow);
        imgBulletCircular = (ImageView) convertView.findViewById(R.id.imgBulletCircular);

        txtCircularSubject.setTypeface(null, Typeface.BOLD);
        txtCircularSubject.setText(headerTitle);
        txtCircularDate.setTypeface(null, Typeface.BOLD);
        txtCircularDate.setText(headerTitle1);

//        llHeaderRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        if (isExpanded) {
            txtCircularSubject.setBackgroundColor(Color.parseColor("#86c129"));
            txtCircularDate.setBackgroundColor(Color.parseColor("#86c129"));
            imgBulletCircular.setImageResource(R.drawable.green_bulletpointwithgreenline);

            if (!headerTitle2.equalsIgnoreCase("1")) {
                String extStorageDirectory = "";
                String saveFilePath = null;
                long currentTime = Calendar.getInstance().getTimeInMillis();
                Log.d("date", "" + currentTime);
                Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
                final String fileName = headerTitle2.substring(headerTitle2.lastIndexOf('/') + 1);
                if (isSDSupportedDevice && isSDPresent) {
                    // yes SD-card is present
                    Utility.ping(_context, "present");
                    extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    saveFilePath = String.valueOf(new File(extStorageDirectory, Utility.parentFolderName + "/" + Utility.childAnnouncementFolderName + "/" + fileName).getPath());

                } else {
                    // Sorry
//                            Utility.ping(mContext, "notpresent");
//
                    File cDir = _context.getExternalFilesDir(null);
                    saveFilePath = String.valueOf(new File(cDir.getPath() + "/" + fileName));
                    Log.d("path", saveFilePath);

                }
//
                Log.d("path", extStorageDirectory);

                String fileURL = AppConfiguration.GALLARY_LIVE+headerTitle2;
                Log.d("URL", fileURL);
                if (Utility.isNetworkConnected(_context)) {
                    progressDialog = new ProgressDialog(_context);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(_context)
                            .load(fileURL)  // download url
                            .write(new File(saveFilePath))  // File no path
                            .setCallback(new FutureCallback<File>() {
                                //                                    @Override
                                public void onCompleted(Exception e, File file) {
                                    progressDialog.dismiss();
                                    if (file != null) {
                                        if (file.length() > 0) {
                                            //Utility.ping(_context, "Download complete.");
                                            file1 = file.getPath();
                                            filepdf = file.getAbsoluteFile();
                                            Log.d("file11", "" + filepdf);
                                            if (file1 != null) {


                                                File file2 = new File(file1);
                                                Log.d("DownloadfilePath", "File to download = " + String.valueOf(file2));

                                                Intent intent;

                                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                                    Uri uri = FileProvider.getUriForFile(_context, _context.getPackageName(), file2);
                                                    intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setData(uri);
                                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                    _context.startActivity(intent);
                                                }
//                                                }
                                                else {
//
                                                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                                                    String ext = file2.getName().substring(file2.getName().indexOf(".") + 1);
                                                    String type = mime.getMimeTypeFromExtension(ext);
                                                    Log.d("type", type);
                                                    intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setDataAndType(Uri.fromFile(file), type);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                    _context.startActivity(intent);
                                                }

//                                                File local_file = new File(file1);
//                                                Log.d("DownloadfilePath", "File to download = " + String.valueOf(local_file));
//                                                MimeTypeMap mime = MimeTypeMap.getSingleton();
//                                                String ext = local_file.getName().substring(local_file.getName().indexOf(".") + 1);
//                                                String type = mime.getMimeTypeFromExtension(ext);
//                                                Log.d("type", type);
//                                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                                intent.setDataAndType(Uri.fromFile(file), type);
//                                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                                _context.startActivity(intent);
                                            }

                                        } else {
                                            Utility.ping(_context, "Something error");
                                        }
                                    }
                                }

                            });
                } else {
                    Utility.ping(_context, "Network not available");
                }

            }
        } else {
            txtCircularSubject.setBackgroundColor(Color.parseColor("#1791d8"));
            txtCircularDate.setBackgroundColor(Color.parseColor("#1791d8"));
            imgBulletCircular.setImageResource(R.drawable.blue_bulletpoint_withline);
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}

